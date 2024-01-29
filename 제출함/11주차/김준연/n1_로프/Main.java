/**
 * 접근 :
 *      1. 로프들을 내림차순 정렬시킨다.
 *      2. 하나씩 더하면서 w/k를 해서 이득이면 갱신 아니면 갱신X
 */

package week11.n1_로프;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        ArrayList<Integer> arr = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            int rope = Integer.parseInt(br.readLine());
            arr.add(rope);
        }

        arr.sort(Collections.reverseOrder());

        int result = 0;
        for(int i = 0; i < N; i++) {
            int now = arr.get(i);

            int limit = now * (i + 1);
            if(result < limit) {
                result = limit;
            }
        }

        System.out.println(result);
    }
}
