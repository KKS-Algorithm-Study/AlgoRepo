package week1.easy2048;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
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
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //여기까지 데이터 input

        Solution solution = new Solution(N);

        System.out.println(solution.dfs(board, 0, new boolean[] {true, true, true, true}));

    }
}

class Solution {

    int N;

    Solution(int N) {
        this.N = N;
    }

    int dfs(int[][] board, int cnt, boolean[] allow) {
        if(cnt == 5) {
            return getMax(board);
        }

        int[] result = {0, 0, 0, 0};

        if(allow[0]) {
            int[][] board_copy_0 = up(board);
            boolean[] next_allow = {true, true, true, true};
            if(Arrays.deepEquals(board, board_copy_0)) next_allow[0] = false;
            result[0] = dfs(board_copy_0, cnt + 1, next_allow);
        }
        if(allow[1]) {
            int[][] board_copy_1 = left(board);
            boolean[] next_allow = {true, true, true, true};
            if(Arrays.deepEquals(board, board_copy_1)) next_allow[1] = false;
            result[1] = dfs(board_copy_1, cnt + 1, next_allow);
        }
        if(allow[2]) {
            int[][] board_copy_2 = down(board);
            boolean[] next_allow = {true, true, true, true};
            if(Arrays.deepEquals(board, board_copy_2)) next_allow[2] = false;
            result[2] = dfs(board_copy_2, cnt + 1, next_allow);
        }
        if(allow[3]) {
            int[][] board_copy_3 = right(board);
            boolean[] next_allow = {true, true, true, true};
            if(Arrays.deepEquals(board, board_copy_3)) next_allow[3] = false;
            result[3] = dfs(board_copy_3, cnt + 1, next_allow);
        }

        int max = 0;
        for(int num : result) max = num > max ? num : max;

        return max;

    }

    int[][] up(int[][] board) {
        int[][] board_copy = deepCopy(board);

        for (int line = 0; line < N; line++) {

            int s = 0, e = -1;

            while(true) {
                for(int i = s; i < N; i++) {
                    if(board_copy[i][line] != 0) {
                        s = i;
                        break;
                    }
                }
                for(int i = s + 1; i < N; i++) {
                    if(board_copy[i][line] != 0) {
                        e = i;
                        break;
                    }
                }
                if(board_copy[s][line] != 0 && e != -1 && board_copy[s][line] == board_copy[e][line]) {
                    board_copy[s][line] *= 2;
                    board_copy[e][line] = 0;
                }
                s++;
                e = -1;
                if(s >= N - 1) break;
            }

            for(int i = 0; i < N; i++) {
                if(board_copy[i][line] == 0) {
                    for(int j = i + 1; j < N; j++) {
                        if(board_copy[j][line] != 0) {
                            board_copy[i][line] = board_copy[j][line];
                            board_copy[j][line] = 0;
                            break;
                        }
                    }
                }
            }
        }

        return board_copy;
    }

    int[][] down(int[][] board) {
        int[][] board_copy = deepCopy(board);

        for (int line = 0; line < N; line++) {

            int s = N - 1, e = -1;

            while(true) {
                for(int i = s; i >= 0; i--) {
                    if(board_copy[i][line] != 0) {
                        s = i;
                        break;
                    }
                }
                for(int i = s - 1; i >= 0 ; i--) {
                    if(board_copy[i][line] != 0) {
                        e = i;
                        break;
                    }
                }
                if(board_copy[s][line] != 0 && e != -1 && board_copy[s][line] == board_copy[e][line]) {
                    board_copy[s][line] *= 2;
                    board_copy[e][line] = 0;
                }
                s--;
                e = -1;
                if(s <= 0) break;
            }

            for(int i = N - 1; i >= 0; i--) {
                if(board_copy[i][line] == 0) {
                    for(int j = i - 1; j >= 0; j--) {
                        if(board_copy[j][line] != 0) {
                            board_copy[i][line] = board_copy[j][line];
                            board_copy[j][line] = 0;
                            break;
                        }
                    }
                }
            }
        }

        return board_copy;
    }

    int[][] left(int[][] board) {
        int[][] board_copy = deepCopy(board);

        for (int line = 0; line < N; line++) {

            int s = 0, e = -1;

            while(true) {
                for(int i = s; i < N; i++) {
                    if(board_copy[line][i] != 0) {
                        s = i;
                        break;
                    }
                }
                for(int i = s + 1; i < N; i++) {
                    if(board_copy[line][i] != 0) {
                        e = i;
                        break;
                    }
                }
                if(board_copy[line][s] != 0 && e != -1 && board_copy[line][s] == board_copy[line][e]) {
                    board_copy[line][s] *= 2;
                    board_copy[line][e] = 0;
                }
                s++;
                e = -1;
                if(s >= N - 1) break;
            }

            for(int i = 0; i < N; i++) {
                if(board_copy[line][i] == 0) {
                    for(int j = i + 1; j < N; j++) {
                        if(board_copy[line][j] != 0) {
                            board_copy[line][i] = board_copy[line][j];
                            board_copy[line][j] = 0;
                            break;
                        }
                    }
                }
            }
        }

        return board_copy;
    }

    int[][] right(int[][] board) {
        int[][] board_copy = deepCopy(board);

        for (int line = 0; line < N; line++) {

            int s = N - 1, e = -1;

            while(true) {
                for(int i = s; i >= 0; i--) {
                    if(board_copy[line][i] != 0) {
                        s = i;
                        break;
                    }
                }
                for(int i = s - 1; i >= 0 ; i--) {
                    if(board_copy[line][i] != 0) {
                        e = i;
                        break;
                    }
                }
                if(board_copy[line][s] != 0 && e != -1 && board_copy[line][s] == board_copy[line][e]) {
                    board_copy[line][s] *= 2;
                    board_copy[line][e] = 0;
                }
                s--;
                e = -1;
                if(s <= 0) break;
            }

            for(int i = N - 1; i >= 0; i--) {
                if(board_copy[line][i] == 0) {
                    for(int j = i - 1; j >= 0; j--) {
                        if(board_copy[line][j] != 0) {
                            board_copy[line][i] = board_copy[line][j];
                            board_copy[line][j] = 0;
                            break;
                        }
                    }
                }
            }
        }

        return board_copy;
    }

    int[][] deepCopy(int[][] board) {    //배열 깊은 복사
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, copy[i].length);
        }
        return copy;
    }

    int getMax(int[][] board) {
        int max = 0;
        for (int[] nums : board) {
            for(int num : nums) max = num > max ? num : max;
        }
        return max;
    }

    void show(int[][] board) {  //테스트용
        for (int[] nums : board) {
            System.out.println(Arrays.toString(nums));
        }
    }

}