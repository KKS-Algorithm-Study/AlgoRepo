import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] split = br.readLine().split(" ");
        int n, m;

        n = Integer.parseInt(split[0]);
        m = Integer.parseInt(split[1]);

        int[] jewelry = new int[m];

        for(int i=0 ; i<m ; i++){
            jewelry[i] = Integer.parseInt(br.readLine());
        }

        int left = 1, right = 1000000000, answer = 1000000000;

        while(left <= right) {
            // 가장 많이 가진 보석 수
            int mid = (left + right) / 2;

            int cnt = 0;

            for(int i=0 ; i<m ; i++){
                // 딱 맞으면 몫만 더해주고
                if(jewelry[i] % mid == 0){
                    cnt += jewelry[i] / mid;
                }else{
                    // 나머지가 0이 아니라면 mid보다 커지니까 다른 애한테 넘겨줘야한다.
                    cnt += ((jewelry[i] / mid) + 1);
                }
            }
            
            if(cnt > n){
                left = mid + 1;
            }else{
                answer = mid;
                right = mid - 1;
            }
        }

        System.out.println(answer);
    }
}