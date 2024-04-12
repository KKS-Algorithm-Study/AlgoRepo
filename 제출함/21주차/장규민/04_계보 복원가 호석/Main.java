import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

class Main{
    private static Map<String, Integer> pre;
    private static Map<String, List<String>> graph;
    private static Map<String, List<String>> child;
    private static List<String> nameList, first;

    private static int n, m;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());

        nameList = new ArrayList<>();
        pre = new HashMap<>();
        graph = new HashMap<>();
        child = new HashMap<>();

        StringTokenizer stk = new StringTokenizer(br.readLine());

        for(int i=0 ; i<n ; i++){
            String name = stk.nextToken();
            nameList.add(name);
            pre.put(name, 0);
            graph.put(name, new ArrayList<>());
            child.put(name, new ArrayList<>());
        }

        Collections.sort(nameList);

        m = Integer.parseInt(br.readLine());
        
        while(m-- > 0){
            stk = new StringTokenizer(br.readLine());
            String desc = stk.nextToken();
            String asc = stk.nextToken();

            graph.get(asc).add(desc);
            pre.put(desc, pre.get(desc) + 1);
        }
        
        first = new ArrayList<>();
        
        topologicalSort();
        Collections.sort(first);
        
        // 출력
        StringBuilder answer = new StringBuilder();

        answer.append(first.size()).append("\n");

        for(String name : first){
            answer.append(name).append(" ");
        }
        answer.append("\n");

        for(String name : nameList){
            answer.append(name).append(" ")
            .append(child.get(name).size()).append(" ");

            Collections.sort(child.get(name));
            for(String childName : child.get(name)){
                answer.append(childName).append(" ");
            }

            answer.append("\n");
        }

        System.out.print(answer);
        
    }

    private static void topologicalSort(){
        Queue<String> que = new LinkedList<>();

        for(String name : nameList){
            if(pre.get(name) != 0)
                continue;

            que.add(name);
            first.add(name);
        }

        while(!que.isEmpty()) {
            String sour = que.poll();

            for(String des : graph.get(sour)){
                pre.put(des, pre.get(des) - 1);
                
                if(pre.get(des) == 0){
                    que.add(des);
                    child.get(sour).add(des);
                }
            }
        }
    }
}