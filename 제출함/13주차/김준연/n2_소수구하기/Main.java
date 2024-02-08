package week13.n2_소수구하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());


        boolean[] table = new boolean[M + 1];

        for(int i = 0 ; i < M + 1; i++) table[i] = true;

        table[0] = false;
        table[1] = false;

        for(int i = 2; i <= Math.sqrt(M); i++) {
            if(table[i]) {
                for(int j = i*i; j <= M; j += i) table[j] = false;
            }
        }

        for(int i = N; i <= M; i++) {
            if(table[i]) System.out.println(i);
        }
    }
}
