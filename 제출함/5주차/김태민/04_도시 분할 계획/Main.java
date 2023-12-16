import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *     길을 없애서 마을을 두 부분으로 나누면서, 연결하는 데 필요없는 비싼 길은 없애는 문제
 *
 *     필요 없는 길을 없애기 위해 최소 스패닝 트리를 구하되,
 *     두 부분으로 나누기 위해 가장 비싼 길 하나를 덜 더하면 된다.
 *
 * 시간 복잡도:
 *     역시 정렬하는 데 시간이 걸리기 때문에
 *     최종 시간복잡도는 O(MlogM)이다.
 */

class Edge implements Comparable<Edge> {
    int nodeA;
    int nodeB;
    int cost;

    public Edge(int nodeA, int nodeB, int cost) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.cost = cost;
    }

    @Override
    public int compareTo(Edge that) {
        return Integer.compare(this.cost, that.cost);
    }
}

class Group {

    private int[] group;

    public Group(int count) {
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

    private static String solve(int nodeCount, int[][] edgeQueries) {
        nodeCount++;
        Group group = new Group(nodeCount);

        List<Edge> edges = new ArrayList<>();
        for (int[] query : edgeQueries) {
            int nodeA = query[0];
            int nodeB = query[1];
            int cost = query[2];
            edges.add(new Edge(nodeA, nodeB, cost));
        }
        edges.sort(Comparator.naturalOrder());

        int total = 0;
        int unionCount = nodeCount - 3;
        for (Edge edge : edges) {
            if (group.findGroupOf(edge.nodeA) != group.findGroupOf(edge.nodeB)) {
                group.unionGroupOf(edge.nodeA, edge.nodeB);
                total += edge.cost;
                if (--unionCount <= 0) {
                    break;
                }
            }
        }
        return Integer.toString(total);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int V = nextInt();
            int E = nextInt();
            int argCount = 3;
            int[][] edges = inputEdges(E, argCount);

            String answer = solve(V, edges);
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

