import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main{
    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        int n, m;
        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        stk = new StringTokenizer(br.readLine());

        PriorityQueue<Long> pq = new PriorityQueue<>();

        for(int i=0 ; i<n ; i++){
            long num = Long.parseLong(stk.nextToken());
            pq.add(num);
        }

        while(m-- > 0){
            long num1 = pq.poll(), num2 = pq.poll();
            long total = num1 + num2;
            pq.add(total);
            pq.add(total);
        }

        long answer = 0;

        while(!pq.isEmpty()){
            answer += pq.poll();
        }

        System.out.println(answer);

    }
}