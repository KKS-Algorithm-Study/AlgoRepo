import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *  문제 풀이 방법:
 *      세 단어의 LCS를 구하는 문제
 *
 *      두 단어의 LCS -> 2차원 DP 라면,
 *      세 단어의 LCS -> 3차원 DP를 쓰면 된다.
 *
 *      세 단어의 특정 알파벳이 같다면, {알파벳을 포함하기 이전의 경우 + 1}으로 갱신
 *      다르다면, 각 단어에서 그 알파벳을 포함하기 이전의 경우들 중 최대 길이로 갱신
 *      이 후, dp의 마지막 칸을 출력하면 된다
 *
 *  주의할 점:
 *      단어 두개의 LCS를 먼저 구한 후, 그걸로 LCS를 구하는 경우,
 *      앞의 두 단어의 LCS가 여러개인데 그 중 정답에 포함되지 않는 LCS를 얻을 수 있어서 오답이 나온다.
 *      abc
 *      acb
 *      aczz
 *      이 경우, 정답은 ac로 2겠지만,
 *      첫 두 단어의 LCS를 ab로 구하면 결과로 a가 나오게 된다.
 *
 *  시간 복잡도:
 *      3차원 DP를 사용하므로,
 *      O(N**3)이다.
 */
public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static String solve(String wordA, String wordB, String wordC) {
        wordA = "." + wordA;
        wordB = "." + wordB;
        wordC = "." + wordC;
        int[][][] lcsDp = new int[wordA.length()][wordB.length()][wordC.length()];
        for (int idxA = 1; idxA < wordA.length(); idxA++) {
            for (int idxB = 1; idxB < wordB.length(); idxB++) {
                for (int idxC = 1; idxC < wordC.length(); idxC++) {
                    if (wordA.charAt(idxA) == wordB.charAt(idxB) & wordB.charAt(idxB) == wordC.charAt(idxC)) {
                        lcsDp[idxA][idxB][idxC] = lcsDp[idxA - 1][idxB - 1][idxC - 1] + 1;
                    } else {
                        lcsDp[idxA][idxB][idxC] = Math.max(lcsDp[idxA][idxB][idxC], lcsDp[idxA - 1][idxB][idxC]);
                        lcsDp[idxA][idxB][idxC] = Math.max(lcsDp[idxA][idxB][idxC], lcsDp[idxA][idxB - 1][idxC]);
                        lcsDp[idxA][idxB][idxC] = Math.max(lcsDp[idxA][idxB][idxC], lcsDp[idxA][idxB][idxC - 1]);
                    }
                }
            }
        }
        return Integer.toString(lcsDp[wordA.length() - 1][wordB.length() - 1][wordC.length() - 1]);
    }

    public static void main(String[] args) {
        try {
            String wordA = br.readLine().strip();
            String wordB = br.readLine().strip();
            String wordC = br.readLine().strip();
            String answer = solve(wordA, wordB, wordC);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}