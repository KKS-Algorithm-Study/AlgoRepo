import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] arr = new int[n];
        String[] str = br.readLine().split(" ");
        Map<Integer, Integer> count = new HashMap<>();

        for(int i=0 ; i<n ; i++){
            arr[i] = Integer.parseInt(str[i]);
            count.put(arr[i], count.getOrDefault(arr[i], 0) + 1);
        }

        LinkedList<Integer> stack = new LinkedList<>();
        int[] list = new int[n];
        Arrays.fill(list, -1);
        StringBuilder answer = new StringBuilder();

        for(int i=0 ; i<n ; i++){
            int cur = count.get(arr[i]);

            while(!stack.isEmpty() && count.get(arr[stack.peekLast()]) < cur){
                list[stack.peekLast()] = arr[i];
                stack.pollLast();
            }
            stack.add(i);
            System.out.println(stack);
        }

        for(int i=0 ; i<n ; i++){
            answer.append(list[i]).append(" ");
        }
        
        System.out.println(answer);
    }
}