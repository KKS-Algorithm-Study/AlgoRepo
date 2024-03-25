import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main{
    private static int n, l, r;
    private static int[][] countries;
    private static final int[] dy = {0,0,1,-1}, dx = {1,-1,0,0};
    private static Queue<int[]> que;
    private static boolean[][] visit;
    private static List<int[]> list;


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        l = Integer.parseInt(stk.nextToken());
        r = Integer.parseInt(stk.nextToken());

        countries = new int[n][n];
        for(int i=0 ; i<n ; i++){
            stk = new StringTokenizer(br.readLine());
            for(int j=0 ; j<n ; j++){
                countries[i][j] = Integer.parseInt(stk.nextToken());
            }
        }

        que = new LinkedList<>();
        int answer = 0;
        boolean flag = true;

        while(true){
            visit = new boolean[n][n];
            flag = false;
            for(int i=0 ; i<n ; i++){
                for(int j=0 ; j<n ; j++){
                    if(visit[i][j])continue;
                    list = new ArrayList<>();
                    bfs(que, i, j);
                    if(list.size() > 1)flag = true;
                }
            }
            if(!flag)break;
            answer++;
        }
        System.out.println(answer);
    }

    private static void bfs(Queue<int[]> que, int y, int x){
        que.add(new int[]{y, x});
        visit[y][x] = true;

        int total = 0;

        while (!que.isEmpty()) {
            int[] cur = que.poll();
            int curY = cur[0], curX = cur[1];

            list.add(new int[]{curY, curX});
            total += countries[curY][curX];

            diffBetweenLAndR(que, curY, curX);
        }

        calculatePopulation(total);
    }

    private static void calculatePopulation(int total){
        int avg = total / list.size();

        for(int[] pos : list){
            int y = pos[0], x = pos[1];
            countries[y][x] = avg;
        }
    }

    private static void diffBetweenLAndR(Queue<int[]> que, int y, int x){
        for(int i=0 ; i<4 ; i++){
            int ny = dy[i] + y, nx = x + dx[i];
            if(0 > ny || ny >= n || 0 > nx || nx >= n || visit[ny][nx])continue;
            int diff = Math.abs(countries[ny][nx] - countries[y][x]);

            if(!(l <= diff && diff <= r))
                continue;
            que.add(new int[]{ny, nx});
            visit[ny][nx] = true;
        }
    }
}