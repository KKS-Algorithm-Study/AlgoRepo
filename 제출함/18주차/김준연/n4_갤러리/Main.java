package week18.n4_갤러리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int M, N;
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        int[][] stage = new int[M][N];

        for(int i = 0; i < M; i++) {
            String str = br.readLine();
            for(int j = 0; j < N; j++) {
                char c = str.charAt(j);
                if(c == 'X') {
                    stage[i][j] = 1;
                }
            }
        }

        int result = 0;

        int state = 0;  //0은 불가능, 1은 왼쪽(위쪽)벽 가능, 2는 오른쪽(아래쪽)벽 가능

        for(int i = 0; i < M - 1; i++) {
            state = 0;
            for(int j = 0; j < N; j++) {
                if(stage[i][j] == stage[i+1][j]) {
                    state = 0;
                }
                else if(stage[i][j] == 0 && stage[i+1][j] == 1) {
                    if(state == 1) {
                        result++;
                        state = 0;
                    }
                    else state = 1;
                }
                else if(stage[i][j] == 1 && stage[i+1][j] == 0) {
                    if(state == 2) {
                        result++;
                        state = 0;
                    }
                    else state = 2;
                }
            }
        }

        for(int i = 0; i < N - 1; i++) {
            state = 0;
            for(int j = 0; j < M; j++) {
                if(stage[j][i] == stage[j][i+1]) {
                    state = 0;
                }
                else if(stage[j][i] == 0 && stage[j][i+1] == 1) {
                    if(state == 1) {
                        result++;
                        state = 0;
                    }
                    else state = 1;
                }
                else if(stage[j][i] == 1 && stage[j][i+1] == 0) {
                    if(state == 2) {
                        result++;
                        state = 0;
                    }
                    else state = 2;
                }
            }
        }

        System.out.println(result);
    }
}
