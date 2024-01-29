/**
 * 접근 :
 *      1. 0, 0 좌표에 1찍어둠
 *      2. 쭉 순회하면서 현재 좌표에 오는 길이 있으면 다음 좌표에 현재까지 경로 수를 찍음
 */

package week11.n3_점프;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        long[][] dp = new long[N][N];

        dp[0][0] = 1;

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                int num = Integer.parseInt(st.nextToken());
                if(num == 0) continue;
                if(i + num < N && dp[i][j] >= 1) {
                    dp[i + num][j] += dp[i][j];
                }
                if(j + num < N && dp[i][j] >= 1) {
                    dp[i][j + num] += dp[i][j];
                }
            }
        }

        System.out.println(dp[N-1][N-1]);

    }

}

