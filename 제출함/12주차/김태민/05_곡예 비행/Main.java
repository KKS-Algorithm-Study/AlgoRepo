import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      오른쪽 위로 향하는 DP와
 *      왼쪽 위로 향하는 DP를 만들어
 *      두 칸의 합을 구하면 된다
 *
 *  주의할 점:
 *      INF 크기 주의
 *
 *  시간 복잡도:
 *      O(N * M)
 *
 */
public class Main {

    static int INF = 1000000001;
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static int[][] inputMatrixNumbers(int rowCount, int colCount) throws Exception {
        int[][] result = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            result[row] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return result;
    }

    private static int[][] getAscendScoreDp(int[][] scores) {
        int height = scores.length;
        int width = scores[0].length;
        int[][] result = new int[height][width];
        for (int[] row : result) {
            Arrays.fill(row, -INF);
        }
        result[height - 1][0] = scores[height - 1][0];
        for (int col = 0; col < width; col++) {
            for (int row = height - 1; row >= 0; row--) {
                int fromLeft = col == 0 ? -INF : result[row][col - 1];
                int fromDown = row == height - 1 ? -INF : result[row + 1][col];
                int maxScore = scores[row][col] + Math.max(fromLeft, fromDown);
                result[row][col] = Math.max(result[row][col], maxScore);
            }
        }
        return result;
    }

    private static int[][] getDescendScoreDp(int[][] scores) {
        int height = scores.length;
        int width = scores[0].length;
        int[][] result = new int[height][width];
        for (int[] row : result) {
            Arrays.fill(row, -INF);
        }
        result[height - 1][width - 1] = scores[height - 1][width - 1];
        for (int col = width - 1; col >= 0; col--) {
            for (int row = height - 1; row >= 0; row--) {
                int fromRight = col == width - 1 ? -INF : result[row][col + 1];
                int fromDown = row == height - 1 ? -INF : result[row + 1][col];
                int maxScore = scores[row][col] + Math.max(fromRight, fromDown);
                result[row][col] = Math.max(result[row][col], maxScore);
            }
        }
        return result;
    }

    private static String solve(int[][] scores) {
        int[][] ascendScoreDp = getAscendScoreDp(scores);
        int[][] descendScoreDp = getDescendScoreDp(scores);

        int result = -INF;
        for (int row = 0; row < scores.length; row++) {
            for (int col = 0; col < scores[0].length; col++) {
                result = Math.max(result, ascendScoreDp[row][col] + descendScoreDp[row][col]);
            }
        }
        return Integer.toString(result);
    }


    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int[][] scores = inputMatrixNumbers(N, M);
            String answer = solve(scores);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
