/**
 * 접근 :
 *      1. dp[1][0] ~ dp[1][2]에 1번집의 RGB 비용을 넣어둔다.
 *      2. dp[2][j] 부터는 바로 이전 값에서 자신과 다른 두 색깔 중 최소값을 본인의 색칠 비용과 더한다.
 *      3. dp.length까지 반복하여 마지막 행 최소값을 출력한다.
 *
 * 시간복잡도 :
 *      집이 1000개 색이 3개
 *      1000번 반복 * 3번반복 * 3번반복(이전 dp의 최소값 찾기) = 9000번
 *      9N => O(N)
 */

package week6.RGB거리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        int[][] houses = new int[N][3];
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            houses[i][0] = Integer.parseInt(st.nextToken());
            houses[i][1] = Integer.parseInt(st.nextToken());
            houses[i][2] = Integer.parseInt(st.nextToken());
        }

        int[][] dp = new int[N][3];

        dp[0] = houses[0];

        for(int i = 1; i < N; i++) {
            for(int j = 0; j < 3; j++) {
                int min = 1000001;
                for(int k = 0; k < 3; k++) {
                    if(k == j) continue;
                    min = Math.min( dp[i-1][k], min );
                }
                dp[i][j] = min + houses[i][j];
            }
        }

        int min = 1000001;
        for(int i = 0; i < 3; i++) {
            min = Math.min(min, dp[N-1][i]);
        }
        System.out.println(min);
    }
}

