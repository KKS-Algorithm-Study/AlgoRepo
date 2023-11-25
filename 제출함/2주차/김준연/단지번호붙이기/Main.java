package week2.단지번호붙이기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        int N;
        int[][] board;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        board = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String str = st.nextToken();
            for (int j = 0; j < N; j++) board[i][j] = Character.getNumericValue(str.charAt(j));
        }

        //여기까지 데이터 input

        Solution solution = new Solution(N, board);

        solution.run();

        System.out.println(solution.house.size());

        while(solution.house.size() > 0) System.out.println(solution.house.poll());


    }
}

class Solution {
    int N;
    int cnt;
    int[][] board;
    boolean[][] checked;
    PriorityQueue<Integer> house = new PriorityQueue<>();

    Solution(int N, int[][] board) {
        this.N = N;
        this.board = board;
        checked = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) checked[i][j] = false;
        }
    }

    void run() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1 && !checked[i][j]) {
                    cnt = 0;
                    dfs(i, j);
                    house.add(cnt);
                }
            }
        }
    }

    void dfs(int x, int y) {
        cnt++;
        checked[x][y] = true;
        if (x - 1 >= 0) {
            if (board[x - 1][y] == 1 && !checked[x - 1][y]) dfs(x - 1, y);
        }
        if (y + 1 < N) {
            if (board[x][y + 1] == 1 && !checked[x][y + 1]) dfs(x, y + 1);
        }
        if (x + 1 < N) {
            if (board[x + 1][y] == 1 && !checked[x + 1][y]) dfs(x + 1, y);
        }
        if (y - 1 >= 0) {
            if (board[x][y - 1] == 1 && !checked[x][y - 1]) dfs(x, y - 1);
        }
    }
}
