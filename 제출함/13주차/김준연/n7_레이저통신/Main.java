/**
 * 접근 :
 *      1. BFS 탐색을 하는데,
 *      2. 방문기록에 대해 x좌표, y좌표, 진행방향 마다 거울이 몇 개 쓰였는지 기록해야함
 *      3. 만약 해당 좌표에서 위로가는 방향에 대해 더 적은 거울로 도달가능했다면? 그 거울 수로 대체
 */

package week13.n7_레이저통신;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int W, H;
    static int[][] stage;
    static int[][][] visited;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};        //북 동 남 서 순서

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        stage = new int[H][W];
        visited = new int[H][W][4];
        for(int i = 0; i < H; i++) {
            for(int j = 0; j < W; j++) {
                for(int k = 0; k < 4; k++) visited[i][j][k] = Integer.MAX_VALUE;
            }
        }
        int[][] points = new int[2][2];
        boolean a = false;
        for(int i = 0; i < H; i++) {
            String str = br.readLine();
            for(int j = 0; j < W; j++) {
                if(str.charAt(j) == '*') stage[i][j] = 1;
                else if(str.charAt(j) == 'C') {
                    if(!a) {
                        points[0][0] = i;
                        points[0][1] = j;
                        a = true;
                    }
                    else {
                        points[1][0] = i;
                        points[1][1] = j;
                    }
                }
            }
        }

        Queue<Point> q = new LinkedList<>();
        int x = points[0][0];
        int y = points[0][1];
        visited[x][y][0] = 0;
        visited[x][y][1] = 0;
        visited[x][y][2] = 0;
        visited[x][y][3] = 0;       //시작점의 거울은 0개로 초기화

        for(int i = 0; i < 4; i++) {    //시작점부터 북 동 남 서에 대해 갈 수 있으면 그 좌표와 방향을 담고있는 객체를 q에 삽입
            int x2 = x + dx[i];
            int y2 = y + dy[i];
            if(getPossibility(x2, y2)) {
                q.add(new Point(x2, y2, 0, i));
                visited[x2][y2][i] = 0;
            }
        }

        while(!q.isEmpty()) {       //BFS
            Point now = q.poll();

            for(int i = 0; i < 4; i++) {
                x = now.x + dx[i];
                y = now.y + dy[i];
                if(getPossibility(x, y)) {
                    int mirror = now.mirror;
                    if(i != now.direction) mirror++;
                    if(visited[x][y][i] > mirror) {     //만일 이 좌표의 이 방향에서 이전에 쓴 거울보다 적게 갈 수 있다면 q에 담음
                        visited[x][y][i] = mirror;
                        q.add(new Point(x, y, mirror, i));
                    }
                }
            }
        }

        x = points[1][0];
        y = points[1][1];
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < 4; i++) {
            min = Math.min(min, visited[x][y][i]);      //도착지점에서 4가지 방향 정보 중 가장 작은 값을 꺼내옴
        }
        System.out.println(min);


    }

    static boolean getPossibility(int x, int y) {
        if(x < 0) return false;
        if(x >= H) return false;
        if(y < 0) return false;
        if(y >= W) return false;
        if(stage[x][y] == 1) return false;

        return true;
    }
}

class Point {
    int x;
    int y;
    int mirror;
    int direction;

    public Point(int x, int y, int mirror, int direction) {
        this.x = x;
        this.y = y;
        this.mirror = mirror;
        this.direction =  direction;
    }
}
