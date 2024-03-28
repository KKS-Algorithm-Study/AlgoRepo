package week19.n4_이모티콘;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int S = Integer.parseInt(br.readLine());

        Queue<Case> q = new ArrayDeque<>();
        q.add(new Case(1, 0, 0));

        int second = 0;

        boolean[][] visited = new boolean[1025][1025];

        Loop1:
        while(true) {
            int cnt = q.size();
            while(cnt-- > 0) {
                Case now = q.poll();

                if(now.n == S) break Loop1;

                if (now.before_action != 1) {
                    if(!visited[now.n][now.n]) {
                        q.add(new Case(now.n, now.n, 1));
                        visited[now.n][now.n] = true;
                    }
                }
                if (now.clipboard > 0) {
                    if(now.n + now.clipboard > 1024) continue;
                    if(!visited[now.n + now.clipboard][now.clipboard]) {
                        q.add(new Case(now.n + now.clipboard, now.clipboard, 2));
                        visited[now.n + now.clipboard][now.clipboard] = true;
                    }
                }
                if (now.n - 1 > 0) {
                    if(!visited[now.n-1][now.clipboard]) {
                        q.add(new Case(now.n - 1, now.clipboard, 3));
                        visited[now.n-1][now.clipboard] = true;
                    }
                }
            }
            second++;
        }

        System.out.println(second);
    }
}

class Case {
    int n;
    int clipboard;
    int before_action;  //문제의 1, 2, 3

    public Case(int n, int clipboard, int before_action) {
        this.n = n;
        this.clipboard = clipboard;
        this.before_action = before_action;
    }
}