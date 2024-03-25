import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main{
    public static void main(String[] args) throws IOException{
        int H, W, X, Y;
        int[][] B, A;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());

        H = Integer.parseInt(stk.nextToken());
        W = Integer.parseInt(stk.nextToken());
        X = Integer.parseInt(stk.nextToken());
        Y = Integer.parseInt(stk.nextToken());

        int n = (H + X), m = (W + Y);

        B = new int[n][m];

        for(int i=0 ; i<n ; i++){
            stk = new StringTokenizer(br.readLine());
            for(int j=0 ; j<m ; j++){
                B[i][j] = Integer.parseInt(stk.nextToken());
            }
        }

        A = new int[H][W];

        for(int i=0 ; i<H ; i++){
            for(int j=0 ; j<W ; j++){
                if(i < X || (i >= X && j < Y))A[i][j] = B[i][j];
                else {
                    A[i][j] = (B[i][j] - A[i - X][j - Y]);
                }
            }
        }

        StringBuilder answer = new StringBuilder();
        for(int i=0 ; i<H ; i++){
            for(int j=0 ; j<W ;j++){
                answer.append(A[i][j]).append(" ");
            }
            answer.append("\n");
        }

        System.out.println(answer);

    }
}