import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 *  문제 풀이 방법:
 *
 *  시간 복잡도:
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

    private static int[] inputNumbers(int numberCount) throws Exception {
        int[] result = new int[numberCount];
        tokenize();
        for (int idx = 0; idx < numberCount; idx++) {
            result[idx] = nextInt();
        }
        return result;
    }

    private static String solve(int[] students, int groupCount) {
        return IntStream.range(1, students.length)
                .mapToObj(idx -> students[idx] - students[idx - 1])
                .sorted(Comparator.reverseOrder())
                .skip(groupCount - 1)
                .reduce(0, Integer::sum)
                .toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int K = nextInt();
            int[] numbers = inputNumbers(N);
            String answer = solve(numbers, K);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
