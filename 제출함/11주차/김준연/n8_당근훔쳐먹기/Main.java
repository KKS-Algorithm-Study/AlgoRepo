/**
 * 접근 :
 *      1. 맨 마지막 날까지 기다렷다가 도핑이 제일 쎈 당근을 먹는게 이득
 *      2. 그럼 마지막의 둘째날은? 마찬가지
 *      3. 도핑 쎈 순으로 정렬시키고 마지막날 기준으로 하나씩 먹기
 */

package week11.n8_당근훔쳐먹기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());

        ArrayList<Carrot> carrots = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int original = Integer.parseInt(st.nextToken());
            int doping = Integer.parseInt(st.nextToken());
            carrots.add(new Carrot(original, doping));
        }


        carrots.sort(Collections.reverseOrder());

        T--;
        long result = 0;
        for(Carrot carrot : carrots) {
            long now = carrot.original + (long) carrot.doping * T;
            result += now;
            T--;
        }

        System.out.println(result);
    }
}

class Carrot implements Comparable<Carrot>{
    int original;
    int doping;

    public Carrot(int original, int doping) {
        this.original = original;
        this.doping = doping;
    }

    @Override
    public int compareTo(Carrot o) {
        return Integer.compare(this.doping, o.doping);
    }
}