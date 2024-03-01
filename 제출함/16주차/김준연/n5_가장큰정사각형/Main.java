/**
 *  접근 :
 *      1. -1 -1 좌표가 만드는 정사각형의 크기가 무엇인가.
 *      2. 내 왼쪽의 1의 개수와 위쪽의 1의 개수가 -1 -1 좌표의 정사각형 크기와 같은가?
 *          => -1 -1 좌표에서 +1 한 크기 기록
 *      3. 그렇지 않다면? 왼쪽 1의 개수와 위쪽 1의 개수중 더 작은 값 기록
 */

package week16.n5_가장큰정사각형;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int n, m;
    static int[][] stage;
    static int[][][] dp;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        stage = new int[n][m];
        dp = new int[n][m][2];

        int result = 0;

        for(int i = 0; i < n; i++) {
            String str = br.readLine();
            for(int j = 0; j < m; j++) {
                stage[i][j] = Character.getNumericValue(str.charAt(j));
                if(stage[i][j] == 1) result = 1;
            }
        }



        for(int i = 1; i < n; i++) {
            for(int j = 1; j < m; j++) {
                if(stage[i][j] == 1) {
                    stage[i][j] = getSquare(i, j);
                    result = Math.max(result, stage[i][j]);
                }
            }
        }

        System.out.println(result * result);


    }

    static int getSquare(int a, int b) {
        dp[a][b][0] = stage[a][b-1] != 0 ? dp[a][b-1][0] + 1 : 0;
        dp[a][b][1] = stage[a-1][b] != 0 ? dp[a-1][b][1] + 1 : 0;

        if(stage[a-1][b-1] <= dp[a][b][0] && stage[a-1][b-1] <= dp[a][b][1]) {
            return stage[a-1][b-1] + 1;
        }
        return Math.min(dp[a][b][0], dp[a][b][1]) + 1;
    }
}
