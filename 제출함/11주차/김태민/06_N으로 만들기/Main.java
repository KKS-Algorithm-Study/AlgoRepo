import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *
 *      왼쪽이나 오른쪽에 숫자를 더해 목표 숫자가 되게끔 하는 경우의 수 ==
 *      목표 숫자에서 왼쪽이나 오른쪽 숫자를 빼 빈 숫자가 되게 하는 것
 *
 *      숫자에 도달하는 과정이 동일하면 동일한 경우로 센다는 것은,
 *      왼쪽을 지운 숫자와 오른쪽을 지운 숫자가 동일하면 둘 중 하나 가지치기 가능하다는 것.
 *
 *      수가 0으로 시작할 수 있다는 조건은
 *      101을 만들때 1 -> 01 -> 101 이 경우도 허락한다는 것이다.
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


    private static int dfsCountCases(int number, int divisor) {
        if (divisor == 1) {
            return 1;
        }
        int result = 0;

        // 왼쪽 숫자 지우기
        result += dfsCountCases(number % divisor, divisor / 10);

        // 가지 치기
        if (number % divisor == number / 10) {
            return result;
        }

        // 오른쪽 숫자 지우기
        result += dfsCountCases(number / 10, divisor / 10);
        return result;
    }

    private static int countCases(int number) {
        int divisor = (int) Math.pow(10, Integer.toString(number).length() - 1);
        return dfsCountCases(number, divisor);
    }

    private static String solve(int number) {
        int result = countCases(number);
        return Integer.toString(result);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            String answer = solve(N);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
