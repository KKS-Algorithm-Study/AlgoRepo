/**
 * 접근 :
 *      1. 누적합
 *      2. 모든 좌표에 대해 1,1 에서 시작한 합들을 누적합 기록
 *      3. 이후 부분을 도려내어 계산
 *      4. 그걸 나눔
 *
 * 시간복잡도 :
 *      O(RXC)
 */

package week18.n2_어두운건무서워;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int R, C, Q;
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        int[][] stage = new int[R+1][C+1];
        for(int i = 1; i <= R; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= C; j++) {
                stage[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] query = new int[Q][4];
        for(int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            query[i][0] = Integer.parseInt(st.nextToken());
            query[i][1] = Integer.parseInt(st.nextToken());
            query[i][2] = Integer.parseInt(st.nextToken());
            query[i][3] = Integer.parseInt(st.nextToken());
        }

        int[][] sum = new int[R+1][C+1];

        for(int i = 1; i <= R; i++) {
            for(int j = 1; j <= C; j++) {
                int a, b, dup;
                a = sum[i-1][j];
                b = sum[i][j-1];
                dup = sum[i-1][j-1];
                sum[i][j] = a + b - dup + stage[i][j];
            }
        }

        for(int i = 0; i < Q; i++) {
            int x1 = query[i][0];
            int y1 = query[i][1];
            int x2 = query[i][2];
            int y2 = query[i][3];

            int result = sum[x2][y2];
            result -= sum[x2][y1-1];
            result -= sum[x1-1][y2];
            result += sum[x1-1][y1-1];

            result = result / ((x2 - x1 + 1) * (y2 - y1 + 1));
            System.out.println(result);
        }
    }
}
