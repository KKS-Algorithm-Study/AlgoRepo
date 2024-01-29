import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  문제 풀이 방법:
 *
 *  시간 복잡도:
 */
public class Main {

    private static class Status implements Comparable<Status> {
        int cost;
        int node;

        public Status(int cost, int node) {
            this.cost = cost;
            this.node = node;
        }

        @Override
        public int compareTo(Status other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    static final int INF = 100_000_001;
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static int[][] inputNumberMatrix(int rowCount, int colCount) throws Exception {
        int[][] result = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            tokenize();
            for (int col = 0; col < colCount; col++) {
                result[row][col] = nextInt();
            }
        }
        return result;
    }

    private static List<Map<Integer, Integer>> makeGraph(int[][] edges, int nodeCount) {
        List<Map<Integer, Integer>> result = IntStream.rangeClosed(0, nodeCount)
                .mapToObj(i -> new HashMap<Integer, Integer>())
                .collect(Collectors.toList());
        for (int idx = 0; idx < edges.length; idx++) {
            int nodeA = edges[idx][0];
            int nodeB = edges[idx][1];
            int cost = edges[idx][2];
            result.get(nodeA).put(nodeB, Math.min(cost, result.get(nodeA).getOrDefault(nodeB, Integer.MAX_VALUE)));
            result.get(nodeB).put(nodeA, Math.min(cost, result.get(nodeB).getOrDefault(nodeA, Integer.MAX_VALUE)));
        }
        return result;
    }

    private static String solve(int[][] edges, int nodeCount, int initNode, int endNode) {
        List<Map<Integer, Integer>> graph = makeGraph(edges, nodeCount);
        int[] minimumCosts = IntStream.rangeClosed(0, nodeCount)
                .map(i -> INF)
                .toArray();
        minimumCosts[initNode] = 0;
        PriorityQueue<Status> dijkQueue = new PriorityQueue<>();
        dijkQueue.offer(new Status(0, initNode));
        while (!dijkQueue.isEmpty()) {
            Status currStatus = dijkQueue.poll();
            if (minimumCosts[currStatus.node] < currStatus.cost) {
                continue;
            }
            for (Entry<Integer, Integer> edge : graph.get(currStatus.node).entrySet()) {
                int nextNode = edge.getKey();
                int nextCost = edge.getValue() + currStatus.cost;
                if (minimumCosts[nextNode] <= nextCost) {
                    continue;
                }
                minimumCosts[nextNode] = nextCost;
                dijkQueue.offer(new Status(nextCost, nextNode));
            }
        }
        return Integer.toString(minimumCosts[endNode]);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int[][] edges = inputNumberMatrix(M, 3);
            tokenize();
            int S = nextInt();
            int T = nextInt();
            String answer = solve(edges, N, S, T);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
