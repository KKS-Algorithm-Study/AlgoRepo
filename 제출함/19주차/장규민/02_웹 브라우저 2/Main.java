import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

class Main{
    private static int n, m, curPage;
    private static LinkedList<Integer> back, front;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        back = new LinkedList<>();
        front = new LinkedList<>();

        while(m-- > 0){
            stk = new StringTokenizer(br.readLine());
            String command = stk.nextToken();

            if(command.equals("A")){
                access(Integer.parseInt(stk.nextToken()));
            }
            else if(command.equals("B")){
                backWard();
            }
            else if(command.equals("C")){
                compress();
            }
            else if(command.equals("F")){
                frontWard();
            }
        }

        StringBuilder answer = new StringBuilder();

        answer.append(curPage).append("\n");

        if(back.isEmpty())answer.append(-1);
        while(!back.isEmpty()){
            answer.append(back.pollLast()).append(" ");
        }

        answer.append("\n");

        if(front.isEmpty())answer.append(-1);
        while(!front.isEmpty()){
            answer.append(front.pollLast()).append(" ");
        }
        System.out.println(answer);
    }

    private static void access(int page){
        if(curPage != 0)
            back.add(curPage);
                    
        front.clear();
        curPage = page;
    }

    private static void backWard(){
        if(back.size() == 0)return;

        front.add(curPage);
        curPage = back.pollLast();
    }

    private static void frontWard(){
        if(front.size() == 0)return;

        back.add(curPage);
        curPage = front.pollLast();
    }

    private static void compress(){
        LinkedList<Integer> tempBack = new LinkedList<>();

        int prev = -1;
        while(!back.isEmpty()){
            int page = back.poll();
            if(prev == page)
                continue;

            prev = page;
            tempBack.add(page);
        }

        back = tempBack;
    }
}