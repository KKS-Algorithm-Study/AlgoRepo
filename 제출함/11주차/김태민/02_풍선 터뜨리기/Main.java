import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  문제 풀이 방법:
 *
 *  5개 에서 하나 터뜨렸을 때 나오는 숫자가
 *      1 == -4 == 5 => 시프트 0번
 *      2 == -3 == 6 => 시프트 1번
 *      3 == -2 == 7 => 시프트 2번
 *      4 == -1 == 8 => 시프트 3번
 *  즉, 음수일 땐 (숫자 % 남은 풍선 갯수) 만큼 시프트를 하면 되지만,
 *  양수일 땐 (숫자 - 1 % 남은 풍선 갯수) 만큼 시프트 해야 한다.
 *
 *  시간 복잡도:
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

    private static int[] inputNumbers(int numberCount) throws Exception {
        int[] result = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        return result;
    }

    private static int mod (int left, int right) {
        return (left % right + right) % right;
    }

    private static String solve(int[] balloonQuery) {
        Queue<int[]> balloons = IntStream.range(0, balloonQuery.length)
                .mapToObj(idx -> new int[] {idx + 1, balloonQuery[idx]})
                .collect(Collectors.toCollection(LinkedList::new));
        StringJoiner result = new StringJoiner(" ");

        while (true) {
            int[] balloon = balloons.poll();
            int balloonNumber = balloon[0];
            result.add(Integer.toString(balloonNumber));
            if (balloons.isEmpty()) {
                break;
            }
            int paperNumber = balloon[1];
            if (paperNumber > 0) {
                paperNumber--;
            }
            int shiftCount = mod(paperNumber, balloons.size());
            for (int i = 0; i < shiftCount; i++) {
                balloons.offer(balloons.poll());
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int[] balloons = inputNumbers(N);
            String answer = solve(balloons);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
