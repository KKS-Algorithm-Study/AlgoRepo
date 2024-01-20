/**
 * 접근 :
 *      1. 존재할 수 없는 4세트가 존재하는가?
 *      <federer의 경우>
 *      1. 일단 federer가 있는지 먼저 본다.
 *      2. federer가 있으면 federer가 진 세트가 있는지 검수
 *      3. 검수 결과 문제가있으면 체크하고 정상적인 경우 일반 연산으로 돌아감
 *      <일반연산>
 *      1. 1세트 경기가 6점 이상에 2점차이거나 7:6 꼴인가?
 *      2. 2세트 경기가 6점 이상에 2점차이거나 7:6 꼴인가?
 *      3. 승패가 갈렸는데, 다음 세트가 존재하는가?
 *      4. 3세트 경기가 6점 이상에 2점차이로 끝났는가?
 */


package week10.n5_테니스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static String playerA, playerB;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        playerA = st.nextToken();
        playerB = st.nextToken();

        st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String[] sets = new String[5];
            int cnt = 0;
            int a_score = 0;
            int b_score = 0;
            while(st.hasMoreTokens()) {
                sets[cnt] = st.nextToken();
                cnt++;
            }
            int[][] scores = new int[cnt][2];
            for(int j = 0; j < cnt; j++) {
                String[] parse = sets[j].split(":");
                scores[j][0] = Integer.parseInt(parse[0]);
                scores[j][1] = Integer.parseInt(parse[1]);
            }
            //---여기까지 한 경기의 데이터 input과 int배열 파싱


            if(cnt >= 4 || cnt <= 1) {
                System.out.println("ne");
                continue;
            }

            if(isFedererCheat(scores)) {
                System.out.println("ne");
                continue;
            }
            if(getOneTwoCheat(scores[0])) {
                System.out.println("ne");
                continue;
            }
            if(getWinner(scores[0])) a_score++;
            else b_score++;
            if(getOneTwoCheat(scores[1])) {
                System.out.println("ne");
                continue;
            }
            if(getWinner(scores[1])) a_score++;
            else b_score++;

            if(cnt == 2 && a_score == b_score) {
                System.out.println("ne");
                continue;
            }
            if((a_score == 2 || b_score == 2) && cnt > 2) {
                System.out.println("ne");
                continue;
            }
            if(cnt > 2  && getThreeCheat(scores[2])) {
                System.out.println("ne");
                continue;
            }
            System.out.println("da");




        }
    }

    static boolean isFedererCheat(int[][] scores) {
        if(playerA.equals("federer")) {
            for(int i = 0; i < scores.length; i++) {
                if(scores[i][0] < scores[i][1]) return true;
            }
        }
        else if(playerB.equals("federer")) {
            for(int i = 0; i < scores.length; i++) {
                if(scores[i][0] > scores[i][1]) return true;
            }
        }
        return false;
    }

    static boolean getOneTwoCheat(int[] score) {
        int a = score[0];
        int b = score[1];
        if(a == b) return true;
        if(a > 7 || b > 7) return true;
        if(a == 7) {
            if(b == 5 || b == 6) return false;
            else return true;
        }
        if(a == 6) {
            if(b <= 4) return false;
            if(b == 7) return false;
            else return true;
        }
        if(b == 7) {
            if(a == 5) return false;
            else return true;
        }
        if(b == 6) {
            if(a <= 4) return false;
            return true;
        }

        return true;
    }

    static boolean getThreeCheat(int[] score) {
        int a = score[0];
        int b = score[1];

        if(a == 6) {
            if(a - b >= 2) return false;
        }
        if(b == 6) {
            if(b - a >= 2) return false;
        }
        if(a > 6) {
            if(a - b == 2) return false;
        }
        if(b > 6)  {
            if(b - a == 2) return false;
        }
        return true;
    }

    static boolean getWinner(int[] score) {
        return score[0] > score[1];
    }
}
