/**
 *  접근 :
 *      1. 매번 정렬, 탐색하면 시간초과 날 것임
 *      2. 하나 들어올 때 마다, 중앙값이 뭔지 알고있어야함
 *      3. 중앙값 기준으로 왼쪽의 수 = 오른쪽의 수
 *      4. 우선순위 큐로 왼쪽은 내림차순, 오른쪽은 오름차순인 두 힙의 사이즈를 비교해가면서
 *      5. 오른쪽이 더 크면 현재 중앙값을 왼쪽에 넣고 오른쪽걸 하나 빼서 중앙값으로 두는 식으로 무게를 맞추기
 */

package week14.n10_중앙값구하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());

        for(int i = 0; i < T; i++) {
            int M = Integer.parseInt(br.readLine());

            System.out.println((M / 2) + 1);

            PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder());
            PriorityQueue<Integer> right = new PriorityQueue<>();

            st = new StringTokenizer(br.readLine());
            int mid = Integer.parseInt(st.nextToken());
            System.out.print(mid + " ");
            for(int j = 1; j < M; j++) {
                if(j % 10 == 0) st = new StringTokenizer(br.readLine());
                if(j % 20 == 0) System.out.println("");
                int num = Integer.parseInt(st.nextToken());
                if(num <= mid) left.add(num);
                else right.add(num);

                if(j % 2 == 0) {
                    if(left.size() > right.size()) {
                        right.add(mid);
                        mid = left.poll();
                    }
                    else if(right.size() > left.size()) {
                        left.add(mid);
                        mid = right.poll();
                    }
                    System.out.print(mid + " ");
                }
            }

            System.out.println("");
        }
    }
}
