import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      시작 정점과 가중치 간선 정보가 주어지면, 시작 정점에서 다른 모든 정점으로 가는 최단 경로 값을 한 줄에 하나씩 출력하는 문제.
 *
 *      정점의 갯수가 20000개이므로, 플로이드 워셜은 불가능, 따라서 다익스트라로 접근해야 함.
 *
 * 시간 복잡도:
 *      다익스트라 시간복잡도는 O(ElogV)이다.
 *
 *      다익스트라에서 각 간선이 탐색 되는 건 정확히 한 번씩 == O(E) 일어나는데,
 *      그 간선이 최단 경로라면 heap 자료 구조에 넣는 작업 == O(logE) 이 일어난다.
 *      즉, 간선마다 한 번씩 heap 자료 구조에 넣으므로, O(ElogE) 이다.
 *
 *      여기까지만 보면 O(ElogE)인데, 한 단계 더 나아가보자.
 *
 *      간선의 갯수 E의 최대값은 모든 정점이 연결된 경우인 V * (V-1)이다.
 *      즉, E의 최악의 경우는 V**2 - V 이고, 이는 V**2 보다 무조건 작다.
 *      이를 log 안의 E와 치환하면 O(ElogV**2) 인데,
 *      logV**2 = 2logV 이므로, O(2ElogV)가 되고,
 *      시간복잡도의 상수항을 제거해 O(ElogV)가 나오게 된다.
 *
 *      참고: https://wooono.tistory.com/397
 */

class Edge {
    public int nodeFrom;
    public int nodeTo;
    public int cost;

    public Edge (int nodeFrom, int nodeTo, int cost) {
        this.nodeFrom = nodeFrom;
        this.nodeTo = nodeTo;
        this.cost = cost;
    }
}

class Route implements Comparable<Route>{

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

    private static List<List<Edge>> initializeGraph(int nodeCount, int[][] edges) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int idx = 0; idx < nodeCount; idx++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge: edges) {
            int nodeFrom = edge[0];
            int nodeTo = edge[1];
            int cost = edge[2];
            graph.get(nodeFrom).add(new Edge(nodeFrom, nodeTo, cost));
        }

        return graph;
    }

    private static int[] doDijkstra(List<List<Edge>> graph, int startingNode, int nodeCount, int INF) {
        int[] costs = new int[nodeCount];
        Arrays.fill(costs, INF);
        costs[startingNode] = 0;

        PriorityQueue<Route> routeQueue = new PriorityQueue<>();
        routeQueue.offer(new Route(startingNode, 0));

        while(!routeQueue.isEmpty()) {

            Route curRoute = routeQueue.poll();
            if (costs[curRoute.currentNode] < curRoute.currentCost) {
                continue;
            }

            for (Edge edge: graph.get(curRoute.currentNode)) {
                int nxtNode = edge.nodeTo;
                int nxtCost = curRoute.currentCost + edge.cost;
                if (costs[nxtNode] <= nxtCost) {
                    continue;
                }
                costs[nxtNode] = nxtCost;
                routeQueue.offer(new Route(nxtNode, nxtCost));
            }
        }

        return costs;
    }

    private static String stringifyCosts(int[] costs, int INF) {
        StringJoiner result = new StringJoiner("\n");
        for (int idx = 1; idx < costs.length; idx++) {
            int cost = costs[idx];
            if (cost == INF) {
                result.add("INF");
                continue;
            }
            result.add(Integer.toString(cost));
        }
        return result.toString();
    }

    private static String solve(int nodeCount, int[][] edges, int startingNode) {
        nodeCount++;
        int INF = 10000001;
        List<List<Edge>> graph = initializeGraph(nodeCount, edges);
        int[] costs = doDijkstra(graph, startingNode, nodeCount, INF);

        return stringifyCosts(costs, INF);
    }


    public static void main(String[] args) {
        try {
            tokenize();
            int V = nextInt();
            int E = nextInt();
            tokenize();
            int K = nextInt();
            int argCount = 3;
            int[][] edges = inputEdges(E, argCount);

            String answer = solve(V, edges, K);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
