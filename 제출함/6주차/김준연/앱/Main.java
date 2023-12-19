/**
 * 접근 :
 *      1. M은 필요한 바이트 수. ci는 단순히 비용이라고 생각
 *      2. 일단 배낭문제인데, M의 좌극한이 아니라 우극한으로 접근해야함. 60바이트가 필요하니까 60이상.. 63, 62바이트 씩 여유가 있어야함
 *      3. 무게에 대해 배낭담으면 무게 백만, 앱 개수 100이라 연산 횟수 = 10억
 *      4. 비용에 대해 담으면 최대비용 만, 앱 개수 100 => 100만
 *      5. 모든 비용의 합에 대해, 앱을 하나씩 늘려가며 DP하고, 마지막 DP행을 순회하면서 M을 넘는 인덱스를 출력
 *
 * 시간복잡도 :
 *      비용의 합 * 앱의 개수
 *      최대 10000 * 최대 100 => 1000000 (백만)
 *      C * N * N => O(N^2)
 *
 */

package week6.앱;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] bytes = new int[N + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= N; i++) {
            bytes[i] = Integer.parseInt(st.nextToken());
        }

        int[] costs = new int[N + 1];
        int max_cost = 0;
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= N; i++) {
            costs[i] = Integer.parseInt(st.nextToken());
            max_cost += costs[i];
        }

        int[][] dp = new int[N + 1][max_cost + 1];

        for(int i = 1; i <= N; i++) {
            int curr_byte = bytes[i];
            int curr_cost = costs[i];   //앱 하나의 정보 담음

            for(int j = 0; j <= max_cost; j++) {
                dp[i][j] = dp[i-1][j];
                if(curr_cost > j) continue;     //비용 1씩 늘려가며 해당 비용이 앱에 맞을때까지 스킵
                int remain = dp[i-1][j - curr_cost];     //반복중인 비용에서 현재 앱의 비용을 뺀 비용을 갖고 지금까지 계산한 dp배열 상에 몇 바이트를 처리가능한지 저장
                int result_byte = curr_byte + remain;       //현재 앱을 끔 + 남은 비용으로 현재까지 계산한 최적의 바이트 계싼

                if(result_byte > dp[i-1][j]) dp[i][j] = result_byte;       //위 계산이 여태까지 한 dp보다 이득이면 값을 갈아치움
            }
            //System.out.println(Arrays.toString(dp[i]));
        }

        for(int i = 0; i <= max_cost; i++) {
            if(dp[N][i] >= M) {
                System.out.println(i);
                return;
            }
        }

    }
}
