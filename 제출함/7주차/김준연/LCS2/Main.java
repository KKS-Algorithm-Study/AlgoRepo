/**
 * 접근 :
 *      1. LCS의 결과를 출력
 *      2. 계산된 LCS를 백트래킹하며 문자를 기록
 *      3. 쌓인 문자들을 반대로출력
 */

package week7.LCS2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        String str = st.nextToken();

        st = new StringTokenizer(br.readLine());

        String str2 = st.nextToken();

        int len = str.length();
        int len2 = str2.length();
        int[][] dp = new int[len2+1][len+1];

        for(int i = 1; i <= len2; i++) {
            for(int j = 1; j <= len; j++) {
                if(str.charAt(j-1) == str2.charAt(i-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                }
                else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }

        int max = dp[len2][len];
        System.out.println(max);

        Stack<Character> stack = new Stack<>();

        while(max > 0) {
            if(dp[len2][len] == max && str2.charAt(len2-1) == str.charAt(len-1)) {
                stack.push(str2.charAt(len2-1));
                max--;
                len2--;
                len--;
            }
            else {
                if(dp[len2-1][len] == dp[len2][len]) {
                    len2--;
                }
                else len--;
            }
        }

        while(!stack.isEmpty()) {
            System.out.print(stack.pop());
        }

    }
}