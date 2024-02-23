/**
 * 접근 :
 *      1. dp
 *      2. 내 앞이 비었는지, 1개 마셨는지, 2개 마셨는지에 대한 기록이 필요함
 *      3. 1개마신 상황은 0개마신상황에서 + 하고, 2개 마신상황은 1개 마신상황에서 + 한다.
 *      4. 단, 0개 마신 상황은 앞의 세가지 상황 중 가장 큰 값을 계승한다.
 */

package week15.n3_포도주시식;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());

        int[][] dp = new int[n+1][3];
        Arrays.fill(dp[0], 0);
        for(int i = 1; i < n + 1; i++) {
            int num = Integer.parseInt(br.readLine());
            int max = 0;
            for(int j = 0; j < 3; j++) {
                max = Math.max(max, dp[i-1][j]);
            }
            dp[i][0] = max;
            dp[i][1] = dp[i-1][0] + num;
            dp[i][2] = dp[i-1][1] + num;
        }

        int max = 0;
        for(int i = 0; i < 3; i++) {
            max = Math.max(dp[n][i], max);
        }

        System.out.println(max);
    }
}
