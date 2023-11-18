import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * 문제 접근법:
 *      최대 50 크기의 사각형이 주어졌을 때, 회전 연산을 적용한 후 각 행 숫자의 합 중 최솟값을 구하는 문제
 *
 *      회전 연산은 임의로 적용 가능, 회전 연산 순서가 바뀌면 결과가 특정 패턴 없이 달라짐
 *      -> 모든 연산 순서를 순열로 뽑고, 직접 연산해봐야함
 *
 *
 * 시간 복잡도:
 *      최대 6개의 쿼리 중 모두를 고르는 순열 조합(kPk) 중에서, 최대 50 크기의 정사각형(N**2)을 모두 바꿔줌
 *      O(kPk * N ** 2)
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

    private static int[][] copyMatrix(int[][] matrix) throws Exception {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        for (int r = 0; r < matrix.length; r++) {
            newMatrix[r] = matrix[r].clone();
        }
        return newMatrix;
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

    private static void swapElements(int[] elements, int indexFrom, int indexTo) {
        int tmp = elements[indexFrom];
        elements[indexFrom] = elements[indexTo];
        elements[indexTo] = tmp;
    }


    // 스왑으로 순열을 구하는 함수
    private static void getPermutations(List<int[]> repository, int[] elements, int count) {
        if (count == 0) {
            repository.add(elements.clone());
            return;
        }

        int currentIndex = elements.length - count;

        for (int nextIndex = currentIndex; nextIndex < elements.length; nextIndex++) {
            swapElements(elements, currentIndex, nextIndex);
            getPermutations(repository, elements, count-1);
            swapElements(elements, nextIndex, currentIndex);
        }
    }

    private static String solve(int[][] board, int[][] queries) throws Exception {

        // 6개의 쿼리를 임의의 순서로 실행할 수 있음
        List<int[]> permutations = new ArrayList<>();
        int[] indice = IntStream.range(0, queries.length).toArray();
        getPermutations(permutations, indice, indice.length);
//        permutations.forEach(orders -> System.out.println(Arrays.toString(orders)));

        int answer = Integer.MAX_VALUE;

        for (int[] order: permutations) {
            // 원본은 놔두고 새로 복사한 board로 진행
            int[][] boardResult = copyMatrix(board);
//            printMatrix(boardResult);
            // row, col, size 크기로 시계방향으로 돌림
            for (int index: order) {
                int row = queries[index][0]-1;
                int col = queries[index][1]-1;
                int size = queries[index][2];

                // 1 ~ size 크기의 정사각형마다
                for (int s = 1; s <= size; s++) {
                    // 1. 가장 왼쪽 위의 숫자를 빼놓고,
                    int firstItem = boardResult[row-s][col-s];
                    // 2-1. 왼쪽 줄은 위로 한 칸씩 옮기고
                    for (int nr = row - s; nr < row + s; nr++) {
                        boardResult[nr][col-s] = boardResult[nr+1][col-s];
                    }
                    // 2-2. 밑쪽 줄은 왼쪽으로 한 칸씩 옮기고
                    for (int nc = col - s; nc < col + s; nc++) {
                        boardResult[row+s][nc] = boardResult[row+s][nc+1];
                    }
                    // 2-3. 오른쪽 줄은 밑으로 한 칸씩 옮기고
                    for (int nr = row + s - 1; nr > row - s - 1; nr--) {
                        boardResult[nr+1][col+s] = boardResult[nr][col+s];
                    }
                    // 2-4. 윗 줄은 오른쪽으로 한 칸씩 옮기고
                    for (int nc = col + s - 1; nc > col - s - 1; nc--) {
                        boardResult[row-s][nc+1] = boardResult[row-s][nc];
                    }
                    // 3. 마지막으로 저장해뒀던 숫자를 오른쪽칸에 집어넣음
                    boardResult[row-s][col-s+1] = firstItem;
                }
            }

//            printMatrix(boardResult);
//            System.out.println();
            // 각 행마다 모든 숫자의 합을 구한 후 answer에 최솟값 갱신
            for (int[] line : boardResult){
                answer = Math.min(answer, Arrays.stream(line).sum());
            }
        }

        return Integer.toString(answer);
    }

    public static void main(String[] args) throws Exception {
        try {
//      ------- 인풋 --------
            tokenize();
            int boardWidth = nextInt();
            int boardHeight = nextInt();
            int queryCount = nextInt();
            int[][] board = inputMatrix(boardWidth, boardHeight);
            int[][] queries = inputMatrix(queryCount, 3);
//            printMatrix(board);
//            printMatrix(queries);

//      ------- 아웃풋 -------

        String answer = solve(board, queries);
        bw.write(answer);
        bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
