import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

class Main{

    private static int n, m;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        ArrayDeque<Integer> p1 = new ArrayDeque<>();
        ArrayDeque<Integer> p2 = new ArrayDeque<>();

        for(int i=0 ; i<n ; i++){
            stk = new StringTokenizer(br.readLine());
            p1.addFirst(Integer.parseInt(stk.nextToken()));
            p2.addFirst(Integer.parseInt(stk.nextToken()));
        }

        ArrayDeque<Integer> dummy1 = new ArrayDeque<>();
        ArrayDeque<Integer> dummy2 = new ArrayDeque<>();

        for(int i=0 ; i<m ; i++){

            if(i % 2 == 0){
                int card1 = p1.pollFirst();
                dummy1.add(card1);
            }else{
                int card2 = p2.pollFirst();
                dummy2.add(card2);
            }

            if(p1.isEmpty() || p2.isEmpty()){
                break;
            }

            if((!dummy1.isEmpty() && dummy1.peekLast() == 5) 
            || (!dummy2.isEmpty() && dummy2.peekLast() == 5)){
                mergeDummy(p1, dummy2, dummy1);
                continue;
            }

            if((!dummy1.isEmpty() && !dummy2.isEmpty()) 
            && (dummy1.peekLast() + dummy2.peekLast()) == 5){
                mergeDummy(p2, dummy1, dummy2);
            }
        }

        String answer = "";
        if(p2.isEmpty() || p1.size() > p2.size()){
            answer = "do";
        }
        else if(p1.isEmpty() || p1.size() < p2.size()){
            answer = "su";
        }else{
            answer = "dosu";
        }

        System.out.println(answer);
    }

    private static void mergeDummy(ArrayDeque<Integer> cardDeck, ArrayDeque<Integer> dummy1, ArrayDeque<Integer> dummy2){
        cardDeck.addAll(dummy1);
        cardDeck.addAll(dummy2);

        dummy1.clear();
        dummy2.clear();
    }
}