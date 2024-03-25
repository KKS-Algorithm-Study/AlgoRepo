import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main {
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String sticks = br.readLine();
        ArrayDeque<Character> stack = new ArrayDeque<>();
        int answer = 0;
        char prev = '(';

        for(int i=0 ; i<sticks.length() ; i++){
            char ch = sticks.charAt(i);
            if(ch == '('){
                stack.add(ch);
            }
            else{
                stack.pollLast();
                if(prev == '('){
                    answer += stack.size();
                }else{
                    answer++;
                }
            }

            prev = ch;
        }

        System.out.println(answer);
    }    
}
