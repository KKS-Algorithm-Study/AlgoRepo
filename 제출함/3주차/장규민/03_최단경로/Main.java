import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    private static int n;
    private static Map<Integer, Integer>[] graph;
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        int m = Integer.parseInt(stk.nextToken());

        graph = new HashMap[n + 1];
        for(int i=0 ; i<=n ; i++){
            graph[i] = new HashMap<>();
        }

        int start = Integer.parseInt(br.readLine());

        for(int i=0 ;i<m ; i++){
            stk = new StringTokenizer(br.readLine());

            int sour = Integer.parseInt(stk.nextToken()),
            des = Integer.parseInt(stk.nextToken()),
            cost = Integer.parseInt(stk.nextToken());

            if(graph[sour].containsKey(des) && graph[sour].get(des) <= cost){
                continue;
            }

            graph[sour].put(des, cost);
        }

        dijkstra(start);
        
    }

    private static void dijkstra(int start){
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

        int[] costs = new int[n + 1];
        Arrays.fill(costs, Integer.MAX_VALUE);

        costs[start] = 0;
        pq.add(new int[]{start, 0});

        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int sour = cur[0], cost = cur[1];

            if(costs[sour] < cost)continue;

            for(int des : graph[sour].keySet()){
                int nextCost = graph[sour].get(des) + cost;

                if(nextCost >= costs[des])continue;

                costs[des] = nextCost;
                pq.add(new int[]{des, nextCost});
            }
        }

        printValue(costs);
    }

    private static void printValue(final int[] costs){
        StringBuilder answer = new StringBuilder();
        for(int i=1 ; i<=n ; i++){
            answer.append(costs[i] != Integer.MAX_VALUE ? costs[i] : "INF").append("\n");
        }
        System.out.println(answer);
    }

}
