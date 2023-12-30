import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *      트리 구조의 회사에서 뉴스를 전할 때, 각자의 부하 직원들에게 전화를 걸어야 하는데,
 *      하나의 부하 직원에게 전화 거는게 1분 걸릴 때, 모든 사원들에게 뉴스가 퍼지는데 걸리는 최소 시간을 구하는 문제
 *
 *      부하 직원이 많은 직원에게 먼저 전화를 걸어야 하므로,
 *      트리를 역으로 탐색하며 DP에 "내가 모든 부하직원에게 소식을 전하는 데 걸리는 시간" 을 저장해 풀 수 있다.
 *
 *      한 직원의 전화 시간을 구하려면,
 *      그 부하 직원들의 전화 시간을 역순으로 정렬한 뒤,
 *      전화 시간이 첫째로 큰 직원의 시간 + 1, 둘째로 큰 직원 시간 + 2, 셋째로 큰 직원 시간 + 3...
 *      중에서 가장 큰 값을 택하면 된다.
 *
 *      DFS로도 풀 수 있지만, 상하관계가 주어졌으므로, 위상 정렬 방식으로 해결했다.
 *      indegree[] 배열에 "아직 계산이 덜 끝난 자식 노드의 수"를 저장하고,
 *      Queue는 indegree 배열 숫자가 0 == 말단 노드들을 넣어 초기화,
 *      하나씩 뽑으며 계산이 완료된 노드는 그 부모노드의 indegree 배열 숫자를 1씩 줄여
 *      indegree 배열 숫자가 0이 된 노드를 다시 Queue에 집어넣으며 계산하는 방식이다.
 *
 *  시간 복잡도:
 *
 *      위상 정렬 방식으로 노드를 탐색하면, 모든 노드를 정확히 한 번씩 탐색하므로 O(N)이고,
 *      각 노드를 탐색할 때마다 부하 직원 리스트를 전화 시간 역순으로 정렬하므로, O(NlogN)이다.
 *      따라서 O(N**2logN)이 나온다.
 */

public class Main {

    static int INF = Integer.MAX_VALUE;
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static int[] inputNumberArray(int numberCount) throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static String solve(int[] bosses) {
        bosses[0] = 0;
        int employeeCount = bosses.length;

        List<List<Integer>> relationshipTree = new ArrayList<>();
        for (int employee = 0; employee < employeeCount; employee++) {
            relationshipTree.add(new ArrayList<>());
        }
        for (int employee = 1; employee < employeeCount; employee++) {
            int boss = bosses[employee];
            relationshipTree.get(boss).add(employee);
        }

        Queue<Integer> queue = new LinkedList<>();
        int[] indegree = new int[employeeCount];

        for (int employee = 0; employee < employeeCount; employee++) {
            int subordinateCount = relationshipTree.get(employee).size();
            if (subordinateCount == 0) {
                queue.offer(employee);
            } else {
                indegree[employee] = subordinateCount;
            }
        }

        int[] callTimeDp = new int[employeeCount];
        while (!queue.isEmpty()) {
            int employee = queue.poll();
            List<Integer> subordinates = relationshipTree.get(employee);
            if (subordinates.size() != 0) {
                // 제일 오래 걸리는 직원에게 먼저 전화하기 위해 정렬
                subordinates.sort(Comparator.comparing(emp -> -callTimeDp[emp]));
                // 제일 오래 걸리는 직원의 시간 + 1, 두번째 오래 + 2, 세번째 오래 + 3... 비교
                callTimeDp[employee] = IntStream.range(0, subordinates.size())
                        .map(callOrder -> callTimeDp[subordinates.get(callOrder)] + callOrder + 1)
                        .reduce(0, Integer::max);
            }
            int boss = bosses[employee];
            if (--indegree[boss] == 0) {
                queue.offer(boss);
            }
        }

        return Integer.toString(callTimeDp[0]);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[] bosses = inputNumberArray(N);
            String answer = solve(bosses);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

