package week1.오목;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {

        int[][] board = Main.data();
        int num = 0;
        for(int i = 0; i < 19; i++) {
            for(int j = 0; j < 19; j++) {
                if(board[i][j] != 0) num = check(board, i, j);
                if(num != 0) {
                    return;
                }
            }
        }

        System.out.println(0);
    }

    private static int check(int[][] board, int x, int y) {
        int num = board[x][y];
        int cnt = 1;
        int result_x = 0, result_y = 0;

        //상하로 5개인가?

        for(int i = 1; i < 6; i++) {
            if(x + i < 19) {
                if(board[x+i][y] == num){
                    cnt++;
                }
                else break;
            }
        }

        for(int i = 1; i < 6; i++) {
            if(x - i >= 0) {
                if(board[x-i][y] == num) cnt++;
                else break;
            }
        }

        if(cnt == 5) {
            System.out.println(num);
            System.out.println((x+1) + " " + (y+1));
            return num;
        }

        cnt = 1;

        //좌우로 5개인가?

        for(int i = 1; i < 6; i++) {
            if(y + i < 19) {
                if(board[x][y+i] == num) cnt++;
                else break;
            }
        }

        for(int i = 1; i < 6; i++) {
            if(y - i >= 0) {
                if(board[x][y-i] == num) cnt++;
                else break;
            }
        }

        if(cnt == 5) {
            System.out.println(num);
            System.out.println((x+1) + " " + (y+1));
            return num;
        }

        cnt = 1;

        //대각으로 5개인가?

        for(int i = 1; i < 6; i++) {
            if(x + i < 19 && y + i < 19) {
                if(board[x+i][y+i] == num) {
                    cnt++;
                }
                else break;
            }
        }

        for(int i = 1; i < 6; i++) {
            if(x - i >= 0 && y - i >= 0) {
                if(board[x-i][y-i] == num) cnt++;
                else break;
            }
        }

        if(cnt == 5) {
            System.out.println(num);
            System.out.println((x+1) + " " + (y+1));
            return num;
        }

        cnt = 1;

        for(int i = 1; i < 6; i++) {
            if(x + i < 19 && y - i >= 0) {
                if(board[x+i][y-i] == num) {
                    result_x = x+i;
                    result_y = y-i;
                    cnt++;
                }
                else break;
            }
        }

        for(int i = 1; i < 6; i++) {
            if(x - i >= 0 && y + i < 19) {
                if(board[x-i][y+i] == num) cnt++;
                else break;
            }
        }

        if(cnt == 5) {
            System.out.println(num);
            System.out.println((result_x+1) + " " + (result_y+1));
            return num;
        }

        return 0;
    }

    private static int[][] data() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[][] board = new int[19][19];

        for (int i = 0; i < 19; i++) {
            String input = br.readLine();
            for (int j = 0, index = 0; j < 19; index += 2, j++) {
                board[i][j] = Character.getNumericValue(input.charAt(index));
            }
        }

        return board;
    }   //input하는 방법은 컨닝함..
}
