/**
 * !!답안 참고함
 *
 * 접근 :
 *      1. 1을 만나면 우측으로 쭉 가보고 최대 크기 색종이가 뭔지 판단.
 *      2. 제일 큰 색종이 붙이고 다음 연산 진행
 *      3. 진행이 불가하면 백트래킹, 다 붙였으면 min값 갱신
 *
 * 문제점 :
 *      현좌표에서 사각형의 크기를 구하는데 자꾸 잘못구함
 *      디버깅 하려니까 연산횟수가 너무 많아서 중단점을 잡기가 힘듦(디버깅 실력부족)
 *      그래서 답 보고 따라씀
 */

package week8.num8색종이붙이기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] inven = {0, 5, 5, 5, 5, 5};
    static int[][] paper = new int[10][10];
    static int[][] visited = new int[10][10];
    static int min = 27;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for(int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 10; j++) {
                paper[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0, 0);
        System.out.println(min == 27 ? -1 : min);

    }

    static void dfs(int x, int y, int cnt) {
        if(x >= 9 && y > 9) {
            min = Math.min(min, cnt);
            return;
        }
        if(cnt >= min) return;
        if(y > 9) {
            dfs(x + 1, 0, cnt);
            return;
        }


        if(paper[x][y] == 1 && visited[x][y] == 0) {
            int size = 5;
            while(size > 0) {
                if(inven[size] > 0 && isAttach(x, y, size)) {
                    int[][] save  = stick(x, y, size);
                    inven[size]--;
                    dfs(x, y + 1, cnt + 1 );
                    visited = save;
                    inven[size]++;
                }
                size--;
            }
        }
        else {
            dfs(x, y + 1, cnt);
        }
    }

    static boolean compare() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(paper[i][j] != visited[i][j]) return false;
            }
        }
        return true;
    }


    static int[][] stick(int x, int y, int size) {
        int[][] before = new int[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                before[i][j] = visited[i][j];
            }
        }
        for(int i = x; i < x + size; i++) {
            for(int j = y; j < y + size; j++) {
                visited[i][j] = 1;
            }
        }

        return before;
    }

    public static boolean isAttach(int x, int y, int size) {
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (i < 0 || i >= 10 || j < 0 || j >= 10) {
                    return false;
                }

                if (paper[i][j] != 1 || visited[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }




}
