import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Main{
    private static int n, characterCount, answer;
    private static List<Integer> words;
    private static Map<Integer, Boolean> visit;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
        String[] input = br.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        characterCount = Integer.parseInt(input[1]) - 5;

        words = new ArrayList<>();

        char[] characters = {'a', 'c', 't', 'i', 'n'};

        int path = 0;
        for(int i=0 ; i<5 ; i++){
            path |= (1 << (characters[i] - 'a'));
        }

        for(int i=0 ; i<n ; i++){
            String word = br.readLine();
            int contain = 0;
            for(int j=4 ; j<word.length() - 4; j++){
                contain |= (1 << (word.charAt(j) - 'a'));
            }
            words.add(contain);
        }

        answer = characterCount == 21 ? n : 0;

        visit = new HashMap<>();
        visit.put(path, true);
        
        dfs(0, 1, path);

        System.out.println(answer);
    }

    private static void dfs(int depth, int start, int path){
        if(characterCount < 0 || characterCount == 21)return;

        if(depth == characterCount){
            count(path);            
            return;
        }

        for(int i=start ; i<26 ; i++){
            if((path & (1 << i)) != 0)
                continue;

            if(visit.containsKey(path | (1 << i)))
                continue;

            visit.put(path | (1 << i), true);

            path |= (1 << i);
            dfs(depth + 1, i + 1, path);
            path &= ~(1 << i);
        }
    }

    private static void count(int path){
        int ret = 0;
        for(int word : words){
            int bit = (path & word);

            if(bit != word && bit != path)
                continue;

            ret++;
        }
        answer = Math.max(answer, ret);
    }

}