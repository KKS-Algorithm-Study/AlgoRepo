/**
 * 접근 :
 *      1. 각 의상종류의 수 + 1 을 계속 곱해나감
 *      2. 그 결과 - 1 이 정답
 */

package week14.n1_패션왕신해빈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());

        for(int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            HashMap<String, Integer> hm = new HashMap<>();
            for(int j = 0; j < n; j++) {
                st = new StringTokenizer(br.readLine());
                String str = st.nextToken();
                str = st.nextToken();
                int temp = hm.getOrDefault(str, 0);
                hm.put(str, temp + 1);
            }

            int result = 1;
            for(int num : hm.values()) {
                result = result * (num + 1);
            }

            System.out.println(result - 1);
        }
    }
}
