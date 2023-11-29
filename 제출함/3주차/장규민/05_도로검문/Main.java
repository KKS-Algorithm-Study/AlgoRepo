import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    private static int n;
    private static List<int[]>[] road;

    private static class Path{
        int[] path;
        int dist;

        public Path(int[] path, int dist){
            this.path = path;
            this.dist = dist;
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        int m = Integer.parseInt(stk.nextToken());

        road = new ArrayList[n + 1];
        for(int i=0 ; i<=n ;i++){
            road[i] = new ArrayList<>();
        }

        for(int i=0 ; i<m ; i++){
            stk = new StringTokenizer(br.readLine());

            int sour = Integer.parseInt(stk.nextToken()),
            des = Integer.parseInt(stk.nextToken()),
            cost = Integer.parseInt(stk.nextToken());

            road[sour].add(new int[]{des, cost});
            road[des].add(new int[]{sour, cost});
        }

        solution();
    }

    private static Path dijkstra(int police_y, int police_x, boolean block){
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        int[] path = new int[n + 1];

        int[] costs = new int[n + 1];
        Arrays.fill(costs, Integer.MAX_VALUE);
        costs[1] = 0;

        pq.add(new int[]{1, 0});

        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int sour = cur[0], cur_cost = cur[1];

            for(int[] next : road[sour]){
                int des = next[0], cost = next[1];
                int nextCost = cost + cur_cost;

                if(block && ((des == police_x && sour == police_y) 
                || (des == police_y && sour == police_x)) 
                || nextCost > costs[des]) 
                    continue;

                if(nextCost < costs[des]){
                    pq.add(new int[]{des, nextCost});
                    costs[des] = nextCost;
                }

                path[des] = sour;
            }
        }

        return new Path(path, costs[n]);
    }

    private static List<int[]> findShortestPath(int[] path){
        List<int[]> list = new ArrayList<>();

        int sour = n;
        while(sour != 1){
            int des = path[sour];
            list.add(new int[]{sour, des});
            sour = des;
        }

        return list;
    }

    private static void solution(){
        Path path = dijkstra(-1, -1, false);

        List<int[]> list = findShortestPath(path.path);

        int min_dist = path.dist;

        int answer = 0;
        for(int[] data : list){
            int sour = data[0], des = data[1];
            Path blockPath = dijkstra(sour, des, true);
            if(blockPath.dist == Integer.MAX_VALUE){
                System.out.println(-1);
                return;
            }
            answer = Math.max(answer, blockPath.dist - min_dist);
        }
        System.out.println(answer);
    }
}
