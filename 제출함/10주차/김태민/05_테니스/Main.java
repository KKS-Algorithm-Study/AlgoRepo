import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      테니스 경기 결과가 주어질 때, 각 경기 결과가 올바른지 판단하는 문제
 *          * 승자는 6점 이상 획득, 패자와 2점차 이상
 *          * 앞 두 세트는 동점이면 딱 1경기만 더 치르고, 마지막 세트는 듀스 끝날 때까지
 *          * federer 는 세트에서 지지 않음
 *          * 두 세트를 이기면 승, 경기 결과가 나오면 3세트를 진행하지 않음
 *
 *      다른 알고리즘은 필요 없으나,
 *      조건 분기가 많고, 지문에 숨어있으므로 잘 파악해서 풀어야 한다.
 *
 *      세트가 올바르지 않기 위한 조건
 *          * 승자의 점수가 5점 이하
 *          * 승자 6점이면, 패자가 5 or 6점
 *          * 승자 7점이면,
 *              * 1, 2 세트에서 패자가 0 ~ 4점
 *              * 3세트에서 패자가 0 ~ 4 or 6점
 *          * 승자 8점 이상이면,
 *              * 1, 2 세트인 모든 경우
 *              * 3세트면 2점차 이외의 모든 경우
 *
 *      매치가 올바르지 않기 위한 조건
 *          * 세트가 하나라도 올바르지 않음
 *          * 세트 패자 이름이 federer
 *          * 두 번 승리도 못함
 *          * 두 번 승리 했는데 게임이 더 있음
 *      이 조건을 뚫으면 da를, 못뚫으면 ne를 출력하면 된다.
 *
 *  시간 복잡도:
 *
 */
public class Main {


    private enum GameResult {
        INVALID("ne"), VALID("da");
        final String value;

        GameResult(String value) {
            this.value = value;
        }
    }

    private enum SetResult {
        INVALID, PLAYER1, PLAYER2;
    }

    static final int[][][] VALID_SET_POINT = new int[][][]{
            {},
            {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 1, 0},
            },
            {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 1, 0},
            },
            {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
            },
    };

    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static void tokenize() throws Exception {
        st = new StringTokenizer(br.readLine());
    }

    private static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }


    private static List<List<String>> inputMatrix(int rowCount) throws Exception {
        List<List<String>> result = new ArrayList<>();
        for (int row = 0; row < rowCount; row++) {
            List<String> info = Arrays.stream(br.readLine().split(" ")).toList();
            result.add(info);
        }
        return result;
    }


    private static SetResult getSetResult(int setNumber, int[] setPoint) {
        int player1Point = setPoint[0];
        int player2Point = setPoint[1];
        int winnerPoint = Math.max(player1Point, player2Point);
        int loserPoint = Math.min(player1Point, player2Point);
        boolean isValidSet = true;

        if (winnerPoint <= 7) {
            if (VALID_SET_POINT[setNumber][winnerPoint][loserPoint] == 0) {
                isValidSet = false;
            }
        } else {
            if (setNumber <= 2 || winnerPoint - loserPoint != 2) {
                isValidSet = false;
            }
        }

        if (!isValidSet) {
            return SetResult.INVALID;
        }
        if (player1Point == winnerPoint) {
            return SetResult.PLAYER1;
        } else {
            return SetResult.PLAYER2;
        }
    }

    private static boolean didChampionLose(String[] players, SetResult setResult) {
        String loserPlayer;
        if (setResult == SetResult.PLAYER1) {
            loserPlayer = players[1];
        } else {
            loserPlayer = players[0];
        }
        if (loserPlayer.equals("federer")) {
            return true;
        }
        return false;
    }

    private static boolean hasGameEndedWithTwoPoint(SetResult[] setResults) {
        boolean result = false;
        EnumMap<SetResult, Integer> pointCount = new EnumMap<>(SetResult.class);
        for (SetResult setResult : setResults) {
            if (result) {
                return false;
            }
            pointCount.put(setResult, pointCount.getOrDefault(setResult, 0) + 1);
            if (pointCount.get(setResult) == 2) {
                result = true;
            }
        }
        return result;
    }

    // da 나 ne를 반환
    private static String getMatchResult(String[] players, List<String> setPoints) {
        int setNumber = 0;
        SetResult[] setResults = new SetResult[setPoints.size()];

        for (String setPoint : setPoints) {
            setNumber++;
            int[] setPointNumber = Arrays.stream(setPoint.split(":"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            SetResult setResult = getSetResult(setNumber, setPointNumber);

            if (setResult == SetResult.INVALID || didChampionLose(players, setResult)) {
                return GameResult.INVALID.value;
            }
            setResults[setNumber - 1] = setResult;
        }

        if (!hasGameEndedWithTwoPoint(setResults)) {
            return GameResult.INVALID.value;
        }

        return GameResult.VALID.value;
    }

    private static String solve(String[] players, List<List<String>> gamePoints) {
        StringJoiner result = new StringJoiner("\n");
        for (List<String> setPoints : gamePoints) {
            result.add(getMatchResult(players, setPoints));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            String[] players = br.readLine().split(" ");
            tokenize();
            int N = nextInt();
            List<List<String>> points = inputMatrix(N);
            String answer = solve(players, points);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
