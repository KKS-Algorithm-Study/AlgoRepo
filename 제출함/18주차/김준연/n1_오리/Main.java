package week18.n1_오리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();

        int result = 0;
        int[] cnt = new int[5];
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            boolean errored = false;
            switch (c) {
                case 'q' :
                    if(cnt[4] > 0) {
                        cnt[4]--;
                        cnt[0]++;
                    }
                    else cnt[0]++;
                    break;
                case 'u' :
                    if(cnt[0] > 0) {
                        cnt[0]--;
                        cnt[1]++;
                    }
                    else errored = true;
                    break;
                case 'a' :
                    if(cnt[1] > 0) {
                        cnt[1]--;
                        cnt[2]++;
                    }
                    else errored = true;
                    break;
                case 'c' :
                    if(cnt[2] > 0) {
                        cnt[2]--;
                        cnt[3]++;
                    }
                    else errored = true;
                    break;
                case 'k' :
                    if(cnt[3] > 0) {
                        cnt[3]--;
                        cnt[4]++;
                    }
                    else errored = true;
                    break;
            }
            if(errored) {
                System.out.println(-1);
                return;
            }
        }
        if(cnt[0] > 0 || cnt[1] > 0 || cnt[2] > 0 || cnt[3] > 0) cnt[4] = -1;

        System.out.println(cnt[4]);

    }
}
