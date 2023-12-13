/**
 * 접근 :
 *      1. 최소 스패닝 트리를 찾는다.
 *      2. 이때 부모가 같은지(순환인지)를 판별함과 동시에 발전소와 연결되었는지를 판별한다.
 *      3. 끝
 *
 */

package week5.전기가부족해;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int[] parent;
    static ArrayList<Integer> hs;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        parent = new int[N+1];
        for(int i = 0; i < N + 1; i++) {
            parent[i] = i;
        }

        hs = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < K; i++) {
            hs.add(Integer.parseInt(st.nextToken()));
        }

        ArrayList<Line> lines = new ArrayList<>();

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            lines.add(new Line(a, b, cost));
        }

        Collections.sort(lines);

        int sum = 0;
        for(Line now : lines) {
            if((!checkPower(now.a) || !checkPower(now.b)) && !checkParent(now.a, now.b)) {
                union(now.a, now.b);
                sum += now.cost;
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

    static boolean checkPower(int x) {
        x = find(x);
        for(int now : hs) {
            if(find(now) == x) return true;
        }
        return false;
    }

    static boolean checkParent(int x, int y) {
        x = find(x);
        y = find(y);
        return x == y;
    }
}

class Line implements Comparable<Line>{
    int a;
    int b;
    int cost;

    public Line(int a, int b, int cost) {
        this.a = a;
        this.b = b;
        this.cost = cost;
    }

    @Override
    public int compareTo(Line o) {
        return Integer.compare(this.cost, o.cost);
    }
}