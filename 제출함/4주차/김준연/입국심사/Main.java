/**
 * 접근 :
 * 1. 몇 초가 걸리는 지에 대해 이분탐색
 * 2. 만일 N개의 심사대를 통해 x초안에 M명이 통과할 수 없다면 우측, 통과할 수 있다면 좌측으로 진행
 * 3. 최대 1 ~ max 초에 대해 탐색해야하는데, max를 어떻게 구하는가
 * 4. 최악의 경우 1000000000초 걸리는 심사대가 1개 있고 1000000000명이 지나가려면 10^18 초 걸림
 * 5. log 10^18 = 약 60, 그냥 10^18로 가정하고 풀어봄
 *
 * check 함수 :
 * 1. 그리디 알고리즘 :
 * 	내가 가장 오래 걸리는 곳에 서면 몇초걸리지?
 * 	앞에 서는 것보다 이게 낫나? ㅇㅋ
 * 	앞이 더 낫나? 앞으로 가보기
 * 	M명치 반복
 * 	M이 10^9 고 심사대 N이 10^5니까 10^14번 연산?
 * 	이 방식이 아닌 듯함.
 *
 *
 * 2. 나누기 연산 :
 * 	현재 이분탐색 중인 now 초에 대해
 * 	now / n번째 심사대 반복한 합계가 M명을 소화할 수 있는가
 *
 * 	문제점 :
 * 	1. check함수에서 sum 값이 M을 넘었으면 즉시 true를 return 시킴.
 * 	2. 전부 다 계산하도록 놔두니까 다른 자료형간 충돌 문제인지 통과되지 못함.
 *
 * 	시간복잡도 : log2(10^18) <= 60
 *
 */

package week4.입국심사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        int N, M;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        ArrayList<Integer> times = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            times.add(Integer.parseInt(st.nextToken()));
        }

        //여기까지 데이터 input

        Solution solution = new Solution(times, M);

        solution.run();

    }
}

class Solution {
    ArrayList<Integer> times;
    int M;

    Solution(ArrayList<Integer> times, int M) {
        this.times =  times;
        this.M = M;
    }

    void run() {
        long s = 0;
        long e = (long) Math.pow(10, 18);
        long mid = 0;
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

    boolean check(long time) {
        long sum = 0;

        for(int num : times) {
            sum += time / num;
            if(sum >= M) return true;
        }

        return false;

    }

}