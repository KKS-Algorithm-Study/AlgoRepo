import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *
 *      만약 row와 col이 정답이 되기까지 일일히 탐색하게 되면
 *      연산 횟수가 2 ** (15 * 2) = 약 10억 이므로 시간초과가 나게 된다.
 *
 *      사각형을 사분면으로 나눠가며 탐색하는 문제
 *      a a b b   0행 3열, 오른쪽 위 b 탐색 순서 == a 탐색 마침 + 오른쪽 위 a 탐색 순서
 *      a a b b   2행 1열, 오른쪽 위 c 탐색 순서 == a, b 탐색 마침 + 오른쪽 위 a 탐색 순서
 *      c c d d   2형 3열, 오른쪽 위 d 탐색 순서 == a, b, c 탐색 마침 + 오른쪽 위 a 탐색 순서
 *      c c d d   즉, 탐색 마침 갯수만큼 정답에 사각형 크기의 1/4씩 더하고, a 사각형을 다시 네 개로 나눠 똑같이 탐색하면 된다.
 *
 *  시간 복잡도:
 *      O(N)
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

    private static String solve(int size, int row, int col) {
        int result = 0;
        int length = (int) Math.pow(2, size);
        while (length >= 2) {
            int half = length / 2;
            if (row >= half) {
                result += length * half;
                row -= half;
            }
            if (col >= half) {
                result += half * half;
                col -= half;
            }
            length = half;
        }
        return Integer.toString(result);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int R = nextInt();
            int C = nextInt();
            String answer = solve(N, R, C);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
