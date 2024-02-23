/**
 * 접근 :
 *      1. 많은 컵라면부터 픽하면서
 *      2. 가능한 데드라인에 아슬아슬하게 배치하기
 *
 *      근데?
 *      그냥 데드라인 쭉 훑으면 개손해임
 *      자기자신을 가리키는 배열만들고
 *      그 자리가 찼으면 자신의 앞을 가리키기
 *      (이거 경로압축하면서)
 */

package week15.n9_컵라면;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int[] parents;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        ArrayList<Cupramen> cups = new ArrayList<>();

        int N = Integer.parseInt(st.nextToken());

        parents = new int[N+1];
        for(int i = 0; i < N + 1; i++) {
            parents[i] = i;
        }

        while(N-- > 0) {
            st = new StringTokenizer(br.readLine());
            int deadline = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());
            cups.add(new Cupramen(count, deadline));
        }

        cups.sort(Collections.reverseOrder());

        int result = 0;

        for(Cupramen now : cups) {
            if(getParent(now.deadline) != 0) {
                result += now.count;
            }
        }

        System.out.println(result);

    }

    static int getParent(int x) {
        if(parents[x] == x) {
            if(x != 0) parents[x] = x - 1;
            return x;
        }

        return parents[x] = getParent(parents[x]);
    }
}

class Cupramen implements Comparable<Cupramen>{
    int count;
    int deadline;

    public Cupramen(int count, int deadline) {
        this.count = count;
        this.deadline = deadline;
    }

    @Override
    public int compareTo(Cupramen o) {
        return Integer.compare(this.count, o.count);
    }
}