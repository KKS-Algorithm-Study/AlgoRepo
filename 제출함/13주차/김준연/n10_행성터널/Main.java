/**
 * !! 알고리즘 분류 참고함
 * 접근 :
 *      1. 유니온 파인드
 *      2. 모든 간선을 구하고 가기에는 약 50억회 연산이 필요함
 *      3. x기준, y기준, z기준 정렬시키고 인접한 간선만 구하면?
 *          약 30만개의 간선
 *      4. 그 간선들을 pq에 넣고 union-find 하면 끝
 *
 * 시간복잡도 :
 *      정렬 O(nlogn)
 *      유니온 파인드 O(1)
 *
 */

package week13.n10_행성터널;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N;
    static int[][] stars;
    static int[] parents;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        stars = new int[N + 1][3];
        parents = new int[N + 1];

        for(int i = 0; i < N + 1; i++) parents[i] = i;

        ArrayList<Star> stars = new ArrayList<>();

        PriorityQueue<Line> pq = new PriorityQueue<>(Comparator.comparingInt(Line::getCost));

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            Star star = new Star(i, x, y, z);
            stars.add(star);
        }

        //x에 대한 정렬 후 pq삽입 과정
        stars.sort(Comparator.comparingInt(Star::getX));

        for(int i = 1; i < N; i++) {
            Star star1 = stars.get(i - 1);
            Star star2 = stars.get(i);
            pq.add(new Line(star1, star2, Math.abs(star1.getX() - star2.getX())));
        }

        //y에 대한 정렬 후 pq삽입 과정
        stars.sort(Comparator.comparingInt(Star::getY));

        for(int i = 1; i < N; i++) {
            Star star1 = stars.get(i - 1);
            Star star2 = stars.get(i);
            pq.add(new Line(star1, star2, Math.abs(star1.getY() - star2.getY())));
        }

        //Z에 대한 정렬 후 pq삽입 과정
        stars.sort(Comparator.comparingInt(Star::getZ));

        for(int i = 1; i < N; i++) {
            Star star1 = stars.get(i - 1);
            Star star2 = stars.get(i);
            pq.add(new Line(star1, star2, Math.abs(star1.getZ() - star2.getZ())));
        }

        long result = 0;
        int cnt = 0;
        while(!pq.isEmpty()) {
            Line now = pq.poll();

            if(find(now.star1.index) == find(now.star2.index)) continue;

            union(now.star1.index, now.star2.index);
            result += now.cost;
            cnt++;

            if(cnt == N-1) break;

        }

        System.out.println(result);
    }

    static int find(int x) {
        if(parents[x] == x) return x;
        return parents[x] = find(parents[x]);
    }

    static void union(int x, int y) {
        int x_parent = find(x);
        int y_parent = find(y);
        if(x_parent <= y_parent) parents[y_parent] = x_parent;
        else parents[x_parent] = y_parent;
    }

}

class Line {
    Star star1;
    Star star2;
    int cost;

    public int getCost() {
        return cost;
    }

    public Line(Star star1, Star star2, int cost) {
        this.star1 = star1;
        this.star2 = star2;
        this.cost = cost;
    }
}

class Star {
    int index;
    int x;
    int y;
    int z;

    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Star(int index, int x, int y, int z) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.z = z;
    }

}