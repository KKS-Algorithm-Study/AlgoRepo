import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(stk.nextToken()), m = Integer.parseInt(stk.nextToken());
        char[][] board = new char[n + 1][m + 1];
        int[][] dp = new int[n + 1][m + 1];
        int answer = 0;
        
        for(int i=1 ; i<=n ; i++){
            board[i][0] = '0';
            String str = br.readLine();
            for(int j=1 ; j<=m ; j++){
                board[0][j] = '0';
                board[i][j] = str.charAt(j - 1);
                if(board[i][j] == '1')dp[i][j] = 1;
            }
        }

        for(int i=1 ; i<=n ; i++){
            for(int j=1 ; j<=m ; j++){
                if(board[i][j] == '1' && board[i-1][j]=='1' && board[i][j-1] == '1' && board[i-1][j-1] == '1'){
                    if(dp[i-1][j]>=1 && dp[i-1][j]>=1)
                        dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1]))+ 1;
                }
            }
        }


        for(int i=0 ; i<=n ; i++){
            for(int j=0 ; j<=m ; j++){
                answer = Math.max(dp[i][j] * dp[i][j], answer);
            }
        }

        System.out.println(answer);
    }
}