/**
 * 접근 :
 *      1. BFS
 *      2. 방문기록은 String 을 set에 담아서
 *      3. 옮길 수 있는 경우 옮기기
 *
 * 문제점 :
 *      1. 정답이 들어온 경우를 처리 안함
 */

package week16.n9_퍼즐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static HashSet<String> hs = new HashSet<>();
    static String answer = "123456780";
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[][] stage = new int[3][3];
        int x = 0 ,y = 0;
        for(int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 3; j++) {
                stage[i][j] = Integer.parseInt(st.nextToken());
                if(stage[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        Queue<Puzzle> q = new ArrayDeque<>();
        q.add(new Puzzle(stage, x, y, 0));
        hs.add(q.peek().getString());
        while(!q.isEmpty()) {
            Puzzle now = q.poll();

            if(now.getString().equals(answer)) {
                System.out.println(now.moved);
                return;
            }

            for(int i = 0; i < 4; i++) {
                int x2 = now.x + dx[i];
                int y2 = now.y + dy[i];
                if(getPossibility(x2, y2)) {
                    int[][] next_stage = deepCopy(now.stage);
                    next_stage[now.x][now.y] = now.stage[x2][y2];
                    next_stage[x2][y2] = 0;
                    Puzzle next = new Puzzle(next_stage, x2, y2, now.moved + 1);

                    String str = next.getString();

                    if(hs.contains(str)) continue;
                    hs.add(str);
                    q.add(next);
                }
            }
        }

        System.out.println(-1);
    }

    static int[][] deepCopy(int[][] stage) {
        int[][] copy = new int[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                copy[i][j] = stage[i][j];
            }
        }

        return copy;
    }

    static boolean getPossibility(int x, int y) {
        if(x < 0 || y < 0) return false;
        if(x >= 3 || y >= 3) return false;
        return true;
    }
}

class Puzzle {
    int[][] stage;
    int x, y;
    int moved;

    public Puzzle(int[][] stage, int x, int y, int moved) {
        this.stage = stage;
        this.x = x;
        this.y = y;
        this.moved = moved;
    }

    String getString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                sb.append(stage[i][j]);
            }
        }
        return sb.toString();
    }

}