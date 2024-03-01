/**
 * 접근 :
 *      1. 각 연속되는 초밥의 경우를 한번만 순회하기
 *      2. L과 R 포인트를 두고 옮겨가면서 각 초밥의 종류마다 몇 개 들고있는지
 *      3. 그리고 그 결과 몇 종류를 챙길 수 있는지 계산
 *
 * 시간복잡도 :
 *      O(N)
 *
 * 문제점 :
 *      1. 회전초밥이니까 끝에 부딪히지 않는다는걸 까먹어서 틀림
 */

package week16.n4_회전초밥;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[] sushi, type;
    static int N, d, k, c;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        sushi = new int[N];
        type = new int[d + 1];
        int result = 0;
        int cnt = 0;
        for(int i = 0; i < N; i++) {
            sushi[i] = Integer.parseInt(br.readLine());
            if(cnt < k) {
                if(type[sushi[i]] == 0) result++;
                type[sushi[i]]++;
                cnt++;
            }
        }

        int L = 0;
        int R = k - 1;

        int max = result;
        if(type[c] == 0) max++;

        while(L < N) {


            if(--type[sushi[L]] == 0) result--;
            L++;

            R++;
            if(R == N) R = 0;
            if(++type[sushi[R]] == 1) result++;

            if(type[c] == 0) {
                max = Math.max(max, result + 1);
            }
            else {
                max = Math.max(max, result);
            }

        }

        System.out.println(max);


    }
}
