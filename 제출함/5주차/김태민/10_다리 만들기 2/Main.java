import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * 문제 접근 방법:
 *
 *     섬들로 이루어진 나라의 지도가 주어질 때,
 *     모든 섬들을 땅과 수직으로만 연결시킬 수 있는 다리로 연결시켜서
 *     모든 섬들이 연결되도록 하는 다리의 최소 길이를 구하는 문제
 *
 *     먼저 BFS/DFS로 섬에 번호를 매겨놓고,
 *     이중 for문을 돌려 한 섬에서 다른 섬으로 가는 다리의 최소 길이를 인접 행렬 형태로 만들어 구해준다
 *     그 후 인접 행렬에서 INF 값을 제외한 연결 다리들을 인접 리스트에 넣고
 *     다리 길이들에 대해 크루스칼 알고리즘을 돌려 최소 길이를 얻으면 된다
 *
 * 시간 복잡도:
 *     BFS나 DFS의 시간복잡도가 O(V + E)인데, 2차원 행렬의 그래프 탐색이므로 O(N ** 2),
 *     이중 for문이 O(N ** 2),
 *     크루스칼 알고리즘은 섬의 갯수가 K라고 할 때 O(K**2logK) 이다.
 *          다리의 최대 갯수는 모든 섬을 연결하는 경우인 K(K-1)/2개,
 *          이를 정렬하는 데 드는 시간 복잡도는 O(K**2logK) 이기 때문
 *
 *     따라서 최종 시간 복잡도는 O(K**2logK) 이다.
 */

class Islands {

    private static class Coordinate {
        int row;
        int col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{1, 0, -1, 0};

    int count;
    int[][] map;

    public Islands(String[][] map) {
        this.map = numberMap(map);
    }

    private boolean isOutOfBorder(int row, int col, int height, int width) {
        return row < 0 || row >= height || col < 0 || col >= width;
    }

    private boolean isSea(String mapString) {
        return mapString.equals("0");
    }

    private void markLand(int initRow, int initCol, int islandNumber,
                          String[][] map, boolean[][] isVisited, int[][] numberMap) {
        isVisited[initRow][initCol] = true;
        numberMap[initRow][initCol] = islandNumber;
        Queue<Coordinate> bfsQueue = new LinkedList<>();
        bfsQueue.add(new Coordinate(initRow, initCol));

        while (!bfsQueue.isEmpty()) {
            Coordinate currentCoordinate = bfsQueue.poll();
            for (int d = 0; d < 4; d++) {
                int nextRow = currentCoordinate.row + dy[d];
                int nextCol = currentCoordinate.col + dx[d];
                if (isOutOfBorder(nextRow, nextCol, numberMap.length, numberMap[0].length) ||
                        isSea(map[nextRow][nextCol]) ||
                        isVisited[nextRow][nextCol]) {
                    continue;
                }
                isVisited[nextRow][nextCol] = true;
                numberMap[nextRow][nextCol] = islandNumber;
                bfsQueue.offer(new Coordinate(nextRow, nextCol));
            }
        }
    }

    private int[][] numberMap(String[][] map) {
        int height = map.length;
        int width = map[0].length;
        int[][] result = new int[height][width];
        int islandCount = 0;

        boolean[][] isVisited = new boolean[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isSea(map[y][x]) || isVisited[y][x]) {
                    continue;
                }
                markLand(y, x, ++islandCount, map, isVisited, result);
            }
        }

        this.count = islandCount;
        return result;
    }
}

class Bridge {

    int[][] bridgeCosts;

    public Bridge(Islands islands, int INF) {
        this.bridgeCosts = calculateBridgeCosts(islands, INF);
    }

    private void scanHorizontally(Islands islands, int[][] bridgeCostsMatrix) {
        for (int row = 0; row < islands.map.length; row++) {
            int prevIslandNumber = 0;
            int prevIslandLocation = 0;
            for (int col = 0; col < islands.map[0].length; col++) {
                int currIslandNumber = islands.map[row][col];
                int bridgeLength = col - prevIslandLocation - 1;
                if (currIslandNumber == 0) {
                    continue;
                }
                if (prevIslandNumber != 0 && currIslandNumber != prevIslandNumber && bridgeLength >= 2) {
                    bridgeLength = Math.min(bridgeLength, bridgeCostsMatrix[prevIslandNumber][currIslandNumber]);
                    bridgeCostsMatrix[prevIslandNumber][currIslandNumber] = bridgeLength;
                    bridgeCostsMatrix[currIslandNumber][prevIslandNumber] = bridgeLength;
                }
                prevIslandNumber = currIslandNumber;
                prevIslandLocation = col;
            }
        }
    }

    private void scanVertically(Islands islands, int[][] bridgeCostsMatrix) {
        for (int col = 0; col < islands.map[0].length; col++) {
            int prevIslandNumber = 0;
            int prevIslandLocation = 0;
            for (int row = 0; row < islands.map.length; row++) {
                int currIslandNumber = islands.map[row][col];
                int bridgeLength = row - prevIslandLocation - 1;
                if (currIslandNumber == 0) {
                    continue;
                }
                if (prevIslandNumber != 0 && currIslandNumber != prevIslandNumber && bridgeLength >= 2) {
                    bridgeLength = Math.min(bridgeLength, bridgeCostsMatrix[prevIslandNumber][currIslandNumber]);
                    bridgeCostsMatrix[prevIslandNumber][currIslandNumber] = bridgeLength;
                    bridgeCostsMatrix[currIslandNumber][prevIslandNumber] = bridgeLength;
                }
                prevIslandNumber = currIslandNumber;
                prevIslandLocation = row;
            }
        }
    }

    private int[][] calculateBridgeCosts(Islands islands, int INF) {
        int matrixSize = islands.count + 1;
        int[][] bridgeCostsMatrix = new int[matrixSize][matrixSize];
        for (int[] row : bridgeCostsMatrix) {
            Arrays.fill(row, INF);
        }

        scanHorizontally(islands, bridgeCostsMatrix);
        scanVertically(islands, bridgeCostsMatrix);

        return bridgeCostsMatrix;
    }
}

class MinimumSpanningTree {

    static class Edge implements Comparable<Edge> {
        int endA;
        int endB;
        int cost;

        public Edge(int endA, int endB, int cost) {
            this.endA = endA;
            this.endB = endB;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge that) {
            return Integer.compare(this.cost, that.cost);
        }
    }

    private static class Group {
        private int[] group;
        private int groupCount;

        public Group(int count) {
            group = IntStream.range(0, count).toArray();
            groupCount = group.length - 1;
        }

        public int findGroupOf(int x) {
            if (x != group[x]) {
                group[x] = findGroupOf(group[x]);
            }
            return group[x];
        }

        public void unionGroupOf(int x, int y) {
            int groupX = findGroupOf(x);
            int groupY = findGroupOf(y);

            if (groupX < groupY) {
                group[groupX] = groupY;
            } else {
                group[groupY] = groupX;
            }
            groupCount--;
        }

        public boolean isOneGroup() {
            return groupCount == 1;
        }
    }

    int totalCost;
    boolean isAllConnected;

    MinimumSpanningTree(int[][] costMatrix, int INF) {
        List<Edge> edges = convertMatrixToList(costMatrix, INF);
        totalCost = calculateTotal(costMatrix.length, edges);
    }

    private List<Edge> convertMatrixToList(int[][] bridgeCostsMatrix, int INF) {
        List<Edge> result = new ArrayList<>();
        for (int islandA = 1; islandA < bridgeCostsMatrix.length; islandA++) {
            for (int islandB = islandA + 1; islandB < bridgeCostsMatrix.length; islandB++) {
                if (bridgeCostsMatrix[islandA][islandB] == INF) {
                    continue;
                }
                result.add(new Edge(islandA, islandB, bridgeCostsMatrix[islandA][islandB]));
            }
        }
        return result;
    }

    private int calculateTotal(int nodeCount, List<Edge> edges) {
        Group group = new Group(nodeCount);
        edges.sort(Comparator.naturalOrder());
        int total = 0;
        for (Edge edge : edges) {
            if (group.findGroupOf(edge.endA) != group.findGroupOf(edge.endB)) {
                group.unionGroupOf(edge.endA, edge.endB);
                total += edge.cost;
                if (group.isOneGroup()) {
                    break;
                }
            }
        }
        isAllConnected = group.groupCount == 1;
        return total;
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

    private static String[][] inputMatrix(int rowCount, int colCount) throws Exception {
        String[][] result = new String[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            result[row] = br.readLine().split(" ");
        }
        return result;
    }

    private static String solve(String[][] map) {
        int INF = 11;

        // 섬에 숫자 붙이기
        Islands islands = new Islands(map);
        // 다리 길이 인접 행렬로 뽑기
        Bridge bridge = new Bridge(islands, INF);
        // 크루스칼 알고리즘으로 최소 길이 합 구하기
        MinimumSpanningTree mst = new MinimumSpanningTree(bridge.bridgeCosts, INF);

        int result = -1;
        if (mst.isAllConnected) {
            result = mst.totalCost;
        }

        return Integer.toString(result);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            String[][] islandMap = inputMatrix(N, M);

            String answer = solve(islandMap);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

