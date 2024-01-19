import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      지문 내에서 특정 패턴이 어디에 있는지 찾는 문제다.
 *
 *      지문과 패턴의 길이가 최대 100만이므로,
 *      지문의 각 인덱스에서 문자열이 맞는지 비교하는 연산을 하면 O(N * M)이라 시간이 부족하다.
 *
 *      시간 복잡도가 O(N + M)인 KMP 알고리즘으로 접근하는 문제로
 *      인덱스 길이까지 자른 문자열의 접두사와 접미사가 어디까지 겹치는지 저장하는 파이 테이블을 패턴을 대상으로 만들고,
 *      그 테이블을 이용해 지문에서 찾을 때, 해당 위치에 패턴이 맞지 않으면 단어를 땡겨와 다시 비교하도록 만든다.
 *
 *  주의할 점:
 *      문제 입력에 공백도 포함되어있기 때문에. 입력 받을 때 .strip()을 쓰면 의도적인 공백이 사라질 수 있다.
 *      예를 들어, "a a" 에서 " " 를 찾을 때, .strip()으로 공백을 지우면 찾을 수 없다.
 *
 *  시간 복잡도:
 *      O(N + M)이다.
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

    private static void fillPiTable(int[] piTable, int[] queryTable, String text, String pattern, int initIdx) {
        int suffixIdx = 0;
        for (int prefixIdx = initIdx; prefixIdx < text.length(); prefixIdx++) {
            while (suffixIdx > 0 && text.charAt(prefixIdx) != pattern.charAt(suffixIdx)) {
                suffixIdx = queryTable[suffixIdx - 1];
            }
            if (text.charAt(prefixIdx) == pattern.charAt(suffixIdx)) {
                piTable[prefixIdx] = ++suffixIdx;
                if (suffixIdx == pattern.length()) {
                    suffixIdx = queryTable[suffixIdx - 1];
                }
            }
        }
    }

    private static String solve(String text, String pattern) {
        int[] patternPiTable = new int[pattern.length()];
        fillPiTable(patternPiTable, patternPiTable, pattern, pattern, 1);
        int[] textPiTable = new int[text.length()];
        fillPiTable(textPiTable, patternPiTable, text, pattern, 0);

        int foundCount = 0;
        StringJoiner foundLocations = new StringJoiner(" ");
        for (int idx = pattern.length() - 1; idx < text.length(); idx++) {
            if (textPiTable[idx] == pattern.length()) {
                int foundLocation = idx - pattern.length() + 2;
                foundCount++;
                foundLocations.add(Integer.toString(foundLocation));
            }
        }

        String result = String.format("%d\n%s", foundCount, foundLocations);
        return result;
    }

    public static void main(String[] args) {
        try {
            String text = br.readLine();
            String pattern = br.readLine();
            String answer = solve(text, pattern);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
