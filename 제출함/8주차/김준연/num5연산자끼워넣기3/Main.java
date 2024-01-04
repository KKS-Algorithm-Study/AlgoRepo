/**
 * 접근 :
 *      1. DFS로 식을 하나씩 완성해 나간다.
 *      2. 그런데 현재 쌓아온 식이 이미 앞에서 해본 경우라면 제낀다.
 *          => 이미 해본 경우는 set에 기록
 *          => 현재 하고 있는 진행의 방문기록은 비트마스킹 기록
 */

package week8.num5연산자끼워넣기3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static ArrayList<Integer> numbers;
    static int[] ops;
    static int fullBits;
    static int max = -2100000000, min = 2100000000;
    static HashSet<String> hs = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        numbers = new ArrayList<>();
        ops = new int[N - 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            numbers.add(Integer.parseInt(st.nextToken()));
        }
        int cnt = 0;
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i< 4; i++) {
            int num =  Integer.parseInt(st.nextToken());
            for(int j = 0; j < num; j++) ops[cnt++] = i;
        }

        fullBits = (1 << (N - 1)) - 1;

        dfs("", 0);

        System.out.println(max);
        System.out.println(min);

    }

    static void dfs(String str, int bits) {
        if(Integer.bitCount(bits) == N - 1) {
            calculate(str);
            return;
        }
        if(hs.contains(str)) return;
        hs.add(str);

        for(int i = 0; i < N -1; i++) {
            if((bits & (1 << i)) == 0) {
                String nextStr = str.concat(Integer.toString(ops[i]));
                dfs(nextStr, bits | (1 << i));
            }
        }
    }

    static void calculate(String str) {
        ArrayList<Integer> calOps = new ArrayList<>();
        ArrayList<Integer> calNumbers = new ArrayList<>(numbers);
        for(int i = 0; i < str.length(); i++) {
            calOps.add((Character.getNumericValue(str.charAt(i))));
        }
        while(!calOps.isEmpty()) {
            int idx = 0;
            for(int i = 0; i < calOps.size(); i++) {
                if(calOps.get(i) >= 2) {
                    idx = i;
                    break;
                }
            }
            int after;
            switch (calOps.get(idx)) {
                case 0 :
                    after = calNumbers.get(idx) + calNumbers.get(idx + 1);
                    calNumbers.set(idx, after);
                    calNumbers.remove(idx + 1);
                    calOps.remove(idx);
                    break;
                case 1 :
                    after = calNumbers.get(idx) - calNumbers.get(idx + 1);
                    calNumbers.set(idx, after);
                    calNumbers.remove(idx + 1);
                    calOps.remove(idx);
                    break;
                case 2 :
                    after = calNumbers.get(idx) * calNumbers.get(idx + 1);
                    calNumbers.set(idx, after);
                    calNumbers.remove(idx + 1);
                    calOps.remove(idx);
                    break;
                case 3 :
                    after = calNumbers.get(idx) / calNumbers.get(idx + 1);
                    calNumbers.set(idx, after);
                    calNumbers.remove(idx + 1);
                    calOps.remove(idx);
                    break;
            }
        }

        int result = calNumbers.get(0);
        max = Math.max(max, result);
        min = Math.min(min, result);
    }
}
