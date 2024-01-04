/**
 * 접근 :
 *      1. 위상정렬인데 큐가 아니라 프라이어리티 큐 쓰면 될듯함
 */

package week8.num3문제집;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int[] indegree;
    static ArrayList<ArrayList<Integer>> adj;
    static int[] result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        adj = new ArrayList<>();
        for(int i = 0; i < N + 1; i++) adj.add(new ArrayList<>());

        indegree = new int[N + 1];
        result = new int[N + 1];

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adj.get(a).add(b);
            indegree[b]++;
        }

        topological();

        for(int i = 1; i < N; i++) System.out.print(result[i] + " ");
        System.out.print(result[N]);

    }

    static void topological() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for(int i = 1; i <= N; i++) {
            if(indegree[i] == 0) {
                pq.add(i);
            }
        }

        for(int i = 1; i <= N; i++) {
            int now = pq.poll();
            result[i] = now;
            for(int next : adj.get(now)) {
                indegree[next]--;
                if(indegree[next] == 0) pq.add(next);
            }
        }
    }
}
