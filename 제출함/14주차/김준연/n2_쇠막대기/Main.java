/**
 * 접근 :
 *      1. ( : now + 1
 *          현재 쇠막대기 수
 *      2. ) 인데 앞이 ( : now - 1 & result + now
 *          현재 쇠막대기 수만큼 짜르니까 그 수만큼 결과 +, now 빼는 이유는 앞의 괄호가 쇠막대기를 의미하는게 아니기 때문
 *      3. ) : now - 1 & result + 1
 *          쇠막대기 끝이니까 하나빼고, 쇠막대기 기본값 1개 +
 */

package week14.n2_쇠막대기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();

        int now = 0;
        int result = 0;

        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if(c == '(') now++;
            else if(c == ')' && str.charAt(i -1) == '(') {
                now--;
                result += now;
            }
            else {
                now--;
                result++;
            }
        }

        System.out.println(result);

    }
}
