/**
 * 접근 :
 *      1. 모든 숫자 표현 해둠
 *      2. 전부 비교함
 *      3. 맞는 숫자면 기록함
 *      4. 6으로 나누어떨어지는지 확인함
 *
 * 시간복잡도 :
 *
 *      3중 반복문
 *      10 * 3 * 5
 */

package week13.n1_폭탄해체;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static char[][][] table = {
            {
                    {'*', '*', '*'},
                    {'*', ' ', '*'},
                    {'*', ' ', '*'},
                    {'*', ' ', '*'},
                    {'*', '*', '*'}
            },
            {
                    {' ', ' ', '*'},
                    {' ', ' ', '*'},
                    {' ', ' ', '*'},
                    {' ', ' ', '*'},
                    {' ', ' ', '*'}
            },
            {
                    {'*', '*', '*'},
                    {' ', ' ', '*'},
                    {'*', '*', '*'},
                    {'*', ' ', ' '},
                    {'*', '*', '*'}
            },
            {
                    {'*', '*', '*'},
                    {' ', ' ', '*'},
                    {'*', '*', '*'},
                    {' ', ' ', '*'},
                    {'*', '*', '*'}
            },
            {
                    {'*', ' ', '*'},
                    {'*', ' ', '*'},
                    {'*', '*', '*'},
                    {' ', ' ', '*'},
                    {' ', ' ', '*'}
            },
            {
                    {'*', '*', '*'},
                    {'*', ' ', ' '},
                    {'*', '*', '*'},
                    {' ', ' ', '*'},
                    {'*', '*', '*'}
            },
            {
                    {'*', '*', '*'},
                    {'*', ' ', ' '},
                    {'*', '*', '*'},
                    {'*', ' ', '*'},
                    {'*', '*', '*'}
            },
            {
                    {'*', '*', '*'},
                    {' ', ' ', '*'},
                    {' ', ' ', '*'},
                    {' ', ' ', '*'},
                    {' ', ' ', '*'}
            },
            {
                    {'*', '*', '*'},
                    {'*', ' ', '*'},
                    {'*', '*', '*'},
                    {'*', ' ', '*'},
                    {'*', '*', '*'}
            },
            {
                    {'*', '*', '*'},
                    {'*', ' ', '*'},
                    {'*', '*', '*'},
                    {' ', ' ', '*'},
                    {'*', '*', '*'}
            }
    };


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[][] nums = new char[5][];

        for (int i = 0; i < 5; i++) {
            nums[i] = br.readLine().toCharArray();
        }

        int result = 0;

        for(int i = 0; i < nums[0].length; i += 4) {

            if(i + 1 >= nums[0].length) {
                System.out.println("BOOM!!");
                return;
            }

            boolean check = false;

            Loop1:
            for(int j = 0; j <= 9; j++) {
                for(int x = 0; x < 5; x++) {
                    for(int y = 0; y < 3; y++) {
                        if(nums[x][y + i] != table[j][x][y]) {
                            continue Loop1;
                        }
                    }
                }
                check = true;
                result = result * 10 + j;
                break;
            }

            if(!check) {
                System.out.println("BOOM!!");
                return;
            }
        }

        if(result % 6 == 0) {
            System.out.println("BEER!!");
        }
        else {
            System.out.println("BOOM!!");
        }

    }
}
