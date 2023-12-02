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
 *      1번 정점부터 N번 정점까지 가는 최단 경로를 구하되, 두 개의 정점을 지나는 경로의 최단 거리를 출력하는 문제
 *
 *      정점이 총 800개 이므로 다익스트라를 사용해야 한다.
 *
 *      시작 정점부터 방문 정점들을 지나 도착 정점까지 가는 최단 거리는
 *      (시작 ~ 방문1) + (방문1 ~ 방문2) + (방문2 ~ 도착)
 *      위 세 구간 각각의 최단 거리를 구해 더해주는 방식으로 구할 수 있다.
 *
 *      하지만 방문 정점을 지나는 순서에 따라 최단 거리는 바뀔 수 있으므로,
 *      루트 1: 시작 - 방문1 - 방문2 - 도착
 *      루트 2: 시작 - 방문2 - 방문1 - 도착
 *
 *      두 루트의 최단 거리를 구하고, 둘 중 최소값을 출력해야 한다.
 *
 * 시간 복잡도:
 *      다익스트라가 3번 일어나므로,
 *      O(ElogV)
 */

class Route implements Comparable<Route> {

    public int currentNode;
    public int currentCost;

    public Route(int currentNode, int currentCost) {
        this.currentNode = currentNode;
        this.currentCost = currentCost;
    }

    @Override
    public int compareTo(Route other) {
        return Integer.compare(this.currentCost, other.currentCost);
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

    private static int[] doDijkstra(int startingNode, List<List<int[]>> graph, int nodeCount, int INF) {
        int[] costs = new int[nodeCount];
        Arrays.fill(costs, INF);
        costs[startingNode] = 0;

        PriorityQueue<Route> routeQueue = new PriorityQueue<>();
        routeQueue.offer(new Route(startingNode, 0));

        while (!routeQueue.isEmpty()) {

            Route curRoute = routeQueue.poll();
            if (costs[curRoute.currentNode] < curRoute.currentCost) {
                continue;
            }

            for (int[] edge : graph.get(curRoute.currentNode)) {
                int nxtNode = edge[0];
                int nxtCost = curRoute.currentCost + edge[1];
                if (costs[nxtNode] <= nxtCost) {
                    continue;
                }
                costs[nxtNode] = nxtCost;
                routeQueue.offer(new Route(nxtNode, nxtCost));
            }
        }

        return costs;
    }

    private static String solve(int nodeCount, int[][] edges, int visit1, int visit2) {
        int startingNode = 1;
        int endingNode = nodeCount;
        nodeCount++;
        int INF = 10000000;
        List<List<int[]>> graph = initializeGraph(nodeCount, edges);

        int[] costFromStart = doDijkstra(startingNode, graph, nodeCount, INF);
        int[] costFromVisit1 = doDijkstra(visit1, graph, nodeCount, INF);
        int[] costFromVisit2 = doDijkstra(visit2, graph, nodeCount, INF);

        // 시작 -> 1번 -> 2번 -> 끝
        int routeFor1To2 = costFromStart[visit1] + costFromVisit1[visit2] + costFromVisit2[endingNode];
        // 시작 -> 2번 -> 1번 -> 끝
        int routeFor2To1 = costFromStart[visit2] + costFromVisit2[visit1] + costFromVisit1[endingNode];

        int result = Math.min(routeFor1To2, routeFor2To1);
        if (result > INF) {
            result = -1;
        }
        return Integer.toString(result);
    }


    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int E = nextInt();
            int argCount = 3;
            int[][] edges = inputEdges(E, argCount);
            tokenize();
            int v1 = nextInt();
            int v2 = nextInt();

            String answer = solve(N, edges, v1, v2);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
