import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *
 *      w = p 인 경우, 당근을 키워 먹든 심자마자 먹든, 총합은 같다.
 *      w < p 인 경우, 당근을 키워 먹는 게 총합이 가장 크다.
 *      예를 들어,
 *          3 3 짜리 당근은
 *          3 6 9 12 15 ... 인데, 5일차까지 키워 먹어도 15, 매일 심자마자 먹어도 15다.
 *          3 4 짜리 당근은
 *          3 7 11 15 19... 인데, 5일차까지 키워 먹으면 19, 매일 심자마자 먹으면 15다.
 *     문제 조건은 w <= p 이므로, 무조건 기다렸다가 마지막날에 먹는게 좋다.
 *
 *     당근이 여러개일 경우 하루에 하나씩밖에 못먹으므로, 먹을 순서를 지정해야 하는데,
 *     p가 큰 걸 나중에 먹는게 총합이 가장 크다.
 *     예를 들어,
 *         10 10과 1 100 당근이 있으면
 *         2일 제한이라면 10짜리 당근을 1일차에, 101짜리 당근을 2일차에
 *         3일 제한이라면 20짜리 당근일 2일차에, 201짜리 당근을 3일차에
 *         4일 제한이라면 30짜리 당근을 3일차에, 301짜리 당근을 4일차에 먹는게 좋다.
 *
 *     또, 날짜 제한(T)는 당근 갯수(N)보다 크거나 같으므로, 당근을 모두 못먹는 경우는 계산할 필요 없다.
 *
 *  시간 복잡도:
 *
 *      N개의 당근을 정렬하므로 O(NlogN)이다.
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


    private static int[][] inputNumberMatrix(int rowCount, int colCount) throws Exception {
        int[][] result = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            result[row] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return result;
    }

    private static String solve(int[][] carrotQueries, long days) {
        long result = 0;
        // 영양제 순으로 정렬
        Arrays.sort(carrotQueries, Comparator.comparingInt(carrot -> carrot[1]));

        for (int idx = 0; idx < carrotQueries.length; idx++) {
            int carrotBase = carrotQueries[idx][0];
            int carrotGrow = carrotQueries[idx][1];
            long growDays = days - carrotQueries.length + idx;
            result += carrotBase + (carrotGrow * growDays);
        }
        return Long.toString(result);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int T = nextInt();
            int[][] carrots = inputNumberMatrix(N, 2);
            String answer = solve(carrots, T);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
