/**
 * 접근 :
 *      500*500 필드 = 250000
 *      4칸이니까 10만
 *      각 블록의 회전, 대칭 경우의 수
 *      1번 2개
 *      2번 1개
 *      3번 8개
 *      4번 4개
 *      5번 4개
 *
 *      총 19개
 *
 *      완전탐색시 190만번 연산
 *
 */

package week9.n6_테트로미노;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static int[][] stage;
    static int max = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        stage = new int[N][M];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++) {
                stage[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        bf();

        System.out.println(max);
    }

    static void bf() {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                max = Math.max(max, getPoint(i, j));
            }
        }
    }

    static int getPoint(int x, int y) {
        int result = -1;
        result = Math.max(result, case_1(x, y));
        result = Math.max(result, case_2(x, y));
        result = Math.max(result, case_3(x, y));
        result = Math.max(result, case_4(x, y));
        result = Math.max(result, case_5(x, y));
        return result;
    }

    static int case_1(int x, int y) {
        int result = -1;
        int sum = stage[x][y];
        for(int i = 1; i <= 3; i++) {
            if(getPossible(x, y + i)) sum += stage[x][y+i];
            else {
                sum = -1;
                break;
            }
        }
        result = Math.max(result, sum);
        sum = stage[x][y];
        for(int i = 1; i <= 3; i++) {
            if(getPossible(x + i, y)) sum += stage[x+i][y];
            else {
                sum = -1;
                break;
            }
        }
        result = Math.max(result, sum);
        return result;
    }

    static int case_2(int x, int y) {
        int result = -1;
        if(getPossible(x+1, y+1)) {
            result = stage[x][y] + stage[x][y+1] + stage[x+1][y+1] + stage[x+1][y];
        }
        return result;
    }

    static int case_3(int x, int y) {

        int width = stage[x][y];
        int length = stage[x][y];
        if(getPossible(x, y+2)) {
            int max = 0;
            for(int i = 1; i <= 2; i++) width += stage[x][y+i];
            if(getPossible(x+1, y)) max = Math.max(max, stage[x+1][y]);
            if(getPossible(x-1, y)) max = Math.max(max, stage[x-1][y]);
            if(getPossible(x+1, y+2)) max = Math.max(max, stage[x+1][y+2]);
            if(getPossible(x-1, y+2)) max = Math.max(max, stage[x-1][y+2]);
            width += max;
        }
        if(getPossible(x+2, y)) {
            int max = 0;
            for(int i = 1; i <= 2; i++) length += stage[x+i][y];
            if(getPossible(x, y-1)) max = Math.max(max, stage[x][y-1]);
            if(getPossible(x, y+1)) max = Math.max(max, stage[x][y+1]);
            if(getPossible(x+2, y-1)) max = Math.max(max, stage[x+2][y-1]);
            if(getPossible(x+2, y+1)) max = Math.max(max, stage[x+2][y+1]);
            length += max;
        }

        return Math.max(width,length);
    }

    static int case_4(int x, int y) {
        int result = -1;

        if(getPossible(x+2, y+1)) {
            int sum = stage[x][y] + stage[x+1][y] + stage[x+1][y+1] + stage[x+2][y+1];
            result = Math.max(result, sum);
        }
        if(getPossible(x+2, y-1)) {
            int sum = stage[x][y] + stage[x+1][y] + stage[x+1][y-1] + stage[x+2][y-1];
            result = Math.max(result, sum);
        }
        if(getPossible(x+1, y+2)) {
            int sum = stage[x][y] + stage[x][y+1] + stage[x+1][y+1] + stage[x+1][y+2];
            result = Math.max(result, sum);
        }
        if(getPossible(x-1, y+2)) {
            int sum = stage[x][y] + stage[x][y+1] + stage[x-1][y+1] + stage[x-1][y+2];
            result = Math.max(result, sum);
        }

        return result;
    }

    static int case_5(int x, int y) {

        int width = 0;
        int length = 0;
        if(getPossible(x, y + 2)) {
            width = stage[x][y] + stage[x][y+1] + stage[x][y+2];
            int max = 0;
            if(getPossible(x + 1, y + 1)) max = Math.max(max, stage[x+1][y+1]);
            if(getPossible(x - 1, y + 1)) max = Math.max(max, stage[x-1][y+1]);
            width += max;
        }
        if(getPossible(x+2, y)) {
            length = stage[x][y] + stage[x+1][y] + stage[x+2][y];
            int max = 0;
            if(getPossible(x + 1, y + 1)) max = Math.max(max, stage[x+1][y+1]);
            if(getPossible(x + 1, y - 1)) max = Math.max(max, stage[x+1][y-1]);
            length += max;
        }

        return Math.max(width, length);
    }

    static boolean getPossible(int x, int y) {
        if(x >= N || x < 0) return false;
        if(y >= M || y < 0) return false;
        return true;
    }

}
