import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      시작 숫자에서 목표 숫자로 가도록 만드는 최소한의 명령어 나열을 출력하는 문제.
 *
 *      수학적으로 계산하는 방법은 없어 보임
 *      -> 직접 연산을 적용하며 완전 탐색처럼 탐색함
 *
 *      연산을 적용하다 이미 나온 숫자가 나오면, 연산할 필요 없음
 *      -> 방문 배열을 사용해, 중복 탐색 방지
 *
 *      최소한의 명령어 나열 필요
 *      -> 연산을 하나씩 더해보다가 목표 숫자가 나오면 끝낼 수 있음
 *      -> BFS를 이용해 단계별로 연산을 적용하고, 가장 먼저 목표에 도착하는 방법을 정답으로 반환
 *
 *  시간 복잡도:
 *      노드 -> 각 숫자, 간선 -> 각 숫자마다 연산 == 숫자 갯수 * 연산
 *      최대 6개의 테스트 케이스(T)마다, 최대 10000개의 노드(N이라고 가정)와 각 노드마다 뻗어나가는 4개의 간선(M이라고 가정)씩 탐색
 *      O(T * (N + (N * M))
 */

enum Command {
    D("D") {
        @Override
        public int calculate(int number) {
            return 2 * number % 10000;
        }
    },
    S("S") {
        @Override
        public int calculate(int number) {
            return (number + 9999) % 10000;
        }
    },
    L("L") {
        @Override
        public int calculate(int number) {
            return (number / 1000 + number * 10) % 10000;
        }
    },
    R("R") {
        @Override
        public int calculate(int number) {
            return (number / 10 + number * 1000) % 10000;
        }
    };

    public final String NAME;

    Command(String NAME) {
        this.NAME = NAME;
    }

    abstract int calculate(int number);


}

class Process {
    String commands;
    int number;

    public Process(String commands, int number) {
        this.commands = commands;
        this.number = number;
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


    private static String solve(int[][] queries) {
        StringJoiner answer = new StringJoiner("\n");
        for (int[] query : queries) {
            int initNumber = query[0];
            int goalNumber = query[1];
            answer.add(findMinimalCommands(initNumber, goalNumber));
        }
        return answer.toString();
    }

    private static String findMinimalCommands(int initNumber, int goalNumber) {
        // 방문 배열 사용, 0 ~ 9999만 확인하면 된다
        boolean[] isNumberChecked = new boolean[10000];
        isNumberChecked[initNumber] = true;
        List<Process> curQue = new ArrayList<>();
        curQue.add(new Process("", initNumber));

        while (true) {
            List<Process> nxtQue = new ArrayList<>();
            for (Process curProcess : curQue) {
                // 현재 숫자가 목표 숫자라면, 저장된 명령어 나열 반환
                if (curProcess.number == goalNumber) {
                    return curProcess.commands;
                }

                // DSLR 연산 반복문
                for (Command command : Command.values()) {
                    int nxtNumber = command.calculate(curProcess.number);
                    // 이미 확인한 숫자라면, 큐에 넣지 않음
                    if (isNumberChecked[nxtNumber]) {
                        continue;
                    }
                    isNumberChecked[nxtNumber] = true;
                    // 현재 명령어 + 적용한 명령어, 적용 후 숫자
                    nxtQue.add(new Process(curProcess.commands + command.NAME, nxtNumber));
                }
            }

            if (nxtQue.isEmpty()) {
                break;
            }

            curQue = nxtQue;
        }
        return "";
    }

    /**
     * 입출력을 담당
     */
    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            // { {시작 숫자, 목표 숫자}, ... }
            int[][] queries = inputQueries(N);

            String answer = solve(queries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int[][] inputQueries(int queryCount) throws Exception {
        int[][] queries = new int[queryCount][2];
        for (int cnt = 0; cnt < queryCount; cnt++) {
            tokenize();
            queries[cnt][0] = nextInt();
            queries[cnt][1] = nextInt();
        }
        return queries;
    }
}
