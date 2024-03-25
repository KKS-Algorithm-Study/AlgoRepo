import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine());

        StringBuilder answer = new StringBuilder();
        while(t-- > 0){
            int n = Integer.parseInt(br.readLine());
            PriorityQueue<Integer> asc = new PriorityQueue<>();
            PriorityQueue<Integer> desc = new PriorityQueue<>((o1, o2) -> o2 - o1);
            StringBuilder sb = new StringBuilder();

            /*
             * asc: 9 오른쪽
             * desc: 8, 7 왼쪽
             */
            for(int s=0 ; s <= n / 10 ; s++){
                String[] numbers = br.readLine().split(" ");

                for(int i = 0 ; i < numbers.length ; i++){
                    int number = Integer.parseInt(numbers[i]);

                    asc.add(number);

                    if(asc.size() > desc.size())
                        desc.add(asc.poll());

                    else if(desc.peek() > asc.peek()){
                        int a = asc.poll(), d = desc.poll();
                        desc.add(a);
                        asc.add(d);
                    }

                    if(i % 2 == 0){
                        sb.append(desc.peek()).append(" ");
                    }
                }
            }
            answer.append((n + 1) / 2).append("\n").append(sb).append("\n");
        }
        System.out.println(answer);
    }
}
