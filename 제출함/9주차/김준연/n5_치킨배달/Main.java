/**
 * 접근 :
 *      1. 치킨 집에 인덱스를 부여하고 모든 집과 모든 치킨집 사이의 거리를 구한다.
 *      2. 이를 집을 기준으로 인접리스트로 표현한다.
 *      3. m이 될때까지 치킨집을 하나씩 없애본다.
 *      4. 없앤 치킨집을 비트마스킹하며, 이미 계산해본 경우의 수라면 백트래킹
 *      5. 치킨집이 m개가 됐다? 비트마스크 해둔 치킨집을 제외하고 치킨거리 계산
 */

package week9.n5_치킨배달;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {

    static int[][] people = new int[100][2];
    static int[][] chicken = new int[13][2];
    static int M, peopleCnt, chickenCnt;
    static ArrayList<ArrayList<Line>> adj = new ArrayList<>();
    static HashSet<Integer> hs = new HashSet<>();
    static int min = 2100000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        peopleCnt = 0;
        chickenCnt = 0;

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                int num = Integer.parseInt(st.nextToken());
                if(num == 1) {
                    people[peopleCnt][0] = i;
                    people[peopleCnt][1] = j;
                    adj.add(new ArrayList<>());
                    peopleCnt++;

                }
                else if(num == 2) {
                    chicken[chickenCnt][0] = i;
                    chicken[chickenCnt][1] = j;
                    chickenCnt++;
                }
            }
        }

        for(int i = 0; i < peopleCnt; i++) {
            for(int j = 0; j < chickenCnt; j++) {
                int cost = Math.abs(people[i][0] - chicken[j][0]) + Math.abs(people[i][1] - chicken[j][1]);
                adj.get(i).add(new Line(j, cost));
            }
            Collections.sort(adj.get(i));
        }

        dfs(0);
        System.out.println(min);

    }

    static void dfs(int bits) {
        if(Integer.bitCount(bits) == (chickenCnt - M)) {
            min = Math.min(min, getCost(bits));
            return;
        }
        if(hs.contains(bits)) return;
        hs.add(bits);

        for(int i = 0; i < chickenCnt; i++) {
            if((bits & (1 << i)) == 0) dfs(bits | (1 << i));
        }
    }

    static int getCost(int bits) {
        int result = 0;
        for(int i = 0; i < peopleCnt; i++) {
            ArrayList<Line> nowAdj = adj.get(i);
            for(int j = 0; j < chickenCnt; j++) {
                if((bits & (1 << nowAdj.get(j).point)) == 0) {
                    result += nowAdj.get(j).cost;
                    break;
                }
            }
        }
        return result;
    }
}

class Line implements Comparable<Line>{
    int point;

    int cost;

    public Line(int point, int cost) {
        this.point = point;
        this.cost = cost;
    }

    @Override
    public int compareTo(Line o) {
        return Integer.compare(cost, o.cost);
    }
}
