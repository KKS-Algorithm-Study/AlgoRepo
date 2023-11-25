import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *      주어진 정점과 간선을 인접 리스트로 저장 및 정렬 후, BFS와 DFS를 돌려 방문한 순서대로 출력해주는 문제.
 *
 * 주의해야할 점:
 *      BFS는 Queue 자료구조를 이용해 돌리면 되지만,
 *      DFS는 Stack을 사용하는 방법은 까다롭고, 재귀를 이용해야 쉽게 풀린다.
 *
 *      ex) 1번 예제의 경우, BFS를 위해 오름차순으로 정렬한 인접 리스트를 Stack을 사용한 DFS에서 그대로 사용하면
 *          스택에 [2, 3, 4] 순서로 들어가기 때문에 출력은 거꾸로 4, 3, 2 순서로 나와 틀리게 된다.
 * 시간 복잡도:
 *      BFS 및 DFS 둘 다 같다.
 *      모든 정점을 돌면서(N), 정점에 연결된 간선을 두 번씩(양방향 그래프) 탐색(2 * M)하게 된다.
 *      따라서 BFS 및 DFS의 시간 복잡도는 관례적으로 다음과 같이 표현한다.
 *      O(N + M)
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

    private static List<List<Integer>> inputGraph(int nodeCount, int edgeCount) throws Exception {
        List<List<Integer>> graph = IntStream.rangeClosed(0, nodeCount)
                .mapToObj(num -> new ArrayList<Integer>())
                .collect(Collectors.toList());
        for (int cnt = 0; cnt < edgeCount; cnt++) {
            tokenize();
            int nodeA = nextInt();
            int nodeB = nextInt();
            graph.get(nodeA).add(nodeB);
            graph.get(nodeB).add(nodeA);
        }
        return graph;
    }

    private static void doDfs(List<List<Integer>> graph, int curNode, boolean[] visited, StringJoiner sj) {
        sj.add(Integer.toString(curNode));
        for (int nxtNode : graph.get(curNode)) {
            if (visited[nxtNode]) {
                continue;
            }
            visited[nxtNode] = true;
            doDfs(graph, nxtNode, visited, sj);
        }
    }

    private static String getDfsResult(List<List<Integer>> graph, int initNode) {
        StringJoiner sj = new StringJoiner(" ");
        boolean[] visited = new boolean[graph.size()];
        visited[initNode] = true;
        doDfs(graph, initNode, visited, sj);
        return sj.toString();
    }

    private static void doBfs(List<List<Integer>> graph, int initNode, boolean[] visited, StringJoiner sj) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(initNode);
        while (!queue.isEmpty()) {
            int curNode = queue.poll();
            sj.add(Integer.toString(curNode));
            for (int nxtNode : graph.get(curNode)) {
                if (visited[nxtNode]) {
                    continue;
                }
                visited[nxtNode] = true;
                queue.offer(nxtNode);
            }
        }
    }

    private static String getBfsResult(List<List<Integer>> graph, int initNode) {
        StringJoiner sj = new StringJoiner(" ");
        boolean[] visited = new boolean[graph.size()];
        visited[initNode] = true;
        doBfs(graph, initNode, visited, sj);
        return sj.toString();

    }

    private static void sortGraph(List<List<Integer>> graph) {
        for (List<Integer> edges : graph) {
            edges.sort(Comparator.naturalOrder());
        }
    }

    private static String solve(List<List<Integer>> graph, int initNode) throws Exception {
        sortGraph(graph);
        StringJoiner sj = new StringJoiner("\n");
        sj.add(getDfsResult(graph, initNode));
        sj.add(getBfsResult(graph, initNode));
        return sj.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int V = nextInt();
            List<List<Integer>> graph = inputGraph(N, M);

            String answer = solve(graph, V);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
