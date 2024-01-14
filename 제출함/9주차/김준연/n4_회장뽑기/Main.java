/**
 *  접근 :
 *      1. 인접리스트로 그래프를 기록
 *      2. 각 시작노드부터 bfs 진행하면서 가장 먼 차수 기록
 */

package week9.n4_회장뽑기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N;
    static ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
    static int[] result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        result = new int[N+1];

        for(int i = 0; i < N + 1; i++) adj.add(new ArrayList<>());

        while(true) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if(a == -1) break;
            adj.get(a).add(b);
            adj.get(b).add(a);
        }

        int min = 51;
        for(int i = 1; i < N + 1; i++) {
            bfs(i);
            min = Math.min(min, result[i]);
        }
        int cnt = 0;
        for(int i = 1; i < N + 1; i++) {
            if(result[i] == min) cnt++;
        }

        System.out.println(min + " " + cnt);
        for(int i = 1; i < N + 1; i++) {
            if(result[i] == min) System.out.print(i + " ");;
        }



    }

    static void bfs(int start) {
        Queue<Integer> q = new LinkedList<>();

        int[] degree = new int[N+1];

        degree[start] = -1;
        for(int num : adj.get(start)) {
            q.add(num);
            degree[num]++;
        }
        while(!q.isEmpty()) {
            int now = q.poll();

            for(int num : adj.get(now)) {
                if(degree[num] == 0) {
                    degree[num] = degree[now] + 1;
                    q.add(num);
                }
            }
        }
        int max = 0;
        for(int i : degree) max=Math.max(max, i);
        result[start] = max;

    }
}
