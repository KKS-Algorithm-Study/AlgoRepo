import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String duck = br.readLine();
        final char[] word = {'q', 'u', 'a', 'c', 'k'};
        int idx = 0;

        boolean[] visit = new boolean[duck.length()];

        int answer = 0, length = 0;

        for(int i=0 ; i<duck.length() ; i++){

            idx = 0;
            for(int j=i ; j<duck.length() ; j++){
                
                if(word[idx % 5] != duck.charAt(j) || visit[j]){
                    continue;
                }

                visit[j] = true;
                idx++;

                if(idx == 5)
                    answer++;
            }

            length += idx;
        }

        System.out.println(answer == 0 || length != duck.length() || length % 5 != 0 ? -1 : answer);
    }
}