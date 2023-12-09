import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      N개 만큼 홈을 가진 고무줄을 M개로 나누어 최대 길이를 가지도록 하는 문제
 *      완전 탐색은 nCm으로 최대값 100000C1000은 너무 큰 수를 가지게 된다.
 *
 *      질의 "최소 특정 길이만큼 고무줄을 나누도록 했을 때 개수만큼 나오는가?"를 기준으로
 *      이분탐색으로 해결할 수 있는 문제다.
 *
 * 주의 사항:
 *      고무줄을 자르기 시작하는 지점이 어디인지에 따라 길이가 다르게 나올 수 있다.
 *      따라서 특정 길이 만큼 자를 수 있는지 판단할 때, 모든 홈에서 자르기 시작해봐야 한다.
 *
 *      예를 들어, 총 길이 10에 홈이 [0, 3, 7] 필요 개수는 2개라고 하자.
 *      만약 0에서 자르기 시작한다면, (0~3 3~0) 이나 (0~7 7~0) 으로 아무리 길어도 길이가 3밖에 나오지 않는다.
 *      하지만 3에서 자르기 시작하면, (3~7 7~3) 으로 길이 4 이상인 줄들을 만들 수 있다.
 *  시간 복잡도:
 *      이분 탐색 (logN)에,
 *      고무줄을 자르는 질의로 각 홈에서 다른 모든 홈을 한 번씩 탐색한다 (M**2)
 *      따라서 O(M**2logN)
 *
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

    private static List<Integer> inputNumberList(int numberCount) throws Exception {
        List<Integer> numbers = new ArrayList<>();
        for (int idx = 0; idx < numberCount; idx++) {
            numbers.add(Integer.parseInt(br.readLine().strip()));
        }
        return numbers;
    }

    private static boolean canMakeEnoughStrings(int stringLimit, int requiredStringCount,
                                                List<Integer> markLocations, int markCount) {
        // 고무줄의 모든 홈이 시작 지점이 되어봐야 함
        for (int initIdx = 0; initIdx < markCount; initIdx++) {
            int prevMark = markLocations.get(initIdx);
            int count = 0;

            // 시작 홈의 다음 홈부터 쭉 돌아 시작 홈까지 반복문으로 돌리는데,
            for (int nextIdx = initIdx + 1; nextIdx < markCount + initIdx + 1; nextIdx++) {
                int currMark = markLocations.get(nextIdx);
                int cutStringLength = currMark - prevMark;
                // 만약 현재 홈에서 잘라 길이가 모자르면, 자르지 않고 다음 홈으로 넘어감
                if (cutStringLength < stringLimit) {
                    continue;
                }
                prevMark = currMark;
                count++;
                if (count >= requiredStringCount) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String solve(int totalStringLength, int requiredStringCount, List<Integer> markLocations) {

        int markCount = markLocations.size();
        for (int idx = 0; idx < markCount + 1; idx++) {
            markLocations.add(markLocations.get(idx) + totalStringLength);
        }

        int lft = 0;
        int rgt = totalStringLength + 1;
        while (lft + 1 < rgt) {
            int mid = (lft + rgt) / 2;
            if (canMakeEnoughStrings(mid, requiredStringCount, markLocations, markCount)) {
                lft = mid;
            } else {
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
            int K = nextInt();
            List<Integer> numbers = inputNumberList(M);

            String answer = solve(N, K, numbers);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
