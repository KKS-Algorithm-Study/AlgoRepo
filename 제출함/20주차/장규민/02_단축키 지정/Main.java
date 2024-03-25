import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

class Main{
    private static Map<Character, Boolean> shortCuts;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        StringBuilder answer = new StringBuilder();
        shortCuts = new HashMap<>();

        while(n-- > 0){
            String str = br.readLine();
            answer.append(setShortCut(str)).append("\n");
        }
        System.out.print(answer);
    }

    private static String setShortCut(String str){
        StringBuilder ret = new StringBuilder(str);

        int idx = checkFirstChar(str);
        if(idx < str.length()){
            ret.insert(idx, "[");
            ret.insert(idx + 2, "]");
            return ret.toString();
        }

        for(int i=0 ; i<str.length() ; i++){
            char ch = Character.toLowerCase(str.charAt(i));

            if(ch == ' ')continue;

            if(!shortCuts.containsKey(ch)){
                ret.insert(i, "[");
                ret.insert(i + 2, "]");
                shortCuts.put(ch, true);
                return ret.toString();
            }
        }

        return ret.toString();
    }

    private static int checkFirstChar(String str){
        String[] split = str.split(" ");

        int idx = 0;

        for(int i=0 ; i<split.length ; i++){
            char ch = Character.toLowerCase(split[i].charAt(0));
            if(!shortCuts.containsKey(ch)){
                shortCuts.put(ch, true);
                return idx;
            }

            idx += (split[i].length() + 1);
            
        }

        return idx;
    } 
}