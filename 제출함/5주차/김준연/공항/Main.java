/**
 * 접근 :
 *      유니온-파인드의 parent배열 처럼 자기 자신의 인덱스를 담은 배열에
 *      어느 게이트가 사용가능 한지 가리키도록 작성.
 *      비어있는 게이트를 사용했다면 사용한 게이트가 -1번 인덱스의 게이트를 가리키도록 수정
 *      만일 가리키는 게이트가 0번이라면 중지
 *      10만번 * find메서드 10만번
 *      => 잘모르지만 find메서드 굉장히빠르니까 약 100만 미만으로 추정
 * 문제점 :
 *      1. 자리가 있을 때 까지 -1 하면서 탐색하려하자
 *          =>10만*(10만+1) / 2 = 50억
 *      2. 이분탐색하면 log10만 < 17
 *          => 17 * 10만 = 170만
 *          하지만 모든 게이트가 10만으로 들어올때 얘기지 깔끔하게 정렬되지 못함
 */

package week5.공항;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[] gates;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int G = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int P = Integer.parseInt(st.nextToken());

        gates = new int[G + 1];     //n번째 게이트에 접근하려는 비행기가 n번째 인덱스를 보면 어디로 가야할지 알려주는 역할
        for(int i = 0; i < G + 1; i++) {
            gates[i] = i;
        }
        int cnt = 0;

        for(int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());
            int now = Integer.parseInt(st.nextToken());

            int possible_gate = find(now);
            if(possible_gate == 0) break;
            gates[possible_gate] = possible_gate - 1;
            cnt++;
        }

        System.out.println(cnt);

    }

    static int find(int x) {
        if(gates[x] == x) return x;
        return gates[x] = find(gates[x]);
    }
}
