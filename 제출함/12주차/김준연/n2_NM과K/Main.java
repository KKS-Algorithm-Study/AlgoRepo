/**
 * 시간복잡도 :
 *      (N*M) C (K)
 */

package week12.n2_NM과K;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int result = -4000000;
    static int[][] stage;
    static boolean[][] visited;
    static int N, M, K;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        stage = new int[N][M];
        visited = new boolean[N][M];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++) {
                stage[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0, 0, 0);

        System.out.println(result);
    }

    static void dfs(int x, int y, int cnt, int sum) {
        if(cnt >= K) {
            result = Math.max(result, sum);
            return;
        }
        if(y >= M) {
            y = 0;
            x++;
        }
        if(x >= N) {
            return;
        }

        if(getPossibility(x, y)) {
            visited[x][y] = true;
            dfs(x, y+1, cnt+1, sum + stage[x][y]);
            visited[x][y] = false;
        }
        dfs(x, y+1, cnt, sum);

    }

    static boolean getPossibility(int x, int y) {
        if(x > 0) {
            if(visited[x-1][y]) return false;
        }
        if(x < N - 1) {
            if(visited[x+1][y]) return false;
        }
        if(y > 0) {
            if(visited[x][y-1]) return false;
        }
        if(y < M - 1) {
            if(visited[x][y+1]) return false;
        }
        return true;
    }
}
