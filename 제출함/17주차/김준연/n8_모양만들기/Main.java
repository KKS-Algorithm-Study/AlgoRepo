/**
 * 접근 :
 *      1. 각 묶음에 대해 bfs로 어디까지 한 묶음이고 크기가 몇인지 기록
 *      2. 모든 0에 대해 동서남북의 묶음을 중복없이 센 크기의 합 + 1 에 대해 MAX 값 출력
 */

package week17.n8_모양만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N, M;
    static int[][] stage, visited;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int idx = 1;
    static HashMap<Integer, Integer> hm = new HashMap<>();

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        stage = new int[N][M];
        visited = new int[N][M];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++) {
                stage[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                if(stage[i][j] == 1 && visited[i][j] == 0) bfs(i, j);
            }
        }

        int max = 0;
        hm.put(0, 0);

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                if(stage[i][j] == 0) {
                    max = Math.max(max, getSum(i, j));
                }
            }
        }

        System.out.println(max + 1);
    }

    static int getSum(int x, int y) {
        int sum = 0;
        HashSet<Integer> hs = new HashSet<>();
        for(int i = 0; i < 4; i++) {
            int x2 = x + dx[i];
            int y2 = y + dy[i];
            if(getPossibility(x2, y2)) {
                hs.add(visited[x2][y2]);
            }
        }

        for(int n : hs) sum += hm.get(n);

        return sum;
    }

    static void bfs(int x, int y) {
        Queue<Next> q = new ArrayDeque<>();
        q.add(new Next(x, y));
        visited[x][y] = idx;

        int cnt = 1;

        while(!q.isEmpty()) {
            Next now = q.poll();
            x = now.x;
            y = now.y;
            for(int i = 0; i < 4; i++) {
                if(getPossibility(x + dx[i], y + dy[i])) {
                    if(stage[x + dx[i]][y + dy[i]] == 1 && visited[x + dx[i]][y + dy[i]] == 0) {
                        q.add(new Next(x + dx[i], y + dy[i]));
                        visited[x + dx[i]][y + dy[i]] = idx;
                        cnt++;
                    }
                }
            }
        }

        hm.put(idx,cnt);
        idx++;
    }

    static boolean getPossibility(int x, int y) {
        if(x < 0 || x >= N) return false;
        if(y < 0 || y >= M) return false;
        return true;
    }
}

class Next {
    int x, y;

    public Next(int x, int y) {
        this.x = x;
        this.y = y;
    }
}