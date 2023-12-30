/**
 * 접근 :
 *      1. 0~x까지 오름차순 LIS하고 x~n까지 내림차순 LIS해서 둘의 합이 가장 큰걸 뽑자!
 *      2. 모든 경우의 수에 대해
 *
 *  반례 :
 *      1)
 *          6
 *          1 1 1 2 2 1
 *
 *          오름차순 1 2
 *          내림차순 2 1
 *          합 4가 나옴
 *          사실 1 2 1 인데..
 *
 *          0~3번 / 4~5번 연산하는걸
 *          0~3번 / 3~5번 연산하고 -1 로 범위바꿔서 해결
 *
 *  시간복잡도 :
 *      LIS가 O(N log N) 이걸 2번
 *      이걸 N번 반복
 *      2 N N logN = O(N^2 log N)
 */

package week7.가장긴바이토닉부분수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int A = Integer.parseInt(st.nextToken());
        int[] Ai = new int[A];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < A; i++) {
            Ai[i] = Integer.parseInt(st.nextToken());
        }

        int max = 1;

        int[] dp = new int[A];
        int idx = 1;
        dp[0] = Ai[0];
        for(int i = 1; i < A; i++) {
            int reverse = reverseLis(A, i - 1, Ai);

            if(dp[idx - 1] < Ai[i]) {
                dp[idx] = Ai[i];
                idx++;
            }
            else {
                int point = binarySearch(dp, idx - 1, Ai[i]);
                dp[point] = Ai[i];
            }

            max = Math.max(max, idx + reverse - 1);

        }

        System.out.println(max);
    }

    static int reverseLis(int A, int start, int[] Ai) {
        int[] dp = new int[A];
        dp[0] = Ai[start];
        int idx = 1;
        for(int i = start + 1; i < A; i++) {
            if(dp[idx - 1] > Ai[i]) {
                dp[idx] = Ai[i];
                idx++;
            }
            else {
                int point = reverseBinarySearch(dp, idx - 1, Ai[i]);
                dp[point] = Ai[i];
            }
        }

        return idx;
    }

    static int binarySearch(int[] dp, int e, int n) {
        int s = 0;
        int mid = (s + e) / 2;
        int answer = mid;
        while(s < e) {
            mid = (s + e) / 2;
            if(n <= dp[mid]) {
                answer = mid;
                e = mid;
            }
            else {
                s = mid + 1;
                answer = s;
            }
        }

        return answer;
    }

    static int reverseBinarySearch(int[] dp, int e, int n) {
        int s = 0;
        int mid = (s + e) / 2;
        int answer = mid;
        while(s < e) {
            mid = (s + e) / 2;
            if(n >= dp[mid]) {
                answer = mid;
                e = mid;
            }
            else {
                s = mid + 1;
                answer = s;
            }
        }

        return answer;
    }
}
