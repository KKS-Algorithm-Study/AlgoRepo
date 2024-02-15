package week14.n3_신입사원;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());

        for(int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int[] arr = new int[n+1];
            int[] origin = new int[n+1];

            for(int j = 0; j < n; j++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                origin[a] = b;
            }

            int min = n + 1;
            for(int j = 1; j < n + 1; j++) {
                if(origin[j] > min) arr[j] = min;
                else {
                    min = origin[j];
                    arr[j] = min;
                }
            }

            int result = 1;
            for(int j = 2; j < n + 1; j++) {
                if(arr[j-1] > origin[j]) result++;
            }

            System.out.println(result);
        }
    }
}
