import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *  문제 풀이 방법:
 *      입력받은 유저 정보와 파일 정보를 토대로 유저가 파일에 대한 특정 조작 권한이 있는지 출력하는 문제
 *      단, 수정 권한이 있으면 읽기 권한도 있다.
 *
 *      특별한 알고리즘은 필요 없지만,
 *      문제 조건에 따라 각각의 정보를 어떤 자료 구조로 저장하고, 어떻게 불러와 처리할지에 대한 판단을 빠르게 내릴 수 있는지 보는 문제다
 *
 *      유저 저장은 해시맵<그룹명 : 해시셋<유저명>> 으로 저장
 *      파일 저장은 해시맵<파일명 : 리스트<파일정보>> 로 저장
 *
 *      OWNER 확인은 파일 실행자와 파일 소유자를 비교하면 되고,
 *      OWNER_GROUP 확인은 파일 소유 그룹으로 유저 그룹 해시맵을 조회, 실행자가 그 안에 있는지 확인하면 되고,
 *      OTHER 확인은 위 두개에서 걸러지지 않았을 때로 확인할 수 있다.
 *
 *      권한의 종류는 0~7까지 총 8개인데, 이를 이진수로 표현하면
 *      1의 자리는 읽기, 2의 자리는 수정, 4의 자리는 실행임을 알 수있다.
 *      따라서 권한 확인시 비트 연산을 활용하면 복잡한 조건문 없이 확인이 가능하다.
 *      유저 신분에 맞는 파일의 권한 숫자를 가져와 다음 연산을 확인하면 된다:
 *          읽기 : 권한숫자 & 6 != 0 (수정권한이 있으면 읽기 권한도 있기 때문)
 *          수정 : 권한숫자 & 2 != 0
 *          실행 : 권한숫자 & 1 != 0
 *
 *  시간 복잡도:
 *      각각의 연산은 O(1) 이기 때문에
 *      O(U + F + Q)이다.
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


    private static List<List<String>> inputMatrix(int rowCount) throws Exception {
        List<List<String>> result = new ArrayList<>();
        for (int row = 0; row < rowCount; row++) {
            List<String> info = Arrays.stream(br.readLine().split(" "))
                    .collect(Collectors.toList());
            result.add(info);
        }
        return result;
    }

    private static String solve(List<List<String>> userQueries,
                                List<List<String>> fileQueries,
                                List<List<String>> operationQueries) {
        UserGroup group = new UserGroup(userQueries);
        FileExplorer explorer = new FileExplorer(group, fileQueries);
        StringJoiner result = new StringJoiner("\n");
        for (List<String> operationQuery : operationQueries) {
            if (explorer.canOperate(operationQuery)) {
                result.add("1");
            } else {
                result.add("0");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        try {
            tokenize();
            int U = nextInt();
            int F = nextInt();
            List<List<String>> userQueries = inputMatrix(U);
            List<List<String>> fileQueries = inputMatrix(F);
            tokenize();
            int Q = nextInt();
            List<List<String>> operationQueries = inputMatrix(Q);
            String answer = solve(userQueries, fileQueries, operationQueries);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UserGroup {
    private Map<String, Set<String>> group;

    UserGroup(List<List<String>> userQueries) {
        this.group = new HashMap<>();
        for (List<String> userQuery : userQueries) {
            String userName = userQuery.get(0);
            Set<String> userGroup = group.getOrDefault(userName, new HashSet<>());
            userGroup.add(userName);
            group.put(userName, userGroup);
            if (userQuery.size() == 1) {
                continue;
            }
            List<String> groupNames = Arrays.stream(userQuery.get(1).split(",")).collect(Collectors.toList());
            for (String groupName : groupNames) {
                userGroup = group.getOrDefault(groupName, new HashSet<>());
                userGroup.add(userName);
                group.put(groupName, userGroup);
            }
        }
    }

    public Set<String> findGroup(String groupName) {
        return group.get(groupName);
    }
}

class FileExplorer {

    private UserGroup group;
    private Map<String, File> permissions;
    private static Map<String, Integer> operationMap;

    {
        operationMap = new HashMap<>();
        operationMap.put("R", 6);
        operationMap.put("W", 2);
        operationMap.put("X", 1);
    }

    public FileExplorer(UserGroup group, List<List<String>> fileQueries) {
        this.group = group;
        this.permissions = new HashMap<>();
        for (List<String> fileQuery : fileQueries) {
            File file = new File(fileQuery);
            permissions.put(fileQuery.get(0), file);
        }
    }

    public boolean canOperate(List<String> operationQuery) {
        String filename = operationQuery.get(1);
        File file = permissions.get(filename);
        String username = operationQuery.get(0);
        int operation = operationMap.get(operationQuery.get(2));
        return (file.getOperationPermission(username, group) & operation) != 0;
    }
}

class File {

    int[] permissions;
    String owner;
    String ownedGroup;

    public File(List<String> fileQuery) {
        int[] permissions = Arrays.stream(fileQuery.get(1).split("")).mapToInt(Integer::parseInt).toArray();
        String owner = fileQuery.get(2);
        String ownedGroup = fileQuery.get(3);
        this.permissions = permissions;
        this.owner = owner;
        this.ownedGroup = ownedGroup;
    }

    public int getOperationPermission(String username, UserGroup group) {
        if (owner.equals(username)) {
            return permissions[0];
        } else if (group.findGroup(ownedGroup).contains(username)) {
            return permissions[1];
        } else {
            return permissions[2];
        }
    }
}