/**
 * -x -y 의 좌표가 유효한 범위(0~H-1) 내라면?
 * 그 좌표를 뺀 값이 진짜 값이다.
 */

package week17.n1_배열복원하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int H, W, X, Y;
    static int[][] result;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        Y = Integer.parseInt(st.nextToken());

        result = new int[H][W];

        for(int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < W; j++) {
                int n = Integer.parseInt(st.nextToken());
                if(getPossibility(i - X, j - Y)) {
                    n -= result[i-X][j-Y];
                }
                result[i][j] = n;
                System.out.print(n + " ");
            }
            System.out.println("");
        }


    }

    static boolean getPossibility(int x, int y) {
        if(x < 0 || y < 0) return false;
        if(x >= H || y >= W) return false;
        return true;
    }
}
