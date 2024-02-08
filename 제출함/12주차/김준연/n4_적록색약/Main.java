/**
 * 접근 :
 *      1. RGB에 대해 BFS
 *      2. (RG) B 에 대해 BFS
 *      3. 그룹 두 경우 출력
 */

package week12.n4_적록색약;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        char[][] stage = new char[N][N];

        for(int i = 0; i < N; i++) {
            stage[i] = br.readLine().toCharArray();
        }

        Queue<Location> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];
        int cnt = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(!visited[i][j]) {
                    q.add(new Location(i, j));
                    visited[i][j] = true;
                    cnt++;
                    while(!q.isEmpty()) {
                        Location now = q.poll();
                        for(int k = 0; k < 4; k++) {
                            if(getPossibility(now.x + dx[k], now.y + dy[k], N)) {
                                if(!visited[now.x + dx[k]][now.y + dy[k]] && stage[now.x][now.y] == stage[now.x + dx[k]][now.y + dy[k]]) {
                                    q.add(new Location(now.x + dx[k], now.y + dy[k]));
                                    visited[now.x + dx[k]][now.y + dy[k]] = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.print(cnt + " ");

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(stage[i][j] == 'G') stage[i][j] = 'R';
            }
        }

        q = new LinkedList<>();
        visited = new boolean[N][N];
        cnt = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(!visited[i][j]) {
                    q.add(new Location(i, j));
                    visited[i][j] = true;
                    cnt++;
                    while(!q.isEmpty()) {
                        Location now = q.poll();
                        for(int k = 0; k < 4; k++) {
                            if(getPossibility(now.x + dx[k], now.y + dy[k], N)) {
                                if(!visited[now.x + dx[k]][now.y + dy[k]] && stage[now.x][now.y] == stage[now.x + dx[k]][now.y + dy[k]]) {
                                    q.add(new Location(now.x + dx[k], now.y + dy[k]));
                                    visited[now.x + dx[k]][now.y + dy[k]] = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.print(cnt);


    }

    static boolean getPossibility(int x, int y, int N) {
        if(x < 0) return false;
        if(x >= N) return false;
        if(y < 0) return false;
        if(y >= N) return false;
        return true;
    }

    static class Location {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
