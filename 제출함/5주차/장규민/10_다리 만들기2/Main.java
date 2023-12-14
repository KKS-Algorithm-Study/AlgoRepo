import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Main{

    private static int n, m, number;
    private static int[] parents;
    private static int[][] board;
    private static boolean[][] visit;
    private static PriorityQueue<int[]> pq;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        board = new int[n][m];

        for(int i=0 ; i<n ; i++){
            stk = new StringTokenizer(br.readLine());
            for(int j=0 ; j<m ; j++){
                board[i][j] = Integer.parseInt(stk.nextToken());
            }
        }

        islandNumbering();

        parents = new int[number];
        for(int i=1 ; i<number ; i++){
            parents[i] = i;
        }

        connection();

        int answer = mst();
        System.out.println(allConnection() ? answer : "-1");
    }

    private static boolean allConnection(){
        int parent = findParent(1);

        for(int i=2 ; i < number ; i++){
            if(parent != findParent(i))
                return false;
        }
        return true;
    }

    private static void islandNumbering(){
        visit = new boolean[n][m];
        number = 1;

        for(int i=0 ; i<n ; i++){
            for(int j=0 ; j<m ; j++){
                if(visit[i][j] || board[i][j] == 0)
                    continue;
                bfs(i, j);
                number++;
            }
        }
    }

    private static void bfs(int y, int x){
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[]{y, x});
        visit[y][x] = true;

        final int[] dy = {0,0,1,-1}, dx = {1,-1,0,0};

        while(!que.isEmpty()){
            int[] cur = que.poll();
            int curY = cur[0], curX = cur[1];
            board[curY][curX] = number;

            for(int i=0 ; i<4 ; i++){
                int nextY = curY + dy[i], nextX = curX + dx[i];
                
                if(0 > nextY || nextY >= n || 0 > nextX || nextX >= m)
                    continue;

                if(visit[nextY][nextX] || board[nextY][nextX] == 0)
                    continue;

                que.add(new int[]{nextY, nextX});
                visit[nextY][nextX] = true;
            }
        }
    }

    private static void connection(){
        pq = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);

        for(int i=0 ; i<n ; i++){
            for(int j=0 ; j<m ; j++){
                if(board[i][j] == 0)
                    continue;
                connectHorizon(i, j);
                connectVertical(i, j);
            }
        }
    }

    private static void connectHorizon(int y, int x){
        int dist = 0, curNumber = board[y][x];

        for(int nx = x - 1 ; nx >= 0 ; nx--){
            if(board[y][nx] == 0){
                dist++;
                continue;
            }
            if(dist > 1 && board[y][nx] != curNumber){
                pq.add(new int[]{curNumber, board[y][nx], dist});
            }
            break;
        }

        dist = 0;
        for(int nx = x + 1 ; nx < m ; nx++){
            if(board[y][nx] == 0){
                dist++;
                continue;
            }

            if(dist > 1 && board[y][nx] != curNumber){
                pq.add(new int[]{curNumber, board[y][nx], dist});
            }
            break;
        }
    }

    private static void connectVertical(int y, int x){
        int dist = 0, curNumber = board[y][x];

        for(int ny = y - 1 ; ny >= 0 ; ny--){
            if(board[ny][x] == 0){
                dist++;
                continue;
            }
            if(dist > 1 && board[ny][x] != curNumber){
                pq.add(new int[]{curNumber, board[ny][x], dist});
            }
            break;
        }

        dist = 0;
        for(int ny = y + 1 ; ny < n ; ny++){
            if(board[ny][x] == 0){
                dist++;
                continue;
            }

            if(board[ny][x] != curNumber && dist > 1)
                pq.add(new int[]{curNumber, board[ny][x], dist});
            break;
        }
    }

    private static int mst(){
        int ret = 0;

        boolean[][] visit = new boolean[number][number];

        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int sour = cur[0], des = cur[1], cost = cur[2];

            if(visit[sour][des] || findParent(sour) == findParent(des))
                continue;

            visit[sour][des] = visit[des][sour] = true;

            union(sour, des);
            ret += cost;
        }

        return ret;
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