import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      방향 그래프가 있을 때, 한 정점에서 다른 정점으로 가는 길이 있으면 1, 없으면 0으로 표시하는 문제.
 *
 *      정점의 총 갯수가 100개 이하, 플로이드 워셜은 O(N ** 3) => 1,000,000 이 나오므로, 플로이드 워셜로 풀이 가능.
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

    private static int[][] inputMatrix(int rowCount, int colCount) throws Exception {
        int[][] result = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            tokenize();
            for (int col = 0; col < colCount; col++) {
                result[row][col] = nextInt();
            }
        }
        return result;
    }

    private static void checkAllEdges(int[][] graph) {
        int nodeCount = graph.length;
        for (int mid = 0; mid < nodeCount; mid++) {
            for (int stt = 0; stt < nodeCount; stt++) {
                for (int end = 0; end < nodeCount; end++) {
                    if (graph[stt][mid] == 1 && graph[mid][end] == 1) {
                        graph[stt][end] = 1;
                    }
                }
            }
        }
    }

    private static String stringifyGraph(int[][] graph) {
        StringJoiner result = new StringJoiner("\n");
        int nodeCount = graph.length;
        for (int row = 0; row < nodeCount; row++) {
            StringJoiner rowResult = new StringJoiner(" ");
            for (int col = 0; col < nodeCount; col++) {
                rowResult.add(Integer.toString(graph[row][col]));
            }
            result.add(rowResult.toString());
        }
        return result.toString();
    }

    private static String solve(int[][] graph) {
        checkAllEdges(graph);
        return stringifyGraph(graph);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[][] matrix = inputMatrix(N, N);

            String answer = solve(matrix);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
