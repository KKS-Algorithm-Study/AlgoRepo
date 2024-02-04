import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *
 *             수열 :      1  2  1  2  2  3  3, 목표 합: 3
 *             누적 :   0  1  3  4  6  8 11 14 => 임의의 두 누적합을 뺐을 때 3의 배수가 나오면 해당 구간은 성립
 *                                            => 3으로 나눈 나머지가 같은 것끼리 빼면 성립
 *       나머지 정리 :   0  1  0  1  0  2  2  2 => 3으로 나눈 나머지가 같은 것끼리 묶어보자
 *            0끼리 :   [     ]                -> 1 + 2
 *                           [     ]          -> 1 + 2
 *                     [           ]          -> 1 + 2 + 1 + 2
 *            1끼리 :      [     ]             -> 2 + 1
 *            2끼리 :                  [  ]    -> 3
 *                                       [  ] -> 3
 *                                    [     ] -> 3 + 3
 *            즉, 0 ~ i 까지의 각 누적합을 K로 나눈 나머지들의 갯수를 세고,
 *            그걸 두 개씩 묶는 경우의 갯수 (n * (n-1) // 2)를 모두 더하면 정답이 나온다.
 *
 *  시간 복잡도:
 *      O(N)
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

    private static int[] inputNumberArray(int numberCount) throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static String solve(int[] numbers, int targetSum) {
        // 누적합을 targetSum으로 나눈 나머지 갯수 세기
        Map<Integer, Integer> prefixSumCounts = new HashMap<>();
        prefixSumCounts.put(0, 1);
        int prefixSum = 0;
        for (int idx = 0; idx < numbers.length; idx++) {
            prefixSum += numbers[idx];
            prefixSum %= targetSum;
            prefixSumCounts.put(prefixSum, prefixSumCounts.getOrDefault(prefixSum, 0) + 1);
        }
        // 2개씩 묶는 경우의 수 세기
        long result = prefixSumCounts.values()
                .stream()
                .mapToLong(cnt -> cnt * (cnt - 1) / 2)
                .sum();
        return Long.toString(result);
    }


    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int K = nextInt();
            int[] numbers = inputNumberArray(N);
            String answer = solve(numbers, K);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
