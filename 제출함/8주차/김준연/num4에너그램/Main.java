/**
 * 접근 :
 *      1. 우선 문자열을 각 문자로 나눠 char 배열에 사전순 정렬시킨다.
 *      2. 문자의 방문기록을 비트마스킹 하기 위해 fullBit를 구한다.
 *      3. 중복된 연산과 출력을 피하기 위해 한번 만든 적이 있는 중간과정의 문자열은 HashSet에 기록한다.
 *      4. 모든 chars를 방문했을 때, 현재까지 만든 문자열을 출력한다.
 *
 * 시간복잡도 :
 *      n개의 문자열에 대해
 *      각 m개의 길이를 가질 때
 *      최악의 경우 모든 결과를 방문하면 m^2
 *      => O(m^2)
 */

package week8.num4에너그램;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static char[] chars;
    static HashSet<String> hs;
    static int fullBit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String str = st.nextToken();
            chars = str.toCharArray();
            Arrays.sort(chars);
            fullBit = (1 << chars.length) - 1;
            hs = new HashSet<>();
            dfs(0, "");
        }
    }

    static void dfs(int bits, String str) {
        if(bits == fullBit) {
            System.out.println(str);
        }

        for(int i = 0; i < Integer.bitCount(fullBit); i++) {
            if((bits & 1 << i) == 0) {
                String next = str.concat(String.valueOf(chars[i]));
                if(!hs.contains(next)) {
                    hs.add(next);
                    dfs((bits | 1 << i), next);
                }
            }
        }
    }
}
