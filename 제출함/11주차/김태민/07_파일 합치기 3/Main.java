import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *  문제 풀이 방법:
 *
 *      파일들이 하나가 될 때까지 가장 작은 두 파일끼리 합치고,
 *      그 합친 파일들의 크기 누적합을 구해주면 된다.
 *
 *      가장 작은 두 파일을 꺼내기 위해서는 힙 자료구조를 사용하면 된다.
 *
 *      파일이 최대 1,000,000개, 크기는 최대 10,000 이므로 long을 써야 한다.
 *
 *  시간 복잡도:
 *
 *      T개의 테스트케이스마다 K개의 파일들을 모두 힙 자료구조에 넣었다 빼는 작업을 하므로
 *      O(T * KlogK) 이다.
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


    private static long getMergeCost(List<Integer> files) {
        long result = 0;
        PriorityQueue<Long> mergeQueue = files.stream()
                .map(i -> (long) i)
                .collect(Collectors.toCollection(PriorityQueue::new));
        while (mergeQueue.size() > 1) {
            long mergedFile = mergeQueue.poll() + mergeQueue.poll();
            result += mergedFile;
            mergeQueue.offer(mergedFile);
        }
        return result;
    }

    private static String solve(List<List<Integer>> filesQuery) {
        StringJoiner result = new StringJoiner("\n");
        for (List<Integer> files: filesQuery) {
            long mergeCost = getMergeCost(files);
            result.add(Long.toString(mergeCost));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int T = nextInt();
            List<List<Integer>> files = inputMultipleNumbers(T);
            String answer = solve(files);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<List<Integer>> inputMultipleNumbers(int rowCount) throws Exception {
        List<List<Integer>> result = new ArrayList<>();
        for (int idx = 0; idx < rowCount; idx++) {
            br.readLine();
            List<Integer> row = Arrays.stream(br.readLine().split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            result.add(row);
        }
        return result;
    }
}
