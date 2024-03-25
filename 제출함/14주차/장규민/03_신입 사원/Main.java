import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine());

        StringBuilder answer = new StringBuilder();

        while(t-- > 0){
            int n = Integer.parseInt(br.readLine());
            List<int[]> employees = new ArrayList<>();

            for(int i=0 ; i<n ; i++){
                StringTokenizer stk = new StringTokenizer(br.readLine());
                int document = Integer.parseInt(stk.nextToken()), interview = Integer.parseInt(stk.nextToken());

                employees.add(new int[]{document, interview});
            }

            Collections.sort(employees, (o1, o2) -> o1[0] - o2[0]);

            int prev = employees.get(0)[1];
            int count = 0;

            for(int i=0 ; i <n ; i++){
                if(prev >= employees.get(i)[1])
                    count++;

                prev = Math.min(prev, employees.get(i)[1]);
            }

            answer.append(count).append("\n");
        }

        System.out.println(answer);
    }    
}
