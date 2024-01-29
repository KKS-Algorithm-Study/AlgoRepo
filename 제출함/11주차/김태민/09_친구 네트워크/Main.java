import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *  문제 풀이 방법:
 *
 *      들어오는 두 이름을 id화 해서
 *      두 id로 분리집합 구하면서 집합에 포함된 id 갯수를 출력하면 된다.
 *
 *  시간 복잡도:
 *
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


    private static List<List<List<String>>> inputQueries(int caseCount) throws Exception {
        List<List<List<String>>> result = new ArrayList<>();
        for (int cases = 0; cases < caseCount; cases++) {
            tokenize();
            int queryCount = nextInt();
            List<List<String>> queries = new ArrayList<>();
            for (int idx = 0; idx < queryCount; idx++) {
                List<String> query = Arrays.stream(br.readLine().split(" "))
                                .collect(Collectors.toList());
                queries.add(query);
            }
            result.add(queries);
        }
        return result;
    }

    private static String solve(List<List<List<String>>> friendQueriesCases) {
        StringJoiner result = new StringJoiner("\n");
        for (List<List<String>> friendQueries: friendQueriesCases) {
            result.add(getFriendCounts(friendQueries));
        }
        return result.toString();
    }

    private static int getNameId(String name, Map<String, Integer> nameToId) {
        nameToId.put(name, nameToId.getOrDefault(name, nameToId.size()));
        return nameToId.get(name);
    }

    private static int findGroup(int idA, List<Integer> groups) {
        if (groups.get(idA) != idA) {
            groups.set(idA, findGroup(groups.get(idA), groups));
        }
        return groups.get(idA);
    }

    private static int connectFriend(int idA, int idB, List<Integer> groups, List<Integer> counts) {
        while (groups.size() <= Math.max(idA, idB)) {
            groups.add(groups.size());
            counts.add(1);
        }
        int groupA = findGroup(idA, groups);
        int groupB = findGroup(idB, groups);
        if (groupA == groupB) {
            return counts.get(groupA);
        }
        int groupMin = Math.min(groupA, groupB);
        int groupMax = Math.max(groupA, groupB);
        groups.set(groupMax, groupMin);
        counts.set(groupMin, counts.get(groupMin) + counts.get(groupMax));
        return counts.get(groupMin);
    }

    private static String getFriendCounts(List<List<String>> friendQueries) {
        StringJoiner result = new StringJoiner("\n");
        Map<String, Integer> nameToId = new HashMap<>();
        List<Integer> groups = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        for (List<String> friendQuery: friendQueries) {
            int idA = getNameId(friendQuery.get(0), nameToId);
            int idB = getNameId(friendQuery.get(1), nameToId);
            int friendCount = connectFriend(idA, idB, groups, counts);
            result.add(Integer.toString(friendCount));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int T = nextInt();
            List<List<List<String>>> friendQueries = inputQueries(T);
            String answer = solve(friendQueries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
