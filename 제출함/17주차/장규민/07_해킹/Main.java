import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Main{
    private static int n;
    private static List<int[]>[] computers;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();

        while(T-- > 0){
            StringTokenizer stk = new StringTokenizer(br.readLine());
            int d, c;

            n = Integer.parseInt(stk.nextToken());
            d = Integer.parseInt(stk.nextToken());
            c = Integer.parseInt(stk.nextToken());

            computers = new ArrayList[n + 1];
            for(int i=0 ; i<=n ; i++){
                computers[i] = new ArrayList<>();
            }

            for(int i=0 ; i<d ; i++){
                stk = new StringTokenizer(br.readLine());
                int sour = Integer.parseInt(stk.nextToken());
                int des = Integer.parseInt(stk.nextToken());
                int time = Integer.parseInt(stk.nextToken());

                computers[des].add(new int[]{sour, time});
            }

            int[] ret = dijkstra(c);
            answer.append(ret[0]).append(" ").append(ret[1]).append("\n");
        }
        
        System.out.print(answer);
    }

    private static int[] dijkstra(int c){
        final int INF = Integer.MAX_VALUE;

        PriorityQueue<int[]> que = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        que.add(new int[]{c, 0});

        int[] costs = new int[n + 1];
        Arrays.fill(costs, INF);
        costs[c] = 0;

        while(!que.isEmpty()){
            int[] cur = que.poll();
            
            int sour = cur[0], time = cur[1];

            if(costs[sour] < time)continue;

            for(int[] next : computers[sour]){
                int des = next[0], plusTime = next[1];
                int nextTime = plusTime + time;

                if(nextTime >= costs[des])continue;
                costs[des] = nextTime;
                que.add(new int[]{des, nextTime});
            }
        }

        int count = 0, totalTime = 0;
        for(int i=1 ; i<=n ; i++){
            if(INF == costs[i])continue;
            count++;
            totalTime = Math.max(totalTime, costs[i]);
        }

        return new int[]{count, totalTime};

    }
}