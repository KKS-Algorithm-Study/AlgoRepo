import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 *  문제 풀이 방법:
 *      문자열에 두 번 이상 등장한 부분 문자열의 최대 길이를 구하는 문제
 *
 *      만약 완전 탐색 방식으로 문자열을 반복문 돌면서,
 *      이중 반복문으로 같은 알파벳을 찾고, 찾으면 삼중 반복문으로 다음 알파벳들을 비교해 겹치는 패턴 길이를 잴 수 있는데,
 *      이는 O(N ** 3)으로 시간 초과가 난다.
 *
 *      KMP 알고리즘의 파이 테이블을 만드는 방식으로 풀 수 있는데,
 *      파이 테이블을 만들면 (시간복잡도 O(N)) 그 중 최댓값이 접두사 중 겹치는 패턴이 있는 것들의 최장 길이인데,
 *      이걸 문자열을 순회하며(시간복잡도 O(N)) 각 인덱스를 시작 지점으로 하는 파이 테이블을 만들면 된다.
 *      이 방식의 최대 시간 복잡도는 O(N ** 2)인데, 연산 횟수는 25,000,000 이므로 시간초과가 나지 않는다.
 *
 *  시간 복잡도:
 *      위에서 설명했듯, 모든 인덱스에서 파이테이블을 만들기 때문에 O(N ** 2)이다.
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

    private static int[] makePiTable(String pattern) {
        int[] piTable = new int[pattern.length()];
        int suffixIdx = 0;
        for (int prefixIdx = 1; prefixIdx < pattern.length(); prefixIdx++) {
            while (suffixIdx != 0 && pattern.charAt(prefixIdx) != pattern.charAt(suffixIdx)) {
                suffixIdx = piTable[suffixIdx - 1];
            }
            if (pattern.charAt(prefixIdx) == pattern.charAt(suffixIdx)) {
                suffixIdx += 1;
                piTable[prefixIdx] = suffixIdx;
            }
        }
        return piTable;
    }

    private static String solve(String pattern) {
        int maxLength = 0;
        for (int baseIdx = 0; baseIdx + maxLength < pattern.length(); baseIdx++) {
            int[] piTable = makePiTable(pattern.substring(baseIdx));
            maxLength = IntStream.concat(Arrays.stream(piTable), IntStream.of(maxLength))
                    .reduce(Math::max)
                    .orElse(maxLength);
        }
        return Integer.toString(maxLength);
    }

    public static void main(String[] args) {
        try {
            String pattern = br.readLine().strip();
            String answer = solve(pattern);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}