import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * 문제 접근 방법:
 *      지도에 1로 표시된 집들 중 상하좌우로 연결된 집들을 하나로 묶어 단지라고 했을 때,
 *      단지의 갯수와 각 단지의 크기를 오름차순으로 출력하는 문제.
 *
 *      단순히 이중 for문으로 각 칸마다 상하좌우만 보고, 단지 번호가 있는 칸이 있을 때 그 번호를 따라 매겨주는 방법으로 하면
 *      1 0 1
 *      1 1 1
 *      위와 같은 단지는 같은 번호로 묶어줄 수 없다.
 *
 *      따라서 이중 for문으로 탐색하다, 단지로 묶이지 않은 집을 보면,
 *      BFS나 DFS로 주변의 모든 집을 하나의 단지로 묶는 방식으로 진행해야 한다.
 *
 *      단지 갯수를 저장할 int, 단지 크기를 저장할 List<Integer>를 초기화하고,
 *      단지를 하나 탐색할 때마다 갯수에 +1, 크기 리스트에 크기를 추가하고,
 *      출력 전에 크기 리스트를 정렬해 출력하면 된다.
 *
 * 시간 복잡도:
 *      BFS와 DFS 모두 O(정점의 갯수 + 간선의 갯수) 라는 시간복잡도를 가지는데,
 *      이번 문제와 같은 2차원 행렬 탐색의 경우,
 *      정점의 갯수: 2차원 행렬의 칸 갯수 (정확히는 방문 배열의 칸 갯수) = N * N
 *      간선의 갯수: 모든 칸에서 탐색해야할 상하좌우 방향 = N * N * 4
 *      이므로, (N * N) + (N * N + 4) 가 나오는데, 상수항 제거하고 영향력 큰 쪽만 남기면
 *
 *      O(N * N) or O(N ** 2)
 */

class Coordinate {

    public static int[] rowDirection = new int[]{0, 1, 0, -1};
    public static int[] colDirection = new int[]{1, 0, -1, 0};

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
