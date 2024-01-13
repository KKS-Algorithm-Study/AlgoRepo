import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *  문제 풀이 방법:
 *      아기 상어가 물고기를 먹을 수 있을 때까지 모두 먹었을 때 걸린 시간을 출력하는 문제
 *      조건은 다음과 같다:
 *          1. 아기 상어의 처음 크기는 2고, 크기만큼의 물고기를 먹으면 크기가 +1 되고, 물고기는 모두 소화된다.
 *          2. 자기 크기보다 작은 물고기는 먹을 수 있고, 같은 물고기는 지나가기만 할 수 있고, 큰 물고기는 지나가지도 못한다.
 *          3. 먹을 수 있는 물고기 중 가장 가까운 물고기를 먹는데, 거리가 같다면 좌상단에 있는 물고기를 먼저 먹는다.
 *
 *      BFS로 먹을 수 있는 물고기를 탐색하고, 상어를 이동시켜 물고기를 먹이는 시뮬레이션 문제다.
 *      문제에 필요한 조건이 지문 여려 군데에 퍼져있어, 파악이 어렵기도 하고,
 *      또 조건 자체도 복잡하기 때문에, 분기를 잘 처리하는게 포인트다.
 *
 *      여러 버그가 터질 수 있다:
 *          * 처음 상어 위치에 있는 9를 0으로 안바꿨다가, 나중에 크기 9의 물고기인줄 알고 먹으러 온다던가
 *          * 3번 조건을 위해 BFS 탐색 순서를 (상, 좌, 우, 하)로 놓고 먼저 찾는 걸 먹이다가 오답이 나온다던가
 *          * 다음 먹을 물고기의 위치를 먼저 구하고 거리를 나중에 계산했다가, 실제 이동 거리 != 직선 거리여서 잘못 계산한다던가
 *
 *      이 밑은 굳이 안해도 되는 팁:
 *
 *      문제 특성상 BFS가 여러 번 일어나는데,
 *      BFS를 할 때마다 방문배열을 초기화하면 시간 + 공간 낭비가 생긴다.
 *      따라서 BFS가 여러번 일어난다면, 방문 배열을 boolean 대신 int로 만들고, true/false 대신 BFS 횟수를 저장해서
 *      if (방문배열[행][열] >= BFS 횟수) {continue;} 로 해주면,
 *      방문 배열을 초기화 할 필요 없이 계속 재사용 가능하다.
 *
 *      3번 조건의 경우, 왼쪽 위부터 칸에 번호를 매기면 가장 작은 번호의 칸이 되기 때문에
 *      2차원 배열을 1차원으로 flatten 시켜 그 인덱스의 최소값으로 분기처리하면 쉽고 빠르다.
 *
 *  시간 복잡도:
 *      최악의 경우, 모든 칸에서 BFS가 일어나기 때문에
 *      O(N**4)이다.
 */
public class Main {
    static final int NOT_FOUND = Integer.MAX_VALUE;
    static final int SHARK = 9;
    static final int EMPTY = 0;
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static int[][] inputNumberMatrix(int rowCount, int colCount) throws Exception {
        int[][] result = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            result[row] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return result;
    }

    private static int[] flattenNumberMatrix(int[][] matrix) {
        int[][] margin = new int[1][matrix[0].length];
        return Stream.concat(Arrays.stream(matrix), Stream.of(margin))
                .flatMapToInt(row -> IntStream.concat(Arrays.stream(row), IntStream.of(0)))
                .toArray();
    }

    private static int[] makeVisitedArray(int[] flattenedMatrix, int rowLength) {
        int maxNum = flattenedMatrix.length;
        int[] result = new int[maxNum];
        for (int idx = 0; idx < rowLength; idx++) {
            result[(idx + 1) * rowLength - 1] = maxNum;
            result[idx + rowLength * (rowLength - 1)] = maxNum;
        }
        return result;
    }

    private static int findSharkLocation(int[] oceanMap) {
        for (int idx = 0; idx < oceanMap.length; idx++) {
            if (oceanMap[idx] == SHARK) {
                return idx;
            }
        }
        return -1;
    }

    private static int[] findFishToEat(int initLocation, int sharkSize, int[] oceanMap, int[] visited, int[] directions,
                                       int bfsCount) {
        List<Integer> cque = new ArrayList<>();
        cque.add(initLocation);
        visited[initLocation] = bfsCount;
        int result = NOT_FOUND;
        int usedTime = 0;
        while (true) {
            usedTime++;
            List<Integer> nque = new ArrayList<>();
            for (int currLocation : cque) {
                for (int move : directions) {
                    int nextLocation = currLocation + move;
                    if (nextLocation < 0 || nextLocation >= oceanMap.length) {
                        continue;
                    }
                    if (visited[nextLocation] >= bfsCount || oceanMap[nextLocation] > sharkSize) {
                        continue;
                    }
                    if (oceanMap[nextLocation] != EMPTY & oceanMap[nextLocation] < sharkSize) {
                        result = Math.min(result, nextLocation);
                    } else {
                        visited[nextLocation] = bfsCount;
                        nque.add(nextLocation);
                    }

                }
            }
            if (result != NOT_FOUND || nque.isEmpty()) {
                break;
            }
            cque = nque;
        }
        return new int[]{result, usedTime};
    }

    private static String solve(int[][] ocean) {
        int[] oceanMap = flattenNumberMatrix(ocean);
        int rowLength = ocean.length + 1;
        int[] visited = makeVisitedArray(oceanMap, rowLength);
        int[] directions = new int[]{1, -1, rowLength, -rowLength};
        int currLocation = findSharkLocation(oceanMap);
        oceanMap[currLocation] = 0;

        int sharkSize = 2;
        int growthRequirement = sharkSize;
        int totalTime = 0;
        for (int eatCount = 1; eatCount < oceanMap.length; eatCount++) {
            int[] result = findFishToEat(currLocation, sharkSize, oceanMap, visited, directions, eatCount);
            int nextLocation = result[0];
            int usedTime = result[1];
            if (nextLocation == NOT_FOUND) {
                break;
            }
            totalTime += usedTime;
            oceanMap[nextLocation] = EMPTY;
            currLocation = nextLocation;
            if (eatCount == growthRequirement) {
                growthRequirement += ++sharkSize;
            }
        }

        return Integer.toString(totalTime);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[][] ocean = inputNumberMatrix(N, N);
            String answer = solve(ocean);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}