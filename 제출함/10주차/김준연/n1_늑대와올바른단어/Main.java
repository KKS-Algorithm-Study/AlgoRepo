package week10.n1_늑대와올바른단어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        String str = st.nextToken();

        char[] table = {'w', 'o', 'l', 'f'};
        int[] cnt = new int[4];
        int idx = 0;
        int result = 1;

        for(int i = 0; i < str.length(); i++) {
            char now = str.charAt(i);
            if(table[idx] == now) {     //나와야할 문자와 현재 문자가 같으면 카운팅하고 넘어감
                cnt[idx]++;
            }
            else {
                idx++;
                if(idx == 4) {      //인덱스가 4에 닿으면
                    for(int j = 0; j < 3; j++) {    //여태까지 wolf가 모두 같은 수만큼 나왔는지 판별
                        if(cnt[j] != cnt[j+1]) {
                            result = 0;
                            break;
                        }
                    }
                    idx = 0;        //정상이면 인덱스 0 (w)으로 돌림
                }
                if(table[idx] == now) {     //다음 인덱스의 문자가 현재 문자와 같으면 카운팅
                    cnt[idx]++;
                }
                else {      //이전 문자도 아니고, 현재 문자도 아니다? 실패
                    result = 0;
                    break;
                }
            }
        }

        for(int j = 0; j < 3; j++) {        //위 연산이 끝나고 다시 한번 wolf가 같은 수만큼 나왔는지 판별
            if(cnt[j] != cnt[j+1]) {
                result = 0;
            }
        }

        System.out.println(result);
    }
}
