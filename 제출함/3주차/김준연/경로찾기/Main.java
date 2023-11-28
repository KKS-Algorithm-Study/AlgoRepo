/**
 * 접근 :
 * 1. 플로이드 워셜로 중간 점을 통해 갈 수 있는지 비교
 *
 * 시간 복잡도 : O(N^3)
 */

package week3.경로찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        int N;
        int[][] board;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        board = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //여기까지 데이터 input

        Solution.run(board, N);

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                System.out.printf(String.valueOf(board[i][j]));
                System.out.printf(" ");
            }
            System.out.printf("\n");
        }

    }
}

class Solution {
    static void run(int[][] board, int N) {
        for(int k = 0; k < N; k++) {
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if (board[i][j] == 0 && board[i][k] == 1 && board[k][j] == 1) {
                        board[i][j] = 1;
                    }
                }
            }
        }
    }

}
