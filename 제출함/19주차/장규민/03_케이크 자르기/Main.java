import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main{

    private static int n, q, l, min;
    private static int[] cuttingPoint;

    // 10 10 15 20 5 10
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String[] input = br.readLine().split(" ");
        
        n = Integer.parseInt(input[0]);
        q = Integer.parseInt(input[1]);
        l = Integer.parseInt(input[2]);

        cuttingPoint = new int[q + 2];

        cuttingPoint[0] = 0;
        for(int i=1 ; i<=q ; i++){
            cuttingPoint[i] = Integer.parseInt(br.readLine());
        }
        cuttingPoint[q+1] = l;

        StringBuilder answer = new StringBuilder();
        while(n-- > 0){
            int count = Integer.parseInt(br.readLine());
            answer.append(binarySearch(count)).append("\n");
        }
        System.out.println(answer);

    }

    private static int binarySearch(int count){
        int left = min, right = l;

        int answer = 0;

        while(left <= right){
            int mid = (left + right) / 2;

            int total = 0, prev = 0;
            
            for(int i=0 ; i<=q+1 ; i++){
                if(cuttingPoint[i] - prev < mid)continue;
                total++;
                prev = cuttingPoint[i];
            }

            if(total > count){
                answer = Math.max(answer, mid);
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }

        return answer;
    }
}