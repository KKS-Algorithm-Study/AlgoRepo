import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      유저명의 별칭은 이전에 가입한 유저명의 접두사가 아닌 것 중 가장 짧은 접두사로 만들어 질 때,
 *      주어진 유저명들의 별칭을 출력하는 문제
 *
 *      만약 별칭을 확인하기 위해 모든 유저명을 확인한다 하면
 *      유저명 최대 길이를 M이라 할 때
 *      가입한 유저들의 모든 유저명을 반복문으로 확인해야 하기 때문에 O(N**2 * M)이 든다.
 *
 *      하지만 trie를 활용해 가입한 유저들의 유저명 중 중복되는 접두사들을 트리 형태로 겹쳐놓으면
 *      가입한 유저들의 trie를 따라 겹치는 부분만 확인하면 되기 때문에 O(N * M)이 든다.
 *
 *  시간 복잡도:
 *      위에서 설명했듯, trie를 사용하면 O(N * M)이다.
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


    private static List<String> inputRows(int rowCount) throws Exception {
        List<String> result = new ArrayList<>();
        for (int row = 0; row < rowCount; row++) {
            result.add(br.readLine().strip());
        }
        return result;
    }

    private static String substring(String string, int end) {
        StringBuilder sb = new StringBuilder();
        for (int idx = 0; idx < end; idx++) {
            sb.append(string.charAt(idx));
        }
        return sb.toString();
    }

    private static String queryTrie(HashMap trie, String username, int queryIndex, int dupeIndex) {
        if (queryIndex == username.length()) {
            if (trie.containsKey('*')) {
                int dupeNumber = (Integer) trie.get('*');
                trie.put('*', ++dupeNumber);
                return username + dupeNumber;
            } else {
                trie.put('*', 1);
                return username.substring(0, Math.min(username.length(), dupeIndex + 1));
            }
        }
        char queryLetter = username.charAt(queryIndex);
        if (trie.containsKey(queryLetter)) {
            dupeIndex = queryIndex + 1;
        } else {
            trie.put(queryLetter, new HashMap<>());
        }
        return queryTrie((HashMap) trie.get(queryLetter), username, queryIndex + 1, dupeIndex);
    }

    private static String makeNickname(HashMap userMap, String username) {
        return queryTrie(userMap, username, 0, 0);
    }

    private static String solve(List<String> usernames) {
        StringJoiner result = new StringJoiner("\n");
        HashMap userMap = new HashMap<>();
        for (String username : usernames) {
            result.add(makeNickname(userMap, username));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            List<String> userQueries = inputRows(N);
            String answer = solve(userQueries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}