import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      1번에서 N번 컴퓨터를 연결해야 하는데, 그 비용은 경로상의 케이블 최대 값만 청구된다고 함
 *      K개의 인터넷 선을 공짜로 연결해줄 때, 최소 비용은 얼마가 나오는지 계산하는 문제
 *
 *      풀이 방법은 두 가지
 *
 *      1. 이분 탐색 + 다익스트라
 *          이분 탐색을 위한 질의는 "특정 가격 이상의 케이블은 전부 무료로 처리한다고 할 때, N번 컴퓨터까지 K개의 인터넷 선만 이용해서 도달할 수 있는가?"
 *          그리고 이 질의를 해결하기 위해서 무료 처리 횟수를 구하는 다익스트라를 사용함
 *          다익스트라 최소 비용 저장 배열은, 케이블 비용이 아닌 무료 처리 횟수를 저장함
 *
 *      2. 무료 처리 횟수마다 최소 비용을 저장하는 다익스트라
 *          최소 비용을 저장하는 다익스트라인 건 같지만,
 *          0~K 까지 추가로 저장하는 2차원 배열로 만들어서
 *          K개의 케이블을 무료 처리하면서 N번 컴퓨터까지 도달하는 최소비용을 구하는 방법
 *
 * 시간 복잡도:
 *     가격을 M이라고 할 때,
 *     1번 방법: 이분 탐색 logM 에 다익스트라 PlogN 이므로, O(P * logN * logM)
 *     2번 방법: 다익스트라밖에 없지만 우선순위큐에 각 노드가 K번만큼 들어가므로, O(P * log(N * K))
 *
 *     2번 방법이 조금 더 효율적이고, 실제로 결과도 2번 방법이 조금 더 빠르다.
 */

class Route implements Comparable<Route> {

    public int currentCpu;
    public int currentCount;

    public Route(int currentCpu, int currentCount) {
        this.currentCpu = currentCpu;
        this.currentCount = currentCount;
    }

    @Override
    public int compareTo(Route other) {
        return Integer.compare(this.currentCount, other.currentCount);
    }
}

class Path implements Comparable<Path> {

    public int currentCpu;
    public int currentFee;
    public int freeLeft;

    public Path(int currentCpu, int currentFee, int freeLeft) {
        this.currentCpu = currentCpu;
        this.currentFee = currentFee;
        this.freeLeft = freeLeft;
    }

    @Override
    public int compareTo(Path other) {
        return Integer.compare(this.currentFee, other.currentFee);
    }
}

public class Main {

    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static List<Integer> inputNumberList(int numberCount) throws Exception {
        List<Integer> numbers = new ArrayList<>();
        for (int idx = 0; idx < numberCount; idx++) {
            numbers.add(Integer.parseInt(br.readLine().strip()));
        }
        return numbers;
    }

    private static int[][] inputEdges(int edgeCount, int argCount) throws Exception {
        int[][] result = new int[edgeCount][argCount];
        for (int edge = 0; edge < edgeCount; edge++) {
            tokenize();
            for (int arg = 0; arg < argCount; arg++) {
                result[edge][arg] = nextInt();
            }
        }
        return result;
    }

    private static List<List<int[]>> initializeGraph(int nodeCount, int[][] edges) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int idx = 0; idx < nodeCount; idx++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int nodeA = edge[0];
            int nodeB = edge[1];
            int cost = edge[2];
            graph.get(nodeA).add(new int[]{nodeB, cost});
            graph.get(nodeB).add(new int[]{nodeA, cost});
        }

        return graph;
    }

    // 가격 상한선을 넘는 케이블은 무료로 처리할 때, N번 컴퓨터까지 도달하는데 사용하는 무료 케이블의 개수
    private static int getFreeLineCount(int feeLimit, List<List<int[]>> feeGraph, int computerCount, int INF) {
        int[] freeLineCounts = new int[computerCount];
        Arrays.fill(freeLineCounts, INF);
        freeLineCounts[1] = 0;

        PriorityQueue<Route> routeQueue = new PriorityQueue<>();
        routeQueue.offer(new Route(1, 0));

        while (!routeQueue.isEmpty()) {

            Route curRoute = routeQueue.poll();
            if (freeLineCounts[curRoute.currentCpu] < curRoute.currentCount) {
                continue;
            }

            for (int[] cableFee : feeGraph.get(curRoute.currentCpu)) {
                int nextCpu = cableFee[0];
                int nextFee = cableFee[1];
                int nextCount = curRoute.currentCount;
                if (nextFee > feeLimit) {
                    nextCount++;
                }

                if (freeLineCounts[nextCpu] > nextCount) {
                    freeLineCounts[nextCpu] = nextCount;
                    routeQueue.offer(new Route(nextCpu, nextCount));
                }
            }
        }

        return freeLineCounts[freeLineCounts.length - 1];
    }

    private static boolean canConnectWithFeeLimit(int feeLimit, List<List<int[]>> feeGraph, int computerCount, int freeCableCount) {
        int INF = 1000000001;
        int requiredFreeLineCount = getFreeLineCount(feeLimit, feeGraph, computerCount, INF);
        return requiredFreeLineCount <= freeCableCount;
    }

    private static String solve(int computerCount, int freeCableCount, int[][] cableFees) {
        computerCount++;
        List<List<int[]>> feeGraph = initializeGraph(computerCount, cableFees);

        int INF = Arrays.stream(cableFees)
                .mapToInt(fee -> fee[2])
                .reduce(0, Math::max) + 1;
        int lft = -1;
        int rgt = INF;
        while (lft + 1 < rgt) {
            int mid = (lft + rgt) / 2;
            if (canConnectWithFeeLimit(mid, feeGraph, computerCount, freeCableCount)) {
                rgt = mid;
            } else {
                lft = mid;
            }
        }
        if (rgt == INF) {
            rgt = -1;
        }
        return Integer.toString(rgt);
    }

    // 풀이 방법 2

    private static int getMinimalCost(List<List<int[]>> feeGraph, int computerCount, int freeCableCount, int INF)
            throws Exception {

        int initComputer = 1;
        int goalComputer = computerCount - 1;

        int[][] feesByFreeCable = new int[freeCableCount + 1][computerCount];
        for (int[] fees: feesByFreeCable) {
            Arrays.fill(fees, INF);
        }
        feesByFreeCable[freeCableCount][initComputer] = 0;

        PriorityQueue<Path> pathQueue = new PriorityQueue<>();
        pathQueue.offer(new Path(initComputer, 0, freeCableCount));

        while (!pathQueue.isEmpty()) {

            Path curPath = pathQueue.poll();
            if (feesByFreeCable[curPath.freeLeft][curPath.currentCpu] < curPath.currentFee) {
                continue;
            }

            for (int[] cableFee : feeGraph.get(curPath.currentCpu)) {
                int nextCpu = cableFee[0];
                int nextFee = Math.max(curPath.currentFee, cableFee[1]);

                if (feesByFreeCable[curPath.freeLeft][nextCpu] > nextFee) {
                    feesByFreeCable[curPath.freeLeft][nextCpu] = nextFee;
                    pathQueue.offer(new Path(nextCpu, nextFee, curPath.freeLeft));
                }

                int nextFreeLeft = curPath.freeLeft - 1;
                if (nextFreeLeft >= 0 && feesByFreeCable[nextFreeLeft][nextCpu] > curPath.currentFee) {
                    feesByFreeCable[nextFreeLeft][nextCpu] = curPath.currentFee;
                    pathQueue.offer(new Path(nextCpu, curPath.currentFee, nextFreeLeft));
                }
            }
        }

        return Arrays.stream(feesByFreeCable)
                .mapToInt(fees -> fees[goalComputer])
                .reduce(INF, Math::min);
    }

    private static String solve2(int computerCount, int freeCableCount, int[][] cableFees) throws Exception {
        computerCount++;
        List<List<int[]>> feeGraph = initializeGraph(computerCount, cableFees);
        int INF = 1000000001;

        int result = getMinimalCost(feeGraph, computerCount, freeCableCount, INF);
        if (result == INF) {
            result = -1;
        }
        return Integer.toString(result);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int P = nextInt();
            int K = nextInt();
            int argCount = 3;
            int[][] edges = inputEdges(P, argCount);

            String answer = solve(N, K, edges);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

