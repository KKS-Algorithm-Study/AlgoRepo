/**
 * !! 답안 참고함
 * 접근 :
 *      1. 0 ~ k 까지의 총 비용에 대해 동전을 하나씩 늘려가며 경우의 수를 갱신한다.
 *          갱신 방법 :
 *              1) x원짜리 동전에 대해 갱신하려면, 0 ~ k비용 반복문 안에 동전 반복문을 굴린다.
 *              2) dp 배열의 각 원소에 대해 dp[i] = dp[i] + dp[i - x] 로 동전의 비용만큼 이전 인덱스 값을 자신에게 더한다.
 *      2. 처음 0 ~ 10까지 비용에 1원짜리 동전을 사용하면 모든 비용에 대해 경우의 수가 1개씩 존재함.
 *      3. 2원짜리 동전 경우의 수 추가 시, 2원째부터 자신의 -2번째 요소를 합치면서 1 1 2 2 3 3 4 4 5 5 6 이 됨.
 *      4. 5원 추가 시, 5원째부터 자신의 -5번째 요소를 합침. 1 1 2 2 3 4 4 6 7 8 10 이 됨.
 *      5. 따라서 마지막 원소 10을 출력
 *
 * 시간복잡도 : O(k X n)
 */

package week6.동전1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] coins = new int[n];
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            coins[i] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[k+1];

        dp[0] = 1;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < k + 1; j++) {
                if(coins[i] <= j) {
                    dp[j] += dp[j - coins[i]];
                }
            }
        }

        System.out.println(dp[k]);

    }
}
