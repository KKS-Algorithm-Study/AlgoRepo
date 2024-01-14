/**
 * 접근 :
 * 1. 1번과 2번의  LCS에 3번의 LCS 길이
 * 2. 2번과 3번의 LCS에 1번의 LCS 길이
 * 3. 1번과 3번의 LCS에 2번의 LCS 길이
 * 4. 위 세가지 중 가장 큰 것
 */

package week9.n8_LCS3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        String a = st.nextToken();
        st = new StringTokenizer(br.readLine());
        String b = st.nextToken();
        st = new StringTokenizer(br.readLine());
        String c = st.nextToken();

        int max = 0;

        System.out.println(LCS(a, b, c));

    }

    static int LCS(String str, String str2, String str3) {
        int len = str.length();
        int len2 = str2.length();
        int len3 = str3.length();
        int[][][] dp = new int[len + 1][len2 + 1][len3 + 1];

        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= len2; j++) {
                for (int k = 1; k <= len3; k++) {
                    if (str.charAt(i - 1) == str2.charAt(j - 1) && str.charAt(i - 1) == str3.charAt(k - 1)) {
                        dp[i][j][k] = dp[i - 1][j - 1][k - 1] + 1;
                    } else {
                        int max = 0;
                        max = Math.max(max, dp[i - 1][j][k]);
                        max = Math.max(max, dp[i][j - 1][k]);
                        max = Math.max(max, dp[i][j][k - 1]);

                        dp[i][j][k] = max;
                    }
                }

            }
        }

        return dp[len][len2][len3];
    }
}
