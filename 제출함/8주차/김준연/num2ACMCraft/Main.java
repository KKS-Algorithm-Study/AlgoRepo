/**
 * 접근 :
 *      1. 위상정렬 사용
 *      2. 자신으로 향하는 노드들 중 가장 비싼 시간이 나의 시간에 더해져야함.
 *      3. 선행 노드가 존재하지 않는 노드를 하나 씩 처리할 때마다 후행 노드에 선행 노드가 여태까지 기록한 시간을 비교시키기
 *
 * 시간복잡도 : 노드1번 간선1번씩 O(V + E)
 *              근데 이해안됨
 *
 */

package week8.num2ACMCraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N, K, W;
    static int[] costs;
    static int[] indegree;
    static ArrayList<ArrayList<Integer>> building;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());

        for(int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            costs = new int[N + 1];
            indegree = new int[N + 1];
            building = new ArrayList<>();
            for(int j = 0; j < N + 1; j++) {
                building.add(new ArrayList<Integer>());
            }
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                costs[j + 1] = Integer.parseInt(st.nextToken());
            }
            for(int j = 0; j < K; j++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                building.get(x).add(y);
                indegree[y]++;
            }
            st = new StringTokenizer(br.readLine());
            W = Integer.parseInt(st.nextToken());
            System.out.println(topological());
        }
    }

    static int topological() {
        int[] cost = new int[N + 1];

        Queue<Integer> q = new LinkedList<>();

        for(int i = 1; i <= N; i++) {
            if(indegree[i] == 0) {
                q.add(i);
                cost[i] = costs[i];
            }
        }

        for(int i = 1; i <= N; i++) {
            int now = q.poll();
            for(int next : building.get(now)) {
                indegree[next]--;
                if(cost[next] < cost[now]) cost[next] = cost[now];
                if(indegree[next] == 0) {
                    q.add(next);
                    cost[next] += costs[next];
                }
            }
        }

        return cost[W];
    }
}
