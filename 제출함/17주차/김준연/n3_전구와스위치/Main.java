/**
 * !!답안참고함
 *
 * 접근 :
 *      1. 첫번째 전구가 켜진 경우, 꺼진 경우 두 가지 배열로 시작한다.
 *      2. 두번째 인덱스부터 순차적으로 이전 전구가 기대값과 다르다면 현재 버튼을 누르고, 같다면 지나가는 식으로 반복한다.3
 *      3. 그럼 모든 전구가 기대값과 같아지지고 최후의 N번째 전구만 같은지 다른지 판별하면 된다.
 *      4. 이후 첫 전구를 킨 경우, 끈 경우 중 최소값을 출력한다.
 */

package week17.n3_전구와스위치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static boolean[] sequence1 , sequence2, answer;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        answer = new boolean[N];

        sequence1 = new boolean[N];
        sequence2 = new boolean[N];

        String str = br.readLine();
        for(int i = 0; i < str.length(); i++) {
            int n = Character.getNumericValue(str.charAt(i));
            sequence1[i] = n == 1;
            sequence2[i] = n == 1;
        }

        int cnt1 = 0, cnt2 = 0;

        if(sequence1[0]) {
            sequence1[0] = false;
            sequence1[1] = !sequence1[1];
            cnt1++;
        }
        if(!sequence2[0]) {
            sequence2[0] = true;
            sequence2[1] = !sequence2[1];
            cnt2++;
        }

        str = br.readLine();
        for(int i = 0; i < str.length(); i++) {
            int n = Character.getNumericValue(str.charAt(i));
            answer[i] = n == 1;
        }



        for(int i = 1; i < N - 1; i++) {
            if(sequence1[i - 1] != answer[i - 1]) {
                sequence1[i - 1] = !sequence1[i - 1];
                sequence1[i] = !sequence1[i];
                sequence1[i + 1] = !sequence1[i + 1];
                cnt1++;
            }
            if(sequence2[i - 1] != answer[i - 1]) {
                sequence2[i - 1] = !sequence2[i - 1];
                sequence2[i] = !sequence2[i];
                sequence2[i + 1] = !sequence2[i + 1];
                cnt2++;
            }
        }

        if(sequence1[N-2] != answer[N-2]) {
            sequence1[N-2] = !sequence1[N-2];
            sequence1[N-1] = !sequence1[N-1];
            cnt1++;
        }

        if(sequence2[N-2] != answer[N-2]) {
            sequence2[N-2] = !sequence2[N-2];
            sequence2[N-1] = !sequence2[N-1];
            cnt2++;
        }

        if(sequence1[N-1] != answer[N-1]) cnt1 = Integer.MAX_VALUE;
        if(sequence2[N-1] != answer[N-1]) cnt2 = Integer.MAX_VALUE;

        int min = Math.min(cnt1, cnt2);

        if(min == Integer.MAX_VALUE) {
            System.out.println(-1);
        }
        else System.out.println(min);


    }
}
