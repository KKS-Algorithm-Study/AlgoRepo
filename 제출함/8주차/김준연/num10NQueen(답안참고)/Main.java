/**
 * !!답안참고함
 * 접근 :
 *      1. 1차원 배열에 인덱스는 행 값은 퀸을 놓은 열을 좌표로 기록한다.
 *      2. n번째 행까지 진행되었을 때, 0~n-1 번째와 현재 놓으려는 열이 중복되는지 비교한다.
 *      3. 그리고 나의행 - 비교대상의 행 == 나의 열 - 비교대상의 열 이라면 대각선에 위치함을 의미한다.
 *          => arr[0] = 0이고 arr[2] = 1이면
 *          2 - 0 == 0 - 1 => false
 *          arr[0] = 0이고 arr[2] = 2 이면
 *          2 - 0 == 2 - 0 => true
 * 문제점 :
 *      2차원 배열로 인덱스 하나씩 옮겨가며 가로쭈욱 긋고 세로쭈욱 긋고 대각선 긋고
 *      그러고 앉아있으니 시간초과남
 */

package week8.num10NQueen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;

    static int[] arr;
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        arr = new int[N];

        solution(0);

        System.out.println(result);

    }

    static void solution(int col) {
        if(col == N) {
            result++;
            return;
        }

        for(int i = 0; i < N; i++) {
            //col번째 행의 i번째 열에 놓았다.
            arr[col] = i;
            if(getPosiibility(col)) {
                solution(col + 1);
            }
        }
    }

    static boolean getPosiibility(int col) {
        for(int i = 0; i < col; i++) {
            //자신보다 앞쪽 행이 자신과 같은 열에 두었다면? 안됨
            if(arr[col] == arr[i]) return false;


            else if(Math.abs(col - i) == Math.abs(arr[col] - arr[i])) return false;
        }

        return true;

    }

}
