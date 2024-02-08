/**
 *  접근 :
 *      1. 백트래킹
 *      2. 나와 돈이 같거나 더 많은데, 번호가 같거나 더 큰 경우가 있으면 현재 루트는 폐쇄
 *          => 순회
 *      3. 0부터가 아니라 9부터 채워야함
 *
 *  시간복잡도 :
 *
 *
 */

package week13.n8_방번호;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static ArrayList<Product> products = new ArrayList<Product>();
    static BigInteger[] visited;
    static BigInteger result = BigInteger.ZERO;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());


        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            products.add(new Product(i, Integer.parseInt(st.nextToken())));
        }

        products.sort(Collections.reverseOrder());

        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        visited = new BigInteger[M + 1];
        Arrays.fill(visited, BigInteger.ZERO);

        visited[M] = BigInteger.valueOf(-1);

        dfs(M, BigInteger.ZERO);

        System.out.println(result);

    }

    static void dfs(int remain, BigInteger number) {

        if(visited[remain].compareTo(number) >= 0) return;

        for(int i = remain + 1; i <= M; i++) {
            if(visited[remain].compareTo(number) == 1) return;
        }
        visited[remain] = number;

        if(result.compareTo(number) == -1) {
            result = number;
        }

        for(int i = 0; i < N; i++) {
            if((number.compareTo(BigInteger.ZERO) == 0) && products.get(i).number == 0) continue;
            if(products.get(i).cost <= remain) {
                BigInteger next = number.multiply(BigInteger.valueOf(10)).add(BigInteger.valueOf(products.get(i).number));
                dfs(remain - products.get(i).cost, next);
            }
        }
    }
}

class Product implements Comparable<Product>{
    int number;
    int cost;

    public Product(int number, int cost) {
        this.number = number;
        this.cost = cost;
    }

    @Override
    public int compareTo(Product o) {
        return Integer.compare(this.number, o.number);
    }
}