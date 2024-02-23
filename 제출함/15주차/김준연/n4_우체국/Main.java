/**
 * 접근 :
 *      1. 마을 위치 기준 오름차순 시키고
 *      2. 마을 사람 총 합의 수 + 1에서 절반에 대해
 *      3. 하나씩 합쳐가면서 그 값보다 크거나 같으면 출력
 *
 * 문제점 :
 *      1. 사람까지의 거리의 합이 이해가 안됨
 *          => 4칸 마을만큼 떨어져있고 사람이 3명이면 총 12의 거리 비용이라고 생각했는데
 *          그냥 사람의 수만큼만 따짐
 *      2. long 타입 사용 안함
 */

package week15.n4_우체국;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        ArrayList<Town> arr = new ArrayList<>();

        long sum = 0;

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            arr.add(new Town(a, b));
            sum += b;
        }

        Collections.sort(arr);

        long left = 0;

        for(Town now : arr) {
            left += now.people;

            if((sum + 1) / 2 <= left) {
                System.out.println(now.location);
                break;
            }

        }

    }
}

class Town implements Comparable<Town>{
    int location;
    int people;

    public Town(int location, int people) {
        this.location = location;
        this.people = people;
    }

    @Override
    public int compareTo(Town o) {
        return Integer.compare(this.location, o.location);
    }
}