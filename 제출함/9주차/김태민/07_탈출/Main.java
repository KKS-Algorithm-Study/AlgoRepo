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
 *      고슴도치가 밀려드는 물을 피해 비버 굴로 도망칠 수 있는지 판단해서,
 *      도망 갈 수 있다면 그 시간을, 못가면 KAKTUS를 출력하는 문제다
 *
 *      물과 고슴도치를 각각을 BFS로 돌리는 간단한 문제다.
 *      단, 주어지는 지도 자체를 방문 배열로 사용해서 메모리를 아끼고
 *      물이 지날 수 있는 공간과 고슴도치가 지날 수 있는 공간을 조건문으로 잘 판단해줘야 한다.
 *
 *  시간 복잡도:
 *      BFS를 사용하기 때문에 O(V+E) 인데,
 *      V = N * M, E = 4 * V 이므로
 *      O(N*M)이 나온다.
 */
public class Main {
    static final String EMPTY = ".";
    static final String STONE = "X";
    static final String WATER = "*";
    static final String HEDGEHOG = "S";
    static final String BEAVER = "D";

    static final int[] DY = new int[]{0, 1, 0, -1};
    static final int[] DX = new int[]{1, 0, -1, 0};

    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static List<List<String>> inputMatrix(int rowCount, int colCount) throws Exception {
        List<List<String>> result = new ArrayList<>();
        for (int row = 0; row < rowCount; row++) {
            result.add(Arrays.stream(br.readLine().strip().split(""))
                    .collect(Collectors.toList()));
        }
        return result;
    }

    private static void getLocations(List<List<String>> field, List<List<Integer>> waters, List<List<Integer>> hedgehogs, List<List<Integer>> beavers) {
        for (int row = 0; row < field.size(); row++) {
            for (int col = 0; col < field.get(row).size(); col++) {
                String square = field.get(row).get(col);
                if (square.equals(WATER)) {
                    waters.add(List.of(row, col));
                    continue;
                }
                if (square.equals(HEDGEHOG)) {
                    hedgehogs.add(List.of(row, col));
                    continue;
                }
                if (square.equals(BEAVER)) {
                    beavers.add(List.of(row, col));
                    continue;
                }
            }
        }
    }

    private static boolean isOutOfField(int row, int col, List<List<String>> field) {
        return row < 0 || row >= field.size() || col < 0 || col >= field.get(0).size();
    }

    private static boolean isBlocked(String character, String square) {
        if (character.equals(WATER)) {
            if (square.equals(EMPTY) || square.equals(HEDGEHOG)) {
                return false;
            }
            return true;
        }
        if (character.equals(HEDGEHOG)) {
            if (square.equals(EMPTY) || square.equals(BEAVER)) {
                return false;
            }
            return true;
        }
        return true;
    }

    private static List<List<Integer>> move(List<List<Integer>> cque, String character, List<List<String>> field) {
        List<List<Integer>> nque = new ArrayList<>();
        for (List<Integer> location: cque) {
            int y = location.get(0);
            int x = location.get(1);
            for (int d = 0; d < 4; d++) {
                int ny = y + DY[d];
                int nx = x + DX[d];
                if (isOutOfField(ny, nx, field)) {
                    continue;
                }
                if (isBlocked(character, field.get(ny).get(nx))) {
                    continue;
                }
                field.get(ny).set(nx, character);
                nque.add(List.of(ny, nx));
            }
        }
        return nque;
    }

    private static boolean hedgehogMeetsBeaver(List<List<String>> field, List<List<Integer>> beavers) {
        for (List<Integer> location: beavers) {
            int row = location.get(0);
            int col = location.get(1);
            if (field.get(row).get(col).equals(HEDGEHOG)) {
                return true;
            }
        }
        return false;
    }

    private static String solve(List<List<String>> field) {
        List<List<Integer>> waters = new ArrayList<>();
        List<List<Integer>> hedgehogs = new ArrayList<>();
        List<List<Integer>> beavers = new ArrayList<>();
        getLocations(field, waters, hedgehogs, beavers);

        String result = "KAKTUS";
        int time = 0;
        while (!hedgehogs.isEmpty()) {
            time++;
            waters = move(waters, WATER, field);
            hedgehogs = move(hedgehogs, HEDGEHOG, field);
            if (hedgehogMeetsBeaver(field, beavers)) {
                result = Integer.toString(time);
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            List<List<String>> field = inputMatrix(N, M);
            String answer = solve(field);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}