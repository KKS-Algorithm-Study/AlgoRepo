import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      도시의 갯수와 버스 요금이 주어지면, 각 도시에서 다른 도시로 가는 최소 비용을 행렬 형태로 출력하는 문제.
 *
 *      도시의 총 갯수가 100개 이하, 플로이드 워셜은 O(N ** 3) => 1,000,000 이 나오므로, 플로이드 워셜로 풀이 가능.
 *
 * 주의해야할 점:
 *      시작 도시와 도착 도시를 연결하는 노선은 하나가 아닐 수 있다.
 *      -> 행렬을 초기화할 때 최솟값으로 받도록 해야함
 *
 *      버스 하나의 비용의 최댓값이 100,000
 *      -> 행렬 초기화시 무한대 값으로 100,000을 설정시, 총 비용은 100,000보다 클 수 있어 오류가 남
 *
 * 시간 복잡도:
 *      플로이드 워셜이므로 O(N ** 3)
 */

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

    private static int[][] makeWeightedGraph(int nodeCount, int[][] edges, int INF) {
        int[][] graph = new int[nodeCount][nodeCount];
        for (int[] row: graph) {
            Arrays.fill(row, INF);
        }

        for (int[] edge: edges) {
            int stt = edge[0] - 1;
            int end = edge[1] - 1;
            int cost = edge[2];
            graph[stt][end] = Math.min(graph[stt][end], cost);
        }

        return graph;
    }

    private static void checkAllEdges(int[][] graph) {
        int nodeCount = graph.length;
        for (int mid = 0; mid < nodeCount; mid++) {
            for (int stt = 0; stt < nodeCount; stt++) {
                for (int end = 0; end < nodeCount; end++) {
                    if (stt != end && graph[stt][end] > graph[stt][mid] + graph[mid][end]) {
                        graph[stt][end] = graph[stt][mid] + graph[mid][end];
                    }
                }
            }
        }
    }

    private static String stringifyGraph(int[][] graph, int changeFrom, int changeTo) {
        StringJoiner result = new StringJoiner("\n");
        int nodeCount = graph.length;
        for (int row = 0; row < nodeCount; row++) {
            StringJoiner rowResult = new StringJoiner(" ");
            for (int col = 0; col < nodeCount; col++) {
                if (graph[row][col] == changeFrom) {
                    graph[row][col] = changeTo;
                }
                rowResult.add(Integer.toString(graph[row][col]));
            }
            result.add(rowResult.toString());
        }
        return result.toString();
    }

    private static String solve(int busStopCount, int[][] costs) {
        int INF = 10000001;
        int[][] graph = makeWeightedGraph(busStopCount, costs, INF);
        checkAllEdges(graph);
        return stringifyGraph(graph, INF, 0);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            tokenize();
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
