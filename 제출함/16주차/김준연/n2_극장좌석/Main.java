package week16.n2_극장좌석;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int[] dp = new int[N+2];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for(int i = 3; i <= N + 1; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }

        int result = 1;
        int idx = 0;
        int M = Integer.parseInt(br.readLine());

        for(int i = 0; i < M; i++) {
            int n = Integer.parseInt(br.readLine());
            if(n == idx) continue;
            result *= dp[n - idx - 1];
            idx = n;
        }

        result *= dp[N - idx];

        System.out.println(result);
    }
}
