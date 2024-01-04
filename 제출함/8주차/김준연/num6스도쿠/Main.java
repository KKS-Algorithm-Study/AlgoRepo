/**
 * 접근 :
 *      1. dfs로 탐색
 *      2. 한 칸씩 채울때마다 가능한 경우의 수가 없으면 해당 재귀는 return
 *      3. 즁복된 연산을 하게될 경우가 있는가?
 *      => 없음
 *
 * 문제점 :
 *      1. 문제를 똑바로 안읽음. 그냥 스도쿠 룰을 까먹어서 3X3 연산을 배제함
 *          => 시간 너무 오래써서 코드 난잡하게 해결함
 */

package week8.num6스도쿠;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static int fullBits = (1 << 9) - 1;
    static ArrayList<Integer> zeros = new ArrayList<>();
    static int[][] stage = new int[9][9];
    static boolean stop = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[] rowBits = new int[9];
        int[] colBits = new int[9];
        int[] blockBits = new int[9];

        for (int i = 0; i < 9; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                int n = Integer.parseInt(st.nextToken());
                if (n == 0) zeros.add(i * 10 + j);
                rowBits[i] = rowBits[i] | (1 << n);
                colBits[j] = colBits[j] | (1 << n);
                if (i <= 2) {
                    if (j <= 2) blockBits[0] = blockBits[0] | (1 << n);
                    else if (j >= 6) blockBits[2] = blockBits[2] | (1 << n);
                    else blockBits[1] = blockBits[1] | (1 << n);
                } else if (i >= 6) {
                    if (j <= 2) blockBits[6] = blockBits[6] | (1 << n);
                    else if (j >= 6) blockBits[8] = blockBits[8] | (1 << n);
                    else blockBits[7] = blockBits[7] | (1 << n);
                } else {
                    if (j <= 2) blockBits[3] = blockBits[3] | (1 << n);
                    else if (j >= 6) blockBits[5] = blockBits[5] | (1 << n);
                    else blockBits[4] = blockBits[4] | (1 << n);
                }
                stage[i][j] = n;
            }
        }

        for (int i = 1; i <= 9; i++) {
            dfs(i, rowBits, colBits, blockBits, new ArrayList<Integer>());
        }


    }

    static void dfs(int now, int[] rowBits, int[] colBits, int[] blockBits, ArrayList<Integer> result) {
        if (stop) return;
        int x = zeros.get(result.size()) / 10;
        int y = zeros.get(result.size()) % 10;
        if (!validate(now, rowBits, colBits, blockBits, x, y)) return;
        ArrayList<Integer> myResult = new ArrayList<>(result);
        int[] myRowBits = new int[9];
        System.arraycopy(rowBits, 0, myRowBits, 0, 9);
        int[] myColBits = new int[9];
        System.arraycopy(colBits, 0, myColBits, 0, 9);
        int[] myBlockBits = new int[9];
        System.arraycopy(blockBits, 0, myBlockBits, 0, 9);

        myResult.add(now);
        myRowBits[x] = myRowBits[x] | (1 << now);
        myColBits[y] = myColBits[y] | (1 << now);
        if (x<= 2) {
            if (y <= 2) myBlockBits[0] = myBlockBits[0] | (1 << now);
            else if (y >= 6) myBlockBits[2] = myBlockBits[2] | (1 << now);
            else myBlockBits[1] = myBlockBits[1] | (1 << now);
        } else if (x >= 6) {
            if (y <= 2) myBlockBits[6] = myBlockBits[6] | (1 << now);
            else if (y >= 6) myBlockBits[8] = myBlockBits[8] | (1 << now);
            else myBlockBits[7] = myBlockBits[7] | (1 << now);
        } else {
            if (y <= 2) myBlockBits[3] = myBlockBits[3] | (1 << now);
            else if (y >= 6) myBlockBits[5] = myBlockBits[5] | (1 << now);
            else myBlockBits[4] = myBlockBits[4] | (1 << now);
        }

        if (myResult.size() >= zeros.size()) {
            show(myResult);
            stop = true;
            return;
        }

        for (int i = 1; i <= 9; i++) {
            dfs(i, myRowBits, myColBits, myBlockBits, myResult);
        }

    }

    static boolean validate(int now, int[] rowBits, int[] colBits, int[] blockBits, int x, int y) {
        if ((rowBits[x] & (1 << now)) != 0) return false;
        else if ((colBits[y] & (1 << now)) != 0) return false;

        if (x <= 2) {
            if (y <= 2) {
                if ((blockBits[0] & (1 << now)) != 0) return false;
            } else if (y >= 6) {
                if ((blockBits[2] & (1 << now)) != 0) return false;
            } else {
                if ((blockBits[1] & (1 << now)) != 0) return false;
            }
        }
        else if (x >= 6) {
            if (y <= 2) {
                if ((blockBits[6] & (1 << now)) != 0) return false;
            } else if (y >= 6) {
                if ((blockBits[8] & (1 << now)) != 0) return false;
            } else {
                if ((blockBits[7] & (1 << now)) != 0) return false;
            }
        }
        else {
            if (y <= 2) {
                if ((blockBits[3] & (1 << now)) != 0) return false;
            } else if (y >= 6) {
                if ((blockBits[5] & (1 << now)) != 0) return false;
            } else {
                if ((blockBits[4] & (1 << now)) != 0) return false;
            }
        }

        return true;
}

    static void show(ArrayList<Integer> result) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (stage[i][j] == 0) System.out.print(result.remove(0));
                else System.out.print(stage[i][j]);
                if (j != 8) System.out.print(" ");
            }
            if (i != 8) System.out.println("");
        }
    }
}
