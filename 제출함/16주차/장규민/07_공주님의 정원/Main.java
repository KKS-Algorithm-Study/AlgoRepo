import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main{
    static class Flower implements Comparable<Flower>{
        int start, end;
        int period;

        public Flower(int startMonth, int startDay, int endMonth, int endDay){
            this.start = startMonth * 32 + startDay;
            this.end = endMonth * 32 + endDay;
            this.period = this.end - this.start;
        }

        @Override
        public int compareTo(Flower o){
            if(this.start != o.start)return this.start - o.start;
            return o.end - this.end;
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        List<Flower> flowers = new ArrayList<>();
        
        for(int i=0 ; i<n ; i++){
            StringTokenizer stk = new StringTokenizer(br.readLine());
            int sm = Integer.parseInt(stk.nextToken());
            int sd = Integer.parseInt(stk.nextToken());
            int em = Integer.parseInt(stk.nextToken());
            int ed = Integer.parseInt(stk.nextToken());

            flowers.add(new Flower(sm, sd, em, ed));
        }

        Collections.sort(flowers);

        int end = 12 * 32 + 1;
        int start = 3 * 32 + 1;

        int answer = 0, idx = 0;

        while(start < end){
            boolean flag = false;
            int max = 0;
            
            for(int i=idx ; i<n ; i++){
                Flower next = flowers.get(i);

                if(next.start > start)
                    break;

                if(max < next.end){
                    flag = true;
                    idx = i + 1;
                    max = next.end;
                }
            }


            if(flag){
                answer++;
                start = max;
                continue;
            }

            break;
        }

        System.out.println(start < end ? 0 : answer);
    }
}