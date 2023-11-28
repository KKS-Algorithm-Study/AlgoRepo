/**
 * 접근 :
 * 1. V = 정점의 개수, E = 간선의 개수, K = 시작 정점, 이후는 시작, 출발, 비용
 * 2. 플로이드-워셜로 접근하기에는 시작점 이외의 비용이 필요가 없음
 * 3. 다익스트라로 접근
 *
 * 문제점 :
 * 1. 앞선 문제들을 풀듯이 인접행렬로 간선의 비용을 정리하자, 메모리 초과가 발생
 * -> HashMap을 사용한 인접리스트 구현으로 해결
 *
 * 시간복잡도 : O(N log N)
 */

package week3.최단경로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int V, E, K;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());

        HashMap<Integer, ArrayList<Point>> map = new HashMap<>();

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken()) - 1;
            int end = Integer.parseInt(st.nextToken()) - 1;
            int cost = Integer.parseInt(st.nextToken());

            if(map.containsKey(start)) {
                map.get(start).add(new Point(end, cost));
            }
            else {
                ArrayList<Point> arr = new ArrayList<>();
                arr.add(new Point(end, cost));
                map.put(start, arr);
            }

        }

        //여기까지 데이터 input

        Solution.dijkstra(map, K, V);

    }
}

class Solution {

    static void dijkstra(HashMap<Integer, ArrayList<Point>> map, int start, int size) {
        PriorityQueue<Point> pq = new PriorityQueue<>();
        int[] costs = new int[size];
        Arrays.fill(costs, 999999);

        pq.add(new Point(start-1, 0));

        while(!pq.isEmpty()) {
            Point point = pq.poll();

            if(map.containsKey(point.now)) {
                ArrayList<Point> arr = map.get(point.now);
                for(Point next : arr) {
                    if(costs[next.now] > point.cost + next.cost) {
                        costs[next.now] = point.cost + next.cost;
                        pq.add(new Point(next.now, point.cost + next.cost));
                    }
                }
            }
        }

        costs[start-1] = 0;

        for(int num : costs) {
            if(num == 999999) System.out.println("INF");
            else System.out.println(num);
        }
    }

}

class Point implements Comparable<Point>{
    int now;
    int cost;

    Point(int now, int cost) {
        this.now = now;
        this.cost = cost;
    }

    @Override
    public int compareTo(Point o) {
        if(this.cost > o.cost) return 1;
        else if (this.cost < o.cost) return -1;
        return 0;
    }
}