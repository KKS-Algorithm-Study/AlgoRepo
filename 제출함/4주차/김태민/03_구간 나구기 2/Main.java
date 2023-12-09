import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      N개의 숫자를 M개의 그룹으로 나누는 모든 방법은 (N-1)C(M-1) 인데,
 *      최대값 4999C2500 은 엄청 큰 숫자가 나와 완탐은 불가능
 *
 *      이분 탐색을 하기 위한 조건은, 어떤 질의를 했을 때 한 숫자를 기준으로 한 쪽은 모두 true, 한 쪽은 모두 false 가 나와야 한다.
 *      이 문제에서는 "한 그룹의 최대값과 최소값의 차이를 임의로 정해서 숫자를 차례대로 그룹에 배정했을 때, M개의 그룹 이하로 나오는가?"
 *      라는 질의로 이분탐색을 할 수 있다.
 *
 * 시간 복잡도:
 *      이분 탐색이 logN, 숫자들의 그룹 배정이 N이므로
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

    private static int[] inputNumberArray() throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static boolean canGroupNumbers(int[] numbers, int groupLimit, int diffLimit) {

        int currentMin = numbers[0];
        int currentMax = numbers[0];

        int numberIdx = 0;
        int groupCount = 1;

        for (int number : numbers) {
            // 이 숫자가 현재 그룹의 최소값보다 작다면
            if (number < currentMin) {
                // 일단 현재 그룹의 최소값으로 할당해보고
                currentMin = number;
                if (currentMax - currentMin <= diffLimit) {
                    continue;
                }
                // 만약 최대값 - 최소값이 너무 크면, 새로운 그룹을 만듦
                currentMax = number;
                groupCount++;
                continue;
            }
            // 이 숫자가 현재 그룹의 최대값보다 크다면
            if (number > currentMax) {
                currentMax = number;
                if (currentMax - currentMin <= diffLimit) {
                    continue;
                }
                currentMin = number;
                groupCount++;
                continue;
            }
        }
        return groupCount <= groupLimit;
    }

    private static String solve(int groupCount, int[] numbers) {

        int lft = -1;
        int rgt = Arrays.stream(numbers).max().orElse(0);

        while (lft + 1 < rgt) {
            int mid = (lft + rgt) / 2;
            if (canGroupNumbers(numbers, groupCount, mid)) {
                rgt = mid;
            } else {
                lft = mid;
            }
        }
        return Integer.toString(rgt);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int[] numbers = inputNumberArray();

            String answer = solve(M, numbers);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
