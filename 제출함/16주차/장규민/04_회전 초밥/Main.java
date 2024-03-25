import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());
        int n, d, k, c;
        
        n = Integer.parseInt(stk.nextToken());
        d = Integer.parseInt(stk.nextToken());
        k = Integer.parseInt(stk.nextToken());
        c = Integer.parseInt(stk.nextToken());
        
        int[] sushi = new int[n];
        for(int i=0 ; i<n ; i++){
            sushi[i] = Integer.parseInt(br.readLine());
        }

        int answer = 0;
        Map<Integer, Integer> type = new HashMap<>();
        for(int i=0 ; i<k ; i++){
            type.put(sushi[i], type.getOrDefault(sushi[i], 0) + 1);
        }

        answer = type.size();
        if(!type.containsKey(c))answer++;

        int left = 0, right = k;

        while(right <= n + k){
            type.put(sushi[left % n], type.get(sushi[left % n]) - 1);
            type.put(sushi[right % n], type.getOrDefault(sushi[right % n], 0) + 1);
            
            if(type.get(sushi[left % n]) == 0)type.remove(sushi[left % n]);

            int temp = type.size();
            if(!type.containsKey(c))temp++;
            
            answer = Math.max(answer, temp);

            right++;
            left++;
        }
        
        System.out.println(answer);
    }
}