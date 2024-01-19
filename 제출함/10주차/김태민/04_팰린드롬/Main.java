import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      수열과 그 숫자들의 왼쪽 오른쪽 인덱스가 주어지면
 *      왼쪽과 오른쪽 인덱스 사이의 수열이 팰린드롬인지 아닌지 출력하는 문제
 *
 *      수열의 크기가 2000, 질의 갯수가 100000이기 때문에
 *      질의 하나마다 수열을 뽑아 팰린드롬인지 계산하는 이중 반복문을 사용하면
 *      최대 연산 횟수는 200,000,000번으로 시간 제한 0.5초 내로 풀 수 없다.
 *
 *      팰린드롬은 이미 팰린드롬인 수열에서 양 끝에 같은 수를 더해 만들 수 있으므로,
 *      한 인덱스를 잡고 왼쪽 오른쪽만 확인해, 같은 숫자가 아니라면 더 이상 확인하지 않아도 된다.
 *
 *      따라서 쿼리를 처리할 2차원 boolean 배열을 배열[왼쪽 인덱스][오른쪽 인덱스] 형태로 만들고,
 *      각 인덱스에서 왼쪽 오른쪽으로 키워나가며 팰린드롬인지 아닌지 저장해놓고,
 *      쿼리는 그 배열을 참조해 처리하면 된다.
 *
 *  주의할 점:
 *      홀수개 수열과 짝수개 수열의 팰린드롬 모두 처리하는 걸 잊지 말자.
 *
 *  시간 복잡도:
 *      팰린드롬 확인하는 선처리 작업이 O(N**2)
 *      쿼리 처리가 O(M)이므로
 *      최종 시간 복잡도는 O(N**2)이다.
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
            result[row] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return result;
    }

    private static int[] inputNumberArray(int numberCount) throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static boolean[][] getPalindromeChecker(int[] numbers) {
        boolean[][] result = new boolean[numbers.length + 1][numbers.length + 1];
        for (int center = 0; center < numbers.length; center++) {
            result[center + 1][center + 1] = true;
            // 홀수개 확인
            int wingLimit = Math.min(center, numbers.length - center - 1);
            for (int wing = 1; wing <= wingLimit; wing++) {
                int leftWing = center - wing;
                int rightWing = center + wing;
                if (numbers[leftWing] != numbers[rightWing]) {
                    break;
                }
                result[leftWing + 1][rightWing + 1] = true;
            }

            // 짝수개 확인
            wingLimit = Math.min(center, numbers.length - center - 2);
            for (int wing = 0; wing <= wingLimit; wing++) {
                int leftWing = center - wing;
                int rightWing = center + wing + 1;
                if (numbers[leftWing] != numbers[rightWing]) {
                    break;
                }
                result[leftWing + 1][rightWing + 1] = true;
            }
        }

        return result;
    }

    private static String solve(int[] numbers, int[][] queries) {
        boolean[][] isSubstringPalindrome = getPalindromeChecker(numbers);

        for (boolean[] row: isSubstringPalindrome) {
            System.out.println(Arrays.toString(row));
        }

        StringJoiner result = new StringJoiner("\n");
        String YES = "1";
        String NO = "0";
        for (int[] query : queries) {
            int left = query[0];
            int right = query[1];
            if (isSubstringPalindrome[left][right]) {
                result.add(YES);
            } else {
                result.add(NO);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[] numbers = inputNumberArray(N);
            tokenize();
            int Q = nextInt();
            int[][] queries = inputNumberMatrix(Q, 2);
            String answer = solve(numbers, queries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
