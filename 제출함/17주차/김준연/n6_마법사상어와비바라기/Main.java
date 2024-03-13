package week17.n6_마법사상어와비바라기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static int[][] stage;
    static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dx2 = {-1, -1, 1, 1};
    static int[] dy2 = {-1, 1, 1, -1};

    static class Cloud {
        int x, y;

        public Cloud(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void move(int direction, int speed) {
            while (speed-- > 0) {
                x = x + dx[direction];
                y = y + dy[direction];
                if (x < 0) x = N - 1;
                if (x >= N) x = 0;
                if (y < 0) y = N - 1;
                if (y >= N) y = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        stage = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                stage[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        ArrayList<Cloud> clouds = new ArrayList<>();
        clouds.add(new Cloud(N - 1, 0));
        clouds.add(new Cloud(N - 1, 1));
        clouds.add(new Cloud(N - 2, 0));
        clouds.add(new Cloud(N - 2, 1));

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int direction = Integer.parseInt(st.nextToken()) - 1;
            int speed = Integer.parseInt(st.nextToken());

            boolean[][] visited = new boolean[N][N];

            for (Cloud cloud : clouds) {
                cloud.move(direction, speed);
                stage[cloud.x][cloud.y]++;
                visited[cloud.x][cloud.y] = true;
            }

            for (Cloud cloud : clouds) {
                run_bug(cloud.x, cloud.y);
            }


            ArrayList<Cloud> nextClouds = new ArrayList<>();

            for (int q = 0; q < N; q++) {
                for (int w = 0; w < N; w++) {
                    if (stage[q][w] >= 2 && !visited[q][w]) {
                        nextClouds.add(new Cloud(q, w));
                        stage[q][w] -= 2;
                    }
                }
            }

            clouds = nextClouds;

        }

        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += stage[i][j];
            }
        }

        System.out.println(sum);

    }

    static void run_bug(int x, int y) {

        int cnt = 0;
        for (int k = 0; k < 4; k++) {
            int x2 = x + dx2[k];
            int y2 = y + dy2[k];
            if (getPossibility(x2, y2)) {
                if (stage[x2][y2] > 0) cnt++;
            }
        }

        stage[x][y] += cnt;

    }

    static boolean getPossibility(int x, int y) {
        if (x < 0 || x >= N) return false;
        if (y < 0 || y >= N) return false;
        return true;
    }

}

