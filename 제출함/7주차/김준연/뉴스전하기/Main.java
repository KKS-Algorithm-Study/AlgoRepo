/**
 * 접근 :
 *      1. 시간 계산법
 *          자신의 하위 노드가 걸리는 시간을 정렬한다.
 *          제일 오래 걸릴 녀석한테 먼저 전화한다. +1분
 *          이렇게 1분씩 계속 추가하며 마지막 n번째 에는 +n분 한다.
 *      2. 계산된 하위 노드들 중 가장 오래걸리는 시간이 자신의 시간이 된다.
 */

package week7.뉴스전하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int[] dp;
    static ArrayList<ArrayList<Integer>> tree = new ArrayList<ArrayList<Integer>>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N  = Integer.parseInt(st.nextToken());
        dp = new int[N];

        for(int i = 0; i < N; i++) {
            tree.add(new ArrayList<Integer>());
        }

        st = new StringTokenizer(br.readLine());
        st.nextToken();
        for(int i = 1; i < N; i++) {
            tree.get(Integer.parseInt(st.nextToken())).add(i);
        }

        System.out.println(dfs(0));
    }

    static int dfs(int now) {
        int max = 0;
        ArrayList<Integer> subTree = tree.get(now);
        ArrayList<Integer> subTreePoints = new ArrayList<>();

        for(int i = 0; i < subTree.size(); i++) {
            int next = subTree.get(i);
            subTreePoints.add(dfs(next));
        }

        subTreePoints.sort(Collections.reverseOrder());

        for(int i = 0; i < subTreePoints.size(); i++) {
            max = Math.max(max, subTreePoints.get(i) + i + 1);
        }

        return max;
    }
}
