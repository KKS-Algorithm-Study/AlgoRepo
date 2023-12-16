import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *     통나무가 가로로 놓여있고, 개구리는 세로 방향으로만 무한정 점프할 수 있을 때,
 *     한 통나무에서 다른 통나무로 점프해서 갈 수 있는지 출럭하는 문제
 *
 *     통나무의 x 축 좌표가 만나는 것들 끼리 하나의 그룹으로 묶어
 *     같은 그룹인지 판단하면 된다.
 *
 *     시작 좌표 기준으로 정렬 후 반복문으로 하나씩 꺼냈을 때,
 *     이전 통나무의 최대 끝 좌표가 현재 통나무의 시작 좌표보다 작으면 그룹으로 묶으면 된다.
 *
 * 주의할 점:
 *     이전 통나무 끝 좌표를 갱신할 때, max 처리 안하면 틀린다.
 *     통나무의 x 좌표가 {1, 10}, {2, 3}, {6, 7} 이럴 때,
 *     2번 통나무에서 끝 좌표를 max(10, 3) 이 아니라 그대로 3으로 갱신하면
 *     3번 통나무가 같은 그룹으로 처리가 안되기 때문이다.
 *
 * 시간 복잡도:
 *     간선을 정렬 하는 데 O(ElogE),
 *     간선 집합 처리는 O(E)이므로,
 *     최종 시간복잡도는 O(ElogE)이다.
 */

class Log implements Comparable<Log> {
    int left;
    int right;
    int number;

    public Log(int left, int right, int number) {
        this.left = left;
        this.right = right;
        this.number = number;
    }

    @Override
    public int compareTo(Log that) {
        return Integer.compare(this.left, that.left);
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

        if (groupX > groupY) {
            group[groupX] = groupY;
        } else {
            group[groupY] = groupX;
        }
    }

    public int[] getGroup() {
        return this.group;
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

    private static int[][] inputNumberMatrix(int edgeCount, int argCount) throws Exception {
        int[][] result = new int[edgeCount][argCount];
        for (int edge = 0; edge < edgeCount; edge++) {
            tokenize();
            for (int arg = 0; arg < argCount; arg++) {
                result[edge][arg] = nextInt();
            }
        }
        return result;
    }

    private static String solve(int[][] logQueries, int[][] jumpQueries) {
        List<Log> logs = new ArrayList<>();
        for (int idx = 0; idx < logQueries.length; idx++) {
            int logLeft = logQueries[idx][0];
            int logRight = logQueries[idx][1];
            int logNumber = idx + 1;
            logs.add(new Log(logLeft, logRight, logNumber));
        }

        // 통나무 왼쪽점 순서대로 정렬
        logs.sort(Comparator.naturalOrder());
        // 이전 통나무 정보
        int prevRight = -1;
        int prevNumber = 0;
        // 분리집합
        Group group = new Group(logs.size() + 1);
        for (Log currLog : logs) {
            if (prevRight >= currLog.left) {
                group.unionGroupOf(prevNumber, currLog.number);
            }
            if (prevRight < currLog.right) {
                prevRight = currLog.right;
                prevNumber = currLog.number;
            }
        }

        StringJoiner result = new StringJoiner("\n");
        for (int[] query : jumpQueries) {
            int logA = query[0];
            int logB = query[1];
            // 같은 그룹이면 건널 수 있음
            boolean isReachable = group.findGroupOf(logA) == group.findGroupOf(logB);
            if (isReachable) {
                result.add("1");
            } else {
                result.add("0");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int Q = nextInt();
            int[][] logs = inputNumberMatrix(N, 3);
            int[][] jumps = inputNumberMatrix(Q, 2);

            String answer = solve(logs, jumps);
            bw.write(answer);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

