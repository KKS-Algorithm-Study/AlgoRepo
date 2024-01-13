import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      친구 관계가 주어질 때, 다른 모든 사람들과 친구로 연결된 길이가 가장 짧은 사람들의 길이와 명수, 그리고 사람 번호를 출력하는 문제다.
 *
 *      어떤 한 사람의 친구 관계 길이는 BFS의 최대 Depth로 알 수 있는데,
 *      사람 수는 최대 50명이므로, 50명으로 나오는 최대 간선의 수는 50 * 49 = 2450이고,
 *      이 BFS를 50번 돌려도 122500이다.
 *      따라서 BFS로 풀 수 있다.
 *
 *  시간 복잡도:
 *      사람 수가 N이면
 *      친구 관계 구하는 BFS가 O(N**2) 이고, 총 N번 돌기 때문에
 *      O(N**3)
 */
public class Main {
    static int INF = 51;
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static int[][] inputMatrix(int rowCount, int colCount) throws Exception {
        int[][] result = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            result[row] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return result;
    }

    private static List<List<Integer>> inputQueries() throws Exception{
        List<List<Integer>> result = new ArrayList<>();
        while (true) {
            List<Integer> query = Arrays.stream(br.readLine().split(" "))
                    .map(Integer::parseInt)
                    .toList();
            if (query.get(0) == -1) {
                break;
            }
            result.add(query);
        }
        return result;
    }

    private static List<List<Integer>> makeFriendGraph(int peopleCount, List<List<Integer>> friendQueries) {
        List<List<Integer>> result = new ArrayList<>();
        for (int person = 0; person < peopleCount; person++) {
            result.add(new ArrayList<>());
        }
        for (List<Integer> query: friendQueries) {
            int personA = query.get(0);
            int personB = query.get(1);
            result.get(personA).add(personB);
            result.get(personB).add(personA);
        }
        return result;
    }

    private static int getFriendPoint(int initPerson, List<List<Integer>> friends, int[] visited) {
        List<Integer> cque = new ArrayList<>();
        cque.add(initPerson);
        int friendPoint = 0;
        while (true) {
            List<Integer> nque = new ArrayList<>();
            for (int currPerson: cque) {
                for (int nextPerson: friends.get(currPerson)) {
                    if (visited[nextPerson] == initPerson) {
                        continue;
                    }
                    visited[nextPerson] = initPerson;
                    nque.add(nextPerson);
                }
            }

            if (nque.isEmpty()) {
                break;
            }
            friendPoint++;
            cque = nque;
        }
        return friendPoint;
    }

    private static String solve(int peopleCount, List<List<Integer>> friendQueries) {
        peopleCount++;
        List<List<Integer>> friends = makeFriendGraph(peopleCount, friendQueries);
        int[] friendPoints = new int[peopleCount];
        Arrays.fill(friendPoints, INF);

        int[] visited = new int[peopleCount];
        int candidatePoint = INF;
        for (int personNumber = 1; personNumber < peopleCount; personNumber++) {
            int friendPoint = getFriendPoint(personNumber, friends, visited);
            friendPoints[personNumber] = friendPoint;
            candidatePoint = Math.min(candidatePoint, friendPoint);
        }

        int candidateCount = 0;
        StringJoiner candidates = new StringJoiner(" ");
        for (int personNumber = 1; personNumber < peopleCount; personNumber++) {
            if (friendPoints[personNumber] != candidatePoint) {
                continue;
            }
            candidateCount++;
            candidates.add(Integer.toString(personNumber));
        }

        String result = String.format("%d %d\n%s", candidatePoint, candidateCount, candidates);
        return result;
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            List<List<Integer>> friendQueries = inputQueries();
            String answer = solve(N, friendQueries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}