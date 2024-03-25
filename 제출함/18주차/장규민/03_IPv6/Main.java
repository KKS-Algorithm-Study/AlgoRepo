import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String[] str = br.readLine().split(":");

        StringBuilder answer = new StringBuilder();

        int cnt = 0;
        for(int i=0 ; i<str.length ; i++){
            if(str[i].length() == 0)
                cnt++;
        }
        
        boolean group = false;

        for(int i=0 ; i<str.length ; i++){
            if(str[i].length() != 0){
                answer.append(lPadString(str[i])).append(":");
                continue;
            }

            if(group)
                continue;

            group = true;
            for(int j=0 ; j < (8 - str.length + cnt) ; j++){
                answer.append("0000:");
            }
        }

        if(!group){
            for(int j=0 ; j<(8 - str.length + cnt) ; j++){
                answer.append("0000:");
            }
        }

        answer.deleteCharAt(answer.length() - 1);

        System.out.println(answer);
    }

    private static String lPadString(String num){
        StringBuilder ret = new StringBuilder();
        
        for(int i=0 ; i<(4 - num.length()) ; i++){
            ret.append("0");
        }
        ret.append(num);

        return ret.toString();
    }
}