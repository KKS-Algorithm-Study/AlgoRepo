import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *  문제 풀이 방법:
 *
 *      크크루크크 문자열은 R로 이루어져 있고, 그 좌우로 같은 길이의 K가 붙어있는 문자열이라 하자 (R 사이에 K가 올 수 없다)
 *      (R, RR, KRRRRRK 모두 되지만, RKR, KRR 같은건 성립하지 않는다)
 *      K와 R로 이루어진 문자열이 주어질 때, 부분 문자열 중 가장 긴 크크루크크 문자열의 길이를 출력하는 문제다.
 *
 *      문자열의 부분 문자열 중 크크루크크 문자열을 구하려면,
 *      두개의 R을 잡고, 그 사이에 있는 R들만 가져오고, 좌우로 같은 길이의 K만 남기면 된다.
 *      이 문자열의 길이는 {R의 갯수 + min(왼쪽 K 길이, 오른쪽 K 길이) * 2} 가 된다.
 *
 *      투 포인터로 양 끝의 R을 잡고, 좌우의 K의 갯수를 각각 세주면서,
 *      둘 중 K 갯수가 더 작은 쪽의 포인터를 안쪽으로 옮겨가며 위의 수식을 계산하면 된다
 *
 *  주의할 점:
 *      문자열.split(기준) 메서드는 문자열이 기준 문자열로만 이루어져있으면 빈 배열을 반환한다.
 *      "RRR".split("R") 이면, ["", "", "", ""] 이 아니라, [] 을 반환한다.
 *
 *  시간 복잡도:
 *      투포인터로 전체 문자열 길이만큼 돌기 때문에 O(N)이다
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

    private static List<Integer> splitWordLength(String word, char splitter) {
        List<Integer> result = new ArrayList<>();
        int length = 0;
        for (int idx = 0; idx < word.length(); idx++) {
            if (word.charAt(idx) == splitter) {
                result.add(length);
                length = 0;
            } else {
                length++;
            }

        }
        result.add(length);
        return result;
    }

    private static String solve(String word) {
        List<Integer> kCounts = splitWordLength(word, 'R');

        int leftPointer = 0;
        int rightPointer = kCounts.size() - 1;
        int leftSum = kCounts.get(leftPointer);
        int rightSum = kCounts.get(rightPointer);
        int result = 0;
        while (leftPointer < rightPointer) {
            int kkrkkLength = Math.min(leftSum, rightSum) * 2 + (rightPointer - leftPointer);
            result = Math.max(result, kkrkkLength);

            if (leftSum < rightSum) {
                leftSum += kCounts.get(++leftPointer);
            } else {
                rightSum += kCounts.get(--rightPointer);
            }
        }

        return Integer.toString(result);
    }

    public static void main(String[] args) {
        try {
            String word = br.readLine().strip();
            String answer = solve(word);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}