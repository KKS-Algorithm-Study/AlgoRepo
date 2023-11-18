import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 접근법:
 *      최대 20 * 20 크기의 게임판을, 5번을 임의로 움직여서 얻을 수 있는 가장 큰 블록의 숫자를 출력하는 문제.
 *
 *      블록 이동 방법 => 투포인터
 *      일단 0으로 가득 찬 새로운 2차원 배열을 만들고, 이동하는 블럭을 투포인터를 이용해 블록을 합치며 저장해줌.
 *
 *      만약 완전 탐색을 한다면,
 *
 *      1. 4방향으로 5번을 임의로 이동하는 모든 경우의 수 = 4 ** 5 = 1024,
 *         그 경우의 수가 나오기 위한 게임판의 상태의 수 = (1 + 4 + 16 + 64 + 256) = 341
 *
 *      2-1. 한 번의 이동 == 새로운 경우 이므로, 하나의 경우를 계속해서 수정하는 건 안됨, 따라서 둘 중 하나는 해야함
 *          a. 이동한 걸 거꾸로 돌려서 이전의 상태로 만듦 => 어려움
 *          b. 새로운 게임판을 만들어 블록 이동을 저장함 => 2차원 배열만 선언해주면 되니 쉬움
 *
 *      2-2. 블록 이동은 모든 칸을 탐색하므로 최대 20 * 20 = 400
 *
 *      즉, 모든 게임판의 상태들(341)에서 4개 방향으로, 각 방향마다 블록 이동 해줘야 함(400)
 *      그렇게 구한 모든 경우의 수(1024)마다 각 칸을 탐색(400)해 블록의 최대값을 구해줘야 함.
 *
 *      연산 횟수 (341 * 4 * 400) + (1024 * 400) = 545,600 + 409,600 = 약 백만이므로 완전탐색으로 할만 함.
 *
 *  시간 복잡도:
 *      K번 움직임인다고 했을 때,
 *      O(4**K * N**2)
 */

class Board {

    public final int[][] board;
    public final int sideLength;
    public final int[] dr = {0, 1, 0, -1};
    public final int[] dc = {1, 0, -1, 0};


    private Board(int[][] board) {
        this.board = board;
        this.sideLength = board.length;
    }

    public static Board createBoard(int[][] board) {
        return new Board(board);
    }

    public int getHighestBlock() {
        return Arrays.stream(board)
                .flatMapToInt(Arrays::stream)
                .max()
                .orElse(0);
    }

    public void printBoard() {
        for (int row = 0; row < sideLength; row++) {
            for (int col = 0; col < sideLength; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public Board moveUp() {
        int[][] resultBoard = new int[sideLength][sideLength];
        for (int col = 0; col < sideLength; col++) {
            int resultPointer = 0;
            for (int row = 0; row < sideLength; row++) {
                // 이동 숫자가 0이면 넘어가고,
                if (this.board[row][col] == 0) {
                    continue;
                }
                // 새배열 숫자가 0이면 포인터 놔두고 저장하고,
                if (resultBoard[resultPointer][col] == 0) {
                    resultBoard[resultPointer][col] = this.board[row][col];
                    continue;
                }
                // 새배열 숫자와 이동 숫자가 같으면 두배로 만들고 포인터 올려주고,
                if (resultBoard[resultPointer][col] == this.board[row][col]) {
                    resultBoard[resultPointer++][col] *= 2;
                    continue;
                }
                // 새배열 숫자와 이동 숫자가 다르면 포인터 올리고 저장하고,
                resultBoard[++resultPointer][col] = this.board[row][col];
            }
        }
        return Board.createBoard(resultBoard);
    }

    public Board moveLeft() {
        int[][] resultBoard = new int[sideLength][sideLength];
        for (int row = 0; row < sideLength; row++) {
            int resultPointer = 0;
            for (int col = 0; col < sideLength; col++) {
                if (this.board[row][col] == 0) {
                    continue;
                }
                if (resultBoard[row][resultPointer] == 0) {
                    resultBoard[row][resultPointer] = this.board[row][col];
                    continue;
                }
                if (resultBoard[row][resultPointer] == this.board[row][col]) {
                    resultBoard[row][resultPointer++] *= 2;
                    continue;
                }
                resultBoard[row][++resultPointer] = this.board[row][col];
            }
        }
        return Board.createBoard(resultBoard);
    }

    public Board moveDown() {
        int[][] resultBoard = new int[sideLength][sideLength];
        for (int col = 0; col < sideLength; col++) {
            int resultPointer = sideLength - 1;
            for (int row = sideLength - 1; row >= 0; row--) {
                if (this.board[row][col] == 0) {
                    continue;
                }
                if (resultBoard[resultPointer][col] == 0) {
                    resultBoard[resultPointer][col] = this.board[row][col];
                    continue;
                }
                if (resultBoard[resultPointer][col] == this.board[row][col]) {
                    resultBoard[resultPointer--][col] *= 2;
                    continue;
                }
                resultBoard[--resultPointer][col] = this.board[row][col];
            }
        }
        return Board.createBoard(resultBoard);
    }

    public Board moveRight() {
        int[][] resultBoard = new int[sideLength][sideLength];
        for (int row = 0; row < sideLength; row++) {
            int resultPointer = sideLength - 1;
            for (int col = sideLength - 1; col >= 0; col--) {
                if (this.board[row][col] == 0) {
                    continue;
                }
                if (resultBoard[row][resultPointer] == 0) {
                    resultBoard[row][resultPointer] = this.board[row][col];
                    continue;
                }
                if (resultBoard[row][resultPointer] == this.board[row][col]) {
                    resultBoard[row][resultPointer--] *= 2;
                    continue;
                }
                resultBoard[row][--resultPointer] = this.board[row][col];
            }
        }
        return Board.createBoard(resultBoard);
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

    private static void printMatrix(int[][] matrix) throws Exception {
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                bw.write(matrix[r][c] + " ");
            }
            bw.write("\n");
            bw.flush();
        }
    }

    private static String solve(int[][] board) throws Exception {
        int answer = 0;
        Board boardOriginal = Board.createBoard(board);
//        boardOriginal.printBoard();

        List<Board> curQue = new ArrayList<>();
        List<Board> nxtQue;
        curQue.add(boardOriginal);

        for (int turn = 1; turn <= 5; turn ++) {
            System.out.println(curQue.size());
            nxtQue = new ArrayList<>();

            for (Board curBoard : curQue) {
                nxtQue.add(curBoard.moveUp());
                nxtQue.add(curBoard.moveLeft());
                nxtQue.add(curBoard.moveDown());
                nxtQue.add(curBoard.moveRight());
            }

            curQue = nxtQue;
        }

        answer = curQue.stream()
                .map(Board::getHighestBlock)
//                .peek(System.out::println)
                .max(Comparator.naturalOrder())
                .orElse(0);

        return Integer.toString(answer);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int boardLength = nextInt();
            int[][] board = inputMatrix(boardLength, boardLength);
//            printMatrix(board);

            String answer = solve(board);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
