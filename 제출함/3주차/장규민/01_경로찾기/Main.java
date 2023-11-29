import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int n;
    private static int[][] board;

    public static void main(String... args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        board = new int[n][n];

        
        for(int i=0 ; i<n ; i++){
            StringTokenizer stk = new StringTokenizer(br.readLine());    
            for(int j=0 ; j<n ; j++){
                board[i][j] = Integer.parseInt(stk.nextToken());
            }
        }

        solution();
        StringBuilder answer = new StringBuilder();
        for(int i=0 ; i<n ; i++){
            for(int j=0 ; j<n ; j++){
                answer.append(board[i][j]).append(" ");
            }
            answer.append("\n");
        }
        System.out.println(answer);
    }

    private static void solution(){

        for(int k=0 ; k<n ; k++){
            for(int i=0 ; i<n ; i++){
                for(int j=0 ; j<n ; j++){
                    if((board[i][k] == 0 || board[k][j] == 0) && board[i][j] == 0){
                        continue;
                    }
                    board[i][j] = 1;
                }
            }
        }
    }
}
