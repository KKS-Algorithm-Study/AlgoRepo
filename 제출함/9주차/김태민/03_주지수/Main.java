import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      최대 숫자 100인 행렬이 있고, 쿼리로 시작 지점, 끝 지점이 주어질 때,
 *      각 쿼리가 지정하는 사각형에 포함된 숫자를 모두 더해 출력하는 문제
 *
 *      만약 모든 쿼리를 이중포문으로 처리한다고 하면
 *      연산 횟수는 100000 * 1024 * 1024 = 104,857,600,000 으로 시간 초과가 나게 된다.
 *
 *      이 문제는 행렬의 누적합을 미리 구해놓고, 쿼리는 누적합에서 덧셈 뺄셈으로 계산해 풀 수 있는데,
 *      예를 들어, 아래의 그림에서 d의 영역의 합만 구하고 싶다고 하자.
 *      a a a b b
 *      a a a b b
 *      c c c d d
 *      c c c d d
 *      c c c d d
 *      먼저, 모든 row와 col에 대해 (0, 0) 부터 (row, col) 까지의 누적합은
 *          이중 포문으로 가로 누적합, 이중 포문으로 원래 누적합에 대한 세로 누적합
 *          이렇게 이중 포문 두 번으로 영역 누적합을 구해 놓는다.
 *      다음, d의 영역의 합을 구해야 하는데,
 *          a 영역의 누적합을 A라고 하고,
 *          a + b 영역의 누적합은 B,
 *          a + c 영역의 누적합은 C,
 *          a + b + c + d 영역의 누적합을 D라고 하자.
 *          그러면 d 영역의 합은 D + A - B - C 를 계산하면 된다.
 *
 *  시간 복잡도:
 *      누적합을 계산하는데 O(N*M)이 들고,
 *      쿼리를 처리하는 데 O(1)이므로,
 *      최종 시간 복잡도는 O(N*M)이다.
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
            result[row] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return result;
    }
    
    private static int[][] makeCumulativeMatrix(int[][] numbers) {
        int[][] result = new int[numbers.length + 1][numbers[0].length + 1];
        for (int row = 0; row < numbers.length; row++) {
            for (int col = 0; col < numbers[0].length; col++) {
                result[row+1][col+1] = result[row+1][col] + numbers[row][col];
            }
        }
        for (int row = 0; row < numbers.length; row++) {
            for (int col = 0; col < numbers[0].length; col++) {
                result[row+1][col+1] += result[row][col+1];
            }
        }
        return result;
    }

    private static String solve(int[][] numbers, int[][] queries) {
        int[][] cumulativeMatrix = makeCumulativeMatrix(numbers);
//        for (int[] row : cumulativeMatrix) {
//            System.out.println(Arrays.toString(row));
//        }
        StringJoiner result = new StringJoiner("\n");
        for (int[] query : queries) {
            int rowStart = query[0] - 1;
            int colStart = query[1] - 1;
            int rowEnd = query[2];
            int colEnd = query[3];

            int sum = 0;
            sum += cumulativeMatrix[rowEnd][colEnd];
            sum -= cumulativeMatrix[rowStart][colEnd];
            sum -= cumulativeMatrix[rowEnd][colStart];
            sum += cumulativeMatrix[rowStart][colStart];
            result.add(Integer.toString(sum));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int[][] numbers = inputMatrix(N, M);
            tokenize();
            int K = nextInt();
            int queryLength = 4;
            int[][] queries = inputMatrix(K, queryLength);

            String answer = solve(numbers, queries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}