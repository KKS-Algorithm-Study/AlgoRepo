/**
 * 접근 :
 *      1. 시작점부터 각 대포 + 도착점으로 다익스트라
 *      2. 두 점의 거리를 구하고, 걷는게 이득인지 대포타는게 이득인지
 *          => 대포는 구한 거리에서 -50 하고 2초 추가 로직
 *      3. 위 방식으로 구현하고 도착점의 다익스트라 결과 출력
 */

package week17.n9_인간대포;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static final double INF = Double.MAX_VALUE;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        double x = Double.parseDouble(st.nextToken());
        double y = Double.parseDouble(st.nextToken());
        Node start = new Node(0, x, y, 0);

        st = new StringTokenizer(br.readLine());
        x = Double.parseDouble(st.nextToken());
        y = Double.parseDouble(st.nextToken());
        Node end = new Node(1, x, y);

        int n = Integer.parseInt(br.readLine());


        int idx = 2;

        HashSet<Node> canons = new HashSet<>();
        canons.add(end);

        while(n-- > 0) {
            st = new StringTokenizer(br.readLine());
            x = Double.parseDouble(st.nextToken());
            y = Double.parseDouble(st.nextToken());
            Node node = new Node(idx++, x, y);
            canons.add(node);
        }

        double[] distances = new double[idx];
        Arrays.fill(distances, INF);

        PriorityQueue<Node> pq = new PriorityQueue<>();

        distances[0] = 0;

        for(Node canon : canons) {
            double x_diff = Math.abs(start.x - canon.x);
            double y_diff = Math.abs(start.y - canon.y);
            double distance = Math.sqrt(x_diff*x_diff + y_diff*y_diff);

            double walk = distance / 5;

            pq.add(new Node(canon.idx, canon.x, canon.y, walk));
            distances[canon.idx] = walk;
        }

        while(!pq.isEmpty()) {
            Node now = pq.poll();
            if(now.time > distances[now.idx]) continue;
            if(now.idx == 1) continue;

            for(Node canon : canons) {
                double x_diff = Math.abs(now.x - canon.x);
                double y_diff = Math.abs(now.y - canon.y);

                double distance = Math.sqrt(x_diff*x_diff + y_diff*y_diff);
                if(distance == 0) continue;

                double walk = distance / 5;
                double do_canon = 2 + Math.abs(distance - 50) / 5;

                double time = Math.min(walk, do_canon);

                if(now.time + time < distances[canon.idx]) {
                    pq.add(new Node(canon.idx, canon.x, canon.y, now.time + time));
                    distances[canon.idx] = now.time + time;
                }

            }
        }

        System.out.println(distances[end.idx]);

    }
}

class Node implements Comparable<Node> {
    int idx;
    double x, y;
    double time;

    public Node(int idx, double x, double y) {
        this.idx = idx;
        this.x = x;
        this.y = y;
    }

    public Node(int idx, double x, double y, double time) {
        this.idx = idx;
        this.x = x;
        this.y = y;
        this.time = time;
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(time, o.time);
    }
}