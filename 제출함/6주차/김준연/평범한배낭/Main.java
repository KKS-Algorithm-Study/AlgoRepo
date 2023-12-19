package week6.평범한배낭;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[][] items = new int[N+1][2];
        for(int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine());
            items[i][0] = Integer.parseInt(st.nextToken());     //무게
            items[i][1] = Integer.parseInt(st.nextToken());     //가치
        }

        int[][] dp = new int[N+1][K+1];

        for(int i = 1; i < N+1; i++) {
            for(int j = 1; j < K+1; j++) {
                dp[i][j] = dp[i-1][j];  //일단 현재 아이템을 세기 전 상황을 그대로 가져온다.
                int remain = j - items[i][0];   //현재 가방의 무게 - 현재 아이템의 무게
                if(remain >= 0) {   //현재 아이템이 가방에 들어갈 수 있다면?
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][remain]+items[i][1]);   //(현재 무게의 이전아이템 상황) vs (현재아이템을 넣고 남은 무게에 대해 dp에 적힌 값) 을 비교하여 큰 걸 채택
                }
            }
        }

        System.out.println(dp[N][K]);
    }
}
