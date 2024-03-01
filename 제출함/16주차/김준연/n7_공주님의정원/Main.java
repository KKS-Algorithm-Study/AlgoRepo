/**
 *  접근 :
 *      1. 개화 순으로 정렬시키기
 *      2. "피워야하는 날"보다 같거나 빨리피는 꽃들 sublist 해서 가져오기
 *      3. 그 중 가장 늦게지는 꽃의 날을 "피워야하는 날" 로 다시 지정
 *      4. 위 과정을 피워야하는 날이 1130을 넘을 때 까지 반복
 *      5. 반복하는 도중 조건에 만족하지 못하면 0출력하고 리턴
 *          1) "피워야하는 날" 보다 빨리피는 꽃이 없다.
 *          2) 가장 늦게 지는 꽃이 "피워야하는 날" 보다 빨리 진다.
 *
 *  시간복잡도 :
 *      sublist 하면서 결국 1번 순회 => O(N)
 *      정렬 : O(N log N)
 *
 */

package week16.n7_공주님의정원;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        List<Flower> flowers = new ArrayList<>();

        int N = Integer.parseInt(st.nextToken());

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken()) * 100 + Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken()) * 100 + Integer.parseInt(st.nextToken());

            flowers.add(new Flower(start, end));
        }

        flowers.sort(Comparator.comparingInt(Flower::getStart));

        int R = 301;
        int result = 0;
        while(R <= 1130) {
            List<Flower> temp = new ArrayList<>();
            //구간 사이에 개화하는 꽃들 파악
            int i;
            for(i = 0; i < flowers.size(); i++) {
                if(flowers.get(i).start > R) {
                    break;
                }
            }
            temp = flowers.subList(0, i);
            flowers = flowers.subList(i, flowers.size());

            if(temp.isEmpty()) {
                System.out.println(0);
                return;
            }
            temp.sort(Comparator.comparingInt(Flower::getEnd));

            Flower peek = temp.get(temp.size() - 1);
            if(peek.end < R) {
                System.out.println(0);
                return;
            }
            R = peek.end;
            result++;
        }

        System.out.println(result);

    }
}

class Flower {
    int start;
    int end;

    public Flower(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}