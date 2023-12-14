import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main{

    private static int n, m;
    private static int[] parents;
    private static int[] gates;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        parents = new int[n + 1];
        gates = new int[m];

        for(int i=1 ; i<=n ; i++){
            parents[i] = i;
        }

        for(int i=0 ; i<m ; i++){
            gates[i] = Integer.parseInt(br.readLine());
        }

        int answer = 0;
        for(int i=0 ; i<m ; i++){
            int parent = findParent(gates[i]);

            if(parent == 0)
                break;

            union(parent, parent - 1);
            
            answer++;
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

        parents[px] = py;
    }
}