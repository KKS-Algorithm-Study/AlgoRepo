/**
 * 접근 :
 *      1. 집합을 사용하여 친구 단위를 묶음
 *      2. 몇번째 친구를 부모로 삼는 그룹에 속했는지, 해당 그룹의 현재 최소비용을 갱신시키며 반복문 구현
 *      2. 모든 집합에 대해 비용의 합을 출력. 이 때 가진 돈을 초과한다면 Oh no 출력
 */

package week5.친구비;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[] parent;
    static int[] cost;
    public static void main(String[] args) throws IOException {
        int N, M, K;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        parent = new int[N + 1];
        for(int i = 1; i < N + 1; i++) {
            parent[i] = i;
        }

        st = new StringTokenizer(br.readLine());

        cost = new int[N + 1];
        for(int i = 1; i < N + 1; i++) {
            cost[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            union(a, b);

        }

        int[] sum = new int[N + 1];
        for(int i = 1; i < N + 1; i++) {
            int now = find(i);
            if(sum[now] == 0) sum[now] = cost[i];
            else if(sum[now] > cost[i]) sum[now] =  cost[i];
        }
        int result = 0;
        for(int i = 1; i < N + 1; i++) {
            result += sum[i];
        }
        if(result > K) System.out.println("Oh no");
        else System.out.println(result);

        br.close();
    }

    static int find(int x) {
        if(parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    static void union(int x, int y) {
        x = find(x);
        y = find(y);

        if(x != y) {
            if(x < y) parent[y] = x;
            else parent[x] = y;
        }
    }
}
