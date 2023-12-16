import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *     각각의 학생에 대한 친구비와 각 친구들의 친구 관계가 주어질 때
 *     "친구의 친구는 친구"라는 조건으로 모든 학생과 친구가 되기 위한 최소한의 친구비를 구하는 문제
 *
 *      친구의 친구는 친구이기 때문에,
 *      친구 관계로 연결된 친구 그룹이 있다면,
 *      그 중 한 명만 사귀어도 그 그룹의 모든 학생들과 친구가 된다
 *
 *      즉, 각 친구 그룹마다 가장 싼 친구비를 합치면 정답이다.
 *
 *      그룹의 가장 싼 친구비를 갱신하기 위해서
 *      union 메서드가 호출될 때마다 두 그룹의 친구비 중 더 싼 것으로 갱신해준다.
 *
 * 시간 복잡도:
 *     분리 집합의 시간 복잡도는 O(1)이므로,
 *     친구 명수 + 질의 갯수, O(N)이 된다.
 */

class FriendGroups {

    private int[] group;
    private int[] costs;

    public FriendGroups(int[] costs) {
        this.costs = new int[costs.length + 1];
        System.arraycopy(costs, 0, this.costs, 1, costs.length);
        this.group = IntStream.range(0, this.costs.length).toArray();
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
        int groupMin = Math.min(groupX, groupY);
        group[groupX] = groupMin;
        group[groupY] = groupMin;
        costs[groupMin] = Math.min(costs[groupX], costs[groupY]);
    }

    public int getTotalFriendCost() {
        return IntStream.range(1, costs.length)
                .filter(groupNumber -> groupNumber == findGroupOf(groupNumber))
                .map(groupNumber -> costs[groupNumber])
                .sum();
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

    private static String solve(int budget, int[] friendCosts, int[][] queries) {
        FriendGroups friendGroups = new FriendGroups(friendCosts);
        for (int[] query : queries) {
            int friendA = query[0];
            int friendB = query[1];
            friendGroups.unionGroupOf(friendA, friendB);
        }
        int totalFriendCosts = friendGroups.getTotalFriendCost();

        String result = "Oh no";
        if (totalFriendCosts <= budget) {
            result = Integer.toString(totalFriendCosts);
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int K = nextInt();
            int[] costs = inputNumberArray();
            int argCount = 2;
            int[][] queries = inputQueries(M, argCount);

            String answer = solve(K, costs, queries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int[] inputNumberArray() throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
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

