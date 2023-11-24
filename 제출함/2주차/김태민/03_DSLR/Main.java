import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
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
        boolean[] isNumberChecked = new boolean[10000];
        isNumberChecked[initNumber] = true;
        List<Process> curQue = new ArrayList<>();
        curQue.add(new Process("", initNumber));

        while (true) {
            List<Process> nxtQue = new ArrayList<>();
            for (Process curProcess : curQue) {
                if (curProcess.number == goalNumber) {
                    return curProcess.commands;
                }
                for (Command operation : Command.values()) {
                    int nxtNumber = operation.calculate(curProcess.number);
                    if (isNumberChecked[nxtNumber]) {
                        continue;
                    }
                    isNumberChecked[nxtNumber] = true;
                    nxtQue.add(new Process(curProcess.commands + operation.NAME, nxtNumber));
                }
            }

            if (nxtQue.isEmpty()) {
                break;
            }

            curQue = nxtQue;
        }
        return "";
    }


    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
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
