import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 */

class Monkey {

    public static final int[] moveRow = new int[] {0, 1, 0, -1};
    public static final int[] moveCol = new int[] {1, 0, -1, 0};
    public static final int[] jumpRow = new int[] {-2, -1, 1, 2, 2, 1, -1, -2};
    public static final int[] jumpCol = new int[] {1, 2, 2, 1, -1, -2, -2, -1};

    int row;
    int col;
    int jumpLeft;

    public Monkey(int row, int col, int jumpLeft) {
        this.row = row;
        this.col = col;
        this.jumpLeft = jumpLeft;
    }
}

public class Main {

    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static int[][] inputMatrix(int row, int col) throws Exception {
        int[][] matrix = new int[row][col];
        for (int r = 0; r < row; r++) {
            tokenize();
            for (int c = 0; c < col; c++) {
                matrix[r][c] = nextInt();
            }
        }
        return matrix;
    }

    private static boolean needsCheck(int row, int col, int jumps, int[][] board, boolean[][][] visited) {
        return 0 <= row && row < board.length &&
                0 <= col && col < board[0].length &&
                board[row][col] == 0 &&
                !visited[row][col][jumps];
    }

    private static String solve(int[][] board, int jumps) {

        int boardHeight = board.length;
        int boardWidth = board[0].length;

        boolean[][][] visited = new boolean[boardHeight][boardWidth][jumps+1];
        visited[0][0][jumps] = true;

        List<Monkey> curQue = new ArrayList<>();
        curQue.add(new Monkey(0, 0, jumps));
        int moves = 0;

        while (true) {
            List<Monkey> nxtQue = new ArrayList<>();

            for (Monkey curMonkey : curQue) {
                if (curMonkey.row == boardHeight-1 && curMonkey.col == boardWidth-1) {
                    return Integer.toString(moves);
                }
                int curJump = curMonkey.jumpLeft;
                for (int moveD = 0; moveD < 4; moveD++) {
                    int nxtRow = curMonkey.row + Monkey.moveRow[moveD];
                    int nxtCol = curMonkey.col + Monkey.moveCol[moveD];
                    if (!needsCheck(nxtRow, nxtCol, curJump, board, visited)) {
                        continue;
                    }
                    visited[nxtRow][nxtCol][curJump] = true;
                    nxtQue.add(new Monkey(nxtRow, nxtCol, curJump));
                }
                if (curJump == 0) {
                    continue;
                }
                int nxtJump = curJump-1;
                for (int jumpD = 0; jumpD < 8; jumpD++) {
                    int nxtRow = curMonkey.row + Monkey.jumpRow[jumpD];
                    int nxtCol = curMonkey.col + Monkey.jumpCol[jumpD];

                    if (!needsCheck(nxtRow, nxtCol, nxtJump, board, visited)) {
                        continue;
                    }
                    visited[nxtRow][nxtCol][nxtJump] = true;
                    nxtQue.add(new Monkey(nxtRow, nxtCol, nxtJump));
                }
            }

            if (nxtQue.isEmpty()) {
                break;
            }
            curQue = nxtQue;
            moves++;
        }

        return Integer.toString(-1);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int K = nextInt();
            tokenize();
            int W = nextInt();
            int H = nextInt();
            int[][] matrix = inputMatrix(H, W);

            String answer = solve(matrix, K);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
