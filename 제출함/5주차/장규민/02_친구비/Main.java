import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

class Main{

    private static int n, m, k;
    private static int[] parents;
    private static int[] money;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());
        k = Integer.parseInt(stk.nextToken());

        parents = new int[n + 1];
        for(int i=0 ; i<=n ; i++){
            parents[i] = i;
        }

        money = new int[n + 1];
        stk = new StringTokenizer(br.readLine());

        for(int i=1 ; i<=n ; i++){
            money[i] = Integer.parseInt(stk.nextToken());
        }

        for(int i=0 ; i<m ; i++){
            stk = new StringTokenizer(br.readLine());
            int v, w;

            v = Integer.parseInt(stk.nextToken());
            w = Integer.parseInt(stk.nextToken());

            union(v, w);
        }

        Map<Integer, Boolean> visit = new HashMap<>();
        int answer = 0;
        for(int x=1 ; x<=n ; x++){
            int px = findParent(x);

            if(visit.containsKey(px)){
                continue;
            }

            visit.put(px, true);
            answer += money[px];
        }

        System.out.println(answer <= k ? answer : "Oh no");
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

        if(money[py] < money[px]){
            int temp = py;
            py = px;
            px = temp;
        }

        parents[py] = px;
    }
}