/**
 * 접근 :
 *      1. 그림을 그려봄
 *      2. N, M을 이루는 원소는 앞에 a가 붙은 N-1, M과 앞에 z가 붙은 N, M-1로 이루어 짐
 *      3. 앞에 a가 붙은 원소와 z가 붙은 원소의 비율은 N : M 임
 *      4. 따라서 N, M의 중복 조합 개수를 구하고, K가 첫문자 a쪽에 속하는지 z쪽에 속하는지 구분함.
 *          => a에 속하면 문자 a를 기록하고 N-1로 향하면서 K는 그대로둠
 *          => z에 속하면 문자 z를 기록하고 M-1로 향하면서 K는 a시작 원소 개수만큼 뺌
 *      5. 위 과정을 끝까지 반복한 결과를 출력
 *
 * 문제점 :
 *      1. /by zero 에러 발생
 *          => 중복 조합 공식에 따라 팩토리얼 연산시 기본 자료형으로 담을 수 없는 범위가 사용되서 오버플로나는 문제
 *          => 오버플로 날만한 변수들을 BigInteger 클래스로 선언하여 해결
 */

package week6.사전;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static BigInteger K;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = BigInteger.valueOf(Integer.parseInt(st.nextToken()));

        StringBuilder sb = new StringBuilder();

        BigInteger allCnt = combination(N+M, N);
        if(allCnt.compareTo(K) == -1) {
            System.out.println(-1);
            return;
        }

        while(N > 0 && M > 0) {
            allCnt = combination(N+M, N); // ex) 4! / (2! + 2!) 중복 조합 경우의 수
            BigInteger standard = getStandard(allCnt, N, M);    //a시작점과 z시작점을 나누는 기준
            if(standard.compareTo(K) >= 0) {    //사전번호가 a로시작하는 범위라면?
                sb.append("a");
                N--;
            }
            else {      //사전번호가 z로시작하는 범위라면?
                sb.append("z");
                K = K.subtract(standard);       //a시작하는 부분의 숫자를 빼서, 인덱스를 이후 연산에 맞춤
                M--;
            }
        }
        if(N == 0) {
            for(long i = M; i > 0; i--) sb.append("z");
        }
        if(M == 0) {
            for(long i = N; i > 0; i--) sb.append("a");
        }

        System.out.println(sb);
    }

    static BigInteger factorial(int n) {
        if (n == 0 || n == 1) return BigInteger.ONE;
        return BigInteger.valueOf(n).multiply(factorial(n - 1));
    }

    static BigInteger combination(int n, int r) {
        BigInteger numerator = factorial(n);
        BigInteger denominator = factorial(r).multiply(factorial(n - r));

        return numerator.divide(denominator);
    }

    static BigInteger getStandard(BigInteger allCnt, int N, int M) {
            BigInteger NBig = BigInteger.valueOf(N);
            BigInteger MBig = BigInteger.valueOf(M);
            BigInteger sum = NBig.add(MBig);
            BigInteger result = allCnt.multiply(NBig);
            result = result.divide(sum);
            return result;
    }

}
