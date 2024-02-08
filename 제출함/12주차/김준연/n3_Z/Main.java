/**
 * 접근 :
 *      1. 행 or 열에 대해서 log2를 구함.
 *      2. 그럼 몇 단계(n) 블럭에 속했는지 알 수 있음
 *      3. n 단계의 최소값을 저장함
 *      4. 다시 행 or 열에 대해 2^n 을 빼서 n단계 속에서 또 나뉠 행 or 열을 구함
 *      5. 재귀 호출하여 다시 하위단계를 구함
 */

package week12.n3_Z;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        long r = Long.parseLong(st.nextToken());
        long c = Long.parseLong(st.nextToken());

        System.out.println(getRow(r) + getCol(c));

    }

    static long getRow(long r) {
        if(r == 0) return 0;
        if(r == 1) return 2;

        long level = (long) (Math.log(r) / Math.log(2));

        long result = (long) Math.pow(2, 1 + 2 * level);

        level = (long) Math.pow(2, level);

        return result + getRow(r - level);
    }

    static long getCol(long c) {
        if(c == 0) return 0;
        if(c == 1) return 1;

        long level = (long) (Math.log(c) / Math.log(2));

        long result = (long) Math.pow(2, 2 * level);

        level = (long) Math.pow(2, level);

        return result + getCol(c - level);
    }
}
