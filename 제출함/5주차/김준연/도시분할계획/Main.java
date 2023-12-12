/**
 * 접근 :
 *      1. 크루스칼 알고리즘을 돌린다.
 *      2. 유효한 간선 중 가장 높은 유지비를 기록한다.
 *      3. 유지비가 가장 비싼 길을 삭제하여 두 마을을 분할한다.
 * 	        => 크루스칼 알고리즘의 결과 - 가장 비싼 간선
 */

package week5.도시분할계획;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int[] parent;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        ArrayList<Line> lines = new ArrayList<>();

        for(int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            lines.add(new Line(a, b, cost));
        }

        Collections.sort(lines);

        parent = new int[V+1];
        for(int i = 0; i < V+1; i++) {
            parent[i] = i;
        }

        int cnt = 0;
        int sum = 0;
        int index = 0;
        int max_cost = 0;
        while(cnt < V - 1) {
            Line now = lines.get(index++);

            if(checkParent(now.s, now.e)) continue;

            union(now.s, now.e);
            sum += now.cost;
            max_cost = Math.max(max_cost, now.cost);
            cnt++;
        }

        System.out.println(sum - max_cost);

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

    static boolean checkParent(int x, int y) {
        x = find(x);
        y = find(y);
        return x == y;
    }
}

class Line implements Comparable<Line>{
    int s;
    int e;
    int cost;

    public Line(int s, int e, int cost) {
        this.s = s;
        this.e = e;
        this.cost = cost;
    }

    @Override
    public int compareTo(Line o) {
        if(this.cost > o.cost) return 1;
        else if(this.cost < o.cost) return -1;
        return 0;
    }
}
