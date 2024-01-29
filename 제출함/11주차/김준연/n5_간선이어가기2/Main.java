/**
 * 접근 :
 *      1. s에서 시작하는 다익스트라
 *      2. t에 해당하는 비용 출력
 */

package week11.n5_간선이어가기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int MAX = 100000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        HashMap<Integer, ArrayList<Line>> hm = new HashMap<Integer, ArrayList<Line>>();

        for(int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            Line line = new Line(s, e, cost);

            if(!hm.containsKey(s)) {
                hm.put(s, new ArrayList<>());
            }
            hm.get(s).add(line);

            line = new Line(e, s, cost);

            if(!hm.containsKey(e)) {
                hm.put(e, new ArrayList<>());
            }
            hm.get(e).add(line);
        }

        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        int[] result = new int[n + 1];
        Arrays.fill(result, MAX);

        Queue<Line> queue = new LinkedList<>();
        for(Line line : hm.get(s)) {
            queue.add(line);
            result[line.to] = line.cost;
        }

        while(!queue.isEmpty()) {
            Line now = queue.poll();
            for(Line next : hm.get(now.to)) {
                if(next.cost + result[next.from] < result[next.to]) {
                    result[next.to] = next.cost + result[next.from];
                    queue.add(next);
                }
            }
        }

        System.out.println(result[t]);

    }
}

class Line {
    int from;
    int to;
    int cost;

    public Line(int from, int to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}