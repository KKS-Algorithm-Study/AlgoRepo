/**
 * 접근 :
 *      1. 각 숫자에 대해 숫자의 개수 기록
 *      2. 뒤에서부터 세면서
 *      3. stack의 peek이 현재 개수보다 크면 그 숫자를 기록함
 *      4. 그렇지 않다면 현재 개수보다 큰 숫자가 나올때까지 pop함
 *      5. pop할 숫자가 없으면 -1 기록
 *      6. 다음 숫자로 넘어가면서 현재 숫자 스택에 넣기
 *
 *
 * 문제점 :
 *      sout 으로 하나씩 뽑다가 시간초과
 */

package week16.n6_오등큰수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        int[] nums = new int[N];
        int[] result = new int[N];

        HashMap<Integer, Integer> hm = new HashMap<>();

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
            if(hm.containsKey(nums[i])) hm.put(nums[i], hm.get(nums[i]) + 1);
            else hm.put(nums[i], 1);
        }

        Stack<Integer> stack = new Stack<>();

        result[N-1] = -1;
        stack.push(nums[N-1]);
        for(int i = N - 2; i >= 0; i--) {

            while(hm.get(stack.peek()) <= hm.get(nums[i])) {
                stack.pop();
                if(stack.isEmpty()) {
                    result[i] = -1;
                    break;
                }
            }

            if(!stack.isEmpty()) result[i] = stack.peek();
            stack.push(nums[i]);
        }

        StringBuilder sb = new StringBuilder();

        for(int n : result) {
            sb.append(n);
            sb.append(" ");
        }

        System.out.print(sb);
    }
}
