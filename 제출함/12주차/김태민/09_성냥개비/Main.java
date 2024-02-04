import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *
 *      각 숫자에 사용되는 성냥개비 수는 다음과 같다:
 *
 *      숫자: 0 1 2 3 4 5 6 7 8 9
 *      갯수: 6 2 5 5 4 5 6 3 7 6
 *
 *      정해진 성냥개비 갯수로 최대한 큰 수를 만들려면 숫자 하나를 만드는데 성냥개비를 최대한 아껴야 한다.
 *      따라서 숫자 하나에 성냥개비 2개를 쓰는 1을 쓰는게 유리하다.
 *      만약 갯수라 홀수라 1개가 남으면 가장 앞 숫자를 7로 만들면 된다.
 *
 *      반대로, 정해진 성냥개비 갯수로 최대한 작은 수를 만들려면 성냥개비를 숫자 하나에 꽉 채워 넣어야 한다.
 *      따라서 숫자 하나에 성냥개비 7개를 쓰는 8을 쓰는 게 유리하다.
 *      이러면 7의 배수마다 8이 늘어나는 구조가 되는데, 7의 배수가 아닌 수는 다른 작은 수로 만들어 채워지게 된다.
 *
 *  시간 복잡도:
 *      숫자를 만드는 각 연산은 O(1)이므로
 *      O(T) 만큼 걸린다.
 */
public class Main {

    static String[] UNTIL_FOURTEEN = {"", "", "1", "7", "4", "2", "6", "8", "10", "18", "22", "20", "28", "68", "88"};
    static String[] AFTER_FOURTEEN = {"108", "188", "200", "208", "288", "688", "888"};
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    private static List<Integer> inputNumberList(int numberCount) throws Exception {
        List<Integer> result = new ArrayList<>();
        for (int n = 0; n < numberCount; n++) {
            tokenize();
            result.add(nextInt());
        }
        return result;
    }

    private static String makeSmallestNumber(int matchCount) {
        StringBuilder result = new StringBuilder();
        if (matchCount < 15) {
            result.append(UNTIL_FOURTEEN[matchCount]);
        } else {
            matchCount -= 15;
            result.append(AFTER_FOURTEEN[matchCount % 7]);
            result.append("8".repeat(matchCount / 7));
        }
        return result.toString();
    }

    private static String makeLargestNumber(int matchCount) {
        StringBuilder result = new StringBuilder();
        if (matchCount % 2 == 1) {
            result.append("7");
            matchCount -= 3;
        }
        result.append("1".repeat(matchCount / 2));
        return result.toString();
    }

    private static String solve(List<Integer> cases) {

        StringJoiner result = new StringJoiner("\n");
        for(int matchCount: cases) {
            String smallestNumber = makeSmallestNumber(matchCount);
            String largestNumber = makeLargestNumber(matchCount);
            result.add(smallestNumber + " " + largestNumber);
        }
        return result.toString();
    }


    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            List<Integer> cases = inputNumberList(N);
            String answer = solve(cases);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
