/**
 * 접근 :
 * 1. 물먼저 진행시키고
 * 2. BFS 한사이클
 * 3. 다시 물 - 한사이클 - 물 반복
 */

package week9.n7_탈출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int R, C;
    static int[][] stage;

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        stage = new int[R][C];

        int[] start = new int[2];
        int[] destination = new int[2];

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            String str = st.nextToken();
            for (int j = 0; j < C; j++) {
                char c = str.charAt(j);
                if (c == 'D') {
                    stage[i][j] = -3;
                    destination[0] = i;
                    destination[1] = j;
                } else if (c == '*') stage[i][j] = -1;
                else if (c == 'S') {
                    start[0] = i;
                    start[1] = j;
                } else if (c == 'X') stage[i][j] = -2;
            }
        }

        Queue<Location> q = new LinkedList<>();

        q.add(new Location(start[0], start[1], 1));
        stage[start[0]][start[1]] = 1;

        int flush_cnt = 0;
        Loop1:
        while (!q.isEmpty()) {
            Location now = q.poll();

            if (now.cnt > flush_cnt) {
                flush();
                flush_cnt++;
            }

            for (int i = 0; i < 4; i++) {
                if (getPossibility(now.x + dx[i], now.y + dy[i])) {
                    if (stage[now.x + dx[i]][now.y + dy[i]] == 0 || stage[now.x + dx[i]][now.y + dy[i]] == -3) {
                        q.add(new Location(now.x + dx[i], now.y + dy[i], now.cnt + 1));
                        stage[now.x + dx[i]][now.y + dy[i]] = now.cnt + 1;
                        if(destination[0] == now.x + dx[i] && destination[1] == now.y + dy[i]) break Loop1;
                    }
                }
            }
        }

        int result = stage[destination[0]][destination[1]];

        if(result > 0) {
            System.out.println(result - 1);
        }
        else System.out.println("KAKTUS");


    }

    static void flush() {

        int[][] visited = new int[R][C];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (stage[i][j] == -1 && visited[i][j] == 0) {
                    for (int k = 0; k < 4; k++) {
                        if (getPossibility(i + dx[k], j + dy[k])) {
                            if (visited[i + dx[k]][j + dy[k]] == 0 && stage[i + dx[k]][j + dy[k]] >= 0) {
                                stage[i + dx[k]][j + dy[k]] = -1;
                                visited[i + dx[k]][j + dy[k]] = 1;
                            }
                        }
                    }
                }
            }
        }
    }

    static boolean getPossibility(int x, int y) {
        if (x < 0 || x >= R) return false;
        if (y < 0 || y >= C) return false;
        return true;
    }
}

class Location {
    int x;
    int y;
    int cnt;

    public Location(int x, int y, int cnt) {
        this.x = x;
        this.y = y;
        this.cnt = cnt;
    }
}
