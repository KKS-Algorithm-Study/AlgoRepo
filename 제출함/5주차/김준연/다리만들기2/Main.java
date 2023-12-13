/**
 * 접근 :
 *      1. 2차원 배열을 순회하여 섬을 만나면 해당 섬의 범위를 파악(DFS)하고 인덱싱한다.
 *      2. 다시 가로순회, 세로순회하며 섬과 섬사이의 거리를 잰다.
 *      3. 거리가 2이상인 다른 섬에 갈 길이 있다면 간선을 기록한다.
 *      4. 모든 간선에 대한 기록이 끝낫으면 섬과 간선을 이용한 최소 신장 트리 구현
 *
 * 문제점 :
 *      1. 가로 세로 탐색 중 자신의 땅을 다시 밟은 경우 거리 계산 초기화를 안함.
 */

package week5.다리만들기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int[] parent;
    static int island_cnt = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] board = new int[N][M];
        int[][] board_idx = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == 1 && board_idx[i][j] == 0) {
                    island_cnt++;
                    dfs(board, board_idx, i, j);
                }
            }
        }

        parent = new int[island_cnt + 1];

        for (int i = 0; i < island_cnt + 1; i++) {
            parent[i] = i;
        }

        ArrayList<Line> lines = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int island = -1;
            int cnt = 0;
            for (int j = 0; j < M; j++) {
                if (board_idx[i][j] == 0) cnt++;
                else if (board_idx[i][j] == island) cnt = 0;
                else {
                    int a = island;
                    int b = board_idx[i][j];
                    int cost = cnt;
                    cnt = 0;
                    island = b;
                    if (a != -1 && cost > 1) {
                        lines.add(new Line(a, b, cost));
                    }
                }
            }
        }

        for (int i = 0; i < M; i++) {
            int island = -1;
            int cnt = 0;
            for (int j = 0; j < N; j++) {
                if (board_idx[j][i] == 0) cnt++;
                else if (board_idx[j][i] == island) cnt = 0;
                else {
                    int a = island;
                    int b = board_idx[j][i];
                    int cost = cnt;
                    cnt = 0;
                    island = b;
                    if (a != -1 && cost > 1) {
                        lines.add(new Line(a, b, cost));
                    }
                }
            }
        }

        Collections.sort(lines);

        int sum = 0;

        for (Line now : lines) {
            if (!checkParent(now.a, now.b)) {
                union(now.a, now.b);
                sum += now.cost;
            }
        }

        int first_parent = find(1);
        for (int i = 2; i < island_cnt + 1; i++) {
            if (first_parent != find(i)) {
                sum = -1;
                break;
            }
        }

        System.out.println(sum);
    }

    static void dfs(int[][] board, int[][] board_idx, int x, int y) {
        board_idx[x][y] = island_cnt;
        if (x - 1 >= 0) {
            if (board[x - 1][y] == 1 && board_idx[x - 1][y] == 0) {
                dfs(board, board_idx, x - 1, y);
            }
        }
        if (y + 1 < board[0].length) {
            if (board[x][y + 1] == 1 && board_idx[x][y + 1] == 0) {
                dfs(board, board_idx, x, y + 1);
            }
        }
        if (x + 1 < board.length) {
            if (board[x + 1][y] == 1 && board_idx[x + 1][y] == 0) {
                dfs(board, board_idx, x + 1, y);
            }
        }
        if (y - 1 >= 0) {
            if (board[x][y - 1] == 1 && board_idx[x][y - 1] == 0) {
                dfs(board, board_idx, x, y - 1);
            }
        }

    }

    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    static void union(int x, int y) {
        x = find(x);
        y = find(y);
        if (x != y) {
            if (x < y) parent[y] = x;
            else parent[x] = y;
        }
    }

    static boolean checkParent(int x, int y) {
        x = find(x);
        y = find(y);
        return x == y;
    }
}

class Line implements Comparable<Line> {
    int a;
    int b;
    int cost;

    public Line(int a, int b, int cost) {
        this.a = a;
        this.b = b;
        this.cost = cost;
    }

    @Override
    public int compareTo(Line o) {
        return Integer.compare(this.cost, o.cost);
    }
}