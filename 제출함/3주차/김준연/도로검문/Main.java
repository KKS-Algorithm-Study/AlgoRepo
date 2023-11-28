/**
 * 접근 :
 * 1. 일단 1번에서 N번으로 가는 가장 빠른 길을 구한다. 이 때 어느 도시를 방문하는지 기록할 필요가 있음
 * 2. 해당 루트의 모든 도로를 하나 씩 검문하여 막아본다.
 * 3. 막았으면 그 길을 제외한 가장 빠른 길을 구하고 해당 시간 - 기존 빠른 시간을 기록한다.
 * 4. 지연 시간의 기록들 중 가장 큰 것을 출력한다. 만일 탈출이 불가능한 경우가 있다면 -1을 출력한다.
 *
 * 문제점 :
 * 1. 방문한 도로를 인접리스트에서 제외하고 다익스트라를 하나씩 굴리는 과정에서 테스트케이스를 통과하지 못함
 * -> 인접리스트를 깊은 복사하고 특정 도로를 제외하는 방식말고 방문한 도시에 대해 연산하지 않도록 수정하자 해결됨
 *
 * 시간복잡도 : O(N log N)
 */

package week3.도로검문;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        int N, M;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        HashMap<Integer, ArrayList<Point>> map = new HashMap<>();

        for (int i = 0; i < M; i++) {
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

        //여기까지 데이터 input

        Point firstRoute = Solution.firstDijkstra(map, N);

        ArrayList<Integer> delayedTimes = new ArrayList<>();
        for(int i = 1; i < firstRoute.history.size(); i++) {

            int a = firstRoute.history.get(i);

            int time = Solution.dijkstra(map, 0, N, a)[N-1];
            if(time == 99999999) {
                System.out.println(-1);
                return;
            }
            int delayedTime = time - firstRoute.cost;
            delayedTimes.add(delayedTime);
        }

        int result = Collections.max(delayedTimes);
        System.out.println(result);

    }
}

class Solution {

    static Point firstDijkstra(HashMap<Integer, ArrayList<Point>> map, int size) {
        PriorityQueue<Point> pq = new PriorityQueue<>();
        int[] costs = new int[size];
        Point[] points = new Point[size];
        Arrays.fill(costs, 99999999);

        ArrayList<Integer> history = new ArrayList<>();
        history.add(0);
        pq.add(new Point(0, 0, history));

        while(!pq.isEmpty()) {
            Point point = pq.poll();

            if(map.containsKey(point.now)) {
                ArrayList<Point> arr = map.get(point.now);
                for(Point next : arr) {
                    if(costs[next.now] > point.cost + next.cost) {
                        ArrayList<Integer> nextHistory = (ArrayList<Integer>) point.history.clone();
                        nextHistory.add(next.now);
                        costs[next.now] = point.cost + next.cost;
                        Point newPoint = new Point(next.now, point.cost + next.cost, nextHistory);
                        points[next.now] = newPoint;
                        pq.add(newPoint);
                    }
                }
            }
        }

        return points[size-1];
    }

    static int[] dijkstra(HashMap<Integer, ArrayList<Point>> map, int start, int size, int removed) {
        PriorityQueue<Point> pq = new PriorityQueue<>();
        int[] costs = new int[size];
        Arrays.fill(costs, 99999999);

        pq.add(new Point(start, 0));

        while(!pq.isEmpty()) {
            Point point = pq.poll();
            if(point.now == removed) continue;
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
    ArrayList<Integer> history;

    Point(int now, int cost) {
        this.now = now;
        this.cost = cost;
    }

    Point(int now, int cost, ArrayList<Integer> history) {
        this.now = now;
        this.cost = cost;
        this.history = history;
    }

    @Override
    public int compareTo(Point o) {
        if(this.cost > o.cost) return 1;
        else if (this.cost < o.cost) return -1;
        return 0;
    }
}