import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *     대학을 연결하는 여러 길이 있을 때,
 *     남자 대학과 여자 대학이 통하는 길만 두는 조건으로
 *     모든 대학을 연결할 수 있는 최소한의 길들의 합을 출력하는 문제
 *
 *     최소 스패닝 트리에, 연결된 두 지점이 남여 혹은 여남 인지만 확인하면 된다.
 *
 * 시간 복잡도:
 *     역시 O(ElogE)이다.
 */

class School {
    private List<String> types;
    private int[] group;
    private int groupCount;
    private int shortestDistance;

    public School(List<String> types) {
        this.types = types;
        this.group = IntStream.range(0, types.size()).toArray();
        this.groupCount = group.length;
    }

    private int findGroupOf(int x) {
        if (x != group[x]) {
            group[x] = findGroupOf(group[x]);
        }
        return group[x];
    }

    public void unionTwoSchools(Road road) {
        int schoolA = road.endA - 1;
        int schoolB = road.endB - 1;
        // 같은 성별이라면 스킵
        if (types.get(schoolA).equals(types.get(schoolB))) {
            return;
        }

        int groupX = findGroupOf(schoolA);
        int groupY = findGroupOf(schoolB);
        // 이미 연결되어 있다면 스킵
        if (groupX == groupY) {
            return;
        }

        if (groupX < groupY) {
            group[groupX] = groupY;
        } else {
            group[groupY] = groupX;
        }
        shortestDistance += road.distance;
    }

    public boolean shouldStopUnion() {
        return groupCount <= 1;
    }

    public int getShortestDistance() {
        return shortestDistance;
    }
}
class Road implements Comparable<Road> {
    int endA;
    int endB;
    int distance;

    public Road(int endA, int endB, int distance) {
        this.endA = endA;
        this.endB = endB;
        this.distance = distance;
    }

    @Override
    public int compareTo(Road that) {
        return Integer.compare(this.distance, that.distance);
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

    private static String solve(List<String> schoolTypes, int[][] roadInfo) {

        School school = new School(schoolTypes);

        List<Road> roads = new ArrayList<>();
        for (int[] info : roadInfo) {
            int schoolA = info[0];
            int schoolB = info[1];
            int cost = info[2];
            roads.add(new Road(schoolA, schoolB, cost));
        }
        roads.sort(Comparator.naturalOrder());

        for (Road road : roads) {
            if (school.shouldStopUnion()) {
                break;
            }
            school.unionTwoSchools(road);
        }
        return Integer.toString(school.getShortestDistance());
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            List<String> types = inputStringList();
            int argCount = 3;
            int[][] edges = inputEdges(M, argCount);

            String answer = solve(types, edges);
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

