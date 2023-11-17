package week1.로봇청소기;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static Robot robot;

    public static void main(String[] args) throws Exception {
        int[][] board = data();

        while(true) {
            //System.out.println("x : " + robot.x + " / y : " + robot.y);
            robot.cleanUp(board);   //1. 현재 칸을 청소한다.
            if(robot.scan(board)) {
                while(true) {
                    robot.turn();
                    if(robot.go(board)) break;
                }
            }
            else {
                boolean canGoBack = robot.back(board);
                if(!canGoBack) break;
            }
        }

        System.out.println(robot.cleanCnt);

    }

    private static int[][] data() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        robot = new Robot(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

        int[][] board = new int[x][y];

        for (int i = 0; i < x; i++) {
            String input = br.readLine();
            for (int j = 0, index = 0; j < y; index += 2, j++) {
                board[i][j] = Character.getNumericValue(input.charAt(index));
            }
        }

        return board;
    }
}

class Robot {
    int direction;
    int x;
    int y;
    int cleanCnt = 0;

    Robot(int x, int y, int direction) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    boolean back(int[][] board) {
        switch (direction) {
            case 0 :
                if(board[x+1][y] != 1) {
                    x++;
                    return true;
                }
                break;
            case 1 :
                if(board[x][y-1] != 1) {
                    y--;
                    return true;
                }
                break;
            case 2 :
                if(board[x-1][y] != 1) {
                    x--;
                    return true;
                }
                break;
            case 3 :
                if(board[x][y+1] != 1) {
                    y++;
                    return true;
                }
                break;
        }
        return false;
    }

    boolean scan(int[][] board) {   //동서남북 미청소구간 스캔
        if(board[x-1][y] == 0) return true;
        if(board[x+1][y] == 0) return true;
        if(board[x][y-1] == 0) return true;
        if(board[x][y+1] == 0) return true;
        return false;
    }

    boolean go(int[][] board) {     //한 칸 전진
        switch (direction) {
            case 0 :
                if(board[x-1][y] == 0) {
                    x--;
                    return true;
                }
                break;
            case 1 :
                if(board[x][y+1] == 0) {
                    y++;
                    return true;
                }
                break;
            case 2 :
                if(board[x+1][y] == 0) {
                    x++;
                    return true;
                }
                break;
            case 3 :
                if(board[x][y-1] == 0) {
                    y--;
                    return true;
                }
                break;
        }
        return false;
    }

    void turn() {   //반시계 회전
        direction--;
        if(direction < 0) direction = 3;
    }

    void cleanUp(int[][] board) {
        if(board[x][y] == 0) {
            cleanCnt++;
            board[x][y] = 2;
        }
    }

}
