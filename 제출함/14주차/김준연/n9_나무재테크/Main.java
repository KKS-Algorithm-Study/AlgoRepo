/**
 * 문제 정리 :
 *      1. 모든 칸에는 양분이 5 들어있음
 *      2. 한 칸에 나무가 여러개 심어져있을 수 있음
 *      3. 봄에는 나무가 자신의 나이만큼 양분을 먹고 나이가 1 증가함.
 *      4. 한 칸에 나무가 여러개 있다면, 가장 어린 나무부터 양분을 먹음
 *      5. 자기 나이만큼 양분을 먹지 못하는 나무는 즉사
 *      6. 여름에는 죽은 나무가 양분으로 변하게 됨. 나이 / 2 값만큼
 *      7. 가을에는 나무가 번식함. 나이가 5의 배수일 때, 인접한 8개 칸에 나이가 1인 나무가 생김.
 *      8. 겨울에는 로봇이 땅에 양분을 추가함. 이는 각 칸마다 상이함.
 *
 *      k년이 지난 후 살아있는 나무의 개수를 구하라
 *
 * 풀이 :
 *      1. 각 나무의 좌표에 해당하는 배열에 넘버링을 시키고
 *      2. 그 넘버에 맞는 ArrayList를 Map으로 매핑시켜서
 *      3. 해당 좌표에 나이가 몇인 나무가 몇개 있는지 기록시킴
 *      4. 이후 구현은 하라는대로 함
 */

package week14.n9_나무재테크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N;
    static int[][] foods;
    static int[][] additional_foods;
    static int[][] trees;
    static HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();
    static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        foods = new int[N+1][N+1];
        trees = new int[N+1][N+1];
        additional_foods = new int[N+1][N+1];

        int idx = 0;
        for(int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= N; j++) {
                additional_foods[i][j] = Integer.parseInt(st.nextToken());
                foods[i][j] = 5;
                hm.put(idx, new ArrayList<>());
                trees[i][j] = idx++;
            }
        }

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int year = Integer.parseInt(st.nextToken());

            hm.get(trees[x][y]).add(year);
        }

        for(int i = 0; i < K; i++) {
            //System.out.println(i + "년차 --------------");
            do_SpringAndSummer();
            do_fall();
            do_winter();
        }

        System.out.println(get_trees());

    }

    static void do_SpringAndSummer() {
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                ArrayList<Integer> next = new ArrayList<>();

                ArrayList<Integer> now = hm.get(trees[i][j]);
                now.sort(Collections.reverseOrder());
                while(!now.isEmpty()) {
                    int now_tree = now.get(now.size() - 1);
                    //System.out.println(i + " / " + j + " 에서 " + now_tree + "년산 나무가 성장함");
                    if(foods[i][j] >= now_tree) {
                        foods[i][j] -= now_tree;
                        next.add(now_tree + 1);
                        now.remove(now.size() - 1);
                    }
                    else break;
                }

                hm.put(trees[i][j], next);

                for(int dead : now) {
                    //System.out.println(i + " / " + j + " 에서 " + dead + "년산 나무가 뒤짐");
                    foods[i][j] += (dead / 2);
                }
            }
        }
    }

    static void do_fall() {
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                ArrayList<Integer> now = hm.get(trees[i][j]);

                for(int n : now) {
                    if(n % 5 == 0) {
                        //System.out.println(i + " / " + j + " 에서 나무가 증식함");
                        for(int k = 0; k < 8; k++) {
                            if(getPossibility(i + dx[k], j + dy[k])) {
                                hm.get(trees[i + dx[k]][j + dy[k]]).add(1);
                            }
                        }
                    }
                }
            }
        }
    }

    static void do_winter() {
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                foods[i][j] += additional_foods[i][j];
            }
        }
    }

    static int get_trees() {
        int sum = 0;
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                sum += hm.get(trees[i][j]).size();
            }
        }

        return sum;
    }

    static boolean getPossibility(int x, int y) {
        if(x <= 0) return false;
        if(x > N) return false;
        if(y <= 0) return false;
        if(y > N) return false;
        return true;
    }
}
