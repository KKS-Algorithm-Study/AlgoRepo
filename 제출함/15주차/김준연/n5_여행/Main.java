/**
 * 답안참고함!!
 * 
 * 접근 :
 *      1. bfs 로 진행해나감
 *      2. 어느 시점에서 몇번 도시를 방문했을 때, 얼마의 포인트를 지녔는지 기록해나감
 *      3. 기록보다 이득이면 q에넣고 아니면 안하는 식
 */

package week15.n5_여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N, M, K;
    static ArrayList<ArrayList<Next>> flights;
    static int[][] memo;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        flights = new ArrayList<>();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        for(int i = 0; i <= N; i++) flights.add(new ArrayList<>());
        memo = new int[N+1][M+1];

        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int meal = Integer.parseInt(st.nextToken());
            Next next = new Next(b, meal);

            if(a >= b) continue;

            flights.get(a).add(next);
        }

        Queue<Integer> q = new LinkedList<>();

        q.add(1);
        int cnt = 1;

        while(!q.isEmpty() && cnt < M) {

            int size = q.size();

            while(size > 0) {
                int now = q.poll();

                for(Next next : flights.get(now)) {

                    int location = next.location;
                    int sum = memo[now][cnt] + next.sum;

                    if(memo[location][cnt+1] >= sum) {
                        continue;
                    }

                    memo[location][cnt+1] = sum;

                    q.add(location);
                }
                size--;
            }

            cnt++;


        }

        int max = 0;

        for(int i = 1; i < M + 1; i++) {
            max = Math.max(max, memo[N][i]);
        }

        System.out.println(max);

    }

}

class Next {
    int location;
    int sum;

    public Next(int location, int sum) {
        this.location = location;
        this.sum = sum;
    }
}
