import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Main{
    private static int[] parents;
    private static Queue<int[]> pq;
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int[][] number = new int[501][501];

        int num = 0;
        for(int i=1 ; i<501 ; i++){
            for(int j=1 ; j<501 ; j++){
                number[i][j] = num++;
            }
        }

        StringBuilder answer = new StringBuilder();
        while(T-- > 0){
            StringTokenizer stk = new StringTokenizer(br.readLine());
            pq = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);

            int n, m;
            n = Integer.parseInt(stk.nextToken());
            m = Integer.parseInt(stk.nextToken());

            parents = new int[number[n][m] + 1];

            for(int i=0 ; i<parents.length ; i++){
                parents[i] = i;
            }

            for(int i=1 ; i<=n ; i++){
                stk = new StringTokenizer(br.readLine());

                for(int j=1; j < m ; j++){
                    int dist = Integer.parseInt(stk.nextToken());
                    pq.add(new int[]{number[i][j], number[i][j+1], dist});
                }

            }

            for(int i=1 ; i<n ; i++){
                stk = new StringTokenizer(br.readLine());
                
                for(int j=1 ; j<=m ; j++){
                    int dist = Integer.parseInt(stk.nextToken());
                    pq.add(new int[]{number[i][j], number[i+1][j], dist});
                }
            }
            answer.append(MST()).append("\n");
        }
        System.out.println(answer);
    }

    private static int MST(){
        int ret = 0;
        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int sour = cur[0], des = cur[1], dist = cur[2];

            if(findParent(sour) == findParent(des))
                continue;

            union(sour, des);
            ret += dist;
        }
        return ret;
    }

    private static int findParent(int x){
        if(x == parents[x])
            return x;
        
        return parents[x] = findParent(parents[x]);
    }

    private static void union(int x, int y){
        int px = findParent(x), py = findParent(y);

        if(px == py)
            return;

        parents[py] = px;
    }
}