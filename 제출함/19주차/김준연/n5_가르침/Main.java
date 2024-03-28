package week19.n5_가르침;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {

    static ArrayList<HashSet<Integer>> arr;
    static HashSet<Integer> all;
    static ArrayList<Integer> all_arr;
    static int K;
    static int max = 0;
    static HashSet<Integer> visited = new HashSet<>();

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        if(K < 5) {
            System.out.println(0);
            return;
        }
        else if(K == 26) {
            System.out.println(N);
            return;
        }

        K = K - 5;

        HashSet<Character> hs = new HashSet<>();
        hs.add('a');
        hs.add('n');
        hs.add('t');
        hs.add('i');
        hs.add('c');

        arr = new ArrayList<>();
        all = new HashSet<>();

        while(N-- > 0) {
            String str = br.readLine();
            HashSet<Integer> temp = new HashSet<>();
            for(char c : str.toCharArray()) {
                if(hs.contains(c)) continue;
                temp.add((int) c - 96);
                all.add((int) c - 96);
            }
            arr.add(temp);
        }

        all_arr = new ArrayList<>(all);

        dfs(0, 0);

        System.out.println(max);

    }

    static void dfs(int bits, int point) {
        if(Integer.bitCount(bits) >= K || Integer.bitCount(bits) >= all.size()) {
            max = Math.max(max, check(bits));
            return;
        }

        for(int i = point; i < all_arr.size(); i++) {
            int n  = all_arr.get(i);
            if((bits & (1 << n)) == 0 && !visited.contains(bits | (1 << n))) {
                visited.add(bits | (1 << n));
                dfs(bits | (1 << n), i + 1);
            }
        }

    }

    static int check(int bits) {
        int correct = 0;
        Loop:
        for(HashSet<Integer> now : arr) {
            for(int n : now) {
                if((bits & (1 << n)) == 0) continue Loop;
            }
            correct++;
        }

        return correct;
    }
}
