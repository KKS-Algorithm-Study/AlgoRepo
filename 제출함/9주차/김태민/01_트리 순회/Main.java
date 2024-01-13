import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      그래프를 전위, 중위, 후위 순회한 결과를 출력하는 문제
 *
 *      셋 모두 DFS 탐색 방법이지만, 루트 노드를 언제 결과로 더하느냐에 따른 차이가 있다.
 *      전위 순회는 왼쪽 노드 순회 이전에 루트 노드를 더하고,
 *      중위 순회는 왼쪽 순회 이후, 오른쪽 순회 이전에 더하고,
 *      후위 순회는 오른쪽 노드 순회 이후에 더하면 된다.
 *
 *  시간 복잡도:
 *      DFS 이므로, O(V+E)
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

    private static String[][] inputMatrix(int rowCount, int colCount) throws Exception {
        String[][] result = new String[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            result[row] = br.readLine().split(" ");
        }
        return result;
    }

    private static void doDfs(String currNode, Map<String, Node> graph,
                              StringJoiner preorder, StringJoiner inorder, StringJoiner postorder) {
        Node node = graph.get(currNode);
        preorder.add(currNode);
        if (!node.leftNode.equals(".")) {
            doDfs(node.leftNode, graph, preorder, inorder, postorder);
        }
        inorder.add(currNode);
        if (!node.rightNode.equals(".")) {
            doDfs(node.rightNode, graph, preorder, inorder, postorder);
        }
        postorder.add(currNode);
    }

    private static String solve(String[][] graphQueries) {
        Map<String, Node> graph = new HashMap<>();
        for (String[] graphQuery : graphQueries) {
            String rootNode = graphQuery[0];
            String leftNode = graphQuery[1];
            String rightNode = graphQuery[2];
            graph.put(rootNode, new Node(leftNode, rightNode));
        }
        StringJoiner preorder = new StringJoiner("");
        StringJoiner inorder = new StringJoiner("");
        StringJoiner postorder = new StringJoiner("");
        String initNode = "A";

        doDfs(initNode, graph, preorder, inorder, postorder);

        StringJoiner result = new StringJoiner("\n");
        result.add(preorder.toString());
        result.add(inorder.toString());
        result.add(postorder.toString());

        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int queryLength = 3;
            String[][] graphQueries = inputMatrix(N, queryLength);

            String answer = solve(graphQueries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Node {

    String leftNode;
    String rightNode;

    public Node(String leftNode, String rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }
}