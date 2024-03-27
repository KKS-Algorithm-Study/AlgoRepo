import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

class Main{
    static class CustomArray{
        int count;
        int[] numbers;

        public CustomArray(int count, int[] numbers){
            this.count = count;
            this.numbers = numbers;
        }
    }

    private static int n, k;
    private static int[] list;
    private static Map<Integer, Boolean> visit;
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());
        n = Integer.parseInt(stk.nextToken());
        k = Integer.parseInt(stk.nextToken());
        
        list = new int[n];

        stk = new StringTokenizer(br.readLine());
        for(int i=0 ; i<n ; i++){
            list[i] = Integer.parseInt(stk.nextToken());
        }

        visit = new HashMap<>();
        System.out.println(bfs());
    }

    private static int bfs(){
        if(isAsc(list)){
            return 0;
        }
        Queue<CustomArray> que = new LinkedList<>();

        que.add(new CustomArray(0, list));
        int path = makePath(list);

        visit.put(path, true);

        while(!que.isEmpty()){
            CustomArray cur = que.poll();
            int count = cur.count;

            for(int i=0 ; i <= n - k ; i++){
                int[] numbers = Arrays.copyOf(cur.numbers, n);
                
                reversed(numbers, i);

                path = makePath(numbers);

                if(visit.containsKey(path))
                    continue;

                if(isAsc(numbers)){
                    return count + 1;
                }

                que.add(new CustomArray(count + 1, numbers));
                visit.put(path, true);
            }
        }
        
        return -1;
    }

    private static void reversed(int[] arr, int x){
        int right = k + x - 1;

        for(int i = 0 ; i < k / 2 ; i++){
            int left = i + x;

            int temp = arr[left];
            arr[left] = arr[right - i];
            arr[right - i] = temp;
        }
    }

    private static boolean isAsc(int[] arr){
        for(int i=1 ; i<n ; i++){
            if(arr[i - 1] > arr[i])
                return false;
        }

        return true;
    }

    private static int makePath(int[] arr){
        int total = 0;
        for(int i=0 ; i<arr.length ; i++){
            total = (total * 10) + arr[i];
        }
        return total;
    }
}