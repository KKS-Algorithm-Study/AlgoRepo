/**
 * 접근 :
 *      1. 가장 작은 1단계를 만들어서 가져온다.
 *      2. 그걸 level만큼 반복하는 3번붙이기 / 1번공백1번 붙이기 / 3번붙이기를 한다
 *      3. 그걸 가져와서 또 한다.
 *      4. 그걸 가져와서 또 한다.
 */

package week17.n4_별찍기_10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {


    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        StringBuilder[] answer = getStar(N);

        for(int i = 0; i < N; i++) {
            System.out.println(answer[i]);
        }
    }

    static StringBuilder[] getStar(int level) {
        StringBuilder[] answer = new StringBuilder[level];
        for(int i = 0; i < level; i++) answer[i] = new StringBuilder();

        if(level == 1) {
            answer[0].append("*");
            return answer;
        }

        StringBuilder[] before = getStar(level / 3);

        int idx = 0;

        for(int i = 0; i < level / 3; i++) {
            for(int j = 0; j < 3; j++) {
                answer[idx].append(before[i]);
            }
            idx++;
        }

        StringBuilder blank = new StringBuilder();
        for(int i = 0; i < level/3; i++) blank.append(" ");

        for(int i = 0; i < level / 3; i++) {

            for(int j = 0; j < 3; j++) {
                if(j == 1) {
                    answer[idx].append(blank);
                    continue;
                }
                answer[idx].append(before[i]);
            }
            idx++;
        }

        for(int i = 0; i < level / 3; i++) {
            for(int j = 0; j < 3; j++) {
                answer[idx].append(before[i]);
            }
            idx++;
        }

        return answer;

    }
}
