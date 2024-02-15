/**
 * 접근 :
 *      1. BFS로 가는데
 *      2. 방문기록을 좌표뿐 아니라 내가 들고 있는 우산의 조합으로 해야함
 *          visited[x][y][우산비트마스킹]
 *      3. 우산을 들었으면 방어막을 초기화시키고 비트마스킹에 해당 우산의 넘버를 기록하고 방문기록하는 식
 *      4. 이렇게 진행하면서 엔드포인트에 도달하는 순간이 가장 빠른 순간이므로 그 때의 cnt 출력
 *
 * 주의 :
 *      1. 역주행해서 우산을 들고 시작점을 다시 방문할 때, 시작점은 안전지대임 => 피나 방어막 까지말것
 */

package week14.n7_죽음의비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int N, H, D;
    static int[][] stage;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        stage = new int[N][N];
        int[] start = new int[2];
        int[] end = new int[2];
        int u_cnt = 1;
        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < N; j++) {
                char c = str.charAt(j);
                switch (c) {
                    case 'S':
                        start[0] = i;
                        start[1] = j;
                        break;
                    case 'E':
                        end[0] = i;
                        end[1] = j;
                        stage[i][j] = -1;
                        break;
                    case 'U':
                        stage[i][j] = u_cnt++;
                }
            }
        }

        boolean[][][] visited = new boolean[N][N][2048];

        Queue<Next> q = new LinkedList<>();
        q.add(new Next(start[0], start[1], H, 0, 0, 0));
        visited[start[0]][start[1]][0] = true;

        while (!q.isEmpty()) {
            Next now = q.poll();

            for (int i = 0; i < 4; i++) {
                int x = now.x + dx[i];
                int y = now.y + dy[i];
                if (getPossibility(x, y)) {
                    if (!visited[x][y][now.umbrella_bits]) {
                        if(x == end[0] && y == end[1]) {
                            System.out.println(now.count + 1);
                            return;
                        }

                        int defense = now.defense;
                        int umbrella_bits = now.umbrella_bits;
                        if (stage[x][y] > 0 && (umbrella_bits & (1 << stage[x][y])) == 0) {
                            defense = D;
                            umbrella_bits = umbrella_bits | (1 << stage[x][y]);
                        }
                        if(defense > 0) {
                            if(x == start[0] && y == start[1]) defense++;
                            q.add(new Next(x, y, now.health, defense - 1, now.count + 1, umbrella_bits));
                            visited[x][y][umbrella_bits] = true;
                        }
                        else if(now.health > 1){
                            if(x == start[0] && y == start[1]) {
                                q.add(new Next(x, y, now.health, defense, now.count + 1, umbrella_bits));
                            }
                            else q.add(new Next(x, y, now.health - 1, defense, now.count + 1, umbrella_bits));
                            visited[x][y][umbrella_bits] = true;
                        }
                    }
                }
            }
        }
        System.out.println(-1);
    }

    static boolean getPossibility(int x, int y) {
        if (x < 0) return false;
        if (x >= N) return false;
        if (y < 0) return false;
        if (y >= N) return false;
        return true;
    }
}

class Next {
    int x;
    int y;
    int health;
    int defense;
    int count;
    int umbrella_bits;

    public Next(int x, int y, int health, int defense, int count, int umbrella_bits) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.defense = defense;
        this.count = count;
        this.umbrella_bits = umbrella_bits;
    }
}