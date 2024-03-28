/**
 * 접근 :
 *      1. 우선순위 큐에 사진틀의 개수만큼만 사진을 유지시킨다.
 *      2. 이미 들어있는 사진이라면 제거하고 추천수를 1늘리고 다시 삽입시킨다.
 *      3. 들어있지 안읂 사진이라면, 여유가 있으면 그냥 넣고 없으면 poll시켜 버리고 넣는다.
 */

package week19.n1_후보추천하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int frame = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int time = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());

        HashMap<Integer, Picture> hm = new HashMap<>();
        for (int i = 1; i < 101; i++) hm.put(i, new Picture(i, 0, 0));
        PriorityQueue<Picture> pq = new PriorityQueue<>();

        while (time-- > 0) {
            int n = Integer.parseInt(st.nextToken());

            Picture now = hm.get(n);

            if (pq.contains(now)) {
                pq.remove(now);
                now.recommend++;
                pq.add(now);
                continue;
            }

            if (pq.size() >= frame) pq.poll();

            now.recommend = 1;
            now.time = time;
            pq.add(now);
        }

        pq.stream()
                .sorted(Comparator.comparingInt(picture -> picture.number))
                .forEach(picture -> System.out.print(picture.number + " "));
    }
}

class Picture implements Comparable<Picture> {
    int number;
    int recommend;
    int time;

    public Picture(int number, int recommend, int time) {
        this.number = number;
        this.recommend = recommend;
        this.time = time;
    }

    @Override
    public int compareTo(Picture o) {
        if (this.recommend < o.recommend) return -1;
        if (this.recommend > o.recommend) return 1;
        if (this.time > o.time) return -1;
        if (this.time < o.time) return 1;
        return 0;
    }
}