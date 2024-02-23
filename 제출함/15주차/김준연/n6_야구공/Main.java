/**
 * 접근 :
 *      1. 모든 타자의 순서에 대한 경우를 하나씩 구한다.
 *          브루트포스
 *      2. 그 순서로 모든 이닝 게임을 진행하고 결과를 구한다.
 *          반복문 내 비트마스킹
 *      3. 그 결과들의 최대값을 출력한다.
 *
 * 문제점 :
 *      1. 문제 이해를 잘못함
 *          이닝마다 선수들의 순서가 바뀔 수 있는 줄 알아서 오래걸림
 *          문제를 잘읽자
 */

package week15.n6_야구공;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int[][] taja;
    static int max = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        taja = new int[N][9];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 9; j++) {
                taja[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int result = 0;

        getMax(new int[]{-1, -1, -1, -1, -1, -1, -1, -1}, 0);

        System.out.println(max);

    }

    static void getMax(int[] result, int bits) {

        int idx = Integer.bitCount(bits);

        if(idx >= 8) {
            int score = do_ining(result);
            if(max < score) {
                max = score;
            }
            return;
        }


        for(int i = 1; i < 9; i++) {
            if((bits & (1 << i)) == 0) {
                result[idx] = i;
                getMax(result, bits | (1 << i));
                result[idx] = -1;
            }
        }


    }

    static int do_ining(int[] result) {

        int score = 0;
        int taja_num = 0;

        for(int i = 0; i < N; i++) {
            int out = 0;
            int ground_bits = 0;

            while(out < 3) {

                int hit;
                if(taja_num == 3) hit = taja[i][0];
                else if(taja_num < 3) hit = taja[i][result[taja_num]];
                else hit = taja[i][result[taja_num - 1]];

                switch (hit) {
                    case 0 :
                        out++;
                        break;
                    case 1 :
                        ground_bits = (ground_bits << 1) | (1 << 1);    //1칸 씩 밀고, 1루 채우기
                        break;
                    case 2 :
                        ground_bits = (ground_bits << 2) | (1 << 2);    //2칸 씩 밀고, 2루 채우기
                        break;
                    case 3 :
                        ground_bits = (ground_bits << 3) | (1 << 3);    //3칸 씩 밀고, 3루 채우기
                        break;
                    case 4 :
                        ground_bits = (ground_bits << 4) | (1 << 4);    //4칸 씩 밀고, 4번째 채우기(+1)
                        break;
                }
                int next = 0b1111;
                next = ground_bits & next;  // 0,1,2,3루 정보만 저장
                score += Integer.bitCount(ground_bits) - Integer.bitCount(next);    //들어온거 포함 전체 주자 수 - 그라운드의 주자 수
                ground_bits = next;     //그라운드의 남은 주자만 기록
                taja_num++;
                if(taja_num >= 9) taja_num = 0;
            }

        }

        return score;
    }
}
