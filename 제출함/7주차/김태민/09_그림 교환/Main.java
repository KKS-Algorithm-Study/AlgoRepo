import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 접근 방법:
 *      그림을 팔 때, 이미 소장했던 사람에게 못팔고, 산 가격보다 싼 값에는 못 판다고 할 때
 *      최대한 많은 사람들이 그림을 소장했을 때의 사람 수를 출력하는 문제
 *
 *      같은 사람을 지나쳐 왔어도 마지막에 산 사람의 가격은 다를 수 있다
 *      예를 들어 다음과 같은 예제가 있다면
 *      01100
 *      00430
 *      02050
 *      00004
 *      00000
 *      1 > 2 > 3 > 4 순서로 거쳐가면 4번 사람은 5원에 샀으므로 5번 사람에게 4원에 팔 수 없지만,
 *      1 > 3 > 2 > 4 순서로 거쳐가면 4번 사람은 3원에 샀으므로 5번 사람에게 4원에 팔 수 있다.
 *      따라서, TSP 문제와 마찬가지로 dp는 다음과 같이 "거쳐온 사람의 조합"을 인덱스로 저장해야한다.
 *      dp[마지막 소장한 사람][거쳐온 사람 조합]
 *
 *      이 문제는 재귀 DFS로 풀 수도 있고 BFS로도 풀 수 있는데,
 *      이 풀이는 BFS로 해결한 것으로,
 *      "dp[마지막 소장한 사람][거쳐온 사람 조합] = 최소 가격" 을 저장해
 *      BFS의 depth == 소장한 사람 수 => 정답으로 출력하는 방식을 썼는데,
 *
 *      원준님은 재귀 DFS 방식으로 풀어서
 *      "dp[마지막 소장한 사람][거쳐온 사람 조합] = 최대 소장 인원"을 저장해 풀었으니
 *      해당 코드도 참고하면 좋을 것 같다.
 *
 *  시간 복잡도:
 *      TSP 문제처럼 dp의 총 칸수가 (사람 수 * 사람의 조합 수) == N * 2**N이고,
 *      각 칸마다 N만큼 탐색하므로,
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
            int[] numbers = Arrays.stream(br.readLine().strip().split(""))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            for (int col = 0; col < colCount; col++) {
                result[row][col] = numbers[col];
            }
        }
        return result;
    }

    private static int getMaxPeopleSold(int initPerson, int initVisitBit, int[][] prices, int[][] priceDp) {
        priceDp[initPerson][initVisitBit] = 0;
        List<Entry> curQue = new ArrayList<>();
        curQue.add(new Entry(initPerson, initVisitBit));

        int soldPeopleCount = 1;
        while (true) {
            List<Entry> nxtQue = new ArrayList<>();
            for (Entry entry: curQue) {
                int currPerson = entry.currPerson;
                int currVisitBit = entry.currVisitBit;
                for (int nextPerson = 0; nextPerson < priceDp.length; nextPerson++) {
                    // 이미 팔았던 사람이면 스킵 || 이전에 같은 사람들을 경유해서 그림을 산 값이 지금 사는 값보다 싸다면 스킵
                    if ((currVisitBit & 1 << nextPerson) != 0 || priceDp[currPerson][currVisitBit] > prices[currPerson][nextPerson]) {
                        continue;
                    }
                    // nxtQue에 중복 Entry를 넣어주지 않기 위해
                    // 이번이 처음 계산하는 경우, nxtQue에 넣어주기
                    int nextVisitBit = currVisitBit | 1 << nextPerson;
                    if (priceDp[nextPerson][nextVisitBit] == INF) {
                        nxtQue.add(new Entry(nextPerson, nextVisitBit));
                    }
                    // 다음 사람이 살 값을 최솟값으로 갱신
                    priceDp[nextPerson][nextVisitBit] = Math.min(priceDp[nextPerson][nextVisitBit], prices[currPerson][nextPerson]);
                }
            }
            if (nxtQue.isEmpty()) {
                break;
            }
            curQue = nxtQue;
            soldPeopleCount++;
        }
        return soldPeopleCount;
    }


    private static String solve(int peopleCount, int[][] prices) {

        int bitCount = 1 << peopleCount;
        int[][] priceDp = new int[peopleCount][bitCount];
        for (int[] row : priceDp) {
            Arrays.fill(row, INF);
        }

        int initPerson = 0;
        int initVisitBit = 1 << initPerson;
        int maxPeopleSold = getMaxPeopleSold(initPerson, initVisitBit, prices, priceDp);

        return Integer.toString(maxPeopleSold);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[][] prices = inputNumberMatrix(N, N);
            String answer = solve(N, prices);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Entry {
    int currPerson;
    int currVisitBit;

    public Entry(int currPerson, int currVisitBit) {
        this.currPerson = currPerson;
        this.currVisitBit = currVisitBit;
    }
}