import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main{
    private static int n, m;
    private static int[][] A;
    private static final int[] dy = {0, 0, -1, -1, -1, 0, 1, 1, 1}, dx = {0, -1, -1, 0, 1, 1, 1, 0, -1};
    private static boolean[][] isDisappeared;
    private static Queue<int[]> clouds, copyPos;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());
        n = Integer.parseInt(stk.nextToken());        
        m = Integer.parseInt(stk.nextToken());

        A = new int[n][n];
        for(int i=0 ; i<n ; i++){
            stk = new StringTokenizer(br.readLine());
            for(int j=0 ; j<n ; j++){
                A[i][j] = Integer.parseInt(stk.nextToken());
            }
        }

        clouds = new LinkedList<>();
        clouds.add(new int[]{n - 1, 0});
        clouds.add(new int[]{n - 2, 0});
        clouds.add(new int[]{n - 1, 1});
        clouds.add(new int[]{n - 2, 1});

        copyPos = new LinkedList<>();

        while(m-- > 0){
            stk = new StringTokenizer(br.readLine());
            isDisappeared = new boolean[n][n];
            int d = Integer.parseInt(stk.nextToken()), s = Integer.parseInt(stk.nextToken());
            raining(d, s);
            createCloud();
        }

        int answer = 0;
        for(int i=0 ; i<n ; i++){
            for(int j=0 ; j<n ; j++){
                answer += A[i][j];
            }
        }

        System.out.println(answer);
    }

    private static int[] moveCloud(int y, int x, int d, int s){
        int ny = y, nx = x;

        for(int i=0 ; i<s ; i++){
            ny += dy[d];
            nx += dx[d];

            if(ny < 0)ny = n - 1;
            if(nx < 0)nx = n - 1;

            ny %= n;
            nx %= n;
        }

        return new int[]{ny, nx};
    }

    private static void raining(int d, int s){
        while(!clouds.isEmpty()){
            int[] cloud = clouds.poll();
            int y = cloud[0], x = cloud[1];

            int[] next = moveCloud(y, x, d, s);
            int ny = next[0], nx = next[1];

            A[ny][nx]++;
            isDisappeared[ny][nx] = true;
            copyPos.add(new int[]{ny, nx});
        }

        while(!copyPos.isEmpty()){
            int[] cur = copyPos.poll();
            int y = cur[0], x = cur[1];

            A[y][x] += waterCopy(y, x);
        }
    }

    private static int waterCopy(int y, int x){
        int ret = 0;

        for(int i = 2 ; i <= 8 ; i += 2){
            int ny = y + dy[i], nx = x + dx[i];
            if(0 > ny || ny >= n || 0 > nx || nx >= n || A[ny][nx] == 0)continue;
            ret++;
        }

        return ret;
    }

    private static void createCloud(){
        for(int i=0 ; i<n ; i++){
            for(int j=0 ; j<n ; j++){
                if(isDisappeared[i][j] || A[i][j] < 2)continue;
                clouds.add(new int[]{i, j});
                A[i][j] -= 2;
            }
        }
    }
}