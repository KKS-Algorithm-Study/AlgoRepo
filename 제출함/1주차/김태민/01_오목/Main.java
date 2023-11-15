import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * 문제 접근 방식:
 *      모든 칸을 전부 탐색하면서,
 *      각 칸에서 좌,우,대각선 방향을 탐색해 연속으로 놓여진 돌의 갯수가 5개면 그 위치를 출력하면 됨.
 *
 *      동시에 이기는 경우는 주어지지 않는다.
 *      -> 승리한 경우가 확인되는 즉시 반환할 수 있음
 *
 *      여섯 알 이상이 연속적으로 놓인 경우에는 이긴 것이 아니다.
 *      -> 한 방향으로 놓인 같은 색 돌의 갯수를 세서 정확히 5개가 아니라면 스킵
 *      -> 육목의 두 번째 알에서 탐색하면 5개가 나오는데?
 *          -> 탐색할 방향의 역방향을 먼저 보고, 같은 돌이라면 그 방향 탐색 X
 *
 *      가장 왼쪽에 있는 바둑알 위치를 출력
 *      -> 탐색 방향을 ↗ → ↘ ↓ 로 잡으면, 탐색을 시작하는 위치의 돌이 항상 가장 왼쪽에 있는 바둑알이 됨.
 *
 *  시간복잡도:
 *      19 * 19 바둑판의 모든 칸을 탐색하며, 각각의 칸에서 4개의 방향으로, 최대 6칸까지 탐색
 *      (19 * 19) * (4) * (6)
 */

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

    private static void printMatrix(int[][] matrix) throws Exception {
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                bw.write(matrix[r][c] + " ");
            }
            bw.write("\n");
            bw.flush();
        }
    }

    // 돌 위치 반환, 없으면 {-1, -1}
    private static int[] getWinnerLocation(int[][] board) {

        int boardHeight = board.length;
        int boardWidth = board[0].length;
//                            ↗ → ↘ ↓
        int[] rowDirection = {-1, 0, 1, 1};
        int[] colDirection = { 1, 1, 1, 0};

        for (int r = 0; r < boardHeight; r++) {
            for (int c = 0; c < boardWidth; c++) {
                if (board[r][c] == 0) {
                    continue;
                }

                for (int d = 0; d < 4; d++) {
//                  이전 칸에서 이미 체크한 방향인지 확인 =>
//                  체크할 방향의 역방향에 같은 돌이 있으면 스킵
                    int br = r - rowDirection[d];
                    int bc = c - colDirection[d];
                    boolean hasSameStoneBefore =
                            0 <= br && br < boardHeight &&
                            0 <= bc && bc < boardWidth &&
                            board[r][c] == board[br][bc];
                    if (hasSameStoneBefore) {
                        continue;
                    }

//                  다음 칸들이 현재 칸과 같은 돌인지 확인, 맞으면 count++
                    int count = 1;
                    int nr = r;
                    int nc = c;
                    boolean hasSameStoneAfter = true;
                    while (hasSameStoneAfter && count <= 5) {
                        nr += rowDirection[d];
                        nc += colDirection[d];
                        hasSameStoneAfter =
                                0 <= nr && nr < boardHeight &&
                                0 <= nc && nc < boardWidth &&
                                board[r][c] == board[nr][nc];
                        if (hasSameStoneAfter) {
                            count++;
                        }
                    }

//                  count 가 5라면, 돌 위치 반환
                    if (count == 5) {
                        return new int[]{r, c};
                    }
                }
            }
        }

        return new int[]{-1, -1};
    }

    private static String solve(int[][] board) throws Exception {
        StringJoiner sj = new StringJoiner("\n");

        int[] winnerLocation = getWinnerLocation(board);
        if (winnerLocation[0] == -1) {
            sj.add("0");
        } else {
            sj.add(Integer.toString(board[winnerLocation[0]][winnerLocation[1]]));
            sj.add(String.format("%d %d", winnerLocation[0] + 1, winnerLocation[1] + 1));
        }
        return sj.toString();
    }

    public static void main(String[] args) throws Exception {
        int boardWidth = 19;
        int boardHeight = 19;
        int[][] board = inputMatrix(boardWidth, boardHeight);
//        printMatrix(board);

        String answer = solve(board);
        bw.write(answer);
        bw.flush();
    }
}
