import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main{
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int k = Integer.parseInt(br.readLine());

        int[][] candidates = new int[101][2];

        StringTokenizer stk = new StringTokenizer(br.readLine());

        int cnt = 0;
        for(int i=1 ; i<=k ; i++){
            int number = Integer.parseInt(stk.nextToken());

            if(candidates[number][1] == 0){
                cnt++;
                candidates[number][1] = i;
            }
            candidates[number][0]++;
            
            if(cnt <= n || candidates[number][0] > 1)continue;

            int idx = 1, min = 1001;

            for(int j=1 ; j<=100 ; j++){
                if(candidates[j][0] == 0 || candidates[j][1] == i || candidates[j][0] > min)continue;
                min = candidates[j][0];
                idx = j;
            }

            for(int j=1 ; j<=100 ; j++){
                if(candidates[j][0] == 0 || candidates[j][1] == i || min < candidates[j][0])continue;
                if(candidates[idx][1] <= candidates[j][1])continue;
                idx = j;
            }
            
            candidates[idx][0] = 0;
            candidates[idx][1] = 0;
        }


        StringBuilder answer = new StringBuilder();
        for(int i=1 ; i<=100 ; i++){
            if(candidates[i][0] == 0)continue;
            answer.append(i).append(" ");
        }
        System.out.println(answer);
    }
}