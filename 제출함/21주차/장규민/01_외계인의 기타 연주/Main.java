import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

class Main{

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
        StringTokenizer stk = new StringTokenizer(br.readLine());
        int n, m;

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        LinkedList<Integer>[] strings = new LinkedList[7];

        for(int i=0 ; i<=6 ; i++){
            strings[i] = new LinkedList<>();
        }

        int answer = 0;
        boolean[][] isPress = new boolean[7][m + 1];

        for(int i=0 ; i<n ; i++){
            stk = new StringTokenizer(br.readLine());
            int number, plat;

            number = Integer.parseInt(stk.nextToken());
            plat = Integer.parseInt(stk.nextToken());

            while(!strings[number].isEmpty() && strings[number].peekLast() > plat){
                answer++;
                int cur = strings[number].pollLast();
                isPress[number][cur] = false;
            }

            if(!isPress[number][plat]){
                isPress[number][plat] = true;
                answer++;
                strings[number].add(plat);
            }

        }

        System.out.println(answer);
    }

}