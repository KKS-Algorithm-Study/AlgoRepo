import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      왼쪽 위에서 오른쪽 아래로 벽을 피해 이동하되, 주어진 점프 횟수만큼 나이트처럼 벽을 넘어 점프하면서,
 *      동작 수의 최솟값을 출력하는 문제.
 *
 *      오른쪽 아래로 이동해야 하지만, 벽을 피할 때 위나 왼쪽으로도 이동할 수 있음
 *      -> 상하좌우 모두 이동할 수 있게 하면서, 방문 배열을 이용해 이미 탐색한 곳은 탐색하지 않도록 함.
 *
 *      동작 수의 최솟값이 필요
 *      -> BFS로 탐색하면 가장 먼저 목표 좌표에 도달하는 경우 == 이동의 최솟값
 *
 *      점프 횟수가 남아있으면 점프할 수 있어야 함
 *      -> BFS로 저장할 때, {현재 좌표, 남은 점프 횟수} 모두 저장해야 함
 *
 *      같은 좌표라도, 점프 횟수를 소진하고 도달한 경우와 소진하지 않고 도달한 경우가 다를 수 있음
 *      ex)
 *      점프 횟수 = 1
 *      지도는 아래와 같을 때,
 *      0 0 0 1 0
 *      0 0 0 1 0
 *      맨 처음 점프를 사용해 (0, 0) -> (1, 2)로 이동하면 목표 지점에 도달할 수 없지만,
 *      점프를 소진하지 않고 오른쪽으로 2번 밑으로 1번 (0, 0) -> (1, 2) 이동한 후, 점프를 사용해 (1, 2) -> (0, 4) 이동하면 도달 가능
 *      -> 방문 여부를 남은 점프 횟수마다 다르게 확인해야 하므로, 다음 두 방법 중 택 일
 *          1. 방문 배열에 방문 여부를 boolean으로 저장해 3차원으로 사용
 *          2. 이전에 방문했을 때보다 남은 점프 횟수가 많다면 방문해도 무방함
 *          -> 방문 배열에 남은 점프 횟수를 int로 저장해 2차원으로 사용
 *
 * 시간 복잡도:
 *      BFS 시간 복잡도(E + V)로 계산,
 *      정점의 갯수는 방문 배열의 칸 수 = W * H * K
 *      간선의 갯수는 모든 정점 갯수마다 탐색 가능한 방향 (상하좌우 + 나이트) = W * H * K * 12
 *      O(W * H * K)
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

        // [세로][가로][남은 점프 횟수]로 방문 여부를 확인하는 3차원 배열
        boolean[][][] visited = new boolean[boardHeight][boardWidth][jumps+1];
        visited[0][0][jumps] = true;

        List<Monkey> curQue = new ArrayList<>();
        curQue.add(new Monkey(0, 0, jumps));
        // 몇 번의 움직임으로 도착했는지 확인하기 위해, 각 단계마다 +1
        int moves = 0;

        while (true) {
            List<Monkey> nxtQue = new ArrayList<>();

            // moves 번째의 턴에 이동할 수 있는 칸들마다 탐색 진행
            for (Monkey curMonkey : curQue) {

                // 목표 좌표에 도달한 경우, moves 반환
                if (curMonkey.row == boardHeight-1 && curMonkey.col == boardWidth-1) {
                    return Integer.toString(moves);
                }

                int curJump = curMonkey.jumpLeft;
                // 상하좌우 탐색
                for (int moveD = 0; moveD < 4; moveD++) {
                    int nxtRow = curMonkey.row + Monkey.moveRow[moveD];
                    int nxtCol = curMonkey.col + Monkey.moveCol[moveD];
                    if (!needsCheck(nxtRow, nxtCol, curJump, board, visited)) {
                        continue;
                    }
                    visited[nxtRow][nxtCol][curJump] = true;
                    nxtQue.add(new Monkey(nxtRow, nxtCol, curJump));
                }

                // 점프 횟수가 없으면 나이트 이동 불가능
                if (curJump == 0) {
                    continue;
                }
                int nxtJump = curJump-1;
                // 나이트 이동 탐색
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

            // 다음 큐에 탐색할 칸이 없는 경우 == 더 이상 이동할 곳이 없는 경우 == 도착하지 못하는 경우
            if (nxtQue.isEmpty()) {
                break;
            }

            // 다음 턴으로 넘어갈 준비
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
