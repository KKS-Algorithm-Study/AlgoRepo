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

        pq = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
        while(m-- > 0){
            stk = new StringTokenizer(br.readLine());

            int u, v, cost;
            u = Integer.parseInt(stk.nextToken());
            v = Integer.parseInt(stk.nextToken());
            cost = Integer.parseInt(stk.nextToken());
            pq.add(new int[]{u, v, cost});
        }

        System.out.println(mst());
    }

    private static int mst(){
        int ret = 0, last = 0;

        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int u = cur[0], v = cur[1], cost = cur[2];

            if(findParent(u) == findParent(v))
                continue;

            ret += cost;
            last = cost;
            union(u, v);
        }

        return ret - last;
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