/**
 * !! 답안 참고함
 * 접근 :
 *      1. 2차원 배열 dp에 [0][0] , [1][0] 자리에는 같은 좌표의 스티커 점수를 넣는다.
 *      2. 이후 반복문에서 [0][i]는 같은 좌표의 점수 + max([1][i-1], [1][i-2]) 를 넣는다.
 *          -> 만일 해당 경로를 지나온다면 나타나게 될 최대값을 기록하는 행위임.
 *          -> 자신의 반대쪽 -1은 교차로 뜯은 경우, -2는 뜯지 않고 건너뛴 경우에 대해 더 큰 값을 가져와 자신의 점수를 더해 갱신하는 것.
 *      3. 끝 원소 두 개 중 더 큰 점수를 출력한다.
 *
 * 시간복잡도 :
 *      1개 테스트케이스에 대해
 *      input : 2n
 *      dp : n
 *      => O(n)
 */

package week6.스티커;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for(int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int[][] stickers = new int[2][n];
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++) {
                stickers[0][j] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++) {
                stickers[1][j] = Integer.parseInt(st.nextToken());
            }
            sol(stickers);
        }
    }

    static void sol(int[][] stickers) {
        int k = stickers[0].length;

        int[][] dp =  new int[2][k];

        dp[0][0] = stickers[0][0];
        dp[1][0] = stickers[1][0];
        if(k > 1) {
            dp[0][1] = dp[1][0] + stickers[0][1];
            dp[1][1] = dp[0][0] + stickers[1][1];
        }

        for(int i = 2; i < k; i++) {
            dp[0][i] = Math.max(dp[1][i-1], dp[1][i-2]) + stickers[0][i];
            dp[1][i] = Math.max(dp[0][i-1], dp[0][i-2]) + stickers[1][i];
        }

        System.out.println(Math.max(dp[0][k-1], dp[1][k-1]));

    }
}
