import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];

        StringTokenizer stk = new StringTokenizer(br.readLine());

        for(int i=0 ; i<n ; i++){
            arr[i] = Integer.parseInt(stk.nextToken());
        }

        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int answer = 1;
        for(int i=0 ; i<n ; i++){
            for(int j=0 ; j<i ; j++){
                if(arr[i] >= arr[j])continue;
                dp[i] = Math.max(dp[j] + 1, dp[i]);
                answer = Math.max(answer, dp[i]);
            }
        }

        System.out.println(answer);
    }
}