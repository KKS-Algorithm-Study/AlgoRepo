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
 *     가중치가 있는 그래프에서, 모든 정점을 연결하는 간선 집합 중 최소 합을 구하는,
 *     즉, 최소 스패닝 트리를 구할 수 있는지 묻는 문제
 *
 *     크루스칼 알고리즘을 사용하면 쉽게 풀 수 있다.
 *     간선의 정보를 받아 가중치 순으로 정렬하고
 *     정렬된 간선을 순서대로 꺼내면서, 연결된 두 정점을 분리집합으로 처리함으로
 *     모든 정점이 하나의 집합에 속하면 종료시켜 그 합을 출력하면 된다.
 *
 * 시간 복잡도:
 *     간선을 정렬 하는 데 O(ElogE),
 *     간선 집합 처리는 O(E)이므로,
 *     최종 시간복잡도는 O(ElogE)이다.
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
        int unionCount = nodeCount - 2;
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

