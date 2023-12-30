/**
 * 접근 :
 *      1. 외판원 순회처럼 DFS로 접근하며 현재 상태에 어떤 값이 들어가는지 반복할 필요없게 기록하면서 감
 *      2. 근데 현재노드 / 방문한노드 에 더불어 현재가격 까지 기록해야함
 *      3. 따라서 dp[현재노드][방문한노드(비트마스킹)][현재가격] 으로 이루어진 3차원 dp 배열 사용
 */

package week7.그림교환;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int fullBit;
    static int[][] matrix;
    static int[][][] dp;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        fullBit = (1 << N) - 1;

        matrix = new int[N][N];
        dp = new int[N][fullBit][10];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < fullBit; j++) Arrays.fill(dp[i][j], -1);
        }

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            for(int j = 0; j < N; j++) {
                int n = Character.getNumericValue(s.charAt(j));
                matrix[i][j] = n;
            }
        }

        System.out.println(dfs(0, 1, 0));

    }

    static int dfs(int now, int check, int price) {

        if(check == fullBit) {
            return Integer.bitCount(check);
        }

        if(dp[now][check][price] != -1) return dp[now][check][price];

        dp[now][check][price] = Integer.bitCount(check);


        for(int i = 0; i < N; i++) {
            int next = check | 1 << i;

            if(matrix[now][i] >= price && (check & (1 << i)) == 0) {
                dp[now][check][price] = Math.max(dp[now][check][price], dfs(i, next, matrix[now][i]));
            }
        }


        return dp[now][check][price];
    }

}

class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());

        System.out.println(n & (1 << 2));
    }
}