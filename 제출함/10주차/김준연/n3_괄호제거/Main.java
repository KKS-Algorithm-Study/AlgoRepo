/**
 * 접근 :
 *      1. 각 괄호 쌍을 스택을 이용해서 인덱스를 부여함
 *      2. 각 인덱스에 대해 dfs로 완전탐색하며 모든 경우의 수를 구함
 *      3. 중복되지 않게 Set에 담고, 마지막에 Set의 인자를 받은 ArrayList를 정렬시키는 것으로 출력
 */

package week10.n3_괄호제거;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

public class Main {

    static int cnt;
    static int[][] location;
    static String str;
    static HashSet<String> hs = new HashSet<>();

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        str = br.readLine();

        cnt = 0;
        location = new int[10][2];
        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '(') {
                stack.push(i);
            }
            else if(str.charAt((i)) == ')') {
                location[cnt][0] = stack.pop();
                location[cnt][1] = i;
                cnt++;
            }
        }

        boolean[] visited = new boolean[cnt];

        bf(0, visited);
        hs.remove(str);
        ArrayList<String> result = new ArrayList<>(hs);
        Collections.sort(result);
        for(String s : result) {
            System.out.println(s);
        }

    }

    static void bf(int now, boolean[] visited) {

        if(now >= cnt) {
            plus(visited);
            return;
        }

        for(int i = now + 1; i <= cnt; i++) {

            visited[now] = true;
            bf(i, visited);
            visited[now] = false;

            bf(i, visited); //now 번째를 찍지 않은 경우

        }

    }

    static void plus(boolean[] visited) {
        StringBuilder result = new StringBuilder();
        int visited_cnt = 0;
        int[][] visited_elements = new int[visited.length][2];
        for(int i = 0; i < visited.length; i++) {
            if(visited[i]) {
                visited_elements[visited_cnt] = location[i];
                visited_cnt++;
            }
        }

        Loop1:
        for(int i = 0; i < str.length(); i++) {
            char now = str.charAt(i);
            if(now == '(' || now == ')') {
                for(int j = 0; j < visited_cnt; j++) {
                    if(visited_elements[j][0] == i || visited_elements[j][1] == i) continue Loop1;
                }
            }
            result.append(now);

        }

        hs.add(String.valueOf(result));
    }
}
