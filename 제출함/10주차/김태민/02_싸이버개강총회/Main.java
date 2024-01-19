import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *  문제 풀이 방법:
 *      총회 참석자 수를 출력하는 문제
 *      참석자 조건은
 *          1. 시작시간 이전까지 채팅을 쳤다
 *          2. 총회 종료 이후, 스트리밍 종료 이전까지 채팅을 쳤다
 *
 *      문자열을 받아 시간을 파싱할 줄 아는지,
 *      해시셋을 사용해 중복처리를 할 줄 아는지 묻는 문제
 *
 *  시간 복잡도:
 *      쿼리 갯수가 Q일때 O(Q)
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

    private static List<String> inputQueriesUnknownCount() throws Exception {
        List<String> result = new ArrayList<>();
        while (true) {
            String input = br.readLine();
            if (input != null & !input.isEmpty()) {
                result.add(input.strip());
            } else {
                break;
            }
        }
        return result;
    }
    private static String[][] inputQueries (int rowCount, int colCount) throws Exception {
        String[][] result = new String[rowCount][colCount];
        for (int row = 0; row < rowCount; row++) {
            result[row] = br.readLine().split(" ");
        }
        return result;
    }

    private static int parseTime(String time) {
        String[] splitTime = time.split(":");
        return Integer.parseInt(splitTime[0]) * 60 + Integer.parseInt(splitTime[1]);
    }

    private static Set<String> getPeopleArrival(List<String> chats, int streamInitTime) {
        Set<String> result = new HashSet<>();
        for (String chat: chats) {
            String[] chatLog = chat.split(" ");
            if (parseTime(chatLog[0]) <= streamInitTime) {
                result.add(chatLog[1]);
            } else {
                break;
            }
        }
        return result;
    }

    private static int countPeopleLeave(List<String> chats, int streamDoneTime, int streamDeadTime, Set<String> peopleOnTime) {
        int result = 0;
        chats.sort(Comparator.reverseOrder());
        for (String chat: chats) {
            String[] chatLog = chat.split(" ");
            int chatTime = parseTime(chatLog[0]);
            String chatId = chatLog[1];
            if (chatTime > streamDeadTime) {
                continue;
            }
            if (chatTime < streamDoneTime) {
                break;
            }
            if (peopleOnTime.contains(chatId)) {
                peopleOnTime.remove(chatId);
                result++;
            }
        }
        return result;
    }

    private static String solve(String[] times, List<String> chats) {
        int streamInitTime = parseTime(times[0]);
        int streamDoneTime = parseTime(times[1]);
        int streamDeadTime = parseTime(times[2]);

        Set<String> peopleOnTime = getPeopleArrival(chats, streamInitTime);
        int attendanceCount = countPeopleLeave(chats, streamDoneTime, streamDeadTime, peopleOnTime);
        return Integer.toString(attendanceCount);
    }

    public static void main(String[] args) {
        try {
            String[] times = inputQueries(1, 3)[0];
            List<String> chats = inputQueriesUnknownCount();
            String answer = solve(times, chats);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
