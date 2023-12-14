import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main{

    private static int n, m;
    private static int[] parents;
    private static PriorityQueue<int[]> pq;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        parents = new int[n + 1];
        for(int i=0 ; i<=n ; i++){
            parents[i] = i;
        }

        pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        for(int i=1 ; i<=n ; i++){
            stk = new StringTokenizer(br.readLine());
            int x1, x2, y;

            x1 = Integer.parseInt(stk.nextToken());
            x2 = Integer.parseInt(stk.nextToken());
            y = Integer.parseInt(stk.nextToken());

            pq.add(new int[] {x1, x2, y, i});
        }

        int[] prev = pq.poll();
        int prev_x1 = prev[0], prev_x2 = prev[1], prev_y = prev[2], prev_idx = prev[3];

        while(!pq.isEmpty()){
            int[] next = pq.poll();
            int next_x1 = next[0], next_x2 = next[1], next_y = next[2], next_idx = next[3];

            if(prev_y != next_y && prev_x1 <= next_x1 && next_x1 <= prev_x2)
                union(prev_idx, next_idx);

            prev_x1 = next_x1;
            prev_x2 = Math.max(next_x2, prev_x2);
            prev_y = next_y;
            prev_idx = next_idx;
        }

        StringBuilder answer = new StringBuilder();
        while(m-- > 0){
            stk = new StringTokenizer(br.readLine());
            int start, end;

            start = Integer.parseInt(stk.nextToken());
            end = Integer.parseInt(stk.nextToken());

            answer.append(findParent(start) == findParent(end) ? "1\n" : "0\n");
        }

        System.out.println(answer);

    }

    private static int findParent(int x){
        if(x == parents[x]){
            return x;
        }

        return parents[x] = findParent(parents[x]);
    }

    private static void union(int x, int y){
        int px = findParent(x), py = findParent(y);

        if(px == py){
            return;
        }

        parents[py] = px;
    }
}