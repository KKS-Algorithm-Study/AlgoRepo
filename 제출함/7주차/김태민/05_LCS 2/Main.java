import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      최장 공통 부분 수열, 즉, 두 수열이 주어졌을 때 모두의 부분 수열이 되는 수열 중 가장 긴 것을 찾는 문제이다
 *      길이와 함께 그 수열이 무엇인지도 출력해야한다
 *
 *      마찬가지로 2차원 DP로 이중 포문으로 두 문자열을 순회하며
 *      같으면 [i-1][j-1]에서 +1, 다르면 [i-1][j], [i][j-1]중 최대값을 저장하며 길이를 먼저 구하고,
 *
 *      "길이가 +1 된 경우 == 공통 문자를 찾은 경우" 이므로,
 *      역으로 생각해 dp 배열의 현재 칸 기준, 왼쪽과 윗쪽 모두 현재 칸과 숫자가 다른 경우 == 이전에 공통문자를 찾아 +1 된 경우 이다.
 *      따라서 dp에서 왼쪽칸과 윗쪽칸을 탐색하며
 *          같은 숫자가 나오면 그 칸으로 이동
 *          둘 다 다른 숫자라면 왼쪽 윗칸으로 이동하고, 그 칸의 인덱스 == 공통 문자 인덱스로 공통 문자열을 추출
 *      마지막에 모인 문자열을 모아서 출력하면 된다.
 *
 *
 *
 *  시간 복잡도:
 *      문자열의 길이만큼 이중 포문을 돌기 때문에
 *      O(N**2)이 나온다.
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

    private static String solve(String patternA, String patternB) {
        StringJoiner result = new StringJoiner("\n");
        int[][] lcsDp = new int[patternA.length() + 1][patternB.length() + 1];
        for (int idxA = 0; idxA < patternA.length(); idxA++) {
            for (int idxB = 0; idxB < patternB.length(); idxB++) {
                if (patternA.charAt(idxA) == patternB.charAt(idxB)) {
                    lcsDp[idxA + 1][idxB + 1] = lcsDp[idxA][idxB] + 1;
                } else {
                    lcsDp[idxA + 1][idxB + 1] = Math.max(lcsDp[idxA][idxB + 1], lcsDp[idxA + 1][idxB]);
                }
            }
        }
        int lcsLength = lcsDp[patternA.length()][patternB.length()];
        result.add(Integer.toString(lcsLength));
        if (lcsLength == 0) {
            return result.toString();
        }

//        for (int[] row: lcsDp) {
//            System.out.println(Arrays.toString(row));
//        }

        int idxA = patternA.length();
        int idxB = patternB.length();
        char[] lcsPattern = new char[lcsLength];
        while (lcsLength != 0) {
            if (lcsDp[idxA - 1][idxB] == lcsLength) {
                idxA--;
                continue;
            }
            if (lcsDp[idxA][idxB - 1] == lcsLength) {
                idxB--;
                continue;
            }
            idxA--;
            idxB--;
            lcsPattern[--lcsLength] = patternA.charAt(idxA);
        }

        result.add(String.valueOf(lcsPattern));
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            String patternA = br.readLine().strip();
            String patternB = br.readLine().strip();

            String answer = solve(patternA, patternB);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

