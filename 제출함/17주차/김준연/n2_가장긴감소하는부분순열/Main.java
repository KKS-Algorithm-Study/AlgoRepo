package week17.n2_가장긴감소하는부분순열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, idx = 1;
    static int[] arr;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        arr = new int[N];

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        arr[0] = n;

        for(int i = 1; i < N; i++) {
            n = Integer.parseInt(st.nextToken());

            if(n < arr[idx-1]) arr[idx++] = n;
            else arr[bs(n)] = n;

        }

        System.out.println(idx);
    }

    static int bs(int target) {
        int s = 0;
        int e = idx - 1;
        int mid = (s + e) / 2;
        int answer = mid;
        while(s < e) {
            mid = (s + e) / 2;
            if(arr[mid] <= target) {
                answer = mid;
                e = mid;
            }
            else {
                s = mid + 1;
                answer = s;
            }
        }

        return answer;
    }

}
