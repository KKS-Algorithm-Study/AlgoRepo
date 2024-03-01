/**
 * 접근 :
 *      1. 한 나라 기준 오른쪽과 아래를 비교함
 *      2. 만약 L과 R사이의 차이를 가졌다면 union 시킴
 *      3. 모든 나라에 대해 반복하고 같은 집합에 속한 나라들을 인구 이동 시킴
 *      4. 이 과정을 인구이동이 더 이상 일어나지 않을 때 까지 반복
 *
 * 시간복잡도 :
 *      나라 순회 : N^2
 *      인구이동 횟수 제한 : 2000
 *      2000 * 50 * 50 = 5백만
 */


package week16.n3_인구이동;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, L, R;
    static int[] parents;
    static int[][] stage;
    static int[] people;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        stage = new int[N][N];
        int idx = 1;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                stage[i][j] = idx++;
            }
        }

        people = new int[N*N+1];

        idx = 1;
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                people[idx++] = Integer.parseInt(st.nextToken());
            }
        }

        boolean toggle = true;
        int day = 0;
        while(toggle) {
            toggle = false;

            parents = new int[N*N+1];
            for(int i = 0; i < N * N + 1; i++) {
                parents[i] = i;
            }

            for(int i = 0; i < N - 1 ; i++) {
                for(int j = 0; j < N - 1; j++) {
                    if(getRight(i, j)) toggle = true;
                    if(getDown(i, j)) toggle = true;
                }
                if(getDown(i, N - 1)) toggle = true;
            }

            for(int i = 0; i < N - 1; i++) {
                if(getRight(N-1, i)) toggle = true;
            }

            int[] sum = new int[N*N+1];
            int[] cnt = new int[N*N+1];
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    int parent = getParent(stage[i][j]);
                    sum[parent] += people[stage[i][j]];
                    cnt[parent]++;
                }
            }

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    int parent = getParent(stage[i][j]);
                    people[stage[i][j]] = sum[parent] / cnt[parent];
                }
            }

            if(toggle) day++;
        }

        System.out.println(day);
    }

    static boolean getRight(int x, int y) {
        int origin = people[stage[x][y]];
        int right = people[stage[x][y+1]];

        if(Math.abs(origin - right) >= L && Math.abs(origin - right) <= R) {
            union(stage[x][y], stage[x][y+1]);
            return true;
        }
        return false;
    }

    static boolean getDown(int x, int y) {
        int origin = people[stage[x][y]];
        int down = people[stage[x+1][y]];

        if(Math.abs(origin - down) >= L && Math.abs(origin - down) <= R) {
            union(stage[x][y], stage[x+1][y]);
            return true;
        }
        return false;
    }

    static int getParent(int x) {
        if(parents[x] == x) return x;
        return parents[x] = getParent(parents[x]);
    }

    static void union(int a, int b) {
        int a_parent = getParent(a);
        int b_parent = getParent(b);

        if(a_parent <= b_parent) parents[b_parent] = a_parent;
        else parents[a_parent] = b_parent;
    }

}
