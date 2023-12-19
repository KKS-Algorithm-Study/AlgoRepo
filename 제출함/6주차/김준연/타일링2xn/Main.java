package week6.타일링2xn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n =  Integer.parseInt(st.nextToken());

        int[] tile = new int[n];

        tile[0] = 1;
        if(n > 1) tile[1] = 2;

        for(int i = 2; i < n; i++) {
            tile[i] = (tile[i-1] + tile[i-2]) % 10007;
        }
        System.out.println(tile[n-1]);
    }
}

