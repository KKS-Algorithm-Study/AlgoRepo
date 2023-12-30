import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *      증가하는 부분 수열 이후에 감소하는 부분 수열이 나오는 걸 바이토닉 부분 수열이라고 할 때
 *      한 수열의 바이토닉 부분 수열 최대 길이를 구하는 문제
 *
 *      수열의 각 숫자마다, "해당 숫자를 포함시키는 증가하는 부분 수열의 최대 길이"를 구하고,
 *      같은 걸 역방향으로도 구해 "감소하는 부분 수열의 길이들"을 구해
 *      각 인덱스의 (증가하는 부분 수열 길이 값 + 감소하는 부분 수열 길이 값) 중 최대 값을 출력하면 된다.
 *
 *  시간 복잡도:
 *      LIS 이므로, DP + 이분탐색으로 구현해
 *      O(NlogN)
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

    private static String[][] inputMatrix(int rowCount, int colCount) throws Exception {
        String[][] result = new String[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            result[row] = br.readLine().split(" ");
        }
        return result;
    }

    private static int[] inputNumberArray(int numberCount) throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static int bisectLeft(List<Integer> numbers, int number) {
        int lft = -1;
        int rgt = numbers.size();

        while (lft + 1 < rgt) {
            int mid = (lft + rgt) / 2;
            if (numbers.get(mid) >= number) {
                rgt = mid;
            } else {
                lft = mid;
            }
        }

        return rgt;
    }

    private static int[] getLisLengths(int[] numbers, boolean getReversed) {
        int[] lisLengths = new int[numbers.length];
        List<Integer> lisDp = new ArrayList<>();
        lisDp.add(0);
        int startingIdx = getReversed ? numbers.length - 1 : 0;
        int direction = getReversed ? -1 : 1;

        for (int idx = startingIdx; 0 <= idx && idx < numbers.length; idx += direction) {
            int number = numbers[idx];
            if (number > lisDp.get(lisDp.size() - 1)) {
                lisDp.add(number);
                lisLengths[idx] = lisDp.size() - 1;
            } else {
                int numberPlace = bisectLeft(lisDp, number);
                lisDp.set(numberPlace, number);
                lisLengths[idx] = numberPlace;
            }
        }
        return lisLengths;
    }

    private static String solve(int[] numbers) {
        int[] increasingLengths = getLisLengths(numbers, false);
        int[] decreasingLengths = getLisLengths(numbers, true);
        int maxBitonicLength = IntStream.range(0, numbers.length)
                .map(idx -> increasingLengths[idx] + decreasingLengths[idx] - 1)
//                .peek(System.out::println)
                .max().orElse(0);
        return Integer.toString(maxBitonicLength);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[] numbers = inputNumberArray(N);

            String answer = solve(numbers);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

