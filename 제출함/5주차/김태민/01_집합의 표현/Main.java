import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *     기초적인 분리 집합 문제를 풀 수 있는지 묻는 문제
 *
 *     각 숫자가 어느 집합에 속해있는지를 표현할 int[] 배열 초기화 후,
 *     숫자의 집합을 재귀적으로 찾아 집합을 갱신시키는 방식으로 경로를 압축하고 이후 그 집합을 반환하는 find 메서드,
 *     두 숫자가 가리키는 집합 숫자를 더 작은 숫자로 바꿔 집합을 합치는 union 메서드
 *
 *     위 세개를 만들어 질의를 처리하면 된다.
 *
 * 시간 복잡도:
 *     find 메서드는 재귀가 있지만, 경로 압축으로 인해 O(1)이 되고,
 *     union 메서드는 시간 복잡도를 결정할 요인이 find 메서드밖에 없으므로 역시 O(1)이다.
 *
 *     따라서 M개의 쿼리를 처리하는 데 필요한 시간복잡도는 O(M)인데,
 *     N개의 집합을 초기화 시키는 데 필요한 시간복잡도가 O(N)이고, N이 M보다 크므로,
 *     최종 시간 복잡도는 O(N)이다.
 */

class Groups {

    private int[] group;

    public Groups(int count) {
        group = IntStream.range(0, count).toArray();
    }

    public int findGroupOf(int x) {
        if (x != group[x]) {
            group[x] = findGroupOf(group[x]);
        }
        return group[x];
    }

    public void unionGroupOf(int x, int y) {
        int groupX = findGroupOf(x);
        int groupY = findGroupOf(y);

        if (groupX < groupY) {
            group[groupX] = groupY;
        } else {
            group[groupY] = groupX;
        }
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

    private static int[][] inputQueries(int edgeCount, int argCount) throws Exception {
        int[][] result = new int[edgeCount][argCount];
        for (int edge = 0; edge < edgeCount; edge++) {
            tokenize();
            for (int arg = 0; arg < argCount; arg++) {
                result[edge][arg] = nextInt();
            }
        }
        return result;
    }

    private static String solve(int elementCount, int[][] queries) {
        StringJoiner result = new StringJoiner("\n");
        elementCount++;
        Groups groups = new Groups(elementCount);
        for (int[] query : queries) {
            int operation = query[0];
            int elementX = query[1];
            int elementY = query[2];
            if (operation == 0) {
                groups.unionGroupOf(elementX, elementY);
            } else {
                if (groups.findGroupOf(elementX) == groups.findGroupOf(elementY)) {
                    result.add("YES");
                } else {
                    result.add("NO");
                }
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int argCount = 3;
            int[][] queries = inputQueries(M, argCount);

            String answer = solve(N, queries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
}

