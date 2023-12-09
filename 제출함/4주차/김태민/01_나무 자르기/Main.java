import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      높낮이가 다른 나무들 여러 그루가 있는데, 특정한 높이만큼 톱을 세팅해 나무를 잘라 가져가야 한다.
 *      필요한 만큼만 나무를 가져가기 위한 톱의 최대 높이를 구하는 문제.
 *
 *      나무의 높이가 최대 10억이기 때문에, 완전 탐색으로 10억 ~ 0까지 탐색하기엔 너무 오래 걸림
 *
 *      "톱의 높이를 X로 설정하면, 필요한 만큼의 나무를 가져갈 수 있는가?" 라는 질문으로
 *      가져갈 수 있으면 높이를 더 높이고, 없으면 높이를 낮추는 이분탐색의 방식으로 한다면
 *      log(10억) 연산으로 해결 가능
 *
 *      나무를 잘라 계산하는 연산의 경우, 정렬되지 않은 나무들을 모두 계산한다면 시간초과가 난다.
 *      해결 방법은 두 가지 인데,
 *      1. 나무들을 정렬 후, 톱의 높이보다 높은 나무들만 자르도록 반복문을 돌린다.
 *      2. 나무들을 정렬 후 역순으로 누적합을 구해놓고, 톱의 높이보다 높은 나무를 이분탐색으로 찾은 후,
 *         누적합에서 (톱의 높이 * 나무 개수) 만큼 뺀다.
 *
 *         예를 들어, 정렬된 나무들이 [1, 2, 3, 4, 5]이고, 이분탐색으로 결정된 톱의 높이가 2라면
 *         1번 방법은
 *         5에서 2를 빼 3을 얻고
 *         4에서 2를 빼 2를 얻고
 *         3에서 2를 빼 1을 얻고
 *         2부터는 뺄 게 없으니 for문을 종료하고 합인 6을 얻는다면
 *
 *         2번 방법은
 *         역순 누적합 [15, 14, 12, 9, 5, 0]를 먼저 구해놓고,
 *         "나무 높이가 2보다 높은 3의 인덱스 == 2" 를 이분 탐색으로 찾은 후,
 *         "높이가 3인 나무 까지의 역순 누적합 == 12" 에서
 *         "나무 개수(3) * 톱의 높이(2) == 6" 만큼을 누적합에서 뺀 값인
 *         12 - 6 = 6 만큼을 얻는다.
 *
 * 시간 복잡도:
 *      정렬에 O(NlogN),
 *      1번 방식: 이분탐색에 O(logM), 이분탐색마다 반복문으로 나무 자르기(N)
 *      2번 방식: 이분탐색에 O(logM), 이분탐색마다 이분 탐색으로 나무 자르기(logN)
 *
 *      1번 방식이라면 O(NlogM)이, 2번 방식이라면 O(NlogN)이 시간복잡도가 된다.
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

    // 정렬된 numbers에 value를 끼워넣을 알맞은 인덱스를 찾아준다.
    // numbers 배열에 value와 동일한 숫자가 있으면, 그 숫자의 오른쪽 인덱스를 준다.
    // 파이썬의 bisect 라이브러리의 bisect_right와 완전히 동일하다.
    private static int bisectRight(int[] numbers, int value) {
        int lft = 0;
        int rgt = numbers.length;
        while (lft < rgt) {
            int mid = (lft + rgt) / 2;
            if (numbers[mid] > value) {
                rgt = mid;
            } else {
                lft = mid + 1;
            }
        }
        return rgt;
    }

    private static boolean canCutEnoughTree(int required, int cutHeight, int[] trees, long[] accumulatedTrees) {
        int cutIndex = bisectRight(trees, cutHeight);
        return accumulatedTrees[cutIndex] - cutHeight * (trees.length - cutIndex) >= required;
    }

    private static String solve(int requiredAmount, int[] trees) {
        Arrays.sort(trees);

        // 역순 누적합 구해 놓고
        long[] treesCumulativeSums = new long[trees.length + 1];
        for (int idx = trees.length - 1; idx >= 0; idx--) {
            treesCumulativeSums[idx] = treesCumulativeSums[idx + 1] + trees[idx];
        }

        // 이분 탐색으로 톱의 높이 결정
        int lft = 0;
        int rgt = trees[trees.length - 1];
        while (lft + 1 < rgt) {
            int mid = (lft + rgt) / 2;
            // 필요한 만큼 자를 수 있는지 확인
            if (canCutEnoughTree(requiredAmount, mid, trees, treesCumulativeSums)) {
                // 자를 수 있으면 톱의 높이를 높이고
                lft = mid;
            } else {
                // 없으면 톱의 높이를 낮추고
                rgt = mid;
            }
        }

        return Integer.toString(lft);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int[] array = inputNumberArray();

            String answer = solve(M, array);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
