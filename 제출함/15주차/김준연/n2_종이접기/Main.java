/**
 * 접근 :
 *      1. 가운데 접은부분을 기준으로 양 옆은 서로 대칭으로 달라야함.
 *      2. 이거 확인했으면 다시 기준의 양 옆의 가운데를 기준올 또 대칭인지 확인함
 *      3. 이렇게 분할정복하기
 */

package week15.n2_종이접기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());

        Loop1 :
        for(int i = 0; i < T; i++) {
            String str = br.readLine();
            int s = 0;
            int e = str.length() - 1;

            if(solve(str, s, e)) {
                System.out.println("YES");
            }
            else System.out.println("NO");
        }
    }

    static boolean solve(String str, int s, int e) {
        //System.out.println(s + " / " + e);
        int l = s;
        int r = e;
        while(s < e) {
            if(str.charAt(s) == str.charAt(e)) {
                return false;
            }
            s++;
            e--;
        }

        if(r - l <= 2) return true;
        if(solve(str, l, s-1) && solve(str, s+1, r)) return true;
        return false;
    }
}
