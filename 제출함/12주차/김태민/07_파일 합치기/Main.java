import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  문제 풀이 방법:
 *
 *      소설의 순서를 바꾸지 못한 채 합치는 데 필요한 임시비용을 구하는 문제다.
 *
 *      DP로 풀 수 있으며, 아래의 점화식을 이용할 수 있다.
 *      구간 A ~ B 까지의 임시 비용 = min(구간 A와 B 사이를 임의로 나눈 두 구간의 임시 비용의 합) + A ~ B 까지의 파일 크기 합
 *      예를 들어,
 *      [1, 2, 3] 의 임시 비용 = min(1~2 의 임시비용 + 3의 임시 비용, 1의 임시비용 + 2~3의 임시 비용) + 1 + 2 + 3
 *
 *
 *  시간 복잡도:
 *      O(N ** 3)
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

    private static List<Integer> inputNumberArray(int numberCount) throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static List<List<Integer>> inputTestCases(int T) throws Exception {
        List<List<Integer>> result = new ArrayList<>();
        for (int t = 0; t < T; t++) {
            tokenize();
            int N = nextInt();
            result.add(inputNumberArray(N));
        }
        return result;
    }


    private static List<Integer> getPrefixSum(List<Integer> numbers) {
        List<Integer> prefixSum = new ArrayList<>();
        prefixSum.add(0);
        for (int idx = 0; idx < numbers.size(); idx++) {
            prefixSum.add(prefixSum.get(idx) + numbers.get(idx));
        }
        return prefixSum;
    }

    private static int getMinimumTemporaryCost(List<List<Integer>> costDp, int leftIdx, int mergeSize) {
        int result = Integer.MAX_VALUE;
        for (int leftSize = 1; leftSize < mergeSize; leftSize++) {
            int rightIdx = leftIdx + leftSize;
            int rightSize = mergeSize - leftSize;
            int leftTempCost = costDp.get(leftSize).get(leftIdx);
            int rightTempCost = costDp.get(rightSize).get(rightIdx);
            result = Math.min(result, leftTempCost + rightTempCost);
        }
        return result;
    }

    private static int getMinimumMergeCost(List<Integer> chapters) {
        List<List<Integer>> costDp = new ArrayList<>();
        List<Integer> prefixSum = getPrefixSum(chapters);
        costDp.add(IntStream.range(0, chapters.size()).mapToObj(e->0).collect(Collectors.toList()));
        costDp.add(IntStream.range(0, chapters.size()).mapToObj(e->0).collect(Collectors.toList()));
        // 병합 구간 크기
        for (int mergeSize = 2; mergeSize <= chapters.size(); mergeSize++) {
            List<Integer> currDp = new ArrayList<>();
            // leftIdx 와 rightIdx로 병합 구간 지정 
            for (int leftIdx = 0; leftIdx <= chapters.size() - mergeSize; leftIdx++) {
                int rightIdx = leftIdx + mergeSize;
                int minPrevTempCost = getMinimumTemporaryCost(costDp, leftIdx, mergeSize);
                int chapterSum = prefixSum.get(rightIdx) - prefixSum.get(leftIdx);
                currDp.add(minPrevTempCost + chapterSum);
            }
            costDp.add(currDp);
//            System.out.println(currDp);
        }
        return costDp.get(costDp.size() - 1).get(0);
    }

    private static String solve(List<List<Integer>> testCases) {
        StringJoiner result = new StringJoiner("\n");
        for (List<Integer> testCase : testCases) {
            int cost = getMinimumMergeCost(testCase);
            result.add(Integer.toString(cost));
        }
        return result.toString();
    }


    public static void main(String[] args) {
        try {
            tokenize();
            int T = nextInt();
            List<List<Integer>> testCases = inputTestCases(T);
            String answer = solve(testCases);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
