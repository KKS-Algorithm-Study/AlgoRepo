/**
 * !! 답안참고함
 *
 * 접근 :
 *      1. 추 목록을 받아온다.
 *      2. 2차원 dp 행은 추, 열은 무게를 의미
 *      3. dp[0][0]부터 표현가능함 : true를 찍음
 *      3. 여기서 dfs 세갈래로 나뉨
 *          1) 다음 추를 올리지 않음. 그냥 같은 무게 true 찍음
 *          2) 다음 추를 올림. 현재 무게 + 추 무게 true 찍음
 *          3) 다음 추를 반대편에 올림. 현재무게 - 추 무게 true 찍음. (음수처리)
 *      4. 연산 후 나오는 결과
 *          1) 한 쪽에 추를 올릴 때의 무게의 모든 경우
 *          2) 양 쪽에 나누어 올릴 때 두 편의 무게 차의 모든 경우
 *      5. 구슬의 무게를 입력받고, dp의 마지막 행 구슬 무게 열이 true인지 확인하여 Y / N 출력
 *
 * 문제점 :
 *      1. 메모리초과
 *          1) 추에 대해 배낭 담기로 표현가능한 무게를 구함.
 *          2) 구슬과 추를 이용해 표현가능한 무게를 구함
 *          3) 2번을 표현할 때 사용한 추들을 제외한 추들로 다시 배낭 담기 돌림.
 *          4) 다시 돌린 DP로 2번 무게를 표현할 수 있는지 체크함.
 *          시간복잡도는 이론상 가능했는데 구현이 이상리만치 복잡하긴했음.
 *
 */

package week6.양팔저울;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[] chu;
    static boolean[][] dp;
    static int MAX = 400001;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        chu = new int[N+1];
        dp = new boolean[N+1][MAX];

        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < N+1; i++) {
            chu[i] = Integer.parseInt(st.nextToken());
        }
        dp(0, 0);

        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i++) {
            int curr_ball = Integer.parseInt(st.nextToken());
            if(dp[N][curr_ball]) sb.append("Y ");
            else sb.append("N ");
        }

        System.out.println(sb);

    }

    static void dp(int idx, int weight) {
        if(dp[idx][weight]) return;
        dp[idx][weight] = true;
        if(idx>=N) return;

        dp(idx+1, weight);
        dp(idx+1, weight + chu[idx+1]);
        dp(idx+1, Math.abs(weight - chu[idx+1]));
    }
}
