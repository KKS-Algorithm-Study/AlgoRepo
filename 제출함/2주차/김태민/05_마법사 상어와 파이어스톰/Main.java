import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 */

class Ice {
    private static int[] rowDirections = new int[]{0, 1, 0, -1};
    private static int[] colDirections = new int[]{1, 0, -1, 0};

    public int[][] ices;
    public int iceHeight;
    public int iceWidth;

    public Ice(int[][] ices) {
        this.ices = ices;
        this.iceHeight = ices.length;
        this.iceWidth = ices[0].length;
    }

    public void rotateIce(int size) {
        int squareSize = (int) Math.pow(2, size);
        for (int rowSquare = 0; rowSquare < iceHeight; rowSquare += squareSize) {
            for (int colSquare = 0; colSquare < iceWidth; colSquare += squareSize) {
                int topRowIdx = rowSquare;
                int botRowIdx = rowSquare + squareSize - 1;
                int lftColIdx = colSquare;
                int rgtColIdx = colSquare + squareSize - 1;

                for (int rowTurn = 0; rowTurn < squareSize / 2; rowTurn++) {
                    for (int colTurn = 0; colTurn < squareSize / 2; colTurn++) {

                        int lftTopRow = topRowIdx + rowTurn;
                        int lftTopCol = lftColIdx + colTurn;
                        int rgtTopRow = topRowIdx + colTurn;
                        int rgtTopCol = rgtColIdx - rowTurn;
                        int rgtBotRow = botRowIdx - rowTurn;
                        int rgtBotCol = rgtColIdx - colTurn;
                        int lftBotRow = botRowIdx - colTurn;
                        int lftBotCol = lftColIdx + rowTurn;

                        int[][] coordinates = new int[][]{
                                {lftBotRow, lftBotCol}, {rgtBotRow, rgtBotCol}, {rgtTopRow, rgtTopCol},
                                {lftTopRow, lftTopCol},
                        };

                        swapCoordinates(coordinates);
                    }
                }
            }
        }
    }

    private void swapCoordinates(int[][] coordinates) {
        int tmp = ices[coordinates[0][0]][coordinates[0][1]];
        for (int idx = 0; idx < coordinates.length - 1; idx++) {
            ices[coordinates[idx][0]][coordinates[idx][1]] = ices[coordinates[idx + 1][0]][coordinates[idx + 1][1]];
        }
        ices[coordinates[coordinates.length - 1][0]][coordinates[coordinates.length - 1][1]] = tmp;
    }

    public void meltIce() {
        List<int[]> blocksToMelt = new ArrayList<>();
        for (int row = 0; row < iceHeight; row++) {
            for (int col = 0; col < iceWidth; col++) {
                if (ices[row][col] == 0) {
                    continue;
                }

                int adjIceCount = 0;
                for (int d = 0; d < 4; d++) {
                    int nxtRow = row + rowDirections[d];
                    int nxtCol = col + colDirections[d];
                    if (!isIce(nxtRow, nxtCol)) {
                        continue;
                    }
                    adjIceCount++;
                }
                if (adjIceCount >= 3) {
                    continue;
                }
                blocksToMelt.add(new int[]{row, col});
            }
        }

        for (int[] coordinate : blocksToMelt) {
            ices[coordinate[0]][coordinate[1]]--;
        }
    }

    private boolean isIce(int row, int col) {
        return 0 <= row && row < iceHeight && 0 <= col && col < iceWidth &&
                ices[row][col] != 0;
    }

    public int getIceSum() {
        return Arrays.stream(ices)
                .flatMapToInt(Arrays::stream)
                .sum();
    }

    public int getIceBiggestBlockCount() {
        boolean[][] visited = new boolean[iceHeight][iceWidth];
        int result = 0;

        for (int row = 0; row < iceHeight; row++) {
            for (int col = 0; col < iceWidth; col++) {
                if (ices[row][col] == 0 || visited[row][col]) {
                    continue;
                }

                Queue<int[]> queue = new LinkedList<>();
                queue.offer(new int[]{row, col});
                visited[row][col] = true;
                int blockCount = 0;

                while (!queue.isEmpty()) {
                    blockCount++;
                    int[] coordinate = queue.poll();
                    for (int d = 0; d < 4; d++) {
                        int nxtRow = coordinate[0] + rowDirections[d];
                        int nxtCol = coordinate[1] + colDirections[d];
                        if (!isIce(nxtRow, nxtCol) || visited[nxtRow][nxtCol]) {
                            continue;
                        }
                        visited[nxtRow][nxtCol] = true;
                        queue.offer(new int[]{nxtRow, nxtCol});
                    }
                }
                result = Math.max(result, blockCount);
            }
        }
        return result;
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

    private static String solve(int[][] board, int[] queries) throws Exception {
        Ice ice = new Ice(board);
        for (int query : queries) {
            ice.rotateIce(query);
//            printMatrix(ice.ices);
            ice.meltIce();
            printMatrix(ice.ices);
        }

        StringJoiner answer = new StringJoiner("\n");
        answer.add(Integer.toString(ice.getIceSum()));
        answer.add(Integer.toString(ice.getIceBiggestBlockCount()));
        return answer.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int Q = nextInt();
            int[][] matrix = inputMatrix((int) Math.pow(2, N), (int) Math.pow(2, N));
            int[] queries = inputQueries(Q);

            String answer = solve(matrix, queries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int[] inputQueries(int queryCount) throws Exception {
        int[] queries = new int[queryCount];
        tokenize();
        for (int idx = 0; idx < queryCount; idx++) {
            queries[idx] = nextInt();
        }
        return queries;
    }

}
