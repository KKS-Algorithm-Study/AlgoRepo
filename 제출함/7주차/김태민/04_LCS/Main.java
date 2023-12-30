import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      최장 공통 부분 수열, 즉, 두 수열이 주어졌을 때 모두의 부분 수열이 되는 수열 중 가장 긴 것을 찾는 문제이다
 *
 *      2차원 DP로 접근해,
 *      dp[A 문자열 중 인덱스길이까지 사용할 경우][B 문자열 중 인덱스 길이까지 사용할 경우] = 이 경우의 최장 길이
 *      를 저장해 풀 수 있다.
 *      A 문자열과 B 문자열을 이중 포문으로 순회하면서,
 *          만약 A 문자열의 문자 == B 문자열의 문자라면, dp[i-1][j-1]의 경우에서 문자 하나가 추가된 경우이므로 +1
 *          만약 A 문자열의 문자 != B 문자열의 문자라면, dp[i-1][j] 와 dp[i][j-1] 중 더 큰 경우를 가져와 저장한다
 *
 *  시간 복잡도:
 *      LCS는 문자열의 길이만큼 이중 포문을 돌기 때문에
 *      O(N**2)이 나온다.
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

    private static String solve(String patternA, String patternB) {
        int[][] lcsDp = new int[patternA.length() + 1][patternB.length() + 1];
        for (int idxA = 0; idxA < patternA.length(); idxA++) {
            for (int idxB = 0; idxB < patternB.length(); idxB++) {
                if (patternA.charAt(idxA) == patternB.charAt(idxB)) {
                    lcsDp[idxA + 1][idxB + 1] = lcsDp[idxA][idxB] + 1;
                } else {
                    lcsDp[idxA + 1][idxB + 1] = Math.max(lcsDp[idxA][idxB + 1], lcsDp[idxA + 1][idxB]);
                }
            }
        }
        int result = lcsDp[patternA.length()][patternB.length()];
        return Integer.toString(result);
    }

    public static void main(String[] args) {
        try {
            String patternA = br.readLine().strip();
            String patternB = br.readLine().strip();

            String answer = solve(patternA, patternB);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

