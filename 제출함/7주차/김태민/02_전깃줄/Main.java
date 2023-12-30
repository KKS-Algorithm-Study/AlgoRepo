import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      두 전봇대에 여러 전깃줄들이 교차되어 연결되어있을 때,
 *      전깃줄이 교차되지 않도록 전깃줄을 자르는 최소 갯수를 구하는 문제
 *
 *      완전 탐색이라면, 각 전깃줄이 잘려야하는지의 여부를 조합으로 만들어야하는데,
 *      전깃줄의 최대 갯수가 100개이므로, 2 ** 100이 되어
 *      완전 탐색은 불가능하다는 걸 알 수 있다.
 *
 *      전깃줄 예제 그림을 보면, 전깃줄을 잘랐을 때 남은 전깃줄들과 B 전봇대에 연결된 숫자들이
 *      [1, 4, 6, 7, 10] 혹은 [2, 4, 6, 7, 10]으로 증가하는 수열을 보여주는 걸 볼 수 있다.
 *      즉, 오른쪽 전봇대의 숫자들을 왼쪽 전봇대 숫자 순서로 정렬시키고,
 *      그 숫자들의 증가하는 부분 수열의 길이 == 남길 전깃줄의 갯수 이므로,
 *      전깃줄의 총 갯수에서 부분 수열의 길이를 빼면 잘라야할 전깃줄의 갯수가 나온다.
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

    private static int[][] inputNumberMatrix(int rowCount, int colCount) throws Exception {
        int[][] result = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            tokenize();
            for (int col = 0; col < colCount; col++) {
                result[row][col] = nextInt();
            }
        }
        return result;
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

    private static int getLisLength(int[] numbers) {
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
        return result;
    }

    private static String solve(int[][] cables) {
        int[] sortedCables = Arrays.stream(cables)
                .sorted(Comparator.comparingInt(cable -> cable[0]))
                .mapToInt(cable -> cable[1])
                .toArray();
        int preservedCableCount = getLisLength(sortedCables);
        int cutCableCount = cables.length - preservedCableCount;
        return Integer.toString(cutCableCount);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[][] cables = inputNumberMatrix(N, 2);

            String answer = solve(cables);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

