import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *
 *      먼저 적색, 녹색, 청색 각각 조회했을 때 다른 값이 나오는 Map을 만들어 구역 갯수를 세고,
 *      적색과 녹색을 조회 했을 때 같은 값을, 그리고 청색을 조회했을 때 다른 값을 나오게 하는 Map으로 다시 구역 갯수를 세
 *      두 갯수를 출력하면 된다.
 *
 *  시간 복잡도:
 *      2차원 배열의 BFS이므로 O(N**2)
 */
public class Main {

    private static class Coordinate {
        int row;
        int col;
        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    private enum Color {
        R, G, B, RG
    }
    private static int[] DR = new int[]{0, 1, 0, -1};
    private static int[] DC = new int[]{1, 0, -1, 0};
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static List<List<Character>> inputMatrixString(int matrixSize) throws Exception {
        List<List<Character>> result = new ArrayList<>();
        for (int row = 0; row < matrixSize; row++) {
            String stringRow = br.readLine();
            List<Character> resultRow = new ArrayList<>();
            for (int col = 0; col < matrixSize; col++) {
                resultRow.add(stringRow.charAt(col));
            }
            result.add(resultRow);
        }
        return result;
    }

    private static void markTheSameColor(List<Coordinate> cque, List<List<Character>> picture, boolean[][] visited, Map<Character, Color> colorMap) {
        int length = picture.size();
        int row = cque.get(0).row;
        int col = cque.get(0).col;
        visited[row][col] = true;
        Color seeingColor = colorMap.get(picture.get(row).get(col));

        while (true) {
            List<Coordinate> nque = new ArrayList<>();
            for (Coordinate cur: cque) {
                for (int d = 0; d < 4; d++) {
                    int nrow = cur.row + DR[d];
                    int ncol = cur.col + DC[d];
                    if (nrow < 0 || ncol < 0 || nrow >= length || ncol >= length) {
                        continue;
                    }
                    if (visited[nrow][ncol] || seeingColor != colorMap.get(picture.get(nrow).get(ncol))) {
                        continue;
                    }
                    visited[nrow][ncol] = true;
                    nque.add(new Coordinate(nrow, ncol));
                }
            }
            if (nque.size() == 0) {
                break;
            }
            cque = nque;
        }
    }

    private static int getAreaCount(List<List<Character>> picture, Map<Character, Color> colorMap) {
        int result = 0;
        boolean[][] visited = new boolean[picture.size()][picture.size()];
        for (int row = 0; row < picture.size(); row++) {
            for (int col = 0; col < picture.size(); col++) {
                if (visited[row][col]) {
                    continue;
                }
                result++;
                markTheSameColor(List.of(new Coordinate(row, col)), picture, visited, colorMap);
            }
        }
        return result;
    }

    private static String solve(List<List<Character>> picture) {
        Map<Character, Color> notBlind = new HashMap<>();
        notBlind.put('R', Color.R);
        notBlind.put('G', Color.G);
        notBlind.put('B', Color.B);
        Map<Character, Color> redGreenBlind = new HashMap<>();
        redGreenBlind.put('R', Color.RG);
        redGreenBlind.put('G', Color.RG);
        redGreenBlind.put('B', Color.B);

        StringJoiner result = new StringJoiner(" ");
        int notBlindAreaCount = getAreaCount(picture, notBlind);
        int redGreenBlindAreaCount = getAreaCount(picture, redGreenBlind);
        result.add(Integer.toString(notBlindAreaCount));
        result.add(Integer.toString(redGreenBlindAreaCount));
        return result.toString();
    }


    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            List<List<Character>> picture = inputMatrixString(N);
            String answer = solve(picture);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
