/**
 * !! 답안 참고함
 *
 * 접근 :
 *      1. DFS로 각 루트의 분기마다 어느 쪽으로 가는 것이 최선인지 dp배열에 기록.
 *      2. 이 때 dp배열은 현재위치, 여태 방문한 노드 를 표현
 *      3. 여태 방문한 노드는 비트마스킹 표기
 *          => 0번과 2번 도시를 방문했다면 0101
 *          다음 방문이 3번이라면 1101
 */

package week7.외판원순회;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N, fullBit, INF = 20000000;
    static int[][] dp, W;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        fullBit = (1<<N) - 1;
        dp = new int[N][fullBit];
        W = new int[N][N];
        for(int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(tsp(0, 1));

    }

    static int tsp(int x, int check) {
        //모든 도시를 방문했다면
        if(check == fullBit) {
            if(W[x][0] == 0) return INF;    //근데 경로가 없다면 INF 반환
            return W[x][0];
        }

        //이미 방문한 도시들과 현재지점이면 불필요한 계산 하지말고 반환
        if(dp[x][check] != -1) return dp[x][check];

        dp[x][check] = INF;

        //현재 도시에서 갈 수 있는 도시, 즉 본인의 하위트리 중 가장 비용이 싼 루트를 dp[현재][방문기록] 에 저장한다.
        for(int i = 0; i < N; i++) {
            int next = check | (1<<i);

            if(W[x][i] == 0 || (check & (1<<i)) != 0) continue;

            dp[x][check] = Math.min(dp[x][check], tsp(i, next) + W[x][i]);
        }

        return dp[x][check];

    }
}
