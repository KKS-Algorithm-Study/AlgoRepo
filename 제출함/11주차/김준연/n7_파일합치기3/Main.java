/**
 * 접근 :
 *      1. 이득볼라면 큰 녀석이 괜히 초장부터 자리잡으면 안됨
 *      2. 항상 작은 녀석들 부터 합치면서 가기
 *      3. 우선순위큐에 담아서 합치고 넣고 합치고 넣고
 *
 * 문제점 :
 *      1. int 자료형의 크기를 벗어났음
 *      2. 엄청 커보여서 BigInteger 썼다가 시간초과
 */

package week11.n7_파일합치기3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());

        for(int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());
            PriorityQueue<Long> pq = new PriorityQueue<>();
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < K; j++) {
                pq.add(Long.parseLong(st.nextToken()));
            }
            getSum(pq);
        }
    }

    static void getSum(PriorityQueue<Long> pq) {
        long result = 0;
        while(pq.size() > 1) {
            long a = pq.poll();
            long b = pq.poll();
            long sum = a + b;
            result += sum;
            pq.add(sum);
        }
        System.out.println(result);
    }
}
