/**
 * 접근 :
 *      1. 드래곤 커브는 이전 단계에서 해온 각 비행을 '역순'으로 '반시계 90도' 회전시켜 한번 더 수행하는 것이다.
 *          위 -> 왼쪽
 *          오른쪽 -> 위
 *          왼쪽 -> 아래
 *          아래 -> 왼쪽
 *      2. 따라서 0단계는 기본으로 넣어두고
 *      3. 다음 단계를 할 때는 여태까지 한 비행을 리스트에서 역순으로 꺼내 반시계 회전한 방향을 리스트에 추가시키는 것으로 진행
 *      4. 모든 비행을 마치면 한 점에서 네모 꼭짓점이 모두 true인 부분이 몇 개인지 카운팅
 */

package week14.n8_드래곤커브;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static boolean[][] stage = new boolean[101][101];
    static int[] dx = {0, -1, 0, 1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int direction = Integer.parseInt(st.nextToken());
            int level = Integer.parseInt(st.nextToken());

            do_dragon_curve(x, y, direction, level);
        }

        System.out.println(get_square());

    }

    static void do_dragon_curve(int x, int y, int direction, int level) {
        stage[x][y] = true;
        ArrayList<Integer> curve = new ArrayList<>();
        curve.add(direction);
        x += dx[direction];
        y += dy[direction];
        stage[x][y] = true;

        for(int i = 0; i < level; i++) {
            for(int j = curve.size() - 1; j >= 0; j--) {
                direction = curve.get(j);
                direction++;
                if(direction == 4) direction = 0;

                curve.add(direction);
                x += dx[direction];
                y += dy[direction];
                stage[x][y] = true;
            }
        }

    }

    static int get_square() {
        int cnt = 0;
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                if(stage[i][j] && stage[i+1][j] && stage[i][j+1] && stage[i+1][j+1]) cnt++;
            }
        }
        return cnt;
    }
}
