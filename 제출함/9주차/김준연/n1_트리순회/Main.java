/**
 * 접근 :
 *      1. 전위 순회는 DFS
 *      2. 중위순회는.. 뭐야 순서만바꾸면되네
 *
 */

package week9.n1_트리순회;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static HashMap<Character, Character> left = new HashMap<>();
    static HashMap<Character, Character> right = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            char parent = st.nextToken().charAt(0);
            char a = st.nextToken().charAt(0);
            char b = st.nextToken().charAt(0);

            if(a != '.') left.put(parent, a);
            if(b != '.') right.put(parent, b);
        }

        dfs('A');
        System.out.println("");
        dfs2('A');
        System.out.println("");
        dfs3('A');
    }

    static void dfs(char now) {
        System.out.print(now);
        if(left.containsKey(now)) dfs(left.get(now));
        if(right.containsKey(now)) dfs(right.get(now));
    }

    static void dfs2(char now) {
        if(left.containsKey(now)) dfs2(left.get(now));
        System.out.print(now);
        if(right.containsKey(now)) dfs2(right.get(now));
    }

    static void dfs3(char now) {
        if(left.containsKey(now)) dfs3(left.get(now));
        if(right.containsKey(now)) dfs3(right.get(now));
        System.out.print(now);
    }
}
