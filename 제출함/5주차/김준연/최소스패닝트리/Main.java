/**
 * 크루스칼 알고리즘
 * 1. 간선의 비용에 대해 오름차순으로 정렬
 * 2. 간선 하나씩 조회하며 두 노드의 부모가 서로 다르다면 (순환구조가 아니라면) 해당 간선 비용 집계 후 두 노드 union
 * 3. 부모가 같다면 패싱
 */

package week5.최소스패닝트리;

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
        while(cnt < V - 1) {
            Line now = lines.get(index++);

            if(checkParent(now.s, now.e)) continue;

            union(now.s, now.e);
            sum += now.cost;
            cnt++;
        }

        System.out.println(sum);

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