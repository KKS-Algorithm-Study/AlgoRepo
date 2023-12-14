import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main{

    private static int n, m;
    private static int[] bestParents, worstParents;
    private static PriorityQueue<int[]> best, worst;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        bestParents = new int[n + 1];
        worstParents = new int[n + 1];

        for(int i=1 ; i<=n ; i++){
            bestParents[i] = i;
            worstParents[i] = i;
        }

        worst = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
        best = new PriorityQueue<>((o1, o2) -> o2[2] - o1[2]);
        
        stk = new StringTokenizer(br.readLine());
        stk.nextToken();
        stk.nextToken();
        int start = Integer.parseInt(stk.nextToken());

        while(m-- > 0){
            stk = new StringTokenizer(br.readLine());
            int u, v, uphill;

            u = Integer.parseInt(stk.nextToken());
            v = Integer.parseInt(stk.nextToken());
            uphill = Integer.parseInt(stk.nextToken());

            best.add(new int[] {u, v, uphill});
            worst.add(new int[] {u, v, uphill});
        }

        System.out.println(mst(worst, worstParents, start) - mst(best, bestParents, start));
    }

    private static int mst(PriorityQueue<int[]> pq, int[] parents, int start){
        int ret = 1 - start;

        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int u = cur[0], v = cur[1], uphill = cur[2];

            if(findParent(u, parents) == findParent(v, parents))
                continue;

            union(u, v, parents);
            ret += (1 - uphill);
        }

        return ret * ret;
    }

    private static int findParent(int x, int[] parents){
        if(x == parents[x]){
            return x;
        }

        return parents[x] = findParent(parents[x], parents);
    }

    private static void union(int x, int y, int[] parents){
        int px = findParent(x ,parents), py = findParent(y, parents);

        if(px == py){
            return;
        }

        parents[py] = px;
    }
}