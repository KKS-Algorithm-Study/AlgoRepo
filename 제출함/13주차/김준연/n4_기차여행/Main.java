/**
 *  접근 :
 *      1. 일단 해당 철도를 몇 번 이용했는지만 기록
 *      2. 모든 여행이 끝나면 모든 철도에 대해 뭐가 이득인지 판단하고 전부 더하기
 *
 *      => 1번도시에서 10만번도시를 왕복하는 짓을 10만번하면 어떡하지?
 *      그 철도들을 몇 번 들렸는지 기록하는 과정에서 10만 * 10만 = 100억
 *
 *      3. s에서 시작해서, e에서 끝났다는 기록해두면?
 *      4. 10만개의 s와 e에 대해, 딱 한번만 10만개의 철도를 순회하면서 플러스 시키면 됨
 *
 *  시간복잡도 :
 *      N회 연산 4회
 *      O(N)
 *
 *  문제점 :
 *      result는 long이 나올거라 생각했는데,
 *      중간 계산 과정도 long이어야했음
 */

package week13.n4_기차여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] nextCity = new int[M];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i++) {
            nextCity[i] = Integer.parseInt(st.nextToken());
        }

        long[] tickets = new long[N];
        long[] cards = new long[N];
        long[] chips = new long[N];

        for(int i = 1; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            tickets[i] = Long.parseLong(st.nextToken());
            cards[i] = Long.parseLong(st.nextToken());
            chips[i] = Long.parseLong(st.nextToken());
        }

        long[] start = new long[N + 1];
        long[] end = new long[N + 1];

        for(int i = 0; i < M - 1; i++) {
            int min = Math.min(nextCity[i], nextCity[i + 1]);
            int max = Math.max(nextCity[i], nextCity[i + 1]);
            start[min]++;
            end[max]++;
        }

        long cnt = 0;
        long result = 0;
        for(int i = 1; i < N; i++) {
            cnt += start[i];
            cnt -= end[i];
            if(tickets[i] * cnt > (cards[i] * cnt) + chips[i]) {
                result += (cards[i] * cnt) + chips[i];
            }
            else {
                result += tickets[i] * cnt;
            }
        }

        System.out.println(result);


    }
}
