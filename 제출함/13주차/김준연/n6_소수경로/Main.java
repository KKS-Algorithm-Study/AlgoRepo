/**
 * 접근 :
 *      1. 시작된 소수에서 모든 자릿수를 바꿔서 소수가 되는 수를 찾아옴.
 *      2. 이를 너비우선탐색 알고리즘으로 q에 넣고 내가 찾는 소수가 나올 때 까지 반복함
 *      3. 나오면 진행한 횟수를 출력, q가 비면 Impossible 출력
 *      
 */

package week13.n6_소수경로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static boolean[] table;
    static HashSet<Integer> hs = new HashSet<>();

    public static void main(String[] args) throws IOException {
        int M = 10000;
        table = new boolean[M + 1];

        for(int i = 0 ; i < M + 1; i++) table[i] = true;

        table[0] = false;
        table[1] = false;

        for(int i = 2; i <= Math.sqrt(M); i++) {
            if(table[i]) {
                for(int j = i*i; j <= M; j += i) table[j] = false;
            }
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());

        Loop1 :
        for(int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            hs.clear();
            hs.add(a);
            if(a == b) {
                System.out.println(0);
                continue;
            }

            Queue<Prime> q = new LinkedList<>();
            q.add(new Prime(a, 0));

            while(!q.isEmpty()) {
                Prime now = q.poll();

                for(int n : getNext(now.number)) {
                    if(n == b) {
                        System.out.println(now.count + 1);
                        continue Loop1;
                    }
                    q.add(new Prime(n, now.count + 1));
                }
            }

            System.out.println("Impossible");
        }
    }

    static ArrayList<Integer> getNext(int n) {
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            int temp = n - (n % 10);    //1의 자릿수 변경
            if(!hs.contains(temp + i) && table[temp + i]) {
                hs.add(temp + i);
                result.add(temp + i);
            }

            temp = n - (n % 100) + (n % 10);    //10의 자릿수 변경
            if(!hs.contains(temp + (i * 10)) && table[temp + (i * 10)]) {
                hs.add(temp + (i * 10));
                result.add(temp + (i * 10));
            }

            temp = n - (n % 1000) + (n % 100);    //100의 자릿수 변경
            if(!hs.contains(temp + (i * 100)) && table[temp + (i * 100)]) {
                hs.add(temp + (i * 100));
                result.add(temp + (i * 100));
            }

            temp = n % 1000;    //100의 자릿수 변경
            if(!hs.contains(temp + (i * 1000)) && table[temp + (i * 1000)] && i > 0) {
                hs.add(temp + (i * 1000));
                result.add(temp + (i * 1000));
            }
        }
        return result;
    }
}

class Prime {
    int number;
    int count;

    public Prime(int number, int count) {
        this.number = number;
        this.count = count;
    }
}