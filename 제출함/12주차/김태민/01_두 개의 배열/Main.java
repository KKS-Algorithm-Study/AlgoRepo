import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *  문제 풀이 방법:
 *      배열 A의 각 숫자들을 배열 B중 가까운 숫자로 치환해 출력하는 문제
 *
 *      배열 B를 for 문 으로 전부 탐색해 구하면 N*M번이 들기 때문에
 *      배열 B 정렬 후, 이분 탐색으로 각 숫자가 들어갈 위치를 찾고,
 *      왼쪽 오른쪽 숫자를 비교해 더 차이가 적은 숫자로 바꾸면 된다.
 *
 *  시간 복잡도:
 *      정렬하는 데 O(MlogM)
 *      이분 탐색 하는데 O(MlogN) 이므로
 *      총 O(MlogM + MlogN) 이다.
 */
public class Main {
    static final int INF = 2000000001;
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static List<Integer> inputNumbers(int numberCount) throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static int bisectRight(List<Integer> numbers, int target) {
        int left = -1;
        int right = numbers.size();
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            int num = numbers.get(mid);
            if (num <= target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }

    private static long getCloserNumberSum(List<Integer> numbersA, List<Integer> numbersB) {
        long result = 0;
        numbersB.sort(Comparator.naturalOrder());
        for (int number: numbersA) {
            int foundIdx = bisectRight(numbersB, number);
            int rightNumber = INF;
            int leftNumber = -INF;
            if (foundIdx < numbersB.size()) {
                rightNumber = numbersB.get(foundIdx);
            }
            if (foundIdx > 0) {
                leftNumber = numbersB.get(foundIdx - 1);
            }
            if (rightNumber - number < number - leftNumber) {
                result += rightNumber;
            } else {
                result += leftNumber;
            }
        }
        return result;
    }

    private static String solve(List<List<List<Integer>>> queries) {
        StringJoiner result = new StringJoiner("\n");
        for (List<List<Integer>> numberLists : queries) {
            List<Integer> numbersA = numberLists.get(0);
            List<Integer> numbersB = numberLists.get(1);
            numbersB.sort(Comparator.naturalOrder());
            long sum = getCloserNumberSum(numbersA, numbersB);
            result.add(Long.toString(sum));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int T = nextInt();
            List<List<List<Integer>>> queries = new ArrayList<>();
            for (int tc = 0; tc < T; tc++) {
                tokenize();
                int N = nextInt();
                int M = nextInt();
                List<Integer> numbersA = inputNumbers(N);
                List<Integer> numbersB = inputNumbers(M);
                queries.add(List.of(numbersA, numbersB));
            }
            String answer = solve(queries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
