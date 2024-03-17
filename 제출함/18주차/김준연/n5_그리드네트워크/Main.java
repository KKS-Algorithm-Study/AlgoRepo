package week18.n5_그리드네트워크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int[] parents;

    static class Cost implements Comparable<Cost>{
        int a, b, cost;

        public Cost(int a, int b, int cost) {
            this.a = a;
            this.b = b;
            this.cost = cost;
        }

        @Override
        public int compareTo(Cost o) {
            return Integer.compare(cost, o.cost);
        }
    }


    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());

        while(T-- > 0) {
            st = new StringTokenizer(br.readLine());
            int R =  Integer.parseInt(st.nextToken());
            int C =  Integer.parseInt(st.nextToken());
            init(R*C);

            List<Cost> costs = new ArrayList<>();

            int idx = 1;
            for(int i = 0; i < R; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < C - 1; j++) {
                    costs.add(new Cost(idx, idx+1, Integer.parseInt(st.nextToken())));
                    idx++;
                }
                idx++;
            }

            idx = 1;
            for(int i = 0; i < R - 1; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < C; j++) {
                    costs.add(new Cost(idx, idx + C, Integer.parseInt(st.nextToken())));
                    idx++;
                }
            }

            Collections.sort(costs);

            int result = 0;
            int cnt = 0;
            for(Cost now : costs) {
                int a = now.a;
                int b = now.b;
                int cost = now.cost;

                if(getParent(a) != getParent(b)) {
                    union(a, b);
                    result += cost;
                    cnt++;
                }

                if(cnt >= R*C - 1) break;
            }

            System.out.println(result);

        }
    }

    static void init(int n) {
        parents = new int[n+1];
        for(int i = 0; i <= n; i++) {
            parents[i] = i;
        }
    }

    static int getParent(int n) {
        if(parents[n] == n) return n;
        return parents[n] = getParent(parents[n]);
    }

    static void union(int a, int b) {
        int a_parent = getParent(a);
        int b_parent = getParent(b);
        if(a_parent <= b_parent) parents[b_parent] = a_parent;
        else parents[a_parent] = b_parent;
    }
}
