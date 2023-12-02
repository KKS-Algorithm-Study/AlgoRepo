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
 *      1번 정점부터 N번 정점까지 가는 최단 경로를 먼저 구하고,
 *      그 최단 경로에 포함된 도로를 하나씩 막아보며 얼마나 지연시켰는지 계산해 그 최댓값을 출력하는 문제
 *
 *      정점이 총 1000개 이므로 최단 경로를 구하는 알고리즘은 다익스트라를 사용해야 한다.
 *
 * 시간 복잡도:
 *      처음 최단 경로를 찾을 때 시간복잡도 => O(ElogV)
 *      최단 경로에 포함된 길을 하나씩 막으며 다시 최단경로를 찾을 때 시간복잡도 => O(VElogV)
 */

class Route implements Comparable<Route> {

    public int currentNode;
    public int currentCost;
    public int[] currentRoute;

    public Route(int currentNode, int currentCost, int[] currentRoute) {
        this.currentNode = currentNode;
        this.currentCost = currentCost;
        this.currentRoute = currentRoute;
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

    private static void initialize(List<List<Integer>> graph, int[][] distances, int nodeCount, int[][] edges,
                                   int INF) {
        for (int idx = 0; idx < nodeCount; idx++) {
            graph.add(new ArrayList<>());
        }

        for (int[] distance : distances) {
            Arrays.fill(distance, INF);
        }

        for (int[] edge : edges) {
            int nodeA = edge[0];
            int nodeB = edge[1];
            int cost = edge[2];
            graph.get(nodeA).add(nodeB);
            graph.get(nodeB).add(nodeA);
            distances[nodeA][nodeB] = cost;
            distances[nodeB][nodeA] = cost;
        }
    }

    private static Route doDijkstra(int startingNode, int endingNode, boolean needsRoute, List<List<Integer>> graph,
                                    int[][] distances, int nodeCount, int INF) {
        int[] costs = new int[nodeCount];
        Arrays.fill(costs, INF);
        costs[startingNode] = 0;

        PriorityQueue<Route> routeQueue = new PriorityQueue<>();
        int[] route = null;
        if (needsRoute) {
            route = new int[]{startingNode};
        }
        routeQueue.offer(new Route(startingNode, 0, route));

        while (!routeQueue.isEmpty()) {

            Route curRoute = routeQueue.poll();
            if (costs[curRoute.currentNode] < curRoute.currentCost) {
                continue;
            }

            if (curRoute.currentNode == endingNode) {
                return curRoute;
            }

            for (int nxtNode : graph.get(curRoute.currentNode)) {
                int nxtCost = curRoute.currentCost + distances[curRoute.currentNode][nxtNode];
                if (costs[nxtNode] <= nxtCost) {
                    continue;
                }
                costs[nxtNode] = nxtCost;
                int[] nxtRoute = null;
                if (needsRoute) {
                    nxtRoute = Arrays.copyOf(curRoute.currentRoute, curRoute.currentRoute.length + 1);
                    nxtRoute[nxtRoute.length - 1] = nxtNode;
                }
                routeQueue.offer(new Route(nxtNode, nxtCost, nxtRoute));
            }
        }

        return new Route(endingNode, INF, new int[0]);
    }

    private static String solve(int nodeCount, int[][] roads) {
        int startingNode = 1;
        int endingNode = nodeCount;
        nodeCount++;
        int INF = 10000001;

        // 지도 정보 초기화
        List<List<Integer>> graph = new ArrayList<>();
        int[][] distances = new int[nodeCount][nodeCount];
        initialize(graph, distances, nodeCount, roads, INF);

        // 최적의 경로 찾기
        Route bestRoute = doDijkstra(startingNode, endingNode, true, graph, distances, nodeCount, INF);

        int delayedTime = 0;
        // 최적의 경로에 포함된 길 하나씩 막고, 다시 경로를 찾아 지연된 시간 계산하기
        for (int idx = 1; idx < bestRoute.currentRoute.length; idx++) {
            int nodeFrom = bestRoute.currentRoute[idx - 1];
            int nodeTo = bestRoute.currentRoute[idx];
            int distanceOriginal = distances[nodeFrom][nodeTo];

            // 길 막고
            distances[nodeFrom][nodeTo] = INF;
            distances[nodeTo][nodeFrom] = INF;
            // 최단거리 구하고 최대 지연 시간 갱신하고
            Route blockedRoute = doDijkstra(startingNode, endingNode, true, graph, distances, nodeCount, INF);
            if (blockedRoute.currentCost == INF) {
                return "-1";
            }
            delayedTime = Math.max(delayedTime, blockedRoute.currentCost - bestRoute.currentCost);
            // 길 뚫고
            distances[nodeFrom][nodeTo] = distanceOriginal;
            distances[nodeTo][nodeFrom] = distanceOriginal;
        }

        return Integer.toString(delayedTime);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int argCount = 3;
            int[][] edges = inputEdges(M, argCount);

            String answer = solve(N, edges);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
