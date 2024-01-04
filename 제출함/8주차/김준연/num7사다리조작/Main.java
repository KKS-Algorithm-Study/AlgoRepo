/**
 * 접근 :
 *      1. 일단 각 세로가 어디로 도달하는지 파악하는 함수 작성.
 *      2. 처음에 각 세로가 어디로 가는지 파악하고
 *      3. 가능한 사다리를 위에서 부터 하나씩 놓아본다.
 *      4. 가로줄 사다리를 넘버링하면서 dfs하는데, 현재 연산중인 사다리 보다 작은 번호의 사다리는 가지 않는다.
 *      5. 놓은 사다리가 3을 넘으면 return하고 만일 성공하면 min을 갱신한다.
 *
 * 문제점 :
 *      1. 현재까지 놓은 사다리를 ArrayList에 sort하여 담거나 PriorityQueue에 담아서 HashSet에 넣어 중복 연산을 배제하려 했음
 *          => 시간초과
 *          => 현재보다 앞선 사다리를 굳이 하나씩 집어서 한적이 있나 비교하지 말고 아예 현재보다 뒷쪽만 연산하여 해결
 */

package week8.num7사다리조작;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int min = 50000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());

        int[][] ladder = new int[H + 1][N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            ladder[a][b] = 1;
            ladder[a][b + 1] = 2;
        }

        dfs(ladder, 0, 0);

        System.out.println(min == 50000 ? -1 : min);
    }

    static void dfs(int[][] ladder, int cnt, int line_number) {

        if(cnt > 3 || cnt > min) return;
        if (tryLadder(ladder)) {
            min = Math.min(min, cnt);
            return;
        }

        int next_line_number = 0;

        for (int i = 1; i < ladder.length; i++) {
            for (int j = 1; j < ladder[0].length - 1; j++) {
                next_line_number++;
                int left = ladder[i][j];
                int right = ladder[i][j + 1];

                if (ladder[i][j] == 0 && ladder[i][j + 1] == 0 && next_line_number > line_number) {
                    ladder[i][j] = 1;
                    ladder[i][j + 1] = 2;
                    dfs(ladder, cnt + 1, next_line_number);
                    ladder[i][j] = left;
                    ladder[i][j + 1] = right;
                }

            }
        }

    }

    static boolean tryLadder(int[][] ladder) {

        for (int i = 1; i < ladder[0].length; i++) {
            int nowLine = i;
            for (int j = 1; j < ladder.length; j++) {
                if (ladder[j][nowLine] == 1) nowLine++;
                else if (ladder[j][nowLine] == 2) nowLine--;
            }
            if(nowLine != i) return false;
        }

        return true;
    }


}
