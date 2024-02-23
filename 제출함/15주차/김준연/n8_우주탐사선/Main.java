/**
 * 접근 :
 *      1. N이 10이하니까 플로이드-워셜
 *      2. 그 다음 별을 방문하는 순서 전부 시도
 *
 */

package week15.n8_우주탐사선;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, K;
    static int[][] adj;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        adj = new int[N][N];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                int num = Integer.parseInt(st.nextToken());
                if(i == j) adj[i][j] = 1000000000;
                else adj[i][j] = num;
            }
        }

        for(int m = 0; m < N; m++) {
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(adj[i][j] > adj[i][m] + adj[m][j]) {
                        adj[i][j] = adj[i][m] + adj[m][j];
                    }
                }
            }
        }

        dfs(K, 0, 1 << K);

        System.out.println(min);
    }

    static void dfs(int now, int sum, int bits) {
        if(Integer.bitCount(bits) >= N) {
            min = Math.min(min, sum);
            return;
        }

        for(int i = 0; i < N; i++) {
            if((bits & (1 << i)) == 0) {
                dfs(i, sum + adj[now][i], bits | (1 << i));
            }
        }
    }
}
