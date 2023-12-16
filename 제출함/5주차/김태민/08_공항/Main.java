import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *
 *     G개의 게이트를 가진 공항에서 비행기를 받아야 하는데,
 *     1 ~ g 번째 게이트까지 비행기를 도킹 시킬수 있다는 쿼리가 주어질 때,
 *     총 몇 대의 비행기를 받을 수 있는지 계산하는 문제
 *
 *     일단 이 문제는 "비행기는 가능한 한 큰 숫자의 게이트에 도킹시킨다"는 그리디 방식으로 접근 가능하다.
 *     예를 들어, 두 비행기의 도킹 가능 게이트가 {2, 1}로 주어진다면
 *     첫 비행기를 1번 게이트에 도킹시키면 두 번째 비행기는 도킹이 불가능하다.
 *     첫 비행기를 2번 게이트에 도킹시키면 두 번째 비행기는 1번 게이트에 도킹시킬 수 있다.
 *
 *     위 방식으로 완전탐색으로 계속해서 -1 해서 찾는다면,
 *     최악의 경우는 P개의 비행기가 계속해서 G번으로 들어오는 경우로 O(P*G) 가 되고,
 *     연산 횟수가 10,000,000,000 번이 되어서 시간 초과가 난다.
 *
 *     이 문제는 "도킹된 게이트는 -1번 게이트의 그룹으로 묶는" 분리 집합 알고리즘으로 접근할 수 있다
 *     게이트를 찾는 복잡도가 O(1)이 되어서, 최종 시간 복잡도는 비행기 갯수인 O(P)가 되므로
 *     시간 초과 없이 풀 수 있다.
 *
 * 시간 복잡도:
 *     위에서 설명했듯이, O(P)이다.
 */



class Gate {

    private int[] group;

    public Gate(int count) {
        group = IntStream.range(0, count).toArray();
    }

    private int findGroupOf(int x) {
        if (x != group[x]) {
            group[x] = findGroupOf(group[x]);
        }
        return group[x];
    }

    private void unionGroupOf(int x, int y) {
        int groupX = findGroupOf(x);
        int groupY = findGroupOf(y);

        if (groupX > groupY) {
            group[groupX] = groupY;
        } else {
            group[groupY] = groupX;
        }
    }

    public int findDockingGate(int x) {
        return findGroupOf(x);
    }

    public void fillGate(int x) {
        unionGroupOf(x, x-1);
    }
}

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[] inputQueries(int queryCount) throws Exception {
        int[] queries = new int[queryCount];
        for (int index = 0; index < queryCount; index++) {
            queries[index] = Integer.parseInt(br.readLine().strip());
        }
        return queries;
    }

    private static String solve(int GateCount, int[] planeQueries) {
        GateCount++;
        Gate gate = new Gate(GateCount);

        int result = 0;
        for(int planeQuery : planeQueries) {
            int dockingGate = gate.findDockingGate(planeQuery);
            if (dockingGate == 0) {
                break;
            }
            result++;
            gate.fillGate(dockingGate);
        }

        return Integer.toString(result);
    }

    public static void main(String[] args) {
        try {
            int G = Integer.parseInt(br.readLine().strip());
            int P = Integer.parseInt(br.readLine().strip());
            int[] queries = inputQueries(P);

            String answer = solve(G, queries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

