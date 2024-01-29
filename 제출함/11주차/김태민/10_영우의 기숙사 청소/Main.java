import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  문제 풀이 방법:
 *
 *      만약 일반적인 시뮬레이션을 돌려 T일 만큼 곰팡이를 번식시키면
 *      최대 방 크기 90,000을 10,000번 탐색하므로
 *      900,000,000번의 연산 횟수가 필요해 시간 초과가 날 수 있다.
 *
 *      이 방식의 문제는 각 일차마다 방문 배열이 초기화되어 다시 방문하는 데에 있다.
 *      곰팡이는 나이트 처럼 움직이며 번식하는데,
 *      1일차에 곰팡이가 있었던 곳에는 3일차에도, 5일차에도 있게 된다.
 *      이로 알 수 있는 것은
 *          * 검사 날짜가 홀수 날이면, 검사하는 칸 중 하나를 아무 홀수 날짜에 방문하면 "YES" 출력 가능
 *          * 때문에 홀수 날에 방문 했던 곳은 홀수 날에 또 방문할 필요 없다.
 *
 *      즉, 홀수날과 짝수날 따로 방문 배열을 만들어 쓰면 계속해서 다시 방문하는 일이 일어나지 않는다.
 *      이러면 밑의 예제도 201일에 탐색이 종료된다.
 *      300 1 1 10000
 *      1 1
 *      2 1
 *
 *      홀수날과 짝수날 방문 배열은 boolean 배열로 [홀짝][열][행] 으로 만들어도 되지만,
 *      int 배열로 [열][행] 으로 만들어 1 << (날짜 % 2) 에 체크되어있는지 확인하는 비트마스킹을 사용할 수도 있다.
 *
 *  시간 복잡도:
 *      2차원 배열의 BFS이므로 O(N ** 2)이다.
 */
public class Main {

    private static class Room {

        int roomSize;
        int time;
        private int checkTime;
        private int[][] room;
        private Set<Coordinate> checkLocations;
        private List<Coordinate> cque;

        public Room(int roomSize, int checkTime) {
            this.roomSize = roomSize;
            this.checkTime = checkTime;
            this.room = new int[roomSize][roomSize];
            this.time = 0;
        }

        public static Room initRoom(int roomSize, int checkTime,
                                    List<Coordinate> moldLocations,
                                    List<Coordinate> checkLocations) {
            Room result = new Room(roomSize, checkTime);
            result.cque = new ArrayList<>(moldLocations);
            result.checkLocations = new HashSet<>(checkLocations);
            return result;
        }

        public boolean spreadMold() {
            this.time++;
            int moldBit = 1 << (time % 2);
            int checkBit = 1 << (checkTime % 2);
            List<Coordinate> nque = new ArrayList<>();
            for (Coordinate curr : cque) {
                for (Coordinate next : curr.getAdjCoordsInBorder(roomSize, roomSize)) {
                    if ((this.room[next.row][next.col] & moldBit) != 0) {
                        continue;
                    }
                    if (checkBit == moldBit && checkLocations.contains(next)) {
                        return true;
                    }
                    this.room[next.row][next.col] |= moldBit;
                    nque.add(next);
                }
            }
            cque = nque;
            return false;
        }

        public boolean isClean(int time, List<Coordinate> checkLocations) {
            int moldBit = 1 << (time % 2);
            for (Coordinate check : checkLocations) {
                if ((this.room[check.row][check.col] & moldBit) != 0) {
                    return false;
                }
            }
            return true;
        }

        public void print() {
            for (int[] row : room) {
                System.out.println(Arrays.toString(row));
            }
            System.out.println();
        }

        public boolean canStopSpreading() {
            return cque.isEmpty();
        }
    }

    private static class Coordinate {
        static private final int[] dr = new int[]{-2, -1, +1, +2, +2, +1, -1, -2};
        static private final int[] dc = new int[]{+1, +2, +2, +1, -1, -2, -2, -1};

        int row;
        int col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Coordinate(List<Integer> query) {
            this.row = query.get(0) - 1;
            this.col = query.get(1) - 1;
        }

        public static List<Coordinate> convertQueries(List<List<Integer>> coordinateQueries) {
            return coordinateQueries.stream()
                    .map(Coordinate::new)
                    .collect(Collectors.toList());
        }

        public List<Coordinate> getAdjCoordsInBorder(int height, int width) {
            return IntStream.range(0, dr.length)
                    .mapToObj(direction -> of(direction, height, width))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        private Coordinate of(int direction, int height, int width) {
            int nextRow = this.row + dr[direction];
            int nextCol = this.col + dc[direction];
            if (!isInBorder(nextRow, nextCol, height, width)) {
                return null;
            }
            return new Coordinate(nextRow, nextCol);
        }

        private static boolean isInBorder(int row, int col, int height, int width) {
            return 0 <= row && row < height && 0 <= col && col < width;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coordinate that = (Coordinate) o;
            return row == that.row && col == that.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public String toString() {
            return "{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }

    static final String NEEDS_CLEAN = "YES";
    static final String NO_CLEAN = "NO";
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }


    private static List<List<Integer>> inputQueries(int rowCount, int colCount) throws Exception {
        List<List<Integer>> result = new ArrayList<>();
        for (int row = 0; row < rowCount; row++) {
            List<Integer> query = Arrays.stream(br.readLine().split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            result.add(query);
        }
        return result;
    }

    private static String solve(int roomSize, int checkDay,
                                List<List<Integer>> moldQueries,
                                List<List<Integer>> checkQueries) {
        List<Coordinate> moldLocations = Coordinate.convertQueries(moldQueries);
        List<Coordinate> checkLocations = Coordinate.convertQueries(checkQueries);
        Room room = Room.initRoom(roomSize, checkDay, moldLocations, checkLocations);
        for (int day = 1; day <= checkDay; day++) {
            boolean needsClean = room.spreadMold();
            if (needsClean) {
                return NEEDS_CLEAN;
            }
            if (room.canStopSpreading()) {
                break;
            }
        }
        return NO_CLEAN;
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int K = nextInt();
            int T = nextInt();
            List<List<Integer>> moldQueries = inputQueries(M, 2);
            List<List<Integer>> checkQueries = inputQueries(K, 2);
            String answer = solve(N, T, moldQueries, checkQueries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
