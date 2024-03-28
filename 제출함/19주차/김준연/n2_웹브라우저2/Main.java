package week19.n2_웹브라우저2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        Stack<Integer> back = new Stack<>();
        Stack<Integer> front = new Stack<>();
        int now = 0;

        while(Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            char c = st.nextToken().charAt(0);

            switch (c) {
                case 'A' :
                    front.clear();
                    if(now != 0) back.push(now);
                    int next = Integer.parseInt(st.nextToken());
                    now = next;
                    break;
                case 'B' :
                    if(back.isEmpty()) break;
                    front.push(now);
                    now = back.pop();
                    break;
                case 'F' :
                    if(front.isEmpty()) break;
                    back.push(now);
                    now = front.pop();
                    break;
                case 'C' :
                    Stack<Integer> temp = new Stack<>();
                    while(!back.isEmpty()) {
                        int k = back.pop();
                        if(!temp.isEmpty()) {
                            if(temp.peek() == k) continue;
                        }
                        temp.push(k);
                    }
                    while(!temp.isEmpty()) back.push(temp.pop());
                    break;
            }
        }

        System.out.println(now);
        if(back.isEmpty()) System.out.print(-1);
        while(!back.isEmpty()) System.out.print(back.pop() + " ");
        System.out.println("");
        if(front.isEmpty()) System.out.print(-1);
        while(!front.isEmpty()) System.out.print(front.pop() + " ");
    }
}
