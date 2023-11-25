package week2.DFS와BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int N, M, V;
        boolean[][] board;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());
        board = new boolean[N + 1][N + 1];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) board[i][j] = false;
        }

        HashSet<Integer> allNumber = new HashSet<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            board[a][b] = true;
            board[b][a] = true;
            allNumber.add(a);
            allNumber.add(b);
        }

        //여기까지 데이터 input

        allNumber.remove(V);
        PriorityQueue<Integer> allList = new PriorityQueue<>();
        allList.addAll(allNumber);

        Solution solution = new Solution();

        solution.dfs(V, board, allList);
        System.out.println("");
        allList = new PriorityQueue<>();
        allList.addAll(allNumber);
        solution.bfs(V, board, allList);
    }
}

class Solution {

    void dfs(int now, boolean[][] board, PriorityQueue<Integer> allList) {
        System.out.print(now + " ");
        int next = 0;
        ArrayList<Integer> notVisited = new ArrayList<>();
        while (true) {
            if (allList.size() == 0) return;
            int temp = allList.poll();
            if (board[now][temp]) {
                allList.addAll(notVisited);
                dfs(temp, board, allList);
            } else notVisited.add(temp);
        }
    }

    void bfs(int now, boolean[][] board, PriorityQueue<Integer> allList) {
        Queue<Point> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();

        System.out.print(now + " ");

        ArrayList<Integer> notVisited = new ArrayList<>();

        while (!allList.isEmpty()) {
            int temp = allList.poll();
            if (board[now][temp] && !visited.contains(temp)) {
                queue.add(new Point(now, temp));
                visited.add(temp);
            } else notVisited.add(temp);
        }

        allList.addAll(notVisited);

        while(!queue.isEmpty()) {

            Point nowPoint = queue.poll();

            System.out.print(nowPoint.next + " ");

            while (!allList.isEmpty()) {
                int temp = allList.poll();
                if (board[nowPoint.next][temp] && !visited.contains(temp)) {
                    queue.add(new Point(nowPoint.next, temp));
                    visited.add(temp);
                } else notVisited.add(temp);
            }

            allList.addAll(notVisited);

        }

    }
}

class Point {
    int now;
    int next;

    Point(int now, int next) {
        this.now = now;
        this.next = next;
    }
}