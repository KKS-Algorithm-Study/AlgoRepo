import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      외판원 순회 문제로, 어느 한 도시에서 시작해 모든 도시를 방문해 시작 도시로 돌아오는 최소 비용을 구하는 문제다.
 *
 *      최소 비용을 구해야하나, 순회해야한다는 조건이 있어 다익스트라는 불가능하다.
 *      따라서 모든 정점을 방문하도록 DFS/BFS를 해야한다.
 *
 *      이 문제를 위해선 재귀 방식의 DFS를 선택했는데,
 *      그 이유는 백트래킹의 가지치기처럼 이미 계산되었던 "동일한 경우"는 더 이상 계산하지 않도록 하기 위해서이다.
 *
 *      이 문제에서의 "동일한 경우"를 판단하는 기준은 "도착하는 도시와 경유한 도시들의 조합이 동일한가?" 이다
 *      예를 들어, 0>1>2 를 경유해 3으로 가는 경우와 0>2>1 을 경유해 3으로 가는 경우는 동일한 경우이다.
 *      이 동일한 경우들을 저장하고, 그 중 최소값만 사용하기 위해 dp를 사용했다.
 *
 *      따라서 dp 배열은 다음과 같은 형태를 가진다:
 *      dp[도착한 도시][경유한 도시의 조합]
 *
 *      이렇게 경유한 도시의 조합을 DFS 메서드의 매개변수로 넣어줘야하는데,
 *      이 조합들을 HashSet으로 관리하는 것보다 비트마스킹으로 관리하는 편이 효율적이므로,
 *      비트마스킹으로 하는 게 좋다.
 *
 *  시간 복잡도:
 *      외판원 순회는 모든 도착 도시와 경유 도시 조합마다 (== dp 배열의 모든 칸마다 == N * 2**N)
 *      도시를 순회하며 방문 여부를 판단(== N)하기 때문에
 *      O(N**2 * 2**N)이 나온다.
 */

public class Main {

    static int INF = Integer.MAX_VALUE;
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
            tokenize();
            for (int col = 0; col < colCount; col++) {
                result[row][col] = nextInt();
            }
        }
        return result;
    }

    private static int getLowestCost(int currCity, int currVisitedBit, int initCity, int[][] fees, int[][] costDp) {

        int cityCount = costDp.length;
        if (currVisitedBit == (1 << cityCount) - 1) {
            costDp[currCity][currVisitedBit] = fees[currCity][initCity];
            return fees[currCity][initCity];
        }
        if (costDp[currCity][currVisitedBit] != INF) {
            return costDp[currCity][currVisitedBit];
        }

        for (int nextCity = 0; nextCity < cityCount; nextCity++) {
            if ((1 << nextCity & currVisitedBit) != 0) {
                continue;
            }
            int nextVisitedBit = currVisitedBit | (1 << nextCity);
            int nextFee = fees[currCity][nextCity];
            int dfsResult = getLowestCost(nextCity, nextVisitedBit, initCity, fees, costDp) + nextFee;
            costDp[currCity][currVisitedBit] = Math.min(costDp[currCity][currVisitedBit], dfsResult);
        }

        return costDp[currCity][currVisitedBit];
    }

    private static String solve(int cityCount, int[][] fees) {

        int bitCount = 1 << cityCount;
        int[][] costDp = new int[cityCount][bitCount];
        for (int[] row : costDp) {
            Arrays.fill(row, INF);
        }

        // 어느 도시에서 시작할 수는 있지만, 순회해서 돌아와야 함 =>
        // 0>1>2>3>0 순으로 방문하나, 1>2>3>0>1 순으로 방문하나 비용은 같음 =>
        // 한 도시에서 시작해도 결과는 같음
        int initCity = 0;
        int initVisitBit = 1 << initCity;
        int lowestCost = getLowestCost(initCity, initVisitBit, initCity, fees, costDp);

        return Integer.toString(lowestCost);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[][] fees = inputNumberMatrix(N, N);
            String answer = solve(N, fees);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

