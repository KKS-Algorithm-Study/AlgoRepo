import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine());

        StringBuilder answer = new StringBuilder();

        while(t-- > 0){
            String command = br.readLine();
            int n = Integer.parseInt(br.readLine());
            String input = br.readLine();
            String[] arr = input.substring(1, input.length() - 1).split(",");
            
            String str = solution(command, arr, n);

            if(str.equals("error\n")){
                answer.append(str);
                continue;
            }

            answer.append("[").append(str).append("]\n");
        }

        System.out.println(answer);
    }

    private static String createString(LinkedList<Integer> que, int n){
        StringBuilder ret = new StringBuilder();
        while(!que.isEmpty()){
            ret.append(que.poll()).append(",");

            if(que.isEmpty())
                ret.deleteCharAt(ret.length() - 1);
        }

        return ret.toString();
    }

    private static LinkedList<Integer> makeQueue(String[] arr, int n){
        LinkedList<Integer> que = new LinkedList<>();

        for(int i=0 ; i<n ; i++){
            que.add(Integer.parseInt(arr[i]));
        }

        return que;
    }

    private static String solution(String command, String[] arr, int n){
        LinkedList<Integer> que = makeQueue(arr, n);
        int Dcount = 0;
        boolean reversed = false;

        for(int i=0 ; i<command.length() ; i++){
            if(command.charAt(i) == 'R'){
                reversed = !reversed;
                continue;
            }

            Dcount++;
            if(Dcount > n){
                return "error\n";
            }

            if(!reversed){
                que.poll();
                continue;
            }
            que.pollLast();
        }

        if(reversed)
            Collections.reverse(que);

        return createString(que, n);
    }
}
