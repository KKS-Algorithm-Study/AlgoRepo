package week13.n3_침투;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[][] stage;
    static boolean[][] visited;
    static int M, N;
    static boolean possibility = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        stage = new int[M][N];
        visited = new boolean[M][N];

        for(int i = 0; i < M; i++) {
            String str = br.readLine();
            for(int j = 0; j < N; j++) {
                stage[i][j] = Character.getNumericValue(str.charAt(j));
            }
        }

        for(int i = 0; i < N; i++) {
            dfs(0, i);
        }

        if(possibility) System.out.println("YES");
        else System.out.println("NO");
    }

    static void dfs(int x, int y) {
        visited[x][y] = true;
        if(x == M - 1) {
            possibility = true;
            return;
        }

        if(getPossibility(x + 1, y)) {
            dfs(x+1, y);
        }
        if(getPossibility(x, y + 1)) {
            dfs(x, y+1);
        }
        if(getPossibility(x, y - 1)) {
            dfs(x, y-1);
        }
        if(getPossibility(x - 1, y)) {
            dfs(x-1, y);
        }
    }

    static boolean getPossibility(int x, int y) {
        if(possibility) return false;
        if(x >= M) return false;
        if(x < 0) return false;
        if(y >= N) return false;
        if(y < 0) return false;
        if(stage[x][y] == 1 || visited[x][y]) return false;
        return true;
    }
}
