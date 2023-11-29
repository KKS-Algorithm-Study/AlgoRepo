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
    private static List<int[]>[] board;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());
     
        n = Integer.parseInt(stk.nextToken());
        int m = Integer.parseInt(stk.nextToken());

        board = new ArrayList[n + 1];
        for(int i=0 ; i<=n ; i++){
            board[i] = new ArrayList<>();
        }

        while(m-- > 0){
            stk = new StringTokenizer(br.readLine());
            int sour = Integer.parseInt(stk.nextToken()),
            des = Integer.parseInt(stk.nextToken()),
            cost = Integer.parseInt(stk.nextToken());

            board[sour].add(new int[]{des, cost});
            board[des].add(new int[]{sour, cost});
        }

        stk = new StringTokenizer(br.readLine());
        int u = Integer.parseInt(stk.nextToken()), v = Integer.parseInt(stk.nextToken());

        long min_value1 = getMinValue(u, v), min_value2 = getMinValue(v, u);
        long answer = Math.min(min_value1, min_value2);

        System.out.println(answer >= Integer.MAX_VALUE ? -1 : answer);
    }

    private static int dijkstra(int start, int end){
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

        int[] costs = new int[n + 1];
        Arrays.fill(costs, Integer.MAX_VALUE);
        costs[start] = 0;

        pq.add(new int[]{start, 0});

        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int sour = cur[0], cur_cost = cur[1];

            if(costs[sour] < cur_cost)continue;

            for(int[] next : board[sour]){
                int des = next[0], cost = next[1];
                int nextCost = cost + cur_cost;

                if(nextCost >= costs[des])continue;

                costs[des] = nextCost;

                if(end == des)continue;

                pq.add(new int[]{des, nextCost});
            }
        }

        return costs[end];
    }

    private static long getMinValue(int u, int v){
        long ret = 0;

        ret += dijkstra(1, u);
        ret += dijkstra(u, v);
        ret += dijkstra(v, n);

        return ret;
    }
}
