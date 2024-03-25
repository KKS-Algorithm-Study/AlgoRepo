import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());
        
        int n, m, k;
        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());
        k = Integer.parseInt(stk.nextToken());

        int[][] board = new int[n][m];
        int[][] prefixSum = new int[n + 1][m + 1];

        for(int i=0 ; i<n ; i++){
            stk = new StringTokenizer(br.readLine());
            for(int j=0 ; j<m ; j++){
                board[i][j] = Integer.parseInt(stk.nextToken());
            }
        }

        for(int i=1;  i<=n ; i++){
            for(int j=1 ; j<=m ; j++){
                prefixSum[i][j] = board[i - 1][j - 1] + prefixSum[i-1][j] +  prefixSum[i][j-1] - prefixSum[i-1][j-1];
            }
        }

        StringBuilder answer = new StringBuilder();

        while(k-- > 0){
           stk = new StringTokenizer(br.readLine());
           int y1, x1, y2, x2;
           y1 = Integer.parseInt(stk.nextToken());
           x1 = Integer.parseInt(stk.nextToken());
           y2 = Integer.parseInt(stk.nextToken());
           x2 = Integer.parseInt(stk.nextToken());

           int total = (prefixSum[y2][x2] - prefixSum[y2][x1-1] - prefixSum[y1-1][x2] + prefixSum[y1-1][x1-1]);

           answer.append(total / ((y2 - y1 + 1) * (x2 - x1 + 1)))
           .append("\n");
        }
        System.out.print(answer);
    }
}