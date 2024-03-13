/**
 * 접근 :
 *      1. 모든 경우의 수 찾음
 *      2. 문과 문 사이에 목적지가 있으면 두 개의 갈래로,
 *      3. 그렇지 않으면 가까운 문으로
 *      4. BFS로 완전탐색하여 거리 최소값 비교
 */

package week17.n5_벽장문의이동;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());

        int[] destination = new int[k];

        for(int i = 0; i < k; i++) {
            destination[i] = Integer.parseInt(br.readLine());
        }

        if(a > b) {
            int temp = a;
            a = b;
            b = a;
        }

        Queue<Next> q = new ArrayDeque<>();
        if(destination[0] >= a && destination[0] <= b) {
            q.add(new Next(1, destination[0], b, Math.abs(destination[0] - a)));
            q.add(new Next(1, a, destination[0], Math.abs(destination[0] - b)));
        }
        else {
            if(Math.abs(destination[0] - a) < Math.abs(destination[0] - b)) {
                q.add(new Next(1, destination[0], b, Math.abs(destination[0] - a)));
            }
            else {
                q.add(new Next(1, a, destination[0], Math.abs(destination[0] - b)));
            }
        }

        int result = Integer.MAX_VALUE;

        while(!q.isEmpty()) {
            Next now = q.poll();

            if(now.destination_idx >= k) {
                result = Math.min(result, now.cnt);
                continue;
            }

            int dest = destination[now.destination_idx];
            a = now.a;
            b = now.b;

            if(a > b) {
                int temp = a;
                a = b;
                b = a;
            }

            if(dest >= a && dest <= b) {
                q.add(new Next(now.destination_idx + 1, dest, b, now.cnt + Math.abs(dest - a)));
                q.add(new Next(now.destination_idx + 1, a, dest, now.cnt + Math.abs(dest - b)));
            }
            else {
                if(Math.abs(dest - a) < Math.abs(dest - b)) {
                    q.add(new Next(now.destination_idx + 1, dest, b, now.cnt + Math.abs(dest - a)));
                }
                else {
                    q.add(new Next(now.destination_idx + 1, a, dest, now.cnt + Math.abs(dest - b)));
                }
            }
        }

        System.out.println(result);

    }
}

class Next {
    int destination_idx;
    int a,  b;
    int cnt;

    public Next(int destination_idx, int a, int b, int cnt) {
        this.destination_idx = destination_idx;
        this.a = a;
        this.b = b;
        this.cnt = cnt;
    }
}