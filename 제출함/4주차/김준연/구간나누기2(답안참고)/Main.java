/**
 * 접근 :
 * 1. 구간의 점수 : 구간에 속한 수의 최대값 - 최소값
 * 2. N개의 수로 이루어진 배열을 M개의 구간으로 나눌 때, 구간의 점수의 최대값이 가장 작게 나오게 하라.
 * 3. 각 요소는 하나의 구간에 포함되어야 하며, 구간은 1개 이상의 요소를 가져야 한다.
 * 4. 이분탐색은 정렬된 큰 범위의 수에 대해 탐색 연산 하므로, 문제에서 정렬된 큰 범위의 수가 무엇인지 파악
 * 1)나올 수 있는 모든 구간의 점수 (0 ~ 10000)
 * 2)입력 값 기준으로만 나올 수 있는 모든 구간의 점수   <<
 *
 * check함수 :
 * 1. 현재 mid에 해당하는 값 전달
 * 2. 0부터 구간 탐색하며, mid값보다 커지는 순간, 해당 구간만큼을 하나의 구간으로 잡고 다음 구간 구함
 * 3. 이 방식으로 생기는 구간의 수가 M보다 크면 mid는 우측으로, 작거나 같다면 mid를 좌측으로
 *
 * 시간복잡도 : n을 배열 내 최대값, M이 배열의 길이라할 때, O(log2(n) * M)
 * 
 * !!못풀어서 답 참고함.
 */

package week4.구간나누기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        int N, M;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];
        int max = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            max = Math.max(max, arr[i]);
        }

        //여기까지 데이터 input

        Solution solution = new Solution(arr, M);
        solution.run(max);
    }
}

class Solution {
    int[] arr;
    int M;

    Solution(int[] arr, int M) {
        this.arr = arr;
        this.M = M;
    }

    void run(int max) {
        int s = 0;
        int e = max;
        int mid = 0;
        while(s < e) {
            mid = (s + e) / 2;
            if(check(mid)) {
                e = mid;
            }
            else {
                s = mid + 1;
            }
        }
        System.out.println(s);
    }

    boolean check(int mid) {
        int cnt = 1;
        int max = arr[0];
        int min = arr[0];

        for(int num : arr) {
            if(max < num) max = num;
            if(min > num) min = num;
            if(max - min > mid) {
                cnt++;
                max = num;
                min = num;
            }
        }
        return M >= cnt;
    }


}