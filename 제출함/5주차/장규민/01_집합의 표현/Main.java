import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main{

    private static int n, m;
    private static int[] parents;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        parents = new int[n + 1];

        for(int i=0 ; i<=n ; i++){
            parents[i] = i;
        }

        StringBuilder answer = new StringBuilder();
        for(int i=0 ; i<m ; i++){
            stk = new StringTokenizer(br.readLine());
            int opt, a, b;

            opt = Integer.parseInt(stk.nextToken());
            a = Integer.parseInt(stk.nextToken());
            b = Integer.parseInt(stk.nextToken());

            if(opt == 1){
                int pa = findParent(a), pb = findParent(b);
                answer.append(pa == pb ? "YES\n" : "NO\n");
                continue;
            }
            union(a, b);
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