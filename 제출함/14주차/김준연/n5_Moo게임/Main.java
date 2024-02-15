/**
 * 접근 :
 *      1. 하위1단계 + 자신의moo + 하위1단계
 *          이 꼴로 이루어진 문자열
 *      2. 현재 moo의 시작점과 끝을 기록하면서, 자신의 하위 moo를 참조하는 클래스 사용
 *      3. 자릿수가 들어왔을 때, 현재 moo의 시작점과 끝점 사이라면 결과를 출력한다.
 *      4. 현재 moo보다 작다면 그 숫자 그대로 하위참조 moo로 보낸다.
 *      5. 현재 moo보다 크다면 그 숫자에서 현재 moo의 마지막 값을 빼서 하위참조 moo로 보낸다.
 *          => moo의 좌측 자식, 우측 자식 중 무조건 좌측 자식으로 연산하는 효과
 *      6. 그렇게 숫자에 맞게 내려가다보면 자신에게 맞는 moo가 나옴
 */

package week14.n5_Moo게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());

        ArrayList<LikedMoo> arr = new ArrayList<>();
        arr.add(new LikedMoo(null, 1, 3, 3));       //S(0) 단계 moo 삽입
        for(int i = 1; i < 30; i++) {
            arr.add(new LikedMoo(arr.get(i - 1), arr.get(i - 1).length + 1, arr.get(i - 1).length + 3 + i, arr.get(i - 1).length * 2 + i + 3));     //S(i)단계 moo 삽입
        }

        System.out.println(arr.get(29).getMoo(n));

    }
}

class LikedMoo {
    LikedMoo likedMoo;
    int s;
    int e;
    int length;

    public LikedMoo(LikedMoo likedMoo, int s, int e, int length) {
        this.likedMoo = likedMoo;
        this.s = s;
        this.e = e;
        this.length = length;
    }

    public char getMoo(int n) {
        if(n == s) return 'm';
        else if(n > s && n <= e) return 'o';
        else if(n < s) return likedMoo.getMoo(n);
        else return likedMoo.getMoo(n - e);
    }
}