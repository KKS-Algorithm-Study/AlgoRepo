/**
 * 접근 :
 *      1. BFS
 *      2. 방문기록은 x, y, 남은펀치횟수, 낮or밤 으로 4차원 배열에 기록
 *      3. 큐에 담을 경우의 수
 *          1) 갈 수 있는 길이고 방문한적없으면 간다
 *          2) 갈 수 없는 길인데 방문한적없고 낮이라서 펀치날릴 수 있으면 간다
 *          3) 갈 수 없는 길인데 방문한적없고 밤이라며 펀치못날리면 제자리에서 한타임 기다린다.
 *
 * 문제점 :
 *      1. Queue 인터페이스에 LinkedList 구현체를 사용했는데 시간초과남
 *          => ArrayDeque 로 바꾸니까 통과함
 *          왜?
 *              LinkedList는 이중 연결 리스트로 추가, 제거의 속도는 O(1) 이지만 새 객체를 생성하고 참조하는 오버헤드가 존재함
 *              ArrayDeque는 동적 배열을 사용하여 요소를 순차적으로 배치하여 캐시친화적임(?).
 *              즉, ArrayDeque는 내부적으로 연속된 메모리에 저장하여 메모리 오버헤드가 적고, LinkedList는 새 노드를 생성하고 참조하는 과정에서 오버헤드가 크다.
 */

package week15.n10_벽부수고이동하기3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int N, M, K;
    static int[][] stage;
    static boolean[][][][] visited;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        stage = new int[N][M];
        visited = new boolean[N][M][K+1][2];        //x, y, 벽파괴, 낮밤

        for(int i = 0; i < N; i++) {
            String str = br.readLine();
            for(int j = 0; j < M; j++) {
                stage[i][j] = Character.getNumericValue(str.charAt(j));
            }
        }

        Queue<Next> q = new ArrayDeque<>();

        if(stage[0][0] == 1) {
            q.add(new Next(0, 0, 1, K - 1));
            visited[0][0][K - 1][0] = true;
        }
        else {
            q.add(new Next(0, 0, 1, K));
            visited[0][0][K][0] = true;
        }

        while(!q.isEmpty()) {
            Next now = q.poll();

            if(now.x == N - 1 && now.y == M - 1) {
                System.out.println(now.count);
                return;
            }

            for(int i = 0; i < 4; i++) {
                int x = now.x + dx[i];
                int y = now.y + dy[i];
                int day = now.count % 2;
                if(getPossibility(x, y)) {
                    if(stage[x][y] == 0) {
                        if(!visited[x][y][now.punch][day]) {
                            q.add(new Next(x, y, now.count + 1, now.punch));
                            visited[x][y][now.punch][day] = true;
                        }
                    }
                    else {
                        if(!visited[x][y][now.punch][day] && day == 1 && now.punch > 0) {
                            q.add(new Next(x, y, now.count + 1, now.punch - 1));
                            visited[x][y][now.punch][day] = true;
                        }
                        else if(!visited[x][y][now.punch][day] && day == 0 && now.punch > 0) {
                            q.add(new Next(now.x, now.y, now.count + 1, now.punch));
                            visited[x][y][now.punch][day] = true;
                        }
                    }
                }
            }
        }

        System.out.println(-1);


    }

    static boolean getPossibility(int x, int y) {
        if(x < 0 || y < 0) return false;
        if(x >= N || y >= M) return false;
        return true;
    }
}

class Next {
    int x;
    int y;
    int count;
    int punch;

    public Next(int x, int y, int count, int punch) {
        this.x = x;
        this.y = y;
        this.count = count;
        this.punch = punch;
    }
}