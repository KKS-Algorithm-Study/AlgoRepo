/**
 * 접근 :
 *      1. 시작점이 비교대상보다 크다면 끝점도 더 커야한다.
 *      2. 시작점 기준으로 선들을 정렬시킨다.
 *      3. 그러면 시작점은 무조건 앞선 비교대상보다 클테니까, 끝점만 비교하면된다.
 *      4. 이제 단순히 크기를 비교하면서 가장 많은 선을 유지하는 문제가 됐음.
 *      5. LIS로 풀고 전체 개수 - LIS 결과 하면 제거한 선 나옴.
 *
 * 시간복잡도 :
 *      정렬에 O(N log N)
 *      LIS에 O(N log N)
 *      => O(N log N)
 *
 */

package week7.전깃줄;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        ArrayList<Line> lines = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            lines.add(new Line(s, e));
        }

        Collections.sort(lines);

        int[]  dp = new int[N];
        dp[0] = lines.get(0).e;
        int idx = 0;
        for(int i = 1; i < N; i++) {
            int e = lines.get(i).e;
            if(dp[idx] < e) {
                idx++;
                dp[idx] = e;
            }
            else {
                int point = binarySearch(dp, idx, e);
                dp[point] = e;
            }
        }

        System.out.println(N - (idx + 1));

    }

    static int binarySearch(int[] dp, int idx, int n) {
        int s = 0;
        int e = idx;
        int mid = (s + e) / 2;

        while(s < e) {
            mid = (s + e) / 2;
            if(dp[mid] >= n) {
                e = mid;
            }
            else {
                s = mid + 1;
                mid = mid + 1;
            }
        }

        return mid;
    }
}

class Line implements Comparable<Line>{
    int s;
    int e;

    public Line(int s, int e) {
        this.s = s;
        this.e = e;
    }

    @Override
    public int compareTo(Line o) {
        return Integer.compare(this.s, o.s);
    }
}