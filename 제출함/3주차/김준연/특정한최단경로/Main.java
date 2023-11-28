/**
 * 접근 :
 * 1. N = 정점의 개수, E = 간선의 개수, 이후 시작, 도착, 비용 (양방향 간선)
 * 2. 1번 정점에서 N번 정점을 가려는데, 마지막에 주어지는 두 정점을 반드시 경유해야함
 * 3. 두 정점을 a, b라 할 때
 * 4. 1번 정점에서 다익스트라, a에서 다익스트라, b에서 다익스트라 구한 후
 * 5. 1 - a - b - N 과 1 - b - a - N 중 더 작은 것 구하기
 * 6. 다익스트라를 세번 하는게, 플로이드-워셜보다는 나아보임
 *
 * 문제점 :
 * 1. 방문하는 두 정점 a, b가 1이거나 N번째 정점인 경우가 있음
 * -> 다익스트라 계산 후 자기자신으로 가는 비용 0으로 되도록 개선
 * 2. INF 값의 범위를 잘못잡음
 * -> 처음에 cost가 1000이하이기에 99999으로 하였으나  10^5 < 8.0*10^5 < 10^6 으로 1000000 이상으로 주어야하기에 수정함
 *
 * 시간복잡도 : O(N log N)
 */

package week3.특정한최단경로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int N, E;
        int a, b;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

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
            if(map.containsKey(end)) {
                map.get(end).add(new Point(start, cost));
            }
            else {
                ArrayList<Point> arr = new ArrayList<>();
                arr.add(new Point(start, cost));
                map.put(end, arr);
            }
        }
        st = new StringTokenizer(br.readLine());
        a = Integer.parseInt(st.nextToken()) - 1;
        b = Integer.parseInt(st.nextToken()) - 1;

        //여기까지 데이터 input

        int[] fromStart = Solution.dijkstra(map, 0, N);
        int[] fromA = Solution.dijkstra(map, a, N);
        int[] fromB = Solution.dijkstra(map, b, N);

        int planA = fromStart[a] + fromA[b] + fromB[N-1];
        int planB = fromStart[b] + fromB[a] + fromA[N-1];

        int result = Math.min(planA, planB);
        if (result >= 99999999) System.out.println(-1);
        else System.out.println(Math.min(planA, planB));
    }
}

class Solution {

    static int[] dijkstra(HashMap<Integer, ArrayList<Point>> map, int start, int size) {
        PriorityQueue<Point> pq = new PriorityQueue<>();
        int[] costs = new int[size];
        Arrays.fill(costs, 99999999);

        pq.add(new Point(start, 0));

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

        costs[start] = 0;

        return costs;
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