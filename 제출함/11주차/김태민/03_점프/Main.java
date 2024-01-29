import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *
 *  시간 복잡도:
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

    private static String solve(int[][] numbers) {
        int side = numbers.length;
        int[][] jumpDp = new int[side][side];
        jumpDp[0][0] = 1;
        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                int jumpDistance = numbers[row][col];
                if (jumpDistance == 0) {
                    continue;
                }
                if (row + jumpDistance < side) {
                    jumpDp[row + jumpDistance][col] += jumpDp[row][col];
                }
                if (col + jumpDistance < side) {
                    jumpDp[row][col + jumpDistance] += jumpDp[row][col];
                }
            }
        }
//        for (int[] row : jumpDp) {
//            System.out.println(Arrays.toString(row));
//        }
        int result = jumpDp[side - 1][side - 1];
        return Integer.toString(result);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[][] numbers = inputNumberMatrix(N, N);
            String answer = solve(numbers);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
