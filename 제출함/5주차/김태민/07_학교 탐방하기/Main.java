import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *
 *     모든 건물 + 입구를 연결 하려면,
 *     즉, 연결하는데 필요한 최소 도로 갯수는
 *     (건물+입구) - 1개 == (N+1)-1 == N
 *
 *     최소 피로도 코스 => 내리막길이 최대인 코스
 *     내리막길이 최대인 코스를 찾으려면, 내리막길로 그룹을 지으면 됨 (그림에서 오르막길을 지워보자)
 *     오르막길의 갯수 == 위의 그룹들을 연결하는 도로의 갯수 == 내리막길 그룹 갯수 - 1
 *
 *     따라서 내리막길 그룹 갯수를 X라고 하면, 최소 피로도는 다음과 같다
 *     (X-1) ** 2
 *
 *     최대 피로도 코스 => 오르막길이 최대인 코스
 *     오르막길이 최대인 코스를 찾으려면, 오르막길로 그룹을 지으면 됨 (그림에서 내리막길을 지워보자)
 *     내리막길의 갯수 == 위의 그룹들을 연결하는 도로의 갯수 == 오르막길 그룹 갯수 - 1
 *     오르막길의 갯수 == 연결하는데 필요한 최소 도로 갯수 - 내리막길 갯수
 *
 *     따라서 오르막길 그룹 갯수를 Y라고 하면, 최대 피로도는 다음과 같다
 *     (N - (Y-1)) ** 2
 *
 *     두 피로도의 차이를 출력하면 된다.
 *
 * 시간 복잡도:
 *     크루스칼 알고리즘은 O(1) 이므로,
 *     union 쿼리의 갯수인 O(M) 이다.
 */

class Route {

    private List<Road> roads;
    private int[] group;

    public Route(int buildingCount) {
        this.roads = new ArrayList<>();
        this.group = IntStream.range(0, buildingCount + 1).toArray();
    }

    public void add(Road road) {
        this.roads.add(road);
    }

    private int findGroupOf(int x) {
        if (x != group[x]) {
            group[x] = findGroupOf(group[x]);
        }
        return group[x];
    }

    public int getGroupCount() {
        int groupCount = this.group.length;

        for (Road road : roads) {
            int groupA = findGroupOf(road.endA);
            int groupB = findGroupOf(road.endB);

            if (groupA == groupB) {
                continue;
            }

            if (groupA > groupB) {
                group[groupA] = groupB;
            } else {
                group[groupB] = groupA;
            }
            groupCount -= 1;
        }
        return groupCount;
    }
}

class Road {

    int endA;
    int endB;

    public Road(int endA, int endB) {
        this.endA = endA;
        this.endB = endB;
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

    private static int[][] inputEdges(int edgeCount, int argCount) throws Exception {
        int[][] result = new int[edgeCount][argCount];
        for (int edge = 0; edge < edgeCount; edge++) {
            tokenize();
            for (int arg = 0; arg < argCount; arg++) {
                result[edge][arg] = nextInt();
            }
        }
        return result;
    }

    private static String solve(int buildingCount, int[][] roadInfo) {

        Route upHills = new Route(buildingCount);
        Route downHills = new Route(buildingCount);

        for (int[] info : roadInfo) {
            int endA = info[0];
            int endB = info[1];
            boolean isDownHill = info[2] == 1;
            if (isDownHill) {
                downHills.add(new Road(endA, endB));
            } else {
                upHills.add(new Road(endA, endB));
            }
        }

        int upHillGroups = upHills.getGroupCount();
        int downHillGroups = downHills.getGroupCount();

        int upHillTireDegree = (int) Math.pow(buildingCount - (upHillGroups - 1), 2);
        int downHillTireDegree = (int) Math.pow((downHillGroups - 1), 2);

        return Integer.toString(upHillTireDegree - downHillTireDegree);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int argCount = 3;
            int[][] edges = inputEdges(M + 1, argCount);

            String answer = solve(N, edges);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> inputStringList() throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .collect(Collectors.toList());
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

