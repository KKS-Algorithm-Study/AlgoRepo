import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      집들과 치킨집들이 표시된 도시의 지도에서 남길 치킨집 M개 이외 전부 닫아야 하는데,
 *      집과 가장 가까운 치킨집의 거리의 합이 최소가 되는 경우를 출력하는 문제
 *
 *      만약 남길 치킨집의 모든 조합을 뽑아 모든 집과의 거리를 비교하는 연산을 한다면,
 *      치킨집 최대 갯수가 13개이므로, 13개 중 임의의 갯수를 뽑는 최대 연산 횟수는 13C7 = 약 1300이고,
 *      거기에 집의 최대 갯수는 100개이므로, 각 조합으로 뽑은 치킨집과 집의 거리를 구하는 데 필요한 연산 횟수는 7 * 100 = 700이다.
 *      1300 * 700 = 910,000 이므로, 조합으로 풀 수 있다.
 *
 *  시간 복잡도:
 *      최대 치킨집 수가 K개라고 하면,
 *      위에서 설명했듯, O(KCM * N * M)
 */
public class Main {
    static int INF = 51;
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

    private static List<List<Integer>> getHouseLocations(int[][] city) {
        return getLocationsOf(city, 1);
    }

    private static List<List<Integer>> getStoreLocations(int[][] city) {
        return getLocationsOf(city, 2);
    }

    private static List<List<Integer>> getLocationsOf(int[][] city, int building) {
        List<List<Integer>> result = new ArrayList<>();
        for (int row = 0; row < city.length; row++) {
            for (int col = 0; col < city[0].length; col++) {
                if (city[row][col] == building) {
                    result.add(List.of(row, col));
                }
            }
        }
        return result;
    }

    private static List<List<Integer>> getDistances(List<List<Integer>> houseLocations,
                                                    List<List<Integer>> storeLocations) {
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> houseLocation : houseLocations) {
            List<Integer> distances = new ArrayList<>();
            result.add(distances);
            for (List<Integer> storeLocation : storeLocations) {
                int verticalDistance = Math.abs(storeLocation.get(0) - houseLocation.get(0));
                int horizontalDistance = Math.abs(storeLocation.get(1) - houseLocation.get(1));
                distances.add(verticalDistance + horizontalDistance);
            }
        }
        return result;
    }

    private static void saveCombination(int count, int index, int range, ArrayList<Integer> combi,
                                        List<List<Integer>> result) {
        if (count == 0) {
            result.add(new ArrayList<>(combi));
            return;
        }

        for (int number = index; number <= range - count; number++) {
            combi.add(number);
            saveCombination(count - 1, number + 1, range, combi, result);
            combi.remove(combi.size() - 1);
        }

    }

    private static List<List<Integer>> getCombinationOfRange(int range, int count) {
        List<List<Integer>> result = new ArrayList<>();
        saveCombination(count, 0, range, new ArrayList<>(), result);
        return result;
    }

    private static int getDistanceSum(List<List<Integer>> houseToStoreDistances, List<Integer> selectedStores) {
        int result = 0;
        for (List<Integer> storeDistances: houseToStoreDistances) {
            int minDistance = Integer.MAX_VALUE;
            for (int store: selectedStores) {
                minDistance = Math.min(minDistance, storeDistances.get(store));
            }
            result += minDistance;
        }
        return result;
    }

    private static String solve(int[][] city, int storeCount) {
        List<List<Integer>> houseLocations = getHouseLocations(city);
        List<List<Integer>> storeLocations = getStoreLocations(city);
        List<List<Integer>> houseToStoreDistance = getDistances(houseLocations, storeLocations);
        List<List<Integer>> storeCombinations = getCombinationOfRange(storeLocations.size(), storeCount);

        int totalMinimumDistance = Integer.MAX_VALUE;
        for (List<Integer> combination : storeCombinations) {
            int maxDistance = getDistanceSum(houseToStoreDistance, combination);
            totalMinimumDistance = Math.min(totalMinimumDistance, maxDistance);
        }
        return Integer.toString(totalMinimumDistance);

    }

    public static void main(String[] args) {
        try {
            tokenize();
            int N = nextInt();
            int M = nextInt();
            int[][] city = inputNumberMatrix(N, N);
            String answer = solve(city, M);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}