import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main{

    private static int n, m;
    private static int[] parents;
    private static char[] gender;
    private static PriorityQueue<int[]> pq;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        parents = new int[n + 1];
        gender = new char[n + 1];
        stk = new StringTokenizer(br.readLine());

        for(int i=1 ; i<=n ; i++){
            parents[i] = i;
            gender[i] = stk.nextToken().charAt(0);
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

        int answer = mst();
        System.out.println(allSchoolConnection() ? answer : "-1");
    }

    private static int mst(){
        int ret = 0;

        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int u = cur[0], v = cur[1], cost = cur[2];

            if(gender[u] == gender[v] || findParent(u) == findParent(v))
                continue;

            union(u, v);
            ret += cost;
        }

        return ret;
    }

    private static boolean allSchoolConnection(){
        int parent = findParent(1);
        for(int i=2 ; i<=n ; i++){
            if(parent != findParent(i))
                return false;
        }
        return true;
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