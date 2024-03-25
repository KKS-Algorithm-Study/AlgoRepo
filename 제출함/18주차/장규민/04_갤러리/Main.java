import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main{
    private static int n, m, answer;
    private static char[][] walls;
    private static boolean[][] visit;
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        walls = new char[n][m];
        for(int i=0 ; i<n ; i++){
            String values = br.readLine();
            for(int j=0 ; j<m ; j++){
                walls[i][j] = values.charAt(j);
            }
        }

        answer = 0;
        solution();

    }

    private static void solution(){
        final char[][][] pictures = new char[][][]{
            {{'X', 'X'}, {'.', '.'}},
            {{'.', '.'}, {'X', 'X'}},
            {{'X', '.'}, {'X', '.'}},
            {{'.', 'X'}, {'.', 'X'}}
        };

        
        for(int i=0 ; i<4 ; i++){
            visit = new boolean[n][m];
            for(int y = 0 ; y <= n - 2 ; y++){
                for(int x = 0 ; x <= m - 2 ; x++){
                    if(checkSameShape(y, x, pictures[i]))
                        answer++;
                }
            }
        }

        System.out.println(answer);
    }

    private static boolean checkSameShape(int y, int x, char[][] shape){
        for(int i=y ; i<y + 2 ; i++){
            for(int j=x ; j<x + 2 ; j++){
                if(walls[i][j] != shape[i-y][j-x] || visit[i][j])
                    return false;
            }
        }

        for(int i=y ; i<y + 2 ; i++){
            for(int j=x ; j<x + 2 ; j++){
                if(walls[i][j] == '.')visit[i][j] = true;
            }
        }
        
        return true;
    }
}