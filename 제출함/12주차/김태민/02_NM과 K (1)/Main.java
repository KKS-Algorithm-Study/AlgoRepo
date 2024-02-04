import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *  문제 풀이 방법:
 *
 *      숫자를 최대 4개 고를 수 있기 때문에,
 *      백트래킹을 이용해 숫자를 골라도 최대 O(N*M C K) = 약 4백만 이므로 가능하다.
 *
 *      재귀적으로 숫자를 고르고, 고른 숫자와 그 상하좌우를 방문처리해 숫자를 고르면 된다.
 *
 *  시간 복잡도:
 *      O(N*M C K)
 */
public class Main {

    static final int[] dr = new int[]{0, 1, 0, -1};
    static final int[] dc = new int[]{1, 0, -1, 0};
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static List<Integer> inputNumbers(int numberCount) throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static List<List<Integer>> getAdjacentSquares(int row, int col, int[][] numbers, boolean[][] isSelected) {
        List<List<Integer>> result = new ArrayList<>();
        for (int d = 0; d < 4; d++) {
            int nrow = row + dr[d];
            int ncol = col + dc[d];
            if (nrow < 0 || nrow >= numbers.length || ncol < 0 || ncol >= numbers[0].length) {
                continue;
            }
            if (isSelected[nrow][ncol]) {
                continue;
            }
            result.add(List.of(nrow, ncol));
        }
        return result;
    }

    private static int dfsSelectNumbers(int[][] numbers, int selectCount, boolean[][] isSelected) {
        if (selectCount == 0) {
            return 0;
        }
        int result = 0;
        for (int row = 0; row < numbers.length; row++) {
            for (int col = 0; col < numbers[0].length; col++) {
                if (isSelected[row][col]) {
                    continue;
                }
                isSelected[row][col] = true;
                List<List<Integer>> adjSquares = getAdjacentSquares(row, col, numbers, isSelected);
                for (List<Integer> coordinates : adjSquares) {
                    isSelected[coordinates.get(0)][coordinates.get(1)] = true;
                }
                int dfsResult = numbers[row][col] + dfsSelectNumbers(numbers, selectCount - 1, isSelected);
                isSelected[row][col] = false;
                for (List<Integer> coordinates : adjSquares) {
                    isSelected[coordinates.get(0)][coordinates.get(1)] = false;
                }
                result = Math.max(result, dfsResult);
            }
        }
        return result;
    }

    private static String solve(int[][] numbers, int selectCount) {
        boolean[][] isSelected = new boolean[numbers.length][numbers[0].length];
//        System.out.println(getAdjacentSquares(0, 0, new int[][] {{1, 2}, {3, 4}}, new boolean[][]{{false, false}, {false, false}}));
        int sum = dfsSelectNumbers(numbers, selectCount, isSelected);
        return Integer.toString(sum);
    }


    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int K = nextInt();
            int[][] numbers = inputMatrixNumbers(N, M);
            String answer = solve(numbers, K);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int[][] inputMatrixNumbers(int rowCount, int colCount) throws Exception {
        int[][] result = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            result[row] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return result;
    }
}
