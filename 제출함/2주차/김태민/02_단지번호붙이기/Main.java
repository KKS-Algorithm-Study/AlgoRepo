import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 */

class Coordinate {

    public static int[] rowDirection = new int[] {0, 1, 0, -1};
    public static int[] colDirection = new int[] {1, 0, -1, 0};

    public int row;
    public int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
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
            String[] line = br.readLine().split("");
            for (int c = 0; c < col; c++) {
                matrix[r][c] = Integer.parseInt(line[c]);
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

    private static boolean needsCounting(int row, int col, int[][] houses, boolean[][] visited) {
        return 0 <= row && row < houses.length && 0 <= col && col < houses[0].length &&
                houses[row][col] == 1 && !visited[row][col];
    }

    private static String solve(int[][] houses) {
        List<Integer> houseCounts = new ArrayList<>();

        int rowLength = houses.length;
        int colLength = houses[0].length;

        boolean[][] visited = new boolean[rowLength][colLength];

        for (int row = 0; row < rowLength; row++) {
            for (int col = 0; col < colLength; col++) {
                if (!needsCounting(row, col, houses, visited)) {
                    continue;
                }
                int houseCount = countConnected(row, col, houses, visited);
                houseCounts.add(houseCount);
            }
        }

        houseCounts.sort(null);
        String counts = houseCounts.stream()
                .map(num -> Integer.toString(num))
                .collect(Collectors.joining("\n"));
        return houseCounts.size() + "\n" + counts;
    }

    private static int countConnected(int initRow, int initCol, int[][] houses, boolean[][] visited) {
        visited[initRow][initCol] = true;
        List<Coordinate> curQue = new ArrayList<>();
        curQue.add(new Coordinate(initRow, initCol));
        int count = 1;
        while (true) {
            List<Coordinate> nxtQue = new ArrayList<>();
            for (Coordinate cur : curQue) {
                for (int d = 0; d < 4; d++) {
                    int nxtRow = cur.row + Coordinate.rowDirection[d];
                    int nxtCol = cur.col + Coordinate.colDirection[d];
                    if (!needsCounting(nxtRow, nxtCol, houses, visited)) {
                        continue;
                    }
                    count++;
                    visited[nxtRow][nxtCol] = true;
                    nxtQue.add(new Coordinate(nxtRow, nxtCol));
                }
            }
            if (nxtQue.size() == 0) {
                break;
            }
            curQue = nxtQue;
        }
        return count;
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[][] houses = inputMatrix(N, N);

//            printMatrix(houses);

            String answer = solve(houses);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
