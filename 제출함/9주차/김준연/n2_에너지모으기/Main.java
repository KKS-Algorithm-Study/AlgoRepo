/**
 * 접근 :
 *      1. 에너지 구슬 하나 고르면 양 옆의 에너지를 곱한 값이 모인다.
 *      2. 구슬의 수 최대 10개?
 *          10! = 3,628,800
 *          바로 완탐ㄱㄱ
 */

package week9.n2_에너지모으기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static ArrayList<Integer> energies = new ArrayList<>();
    static int max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            energies.add(Integer.parseInt(st.nextToken()));
        }


        for(int i = 1; i < N - 1; i++) {
            bf(i, 0, 0);
        }

        System.out.println(max);

    }

    static void bf(int now, int visited, int point) {
        if(Integer.bitCount(visited) == N - 1) {
            max = Math.max(max, point);
        }
        visited = visited | (1 << now);
        int left = 0;
        int right = 0;
        for(int i = now - 1; i >= 0; i--) {
            if((visited & (1 << i)) == 0) {
                left = energies.get(i);
                break;
            }
        }
        for(int i = now + 1; i < N; i++) {
            if((visited & (1 << i)) == 0) {
                right = energies.get(i);
                break;
            }
        }
        point += left * right;
        for(int i = 0; i < N; i++) {
            if((visited & (1 << i)) == 0) {
                bf(i, visited | (1<<i), point);
            }
        }
    }
}
