import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      시뮬레이션을 돌리는 간단한 문제
 *      방 정보와 로봇 시작 위치 + 방향을 받아서,
 *      로봇 위치부터 반시계방향으로 돌리며 먼지를 확인하고,
 *      먼지가 있으면 그 방향으로 가고, 네 방향 아무데도 먼지가 없으면 뒤로 가는 식으로
 *      총 몇 칸 청소했는지 확인하는 문제
 *
 *      주의해야할 점은
 *      입력으로 받는 로봇 방향은 북, 동, 남, 서이지만,
 *      로봇의 먼지 확인 방향은 북, 서, 남, 동 이라 거꾸로 돌려야한다는 점이다.
 * 시간복잡도:
 *      (N * M)방의 크기 * 4 방향
 *      O(N*M)
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

    private static String solve(int[][] room, int curRow, int curCol, int curDir) throws Exception {

        int[] dirRow = {-1, 0, 1, 0};
        int[] dirCol = {0, 1, 0, -1};

        boolean isWorking = true;
        int count = 1;

        while (isWorking) {
            room[curRow][curCol] = 2;
            boolean hasMoved = false;
            for (int d = 1; d < 5; d++) {
                int nxtDir = (curDir - d + 4) % 4;
                int nxtRow = curRow + dirRow[nxtDir];
                int nxtCol = curCol + dirCol[nxtDir];
                if (room[nxtRow][nxtCol] == 0) {
                    count++;
                    curDir = nxtDir;
                    curRow = nxtRow;
                    curCol = nxtCol;
                    hasMoved = true;
                    break;
                }
            }
            if (hasMoved) {
                continue;
            }
            curRow = curRow - dirRow[curDir];
            curCol = curCol - dirCol[curDir];
            if (room[curRow][curCol] == 1) {
                isWorking = false;
            }
        }
//        System.out.println(curDir);
//        System.out.println(curRow);
//        System.out.println(curCol);
//        printMatrix(room);

        return Integer.toString(count);
    }

    public static void main(String[] args) throws Exception {

//      ------- 인풋 --------
        tokenize();
        int boardWidth = nextInt();
        int boardHeight = nextInt();
        tokenize();
        int startRow = nextInt();
        int startCol = nextInt();
        int startDir = nextInt();
        int[][] room = inputMatrix(boardWidth, boardHeight);
//        printMatrix(room);

//      ------- 아웃풋 -------

        String answer = solve(room, startRow, startCol, startDir);
        bw.write(answer);
        bw.flush();
    }
}
