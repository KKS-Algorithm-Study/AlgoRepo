/**
 * 접근 :
 * 1. 도시의 개수 = 노드의 개수 = N
 * 2. 간선의 개수 = 버스의 개수 = M
 * 3. 출발점, 도착점, 비용을 2차원 배열에 나타낸다.
 * 4. 이를 플로이드 워셜 알고리즘 적용 후 출력
 *
 * 문제점 :
 * 1. "시작 도시와 도착 도시를 연결하는 노선은 하나가 아닐 수 있다." 라는 구문을 하나만 존재한다고 봐서 중복 값 처리를 안했었음.
 *
 * 시간복잡도 : O(N^3)
 */

package week3.플로이드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        int N, M;
        int[][] board;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                board[i][j] = 99999999;
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            board[start-1][end-1] = (Math.min(cost, board[start - 1][end - 1]));
        }

        //여기까지 데이터 input

        Solution.run(board, N);

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(board[i][j] == 99999999) System.out.printf("0 ");
                else System.out.printf(board[i][j] + " ");
            }
            System.out.printf("\n");
        }

    }
}

class Solution {
    static void run(int[][] graph, int N) {
        for(int k = 0; k < N; k++) {
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if ((graph[i][j] > graph[i][k] + graph[k][j]) && i != j) {
                        graph[i][j] = graph[i][k] + graph[k][j];
                    }
                }
            }
        }
    }

}
