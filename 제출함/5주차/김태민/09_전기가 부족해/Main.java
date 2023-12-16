import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *
 *     도시 둘을 케이블로 연결하는 데 필요한 케이블 비용과 발전소들이 설치된 도시 위치가 주어질 때,
 *     모든 도시들이 적어도 하나의 발전소와 연결되도록 하는 케이블 연결 최소 비용을 구하는 문제
 *
 *     발전소가 설치된 도시들을 미리 하나의 그룹으로 묶어놓고,
 *     모든 도시가 그 그룹으로 연결되도록 최소 스패닝 트리를 만들어 그 합을 출력하면 된다
 *
 * 시간 복잡도:
 *     정렬 시간복잡도 O(MlogM)이다.
 */

class Cable implements Comparable<Cable> {
    int cityA;
    int cityB;
    int cost;

    public Cable(int cityA, int cityB, int cost) {
        this.cityA = cityA;
        this.cityB = cityB;
        this.cost = cost;
    }

    @Override
    public int compareTo(Cable that) {
        return Integer.compare(this.cost, that.cost);
    }
}

class City {

    private int[] group;
    private int groupCount;

    public City(int count, int[] generatorQueries) {
        group = IntStream.range(0, count).toArray();
        groupCount = group.length;
        initGenerators(generatorQueries);
    }

    private void initGenerators(int[] citiesWithGenerator) {
        for (int city : citiesWithGenerator) {
            group[city] = 0;
        }
        groupCount -= citiesWithGenerator.length - 1;
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

    public boolean isDoneInstallingCables() {
        return groupCount == 0;
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

    private static int[] inputNumberArray() throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
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

    private static String solve(int cityCount, int[] generatorQueries, int[][] cableQueries) {
        cityCount++;
        City city = new City(cityCount, generatorQueries);

        List<Cable> cables = new ArrayList<>();
        for (int[] query : cableQueries) {
            int cityA = query[0];
            int cityB = query[1];
            int cost = query[2];
            cables.add(new Cable(cityA, cityB, cost));
        }
        cables.sort(Comparator.naturalOrder());

        int totalCost = 0;
        for (Cable cable : cables) {
            if (city.findGroupOf(cable.cityA) != city.findGroupOf(cable.cityB)) {
                city.unionGroupOf(cable.cityA, cable.cityB);
                totalCost += cable.cost;
                if (city.isDoneInstallingCables()) {
                    break;
                }
            }
        }
        return Integer.toString(totalCost);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int K = nextInt();
            int[] generatorQueries = inputNumberArray();
            int argCount = 3;
            int[][] edges = inputEdges(M, argCount);

            String answer = solve(N, generatorQueries, edges);
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

