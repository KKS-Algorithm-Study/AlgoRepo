package week18.n3_IPv6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();

        String[] ip = str.split(":");

        int notBlank = 0;
        for(int i = 0; i < ip.length; i++) {
            if(!ip[i].isEmpty()) {
                notBlank++;
            }
        }

        if(ip.length == 0) ip = new String[]{""};
        if(str.charAt(str.length()-1) == ':' && str.charAt(str.length()-2) == ':') {
            String[] temp = ip;
            ip = new String[temp.length + 1];
            for(int i = 0; i < temp.length; i++) {
                ip[i] = temp[i];
            }
            ip[ip.length - 1] = "";
        }

        int cnt = 0;
        for(int i = 0; i < ip.length; i++) {
            String now = ip[i];
            if(!now.isEmpty() && now.length() < 4) {
                while(now.length() < 4) {
                    now = "0" + now;
                }
                System.out.print(now);
                cnt++;
            }
            else if(now.isEmpty()) {
                int k = 8 - (notBlank - cnt) - i;
                while(k-- > 0) {
                    if((notBlank - cnt) <= 0 && k == 0) {
                        System.out.print("0000");
                        continue;
                    }
                    System.out.print("0000:");
                }
                notBlank = 100;
                continue;
            }
            else {
                System.out.print(now);
                cnt++;
            }
            if(i == ip.length - 1) break;
            System.out.print(":");
        }
    }
}
