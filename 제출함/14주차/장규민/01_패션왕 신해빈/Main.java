import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine());

        StringBuilder answer = new StringBuilder();

        while(t-- > 0){
            int n = Integer.parseInt(br.readLine());

            Map<String, Integer> clothes = new HashMap<>();
            for(int i=0 ; i<n ; i++){
                StringTokenizer stk = new StringTokenizer(br.readLine());
                String name = stk.nextToken(), type = stk.nextToken();

                clothes.put(type, clothes.getOrDefault(type, 1) + 1);
            }

            int count = 1;
            for(Integer value : clothes.values()){
                count *= value;
            }

            answer.append(count - 1).append("\n");
        }
        System.out.println(answer);
    }
}