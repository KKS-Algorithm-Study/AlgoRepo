import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main{

    private static int n, m;
    private static int[] parents;
    private static Map<Integer, Boolean> generators;
    private static PriorityQueue<int[]> pq;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());
        stk.nextToken();

        parents = new int[n + 1];
        for(int i=1 ; i<=n ; i++){
            parents[i] = i;
        }

        stk = new StringTokenizer(br.readLine());
        generators = new HashMap<>();
        while(stk.hasMoreTokens()){
            generators.put(Integer.parseInt(stk.nextToken()), true);
        }
        
        pq = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
        while(m-- > 0){
            stk = new StringTokenizer(br.readLine());
            int sour, des, cost;

            sour = Integer.parseInt(stk.nextToken());
            des = Integer.parseInt(stk.nextToken());
            cost = Integer.parseInt(stk.nextToken());

            pq.add(new int[]{sour, des, cost});
        }

        System.out.println(mst());
    }

    private static int mst(){
        int ret = 0;

        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int sour = cur[0], des = cur[1], cost = cur[2];

            int sourParent = findParent(sour), desParent = findParent(des);
            if((generators.containsKey(sourParent) && generators.containsKey(desParent))
             || sourParent == desParent)
                continue;
            
            union(sour, des);
            ret += cost;
        }

        return ret;
    }

    private static int findParent(int x){
        if(x == parents[x] || generators.containsKey(x)){
            return x;
        }

        return parents[x] = findParent(parents[x]);
    }

    private static void union(int x, int y){
        int px = findParent(x), py = findParent(y);

        if(px == py){
            return;
        }

        if(generators.containsKey(py)){
            int temp = py;
            py = px;
            px = temp;
        }

        parents[py] = px;
    }
}