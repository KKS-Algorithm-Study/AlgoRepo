/**
 * 접근 :
 *      1. 최소 스패닝 트리를 만드는데, 간선이 M과 W사이인 간선만 채택하면 될 듯?
 */

package week5.나만안되는연애;

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

        st = new StringTokenizer(br.readLine());
        int[] gender = new int[V + 1];
        for(int i = 0; i < V; i++) {
            if(st.nextToken().charAt(0) == 'M') gender[i + 1] = 0;
            else gender[i + 1] = 1;
        }

        ArrayList<Line> lines = new ArrayList<>();

        for(int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            if(gender[a] == gender[b]) continue;
            lines.add(new Line(a, b, cost));
        }

        Collections.sort(lines);

        parent = new int[V+1];
        for(int i = 0; i < V; i++) {
            parent[i+1] =  i + 1;
        }

        int sum = 0;

        for(Line now : lines) {
            if(checkParent(now.s, now.e)) continue;

            union(now.s, now.e);
            sum += now.cost;
        }

        for(int i = 1; i < V; i++) {
            if(!checkParent(i, i+1)) {
                System.out.println(-1);
                return;
            }
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
