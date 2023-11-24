import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
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
        for (int nxtNode: graph.get(curNode)) {
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
        while(!queue.isEmpty()) {
            int curNode = queue.poll();
            sj.add(Integer.toString(curNode));
            for (int nxtNode: graph.get(curNode)) {
                if(visited[nxtNode]) {
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
