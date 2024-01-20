/**
 * !!답안참고함
 *
 * 접근 :
 *      1. 양 끝에서부터 하나씩 비교해가기
 */

package week10.n4_팰린드롬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int[] arr = new int[N+1];

        st = new StringTokenizer(br.readLine());

        StringBuilder sb = new StringBuilder();


        for(int i = 1; i < N+1; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if(a == b) sb.append("1\n");
            else {
                while(true) {
                    if(arr[a] != arr[b]) {
                        sb.append("0\n");
                        break;
                    }
                    a++;
                    b--;
                    if(a == b) {
                        sb.append("1\n");
                        break;
                    }
                    if(Math.abs(b - a) == 1) {
                        if(arr[a] == arr[b]) {
                            sb.append("1\n");
                        }
                        else {
                            sb.append("0\n");
                        }
                        break;
                    }
                }
            }
        }

        System.out.println(sb.toString());
    }
}
