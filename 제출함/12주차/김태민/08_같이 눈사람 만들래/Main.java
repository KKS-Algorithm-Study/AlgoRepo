import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *  문제 풀이 방법:
 *
 *      일반적인 조합으로 4개를 고르는 방법은 600 C 4 = 약 50억으로 시간초과다.
 *
 *      네 개의 눈공으로 만든 눈사람의 최소 크기 차이를 구하려면
 *      크기순으로 정렬해 [a, b, b, a] 로 묶으면 된다.
 *
 *      이 점을 이용해, 정렬된 눈공 배열에서 임의의 두 지점을 잡아 a 눈사람으로 만들고,
 *      그 바로 안쪽 인덱스의 두 눈공을 b 눈사람으로 만들어 차이를 구하고,
 *      차이가 0보다 크면 b 눈사람의 오른쪽 눈공을 왼쪽으로, 작으면 왼쪽 눈공을 오른쪽으로 만들면서
 *      눈사람 크기를 구하면 된다.
 *
 *      이 경우 임의의 두 지점을 잡는건 O(N**2),
 *      그 안에서 하는 투포인터는 O(N)이므로
 *      총 O(N**3)이 된다.
 *
 *  시간 복잡도:
 *      O(N**3)
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

    private static List<Integer> inputNumberArray(int numberCount) throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static String solve(List<Integer> sizes) {
        sizes.sort(Comparator.naturalOrder());
        int minDiff = Integer.MAX_VALUE;
        for (int snowATop = 0; snowATop < sizes.size() - 3; snowATop++) {
            for (int snowABot = snowATop + 3; snowABot < sizes.size(); snowABot++) {
                int snowASize = sizes.get(snowATop) + sizes.get(snowABot);
                int snowBTop = snowATop + 1;
                int snowBBot = snowABot - 1;
                while (snowBTop < snowBBot) {
                    int snowBSize = sizes.get(snowBTop) + sizes.get(snowBBot);
                    int diff = snowASize - snowBSize;
                    minDiff = Math.min(minDiff, Math.abs(diff));
                    if (diff > 0) {
                        snowBTop++;
                    } else {
                        snowBBot--;
                    }
                }
            }
        }
        return Integer.toString(minDiff);
    }


    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            List<Integer> sizes = inputNumberArray(N);
            String answer = solve(sizes);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
