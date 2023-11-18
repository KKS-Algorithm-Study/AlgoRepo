import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 문제 접근법:
 *      방을 스캔해 청정기 위치 확인 후, 먼지 확산 시키고 청정기 돌리고의 과정을 시간만큼 반복해 방에 남은 먼지 양를 합산하는 문제.
 *
 * 주의할 점:
 *      확산은 미세먼지가 있는 모든 칸에서 동시에 일어난다.
 *      -> 먼지를 확산시킬 때, 확산되는 먼지를 옆 칸에 그대로 적용시키면, 옆 칸에서 기존 먼지+확산 먼지를 확산시키기 때문에 숫자가 이상해짐
 *      -> 먼지 확산을 저장할 빈 배열을 따로 만들고, +-되는 먼지 양은 모두 그 배열에 저장, 마지막에 한 번에 적용해야 함.
 *
 * 시간 복잡도:
 *      모든 방 칸을 돌면서 (N * M) 네 방향으로 먼지 확산 시키는 걸, 주어진 시간만큼(T) 반복.
 *      O(N * M * T)
 *      청정기 돌리기는 주어진 시간만큼(T) 위 절반 (2M + N) + 아래 절반(2M + N) = (4M + 2N) 만큼 탐색하는데,
 *      (2N+4M)*T는 N*M*T 보다 영향력이 작으므로 빅오에 반영 안함.
 */

class Room {

    public final int[][] room;
    public final int cleanerTop;
    public final int cleanerBot;
    public final int roomWidth;
    public final int roomHeight;
    public final int[] dr = {0, 1, 0, -1};
    public final int[] dc = {1, 0, -1, 0};



    private static int findCleaner(int[][] room) {
        for (int row = 0; row < room.length; row++) {
            if (room[row][0] == -1) {
                return row;
            }
        }
        throw new RuntimeException("No cleaner found");
    }

    private Room(int[][] room) {
        this.room = room;
        this.roomHeight = room.length;
        this.roomWidth = room[0].length;
        this.cleanerTop = findCleaner(room);
        this.cleanerBot = cleanerTop + 1;
        this.room[cleanerTop][0] = 0;
        this.room[cleanerBot][0] = 0;
    }

    public static Room createRoom(int[][] room) {
        return new Room(room);
    }

    public void printRoom() {
        for (int row = 0; row < roomHeight; row++) {
            for (int col = 0; col < roomWidth; col++) {
                System.out.print(room[row][col] + " ");
            }
            System.out.println();
        }
    }

    // 옆 칸이 방 안에 존재하는 칸인지, 청정기 칸은 아닌지 확인
    private boolean canSpread(int adjRow, int adjCol) {
        return 0 <= adjRow && adjRow < roomHeight &&
                0 <= adjCol && adjCol < roomWidth &&
                !(adjCol == 0 && (adjRow == cleanerTop || adjRow == cleanerBot));
    }

    private void spreadDust() {
        // 먼지는 한 번에 퍼지기 때문에, 빈 배열을 만들어 옆으로 확산되는 먼지는 따로 저장해놨다 마지막에 더해야함.
        int[][] spreadMap = new int[roomHeight][roomWidth];

        // 각 칸에서 먼지 확산 확인
        for (int row = 0; row < roomHeight; row++) {
            for (int col = 0; col < roomWidth; col++) {
                int spreadAmount = room[row][col] / 5;
                // 4방향으로 먼지 확산
                for (int d = 0; d < 4; d++) {
                    int adjRow = row + dr[d];
                    int adjCol = col + dc[d];
                    // 옆 칸이 유효한지 체크
                    if (!canSpread(adjRow, adjCol)) {
                        continue;
                    }
                    // 유효하다면, 1/5 만큼 먼지를 옮김
                    spreadMap[row][col] -= spreadAmount;
                    spreadMap[adjRow][adjCol] += spreadAmount;
                }
            }
        }
        // 먼지 확산 적용
        for (int row = 0; row < roomHeight; row++) {
            for (int col = 0; col < roomWidth; col++) {
                room[row][col] += spreadMap[row][col];
            }
        }
    }

    private void shiftDown(int upperRow, int lowerRow, int col) {
        if (upperRow > lowerRow) {
            int tmp = upperRow;
            upperRow = lowerRow;
            lowerRow = tmp;
        }

        for (int row = lowerRow; row > upperRow; row--) {
            room[row][col] = room[row-1][col];
        }
    }

    private void shiftUp(int upperRow, int lowerRow, int col) {
        if (upperRow > lowerRow) {
            int tmp = upperRow;
            upperRow = lowerRow;
            lowerRow = tmp;
        }

        for (int row = upperRow; row < lowerRow; row++) {
            room[row][col] = room[row+1][col];
        }
    }

    private void shiftLeft(int leftCol, int rightCol, int row) {
        if (leftCol > rightCol) {
            int tmp = leftCol;
            leftCol = rightCol;
            rightCol = tmp;
        }

        for (int col = leftCol; col < rightCol; col++) {
            room[row][col] = room[row][col+1];
        }
    }

    private void shiftRight(int leftCol, int rightCol, int row) {
        if (leftCol > rightCol) {
            int tmp = leftCol;
            leftCol = rightCol;
            rightCol = tmp;
        }

        for (int col = rightCol; col > leftCol; col--) {
            room[row][col] = room[row][col-1];
        }
    }


    private void cleanerOn() {
        cleanTopHalf();
        cleanBotHalt();
    }

    private void cleanTopHalf() {
        shiftDown(0, cleanerTop, 0);
        room[cleanerTop][0] = 0;
        shiftLeft(0, roomWidth-1, 0);
        shiftUp(0, cleanerTop, roomWidth-1);
        shiftRight(0, roomWidth-1, cleanerTop);
    }

    private void cleanBotHalt() {
        shiftUp(cleanerBot, roomHeight-1, 0);
        room[cleanerBot][0] = 0;
        shiftLeft(0, roomWidth-1, roomHeight-1);
        shiftDown(cleanerBot, roomHeight-1, roomWidth-1);
        shiftRight(0, roomWidth-1, cleanerBot);
    }

    public void cleanFor (int time) {
        for (int t = 0; t < time; t++) {
            spreadDust();
            cleanerOn();
        }
    }

    public int getDustSum () {
        int sum = 0;
        for (int row = 0; row < roomHeight; row++) {
            for (int col = 0; col < roomWidth; col++) {
                sum += room[row][col];
            }
        }
        return sum;
    }
}

public class Main {

    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static int[][] inputMatrix(int row, int col) throws Exception {
        int[][] matrix = new int[row][col];
        for (int r = 0; r < row; r++) {
            tokenize();
            for (int c = 0; c < col; c++) {
                matrix[r][c] = nextInt();
            }
        }
        return matrix;
    }

    private static void printMatrix(int[][] matrix) throws Exception {
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                bw.write(matrix[r][c] + " ");
            }
            bw.write("\n");
            bw.flush();
        }
    }

    private static String solve(int[][] board, int time) throws Exception {
        Room room = Room.createRoom(board);
        room.cleanFor(time);
        return Integer.toString(room.getDustSum());
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int boardHeight = nextInt();
            int boardWidth = nextInt();
            int time = nextInt();
            int[][] board = inputMatrix(boardHeight, boardWidth);
//            printMatrix(board);

            String answer = solve(board, time);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
