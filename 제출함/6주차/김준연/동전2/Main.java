/**
 * 접근 :
 *      1. 동전을 오름차 순으로 정렬한다.
 *      2. 0 ~ k까지 비용에 대한 dp배열에 첫번째 동전부터 경우의 수 계산을 한다.
 *          dp 계산법 :
 *          1) 비용에 대해 현재 동전으로 나누어 떨어지는가 ?
 *              => 나눈 값이 해당 원소보다 작은 경우 치환한다. *항상 0체크 할것.
 *          2) 나누어 떨어지지 않는가?
 *              => 현재 동전이 1개 쓰이는 경우, 비용 % 동전1개를 remain이라 할 때, dp[remain] 을 조회하여 0이 아니라면
 *              1 + dp[remain]을 구하고 현재 값보다 작다면 현재 원소를 치환한다.
 *              => 현재 동전이 2개 쓰이는 경우... 2 + dp[remain]... 반복
 *      3. dp[k]를 조회하여 0이라면 -1을 출력, 그렇지 않다면 값을 출력하는 것으로 해결
 *
 * 문제점 :
 *      1. 점화식 규칙을 찾는데 상당한 시간이 쓰였음.
 *          1) 동전 정렬을 하지 않는다거나
 *          2) 2와 5가 있을 때 11은 5,2,2,2로 표현 가능한데, 11 % 5 의 경우만 계산하자 5 * 2 + 1 => 1이 없음 의 상황이 되거나
 *
 * 시간복잡도 : O(n X k)
 *
 */

package week6.동전2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        ArrayList<Integer> coins = new ArrayList<>();
        int[] dp = new int[k+1];
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            coins.add(Integer.parseInt(st.nextToken()));
        }

        Collections.sort(coins);

        for(int i = 0; i < n; i++) {
            for(int j = 1; j < k+1; j++) {
                int coin = coins.get(i);
                if(j % coin == 0) {
                    int thisCase = j / coin;
                    if(dp[j] == 0 || dp[j] > thisCase) dp[j] = thisCase;
                }
                else {
                    int cnt = 1;
                    while(j > coin * cnt) {
                        int remain = j - coin * cnt;
                        int thisCase = cnt + dp[remain];
                        cnt++;
                        if(dp[remain] == 0) continue;
                        if(dp[j] == 0 || dp[j] > thisCase) dp[j] = thisCase;
                    }

                }
            }
            //System.out.println(Arrays.toString(dp));
        }

        System.out.println((dp[k] == 0 ? -1 : dp[k]));

    }
}
