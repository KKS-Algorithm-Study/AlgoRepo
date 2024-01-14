package week9.n3_주지수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[][] country;
    static int[][] sumtable;
    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        country = new int[N + 1][M + 1];
        sumtable = new int[N + 1][M + 1];

        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j < M + 1; j++) {
                country[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        makeSum();

        st = new StringTokenizer(br.readLine());

        int K = Integer.parseInt(st.nextToken());


        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            getSum(x1, x2, y1, y2);
        }

    }

    static void getSum(int x1, int x2, int y1, int y2) {
        int whole = sumtable[x2][y2];
        int minus_1 = sumtable[x2][y1 - 1];
        int minus_2 = sumtable[x1 - 1][y2];
        int plus = sumtable[x1 - 1][y1 - 1];

        System.out.println(whole - minus_1 - minus_2 + plus);
    }

    static void makeSum() {
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < M + 1; j++) {
                sumtable[i][j] = country[i][j] + sumtable[i - 1][j] + sumtable[i][j - 1] - sumtable[i - 1][j - 1];
            }
        }
    }

}
