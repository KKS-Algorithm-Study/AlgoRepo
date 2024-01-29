import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

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
        for (int idx = 0; idx < numberCount; idx++) {
            tokenize();
            result[idx] = nextInt();
        }
        return result;
    }

    private static String solve(int[] ropes) {
        Arrays.sort(ropes);
        long result = 0;
        for (int idx = 0; idx < ropes.length; idx++) {
            int rope = ropes[idx];
            long sustainableWeight = rope * (ropes.length - idx);
            result = Math.max(result, sustainableWeight);
        }
        return Long.toString(result);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[] ropes = inputNumbers(N);
            String answer = solve(ropes);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
