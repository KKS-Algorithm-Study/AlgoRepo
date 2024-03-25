import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static int n, h, d;
    private static char[][] board;
    private static int[] dy = {0,0,1,-1}, dx = {1,-1,0,0};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        h = Integer.parseInt(stk.nextToken());
        d = Integer.parseInt(stk.nextToken());

        board = new char[n][n];
        int[] start = new int[2];

        for(int i=0 ; i<n ; i++){
            String values = br.readLine();
            for(int j=0 ; j<n ; j++){
                board[i][j] = values.charAt(j);
                if(values.charAt(j) == 'S'){
                    start = new int[]{i, j};
                }
            }
        }
        
        System.out.println(bfs(start[0], start[1]));
    }

    private static int bfs(int y, int x) {
        Queue<Person> que = new LinkedList<>();

        que.add(new Person(y, x, 0, 0, h, 0));

        boolean[][][] visit = new boolean[n][n][11];
        visit[y][x][0] = true;

        while(!que.isEmpty()){
            Person cur = que.poll();

            for(int i=0 ; i < 4 ; i++){
                int nextY = cur.y + dy[i], nextX = cur.x + dx[i];
                int nextDist = cur.dist + 1, remainH = cur.remainH, remainD = cur.remainD;
                int cnt = cur.cnt;
                
                if(0 > nextY || nextY >= n || 0 > nextX || nextX >= n)
                    continue;

                if(visit[nextY][nextX][cnt])
                    continue;
                
                if(board[nextY][nextX] == 'U'){
                    if(cnt + 1 > 10 || visit[nextY][nextX][cnt + 1])
                        continue;
                    cnt++;
                    remainD = d + 1;
                }

                if(remainD > 0){
                    remainD--;
                }else{
                    remainH--;
                }
                if(remainH == 0)
                    continue;

                if(board[nextY][nextX] == 'E')
                    return nextDist;

                visit[nextY][nextX][cnt] = true;
                que.add(new Person(nextY, nextX, nextDist, cnt, remainH, remainD));
            }
        }

        return -1;
    }

    static class Person{
        int y, x, dist, cnt, remainH, remainD;

        public Person(int y, int x, int dist, int cnt, int remainH, int remainD){
            this.y = y;
            this.x = x;
            this.dist = dist;
            this.cnt = cnt;
            this.remainH = remainH;
            this.remainD = remainD;
        }
    }
}
