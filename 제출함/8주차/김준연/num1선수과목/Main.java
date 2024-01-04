package week8.num1선수과목;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] inDegree = new int[N + 1];
        int[] result = new int[N + 1];

        ArrayList<ArrayList<Integer>> lines = new ArrayList<>();
        for(int i = 0; i < N + 1; i++) {
            lines.add(new ArrayList<Integer>());
        }

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            inDegree[B]++;
            lines.get(A).add(B);
        }

        Queue<Integer> q = new LinkedList<>();

        for(int i = 1; i <= N; i++) {
            if(inDegree[i] == 0) {
                q.add(i);
                result[i] = 1;
            }
        }

        for(int i = 1; i <= N; i++) {

            int now = q.poll();
            for(int num : lines.get(now)) {
                inDegree[num]--;
                if(inDegree[num] == 0){
                    q.add(num);
                    result[num] = result[now] + 1;
                }
            }
        }

        for(int i = 1; i <= N; i++) System.out.print(result[i] + " ");
    }
}
