/**
 * 접근 :
 *      1. 문자열을 내가 원하는 형태로 잘 저장하기
 *      2. 앞에서 뺄지 뒤에서 뺄지 뒤집어야하므로 데크 사용
 *      3. 다시 문자열 잘 만들기
 */

package week14.n4_AC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());

        Loop1:
        for(int i = 0; i < T; i++) {
            String command = br.readLine();
            int n = Integer.parseInt(br.readLine());
            String str = br.readLine();

            str = str.substring(1, str.length() - 1);

            String[] arr = str.split(",");



            Deque<String> dq = new ArrayDeque<>(Arrays.asList(arr));

            if(arr[0].isEmpty()) {
                dq.removeLast();
            }

            boolean trigger = true;

            for(int j = 0; j < command.length(); j++) {
                char c = command.charAt(j);
                if(c == 'R') {
                    trigger = !trigger;
                }
                else if(c == 'D') {
                    if(dq.isEmpty()) {
                        System.out.println("error");
                        continue Loop1;
                    }
                    if(trigger) dq.removeFirst();
                    else dq.removeLast();
                }
                else {
                    System.out.println("error");
                    continue Loop1;
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            while(!dq.isEmpty()) {
                if(trigger) sb.append(dq.removeFirst());
                else sb.append(dq.removeLast());
                if(dq.size() > 0) sb.append(",");
            }
            sb.append("]");

            System.out.println(sb);
        }
    }
}
