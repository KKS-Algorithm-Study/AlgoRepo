/**
 * 접근 :
 *      1. 백트래킹 + 비트마스크
 *      2. 모든 점에서 시작해본다.
 *      3. 여태까지 찍은 점에서
 *          1) 찍히지 않은 위치
 *          2) 갈 수 있는 위치
 *          2 가지를 만족하는 방향으로 계속 들어가는 dfs
 *      4. 남은 자리로 S 파벌을 채울 수 없는 경우 가지치기
 */

package week18.n6_소문난칠공주;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Main {

    static int[][] stage;
    static HashSet<Integer> hs = new HashSet<>();
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int result = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        stage = new int[5][5];

        for(int i = 0; i < 5; i++) {
            String str = br.readLine();
            for(int j = 0; j < 5; j++) {
                char c = str.charAt(j);
                if(c == 'Y') stage[i][j] = 0;
                else stage[i][j] = 1;
            }
        }

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                dfs(1 << (i*5 + j), stage[i][j] == 0 ? 0 : 1);
            }
        }

        System.out.println(result);
    }

    static void dfs(int bits, int cnt) {

        int remain = 4 - cnt;
        if((7- Integer.bitCount(bits)) < remain) return;

        if(hs.contains(bits)) return;
        hs.add(bits);

        if(Integer.bitCount(bits) >= 7) {
            result++;
            return;
        }

        for(int k = 0; k < 25; k++) {
            if((bits & (1 << k)) == 0) continue;
            int a = k / 5;
            int b = k % 5;
            for(int i = 0; i < 4; i++) {
                int x2 = a + dx[i];
                int y2 = b + dy[i];
                if(getPossibility(x2, y2)) {
                    if((bits & (1 << (x2*5 + y2))) == 0) {
                        dfs(bits | (1 << (x2*5 + y2)), stage[x2][y2] == 0 ? cnt : cnt + 1);
                    }
                }
            }
        }

    }

    static boolean getPossibility(int x, int y) {
        if(x < 0 || x >= 5) return false;
        if(y < 0 || y >= 5) return false;
        return true;
    }
}
