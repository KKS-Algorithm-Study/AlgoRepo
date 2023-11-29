import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    private static int n, m;
    private static int[][] cities;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        cities = new int[n + 1][n + 1];

        for(int i=0 ; i<=n ; i++){
            for(int j=0 ; j<=n ; j++){
                cities[i][j] = i == j ? 0 : Integer.MAX_VALUE;
            }
        }

        for(int i=0 ; i<m ; i++){
            StringTokenizer stk = new StringTokenizer(br.readLine());
            int sour = Integer.parseInt(stk.nextToken()), 
            des = Integer.parseInt(stk.nextToken()),
             cost = Integer.parseInt(stk.nextToken());

             cities[sour][des] = Math.min(cities[sour][des], cost);
        }

        solution();
    }

    private static void solution(){
        long[][] costs = new long[n + 1][n + 1];
        for(int i=0 ; i<=n ; i++){
            for(int j=0 ; j<=n ; j++){
                costs[i][j] = i == j ? 0 : Integer.MAX_VALUE;
                costs[i][j] = Integer.MAX_VALUE != cities[i][j] ? cities[i][j] : costs[i][j];
            }
        }

        for(int k=0 ; k<=n ; k++){
            for(int i=0 ; i<=n ; i++){
                for(int j=0 ; j<=n ; j++){
                    costs[i][j] = Math.min(costs[i][j], costs[i][k] + costs[k][j]);
                }
            }
        }

        printAnswer(costs);
    }

    private static void printAnswer(long[][] costs){
        StringBuilder answer = new StringBuilder();

        for(int i=1 ; i<=n ; i++){
            for(int j=1 ; j<=n ; j++){
                answer.append(costs[i][j] != Integer.MAX_VALUE ? costs[i][j] : 0).append(" ");        
            }
            answer.append("\n");
        }

        System.out.println(answer);
    }
}
