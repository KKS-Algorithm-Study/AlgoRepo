import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      가장 긴 증가하는 부분 수열 (Longest Increasing Sequence)의 길이를 구하는 문제로,
 *      평소의 DP 문제와 다르게, 수열의 길이를 저장하는 게 아닌, 수열 자체를 저장해 그 길이를 출력해야하는 DP 문제다.
 *
 *      가장 긴 증가하는 부분 수열 (이하 LIS)를 저장할 DP를 선언하고,
 *      원본 수열을 순회하면서, 숫자가 현재까지 만들어진 LIS의 어느 위치에 들어가는게 알맞은지 탐색하면 된다.
 *          만약 숫자가 LIS의 마지막 숫자보다 크다면, LIS 오른쪽에 추가해주고,
 *          그렇지 않다면 LIS 숫자 중 자신보다 작은 숫자들의 오른쪽 인덱스에 대체해 넣어주면 된다.
 *
 *  시간 복잡도:
 *      "LIS 숫자 중 자신보다 작은 숫자들의 오른쪽 인덱스에 대체해 넣어주"는 과정을
 *      반복문을 돌리면 최종 시간 복잡도가 O(N**2)이 나오고,
 *      이분탐색을 이용하면 O(NlogN)이 나오게 된다.
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

    private static String solve(int[] numbers) {
        List<Integer> lisDp = new ArrayList<>();
        lisDp.add(0);
        for (int number : numbers) {
            if (number > lisDp.get(lisDp.size() - 1)) {
                lisDp.add(number);
            } else {
                int index = bisectLeft(lisDp, number);
                lisDp.set(index, number);
            }
        }
        int result = lisDp.size() - 1;
        return Integer.toString(result);
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

