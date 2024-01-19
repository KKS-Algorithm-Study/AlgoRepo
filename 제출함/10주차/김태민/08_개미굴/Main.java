import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  문제 풀이 방법:
 *      개미굴의 먹이 저장 정보가 주어질 때
 *      그 구조를 도식화해 출력하는 문제
 *
 *      먹이 정보를 trie 형태로 저장 후,
 *      출력시에 각 층을 정렬하며 depth 만큼 "--"를 붙여 출력하면 된다.
 *
 *  시간 복잡도:
 *      모든 먹이 정보를 딱 한 번씩 탐색하므로 O(N * K)이다.
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


    private static List<List<String>> inputMatrix(int rowCount) throws Exception {
        List<List<String>> result = new ArrayList<>();
        for (int row = 0; row < rowCount; row++) {
            tokenize();
            int colCount = nextInt();
            List<String> rowResult = new ArrayList<>();
            for (int col = 0; col < colCount; col++) {
                rowResult.add(st.nextToken());
            }
            result.add(rowResult);
        }
        return result;
    }

    private static void makeTrie(HashMap<String, HashMap> trie, List<String> nodes, int index) {
        if (index == nodes.size()) {
            return;
        }
        String node = nodes.get(index);
        if (!trie.containsKey(node)) {
            trie.put(node, new HashMap<>());
        }
        makeTrie(trie.get(node), nodes, index + 1);
    }

    private static void searchTrie(HashMap<String, HashMap> trie, StringJoiner result, int depth) {
        List<String> foods = trie.keySet().stream()
                .sorted()
                .collect(Collectors.toList());
        String prefix = IntStream.range(0, depth)
                .mapToObj(num -> "--")
                .collect(Collectors.joining());

        for (String food: foods) {
            result.add(prefix + food);
            searchTrie(trie.get(food), result, depth + 1);
        }
    }


    private static String solve(List<List<String>> foodQueries) {
        HashMap<String, HashMap> foodMap = new HashMap<>();
        for (List<String> foodQuery : foodQueries) {
            makeTrie(foodMap, foodQuery, 0);
        }

        StringJoiner result = new StringJoiner("\n");
        searchTrie(foodMap, result, 0);
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            List<List<String>> foodQueries = inputMatrix(N);
            String answer = solve(foodQueries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}