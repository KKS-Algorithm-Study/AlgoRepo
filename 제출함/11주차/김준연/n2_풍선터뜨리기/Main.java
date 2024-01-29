/**
 * 접근 :
 *      1. 풍선을 터뜨리고, 그 안의 숫자만큼 이동
 *      2. 터진 풍선은 이동하는데 세지 않음
 */

package week11.n2_풍선터뜨리기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        ArrayList<Integer> balloons = new ArrayList<>();

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            balloons.add(Integer.parseInt(st.nextToken()));
        }

        ArrayList<Integer> result = new ArrayList<>();

        boolean[] boomed = new boolean[N];
        int cnt = 0;
        int idx = 0;
        while(true) {
            int go = balloons.get(idx);
            result.add(idx + 1);
            boomed[idx] = true;
            if(cnt == N - 1) break;
            idx = getIndex(idx, go, boomed);
            cnt++;
        }

        for(int num : result) {
            System.out.print(num + " ");
        }
    }

    static int getIndex(int idx, int go, boolean[] boomed) {
        int n = 0;
        if(go > 0) {
            while(n < go) {
                idx++;
                if(idx == boomed.length) idx = 0;
                if(!boomed[idx]) {
                    n++;
                }
            }
        }
        else {
            while(n < Math.abs(go)) {
                idx--;
                if(idx == -1) idx = boomed.length - 1;
                if(!boomed[idx]) {
                    n++;
                }
            }
        }
        return idx;
    }
}
