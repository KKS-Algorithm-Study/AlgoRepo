/**
 * 접근 :
 *      1. DFS
 *      2. 모든 왼쪽 열에서 하나씩 시작
 *      3. 밑에 파이프가 손해 안보려면 가능한 위에 딱 붙어서 움직이면 됨
 *
 * 문제점 :
 *      1. 방문기록을 잘못 구현함
 *          1) 그 지점에 도달했을 때 어차피 같은 결과인데, 백트래킹하듯 리턴됐을 때 방문기록을 해제해버림
 */

package week16.n8_빵집;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int R, C;
    static boolean[][] visited;
    static int result = 0;
    static int[] dx = {-1, 0, 1};
    static boolean toggle = true;

    public static void main(String[] args) throws IOException {


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        visited = new boolean[R][C];

        for(int i = 0; i < R; i++) {
            String str = br.readLine();
            for(int j = 0; j < C; j++) {
                if(str.charAt(j) == 'x') {
                    visited[i][j] = true;
                }
            }
        }

        for(int i = 0; i < R; i++) {
            toggle = true;
            dfs(i, 0);
        }


        System.out.println(result);
    }

    static void dfs(int x, int y) {
        if(y >= C - 1) {
            result++;
            toggle = false;
            return;
        }

        for(int i = 0; i < 3; i++) {
            if(getPossibility(x + dx[i], y + 1)) {
                if(!visited[x + dx[i]][y + 1]) {
                    visited[x + dx[i]][y + 1] = true;
                    dfs(x + dx[i], y + 1);
                    if(!toggle) return;
                }
            }
        }

    }

    static boolean getPossibility(int x, int y) {
        if(x < 0 || y < 0) return false;
        if(x >= R || y >= C) return false;
        return true;
    }
}
