package week4.나무자르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        int N, M;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        ArrayList<Integer> road = new ArrayList<>();

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            road.add(Integer.parseInt(st.nextToken()));
        }

        //여기까지 데이터 input


        Collections.sort(road, Collections.reverseOrder());
        Solution solution = new Solution(road, M);

        solution.run();

    }
}

class Solution {

    ArrayList<Integer> road;
    int M;

    Solution(ArrayList<Integer> road, int M) {
        this.road = road;
        this.M = M;
    }
    void run() {
        int s = 0;
        int e = 1000000000;
        int mid;
        int answer = 0;

        while(s < e) {
            mid = (s + e) / 2;
            if(cut(mid)) {
                answer = mid;
                s = mid + 1;
            }
            else {
                e = mid;
            }
        }
        System.out.println(answer);

    }

    boolean cut(int h) {
        long sum = 0;
        for(int num : road) {
            if(num <= h) break;
            sum += num - h;
        }
        return sum >= M;
    }
}
