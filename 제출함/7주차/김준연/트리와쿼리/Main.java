/**
 * 접근 :
 *      1. DFS 탐색하면서 들어감
 *      2. 본인 포함 하위 연산 노드 개수 리턴하면서 기록하면서 나오기
 *
 * 시간복잡도 :
 *      인접리스트로 구현한 DFS니까
 *      각 노드 한번씩 방문, 각 간선 한번씩 사용
 *      O(N + R)
 */

package week7.트리와쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static int N, R, Q;
    static int[] query;
    static ArrayList<ArrayList<Integer>> tree = new ArrayList<ArrayList<Integer>>();
    static int[] result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        result = new int[N + 1];
        query = new int[Q];

        for(int i = 0; i <= N; i++) {
            tree.add(new ArrayList<Integer>());
        }

        for(int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            tree.get(u).add(v);
            tree.get(v).add(u);

        }

        dfs(R, -1);

        for(int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            System.out.println(result[u]);
        }

        br.close();
    }

    static int dfs(int now, int parent) {
        int sum = 1;

        ArrayList<Integer> subTree = tree.get(now);

        for(int i = 0; i < subTree.size(); i++) {
            int next = subTree.get(i);
            if(next == parent) continue;
            sum += dfs(next, now);
        }
        result[now] = sum;
        return sum;
    }


}
