import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      숫자 행렬이 주어질 때, 인접한 네 칸의 합이 최대인 경우를 출력하는 문제
 *      라고만 설명했다면 어려웠겠지만, 테트로미노라는 블럭으로 힌트를 준 문제다
 *
 *      테트로미노를 뒤집고 돌려서 나올 수 있는 형태는 총 19가지,
 *      이를 아래의 그림처럼 0과 1의 행렬로 표현했을 때, 최대 크기는 6칸이 나온다.
 *      {{1, 1, 1}
 *      ,{0, 1, 0}}
 *      이 행렬들을 각 칸마다 놓아서 1과 겹치는 칸의 숫자 합을 구한다고 했을 때
 *      연산 횟수는 N * M * 19 * 6 = 28,500,000으로 완전 탐색이 가능하다는 결론이 나온다.
 *
 *  시간 복잡도:
 *      위에서 설명했듯이 각 칸마다 탐색하므로,
 *      O(N*M)이다.
 */
public class Main {
    static int INF = 51;
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static int[][] inputNumberMatrix(int rowCount, int colCount) throws Exception {
        int[][] result = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            result[row] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return result;
    }

    private static boolean canPlaceTetromino(Tetromino tetromino, int row, int col, int boardHeight, int boardWidth) {
        boolean checkHorizontal = col + tetromino.width <= boardWidth;
        boolean checkVertical = row + tetromino.height <= boardHeight;
        return checkHorizontal & checkVertical;
    }

    private static int getCoverageSum(Tetromino tetromino, int initRow, int initCol, int[][] board) {
        if (!canPlaceTetromino(tetromino, initRow, initCol, board.length, board[0].length)) {
            return 0;
        }
        int sum = 0;
        for (int row = 0; row < tetromino.height; row++) {
            for (int col = 0; col < tetromino.width; col++) {
                if (!tetromino.isInCoverage(row, col)) {
                    continue;
                }
                sum += board[row + initRow][col + initCol];
            }
        }
        return sum;
    }

    private static String solve(int[][] numbers) {
        int result = 0;
        int height = numbers.length;
        int width = numbers[0].length;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                for (Tetromino tetromino: Tetromino.values()) {
                    int sum = getCoverageSum(tetromino, row, col, numbers);
                    result = Math.max(result, sum);
                }
            }
        }

        return Integer.toString(result);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int[][] numbers = inputNumberMatrix(N, M);
            String answer = solve(numbers);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

enum Tetromino {
    I1(new int[][]{
            {1, 1, 1, 1}
    }),
    I2(new int[][]{
            {1},
            {1},
            {1},
            {1}
    }),
    O(new int[][]{
            {1, 1},
            {1, 1}
    }),
    L(new int[][]{
            {1, 0},
            {1, 0},
            {1, 1}
    }),
    L90(new int[][]{
            {1, 1, 1},
            {1, 0, 0}
    }),
    L180(new int[][]{
            {1, 1},
            {0, 1},
            {0, 1}
    }),
    L270(new int[][]{
            {0, 0, 1},
            {1, 1, 1},
    }),
    Lf(new int[][]{
            {0, 1},
            {0, 1},
            {1, 1}
    }),
    Lf90(new int[][]{
            {1, 0, 0},
            {1, 1, 1},
    }),
    Lf180(new int[][]{
            {1, 1},
            {1, 0},
            {1, 0}
    }),
    Lf270(new int[][]{
            {1, 1, 1},
            {0, 0, 1}
    }),
    S(new int[][]{
            {1, 0},
            {1, 1},
            {0, 1}
    }),
    S90(new int[][]{
            {0, 1, 1},
            {1, 1, 0}
    }),
    Sf(new int[][]{
            {0, 1},
            {1, 1},
            {1, 0}
    }),
    Sf90(new int[][]{
            {1, 1, 0},
            {0, 1, 1}
    }),
    T(new int[][]{
            {1, 1, 1},
            {0, 1, 0}
    }),
    T90(new int[][]{
            {0, 1},
            {1, 1},
            {0, 1}
    }),
    T180(new int[][]{
            {0, 1, 0},
            {1, 1, 1}
    }),
    T270(new int[][]{
            {1, 0},
            {1, 1},
            {1, 0}
    });

    int[][] coverage;
    int width;
    int height;

    Tetromino(int[][] coverage) {
        this.coverage = coverage;
        this.height = coverage.length;
        this.width = coverage[0].length;
    }

    public boolean isInCoverage(int row, int col) {
        return coverage[row][col] == 1;
    }
}