import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  문제 풀이 방법:
 *
 *      최대한 많이 배송하려면, 멀리 가는 상자보다 얼마 안 가는 상자를 배송해야 한다.
 *
 *      따라서 각 마을에 도착할 때 마다
 *      배송할 상자들을 도착지가 가까운 순으로 정렬하고,
 *      택배 상자들을 꽉 찰때까지 먼저 싣고,
 *      꽉 찼다면 현재 실려있는 택배 중 거리가 더 먼 것들을 버리고 가까운 것들을 더 실으면 된다.
 *
 *  더 간단한 풀이 방법:
 *      택배를 버릴 필요 없이
 *      (도착마을, 출발마을) 순서로 정렬해, 각 도착마을까지 최대한 배송할 수 있는 택배를 구해주고 모두 더해주면 답이 나온다고 한다.
 *
 *  시간 복잡도:
 *      O(N)
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

    private static int[] inputNumberList(int numberCount) throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static int[][] inputNumberMatrix(int rowCount, int colCount) throws Exception {
        int[][] result = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            result[row] = inputNumberList(colCount);
        }
        return result;
    }

    private static List<Map<Integer, Integer>> makeDeliveryQueue(int cityCount, int[][] deliveryQueries) {
        List<Map<Integer, Integer>> deliveryQueue = new ArrayList<>();
        for (int i = 0; i <= cityCount; i++) {
            deliveryQueue.add(new HashMap<>());
        }

        for (int[] query: deliveryQueries) {
            int departingCity = query[0];
            int arrivingCity = query[1];
            int deliveryAmount = query[2];
            Map<Integer, Integer> boxMap = deliveryQueue.get(departingCity);
            boxMap.put(arrivingCity, boxMap.getOrDefault(arrivingCity, 0) + deliveryAmount);
        }
        return deliveryQueue;
    }

    private static String solve(int cityCount, int truckCapacity, int[][] deliveryQueries) {
        int result = 0;
        List<Map<Integer, Integer>> deliveryWaiting = makeDeliveryQueue(cityCount, deliveryQueries);
        int[] deliveringBoxes = IntStream.rangeClosed(0, cityCount)
                .map(e -> 0)
                .toArray();
        int truckSpaceLeft = truckCapacity;

        for (int fromCity = 1; fromCity <= cityCount; fromCity++) {
            // 택배 하차
            truckSpaceLeft += deliveringBoxes[fromCity];
            result += deliveringBoxes[fromCity];
//            deliveringBoxes[fromCity] = 0;

            // 택배 상차
            Map<Integer, Integer> deliveryQueue = deliveryWaiting.get(fromCity);
            for (int toCity : deliveryQueue.keySet().stream().sorted().collect(Collectors.toList())) {
                int deliveryAmount = deliveryQueue.get(toCity);

                // 트럭에 들어가는 경우
                if (truckSpaceLeft - deliveryAmount >= 0) {
                    truckSpaceLeft -= deliveryAmount;
                    deliveringBoxes[toCity] += deliveryAmount;
                    continue;
                }
                // 안들어가는 경우
                deliveringBoxes[toCity] += truckSpaceLeft;
                deliveryAmount -= truckSpaceLeft;
                truckSpaceLeft = 0;
                if (deliveryAmount == 0) {
                    continue;
                }

                // 멀리 가는 택배 버리고 아직 남아있는 가까운 걸로 채워넣기
                for (int farCity = cityCount; farCity > toCity; farCity--) {
                    if (deliveringBoxes[farCity] == 0) {
                        continue;
                    }
                    if (deliveringBoxes[farCity] < deliveryAmount) {
                        deliveringBoxes[toCity] += deliveringBoxes[farCity];
                        deliveryAmount -= deliveringBoxes[farCity];
                        deliveringBoxes[farCity] = 0;
                        continue;
                    }
                    deliveringBoxes[farCity] -= deliveryAmount;
                    deliveringBoxes[toCity] += deliveryAmount;
                    break;
                }
            }
        }

        return Integer.toString(result);
    }


    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int C = nextInt();
            tokenize();
            int M = nextInt();
            int[][] queries = inputNumberMatrix(M, 3);
            String answer = solve(N, C, queries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
