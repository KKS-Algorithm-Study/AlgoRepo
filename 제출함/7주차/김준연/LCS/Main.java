package week7.LCS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        System.out.println(dp[len2][len]);
    }
}
