import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main{

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int m = Integer.parseInt(br.readLine());

        int[] dp = new int[41];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;

        for(int i=3 ; i<=40 ; i++){
            dp[i] = dp[i - 2] + dp[i - 1];
        }

        int[] vips = new int[m + 1];
        for(int i=0 ; i<m ; i++){
            vips[i] = Integer.parseInt(br.readLine());
        }
        vips[m] = n + 1;

        int left = 0;
        long answer = 1;

        for(int i = 0 ; i<=m ; i++){
            answer *= dp[vips[i] - left - 1];
            left = vips[i];
        }
        System.out.println(answer);
    }
    
}