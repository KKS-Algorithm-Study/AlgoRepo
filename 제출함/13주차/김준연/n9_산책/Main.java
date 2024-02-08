/**
 * !! 답안 참고함
 *
 * 접근 :
 *      1. 경로를 기록하는 다익스트라
 *      2. S -> E 갈때 경로를 사전순으로 정렬시키고 첫번째에 속한 경로 다 제거
 *      3. 제거했으면 남은 노드들로 다익스트라 한번 더 (경로필요없음)
 *      4. 두 번의 합
 *
 * 문제점 :
 *      1. 다익스트라를 진행하면서 방문한 노드들을 String에 저장하고
 *      2. 최종 지점까지 간 String들을 정렬시킨 다음
 *      3. 사전순으로 가장 빠른 String 내부의 노드들을 제외하고 다시 다익스트라
 *      => 실패
 *
 * 개선 :
 *      1. 다익스트라 말고 BFS로
 *      2. 인접리스트를 모두 정렬시켜서 최초로 E에 도달하는 루트가 사전순으로 가장 빠른 루트가 되도록
 *      3. 해당 루트에 속한 노드들을 제외하고 다시 BFS
 */

package week13.n9_산책;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        for(int i = 0; i < N + 1; i++) {
            adj.add(new ArrayList<>());
        }

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adj.get(a).add(b);
            adj.get(b).add(a);
        }

        for(int i = 0; i < N + 1; i++) {
            Collections.sort(adj.get(i));
        }

        st = new StringTokenizer(br.readLine());
        int S = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        Queue<Point> q = new LinkedList<>();

        q.add(new Point(S, 0));


        int[] visited = new int[N+1];
        for(int i = 0; i < N + 1; i++) visited[i] = i;

        int result = 0;

        while(!q.isEmpty()) {
            Point now = q.poll();
            if(now.num == E) {
                result += now.cnt;
                break;
            }

            for(int next : adj.get(now.num)) {
                if(visited[next] == next && next != S) {
                    q.add(new Point(next, now.cnt + 1));
                    visited[next] = now.num;
                }
            }
        }

        HashSet<Integer> hs = new HashSet<>();
        int idx = E;
        while(visited[idx] != idx) {
            hs.add(idx);
            idx = visited[idx];
        }

        q.clear();

        q.add(new Point(E, 0));

        Arrays.fill(visited, 0);

        while(!q.isEmpty()) {
            Point now = q.poll();
            if(now.num == S) {
                result += now.cnt;
                break;
            }

            for(int next : adj.get(now.num)) {
                if(visited[next] == 0 && !hs.contains(next)) {
                    q.add(new Point(next, now.cnt + 1));
                    visited[next] = 1;
                }
            }
        }

        System.out.println(result);

    }
}

class Point {
    int num;
    int cnt;

    public Point(int num, int cnt) {
        this.num = num;
        this.cnt = cnt;
    }
}