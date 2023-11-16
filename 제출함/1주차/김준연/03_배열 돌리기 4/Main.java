package week1.배열돌리기4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        int N, M, K;
        int[][] board;
        int[][] turn;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new int[N][M];
        turn = new int[K][3];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0, index = 0; j < M; index += 2, j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0, index = 0; j < 3; index += 2, j++) {
                turn[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //여기까지 데이터 input

        Solution solution = new Solution();

        int result = solution.dfs(board, turn, 0);
        System.out.println(result);
    }

}

class Solution {

    static int test_cnt = 0;

    int dfs(int[][] board, int[][] turn, int cnt) {
        if(cnt >= turn.length) return getMinimum(board);

        int[] result = new int[turn.length - cnt];
        int result_cnt = 0;
        for(int i = 0; i < turn.length; i++) {
            if(turn[i][0] < 51) {
                int[][] board_copy = arrCopy(board);
                board_copy = this.bingle(board_copy, turn[i][0], turn[i][1],turn[i][2]);
                int[][] turn_copy = arrCopy(turn);
                turn_copy[i][0] = 99;
                result[result_cnt++] = dfs(board_copy, turn_copy, cnt + 1);
            }
        }

        int min = result[0];

        for(int i = 1; i < result.length; i++) {
            min = (result[i] < min) ? result[i] : min;
        }
        return min;
    }


    int[][] bingle(int[][] board, int r, int c, int s) {    //빙그르르 함수

        int[][] copy = arrCopy(board);

        int x_start = r - s - 1;
        int y_start = c - s - 1;
        int x_end = r + s - 1;
        int y_end = c + s - 1;
        //스탑 카운트는 s

        for(int cnt = 0; cnt < s; cnt++) {

            int temp = copy[x_start + cnt][y_start + cnt];
            for(int i = 0; i < 2*(s - cnt); i++) {
                copy[x_start + i + cnt][y_start + cnt] = copy[x_start + i + 1 + cnt][y_start + cnt];
            }
            for(int i = 0; i < 2*(s - cnt); i++) {
                copy[x_end - cnt][y_start + i + cnt] = copy[x_end - cnt][y_start + i + 1 + cnt];
            }
            for(int i = 0; i < 2*(s - cnt); i++) {
                copy[x_end - i - cnt][y_end - cnt] = copy[x_end - i - 1 - cnt][y_end - cnt];
            }
            for(int i = 0; i < 2*(s - cnt); i++) {
                copy[x_start + cnt][y_end - i - cnt] = copy[x_start + cnt][y_end - i - 1 - cnt];
            }
            copy[x_start + cnt][y_start + 1 + cnt] = temp;
        }
//        Solution.show(copy);
        return copy;

    }

    int[][] arrCopy(int[][] board) {
        int[][] copy = new int[board.length][board[0].length];
        for(int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, copy[i].length);
        }
        return copy;
    }

    int getMinimum(int[][] board) {     //현재 배열의 값
        int min = 2147483647;
        for (int[] ints : board) {
            int now = 0;
            for(int num : ints) now += num;
            min = (min > now) ? now : min;
        }
        return min;
    }


    static void show(int[][] test) {    //테스트용
        for (int[] ints : test) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println("-----" + test_cnt++ + "------");
    }

}