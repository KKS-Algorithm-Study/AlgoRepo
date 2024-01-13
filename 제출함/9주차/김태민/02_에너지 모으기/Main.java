import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      구슬을 하나씩 없애가며 없앤 왼쪽 오른쪽 구슬의 곱을 더할 때
 *      최대 값을 구하는 문제
 *
 *      구슬의 최대 갯수가 10개이므로,
 *      재귀 브루트포스로 접근시 최대 연산 횟수 8! 이므로, 가능
 *
 *      연산 속도를 빠르게 하려면 직접 연결리스트를 구현해 구슬을 뺐다 넣었다 하는게 좋겠지만,
 *      연결리스트 구현에 시간 버리기 아깝고, 구슬 갯수가 적어 배열리스트로 풀어도 지장 없으므로
 *      그냥 배열에 넣었다 뺐다 하며 풀었다.
 *
 *  시간 복잡도:
 *      재귀이므로 O(N!) 인데,
 *      구슬을 넣었다 빼는 과정이 O(N)이므로
 *      O(N * N!)이다.
 */
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

    private static int[] inputNumberArray(int numberCount) throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static int findUnusedMarbleToLeft(int initIdx, int[] marbles) {
        for (int idx = initIdx - 1; idx >= 0; idx--) {
            if (marbles[idx] != 0) {
                return marbles[idx];
            }
        }
        return marbles[0];
    }

    private static int findUnusedMarbleToRight(int initIdx, int[] marbles) {
        for (int idx = initIdx + 1; idx < marbles.length; idx++) {
            if (marbles[idx] != 0) {
                return marbles[idx];
            }
        }
        return marbles[marbles.length - 1];
    }

    private static int getHighestEnergy(int useCount, int[] marbles, int totalEnergy, int maxEnergy) {

        if (useCount == 0) {
            return totalEnergy;
        }

        for (int idx = 1; idx < marbles.length - 1; idx++) {
            if (marbles[idx] == 0) {
                continue;
            }

            int usedMarble = marbles[idx];
            int leftMarble = findUnusedMarbleToLeft(idx, marbles);
            int rightMarble = findUnusedMarbleToRight(idx, marbles);

            marbles[idx] = 0;
            int resultEnergy = getHighestEnergy(useCount - 1, marbles, totalEnergy + (leftMarble * rightMarble),
                    maxEnergy);
            marbles[idx] = usedMarble;
            maxEnergy = Math.max(maxEnergy, resultEnergy);
        }

        return maxEnergy;
    }

    private static String solve(int[] marbles) {
        int result = getHighestEnergy(marbles.length - 2, marbles, 0, 0);
        return Integer.toString(result);
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[] numbers = inputNumberArray(N);

            String answer = solve(numbers);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}