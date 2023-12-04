/**
 * 접근 :
 *      1. 가장 길이가 작은 고무줄의 길이 = 활의 길이
 *      2. 활의 길이가 가장 긴 경우를 출력. 만일 활을 만들 수 없다면 -1을 출력
 *      3. 고무줄의 둘레에 대해 이분탐색을 하며, 현재 mid 값에서 check
 *
 * check 함수 :
 *      1. mid 값에 해당하는 것이 가장 짧은 고무줄이 되도록 만들 수 있는가?
 *          -> 홈의 시작점부터 다음 홈까지 길이를 재며 mid값과 같다면 겹수 K가 되는지 판단
 *          -> K까지 된다면 2번으로 그렇지 않다면 홈의 시작점을 다음 배열로 옮기고 다시 길이재기
 *      2. 만들 수 있다면 mid 값을 우측으로, 만들 수 없다면 mid 값을 좌측으로
 *
 * 아 진심 못풀겠다.
 * 0~x번째 1~x번째 자르고 그다음 mid 크기만큼 또 자르듯이
 * 이걸 모든 구간을 시작점으로 두고 계산하는 방법을 구현하기와 마지막 홈과 첫 홈 사이의 원형 길이 구하기가 힘들다.
 */

package week4.최종병기활;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        int N, M, K;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        int[] home = new int[M];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            home[i] = Integer.parseInt(st.nextToken());
        }
        //여기까지 데이터 input

        Solution solution = new Solution(N, K, home);

    }
}

class Solution {
    int N;  //고무줄의 둘레
    int K;  //필요한 고무줄겹의 수
    int[] home;   //홈의 위치

    public Solution(int n, int k, int[] home) {
        N = n;
        K = k;
        this.home = home;
    }

    void run() {
        int s = 0;
        int e = N;
        int mid;
        while(s < e) {
            mid = (s + e) / 2;
            if(check(mid)) {
                s = mid;
            }
            else {
                e = mid - 1;
            }
        }
    }

    boolean check(int mid) {
        int now = 0;
        int next = 1;
        int cnt = 0;
        while(now < home.length) {
            if(home[next] - home[now] == mid) {
                cnt = 2;
                now = next;
                next = now + 1;
                break;
            }

        }
        return false;
    }

}