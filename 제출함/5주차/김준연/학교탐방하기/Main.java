/**
 * 접근 :
 *      최소 스패닝 트리를 내림차순 한번, 오름차순 한번 총 두번
 */

package week5.학교탐방하기;

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

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        ArrayList<Line> lines = new ArrayList<>();

        st = new StringTokenizer(br.readLine());
        int entrance = Integer.parseInt(st.nextToken());
        entrance = Integer.parseInt(st.nextToken());
        entrance = Integer.parseInt(st.nextToken());

        entrance = entrance == 1 ? 0 : 1;

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            lines.add(new Line(a, b, cost));
        }

        Collections.sort(lines);

        parent = new int[N+1];
        for(int i = 0; i < N; i++) {
            parent[i+1] =  i + 1;
        }

        int up_cnt = entrance;

        for(Line now : lines) {     //오름차순 lines에 대해 크루스칼
            if(checkParent(now.s, now.e)) continue;

            union(now.s, now.e);
            if(now.cost == 0) up_cnt++;
        }

        for(int i = 0; i < N; i++) {    //집합을 위한 parent 배열을 초기화
            parent[i+1] =  i + 1;
        }

        int down_cnt = entrance;
        for(int i = lines.size() - 1; i >= 0; i--) {    //lines를 내림차순으로 크루스칼
            Line now = lines.get(i);
            if(checkParent(now.s, now.e)) continue;

            union(now.s, now.e);
            if(now.cost == 0) down_cnt++;
        }

        System.out.println((int) (Math.pow(up_cnt, 2) - Math.pow(down_cnt, 2)));

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