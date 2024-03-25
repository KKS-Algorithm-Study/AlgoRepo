import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class Main{

    // 10 10 15 20 5 10
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int s = Integer.parseInt(br.readLine());

        System.out.println(bfs(s));

    }

    private static int bfs(int target){
        Queue<int[]> que = new LinkedList<>();
        // 현재 숫자, 수행한 시간, 클립 보드안에 이모티콘 개수
        que.add(new int[]{1, 0, 0});

        boolean[][] visit = new boolean[1001][1001];
        visit[1][0] = true;

        while(!que.isEmpty()){
            int[] cur = que.poll();
            int total = cur[0], cnt = cur[1], clipBoardSize = cur[2];
            
            if(total == target)return cnt + 1;
            
            if(!visit[total][total]){
                que.add(new int[]{total, cnt + 1, total});
                visit[total][total] = true;
            }

            int paste = total + clipBoardSize;
            if(paste == target)return cnt + 1;
            if(paste < 1001 && !visit[paste][clipBoardSize]){
                que.add(new int[]{paste, cnt + 1, clipBoardSize});
                visit[paste][clipBoardSize] = true;
            }

            int delete = total - 1;
            if(delete == target)return cnt + 1;
            if(0 <= delete && delete < 1001 && !visit[delete][clipBoardSize]){
                que.add(new int[]{total - 1, cnt + 1, clipBoardSize});
                visit[delete][clipBoardSize] = true;
            }
        }

        return 0;
    }
}