/**
 * 접근 :
 *      1. time을 int형으로 바꾸고 시작시간보다 빨리 채팅 친 인원들을 HashSet에 담음
 *      2. 그다음 끝나고나서 방송 종료 전까지 채팅친 인원들이 HashSet에 들어있는지 판별함
 *      3. 이 때 들어있으면 remove시키고 cnt++
 *      4. cnt출력
 */

package week10.n2_싸이버개강총회;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        String S = st.nextToken();
        String E = st.nextToken();
        String Q = st.nextToken();

        int s = Character.getNumericValue(S.charAt(0)) * 1000 + Character.getNumericValue(S.charAt(1)) * 100 + Character.getNumericValue(S.charAt(3)) * 10 + Character.getNumericValue(S.charAt(4));
        int e = Character.getNumericValue(E.charAt(0)) * 1000 + Character.getNumericValue(E.charAt(1)) * 100 + Character.getNumericValue(E.charAt(3)) * 10 + Character.getNumericValue(E.charAt(4));
        int q = Character.getNumericValue(Q.charAt(0)) * 1000 + Character.getNumericValue(Q.charAt(1)) * 100 + Character.getNumericValue(Q.charAt(3)) * 10 + Character.getNumericValue(Q.charAt(4));

        int cnt = 0;
        HashSet<String> hs = new HashSet<>();

        String str = null;

        while((str = br.readLine()) != null) {
            if(str.isEmpty()) break;
            st = new StringTokenizer(str);
            String time = st.nextToken();
            String name = st.nextToken();
            int int_time = Character.getNumericValue(time.charAt(0)) * 1000 + Character.getNumericValue(time.charAt(1)) * 100 + Character.getNumericValue(time.charAt(3)) * 10 + Character.getNumericValue(time.charAt(4));

            if(int_time <= s) {
                hs.add(name);
            }
            else if(e <= int_time && int_time <= q) {
                if(hs.contains(name)) {
                    cnt++;
                    hs.remove(name);
                }
            }
        }


        System.out.println(cnt);
    }
}
