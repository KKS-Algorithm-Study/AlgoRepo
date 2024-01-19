import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *  문제 풀이 방법:
 *      올바른 괄호 쌍을 제거해서 나올 수 있는 모든 식을 정렬해 출력하는 문제
 *
 *      괄호쌍이 10개 뿐이기 때문에, 모든 괄호를 넣거나 안넣거나 하는 경우의 수는 2**10 = 1024로
 *      완전탐색이 가능하다.
 *
 *      여는 괄호는 넣을지 말지 선택할 수 있고,
 *      여는 괄호를 넣었을 때, 닫는 괄호는 꼭 넣어야하기 때문에,
 *      여는 괄호를 넣었는지 안넣었는지 확인하는 배열이 필요하다.
 *
 *      여는 괄호와 연결된 닫는 괄호는 스택으로 확인 가능하고,
 *      여는 괄호를 넣었으면 true, 안넣었으면 false를 저장해
 *      스택에서 뽑았을 때 true면 닫는 괄호를 넣고, false면 안넣는 방식으로 넣고 안넣고를 판단할 수 있다.
 *
 *      괄호쌍이 10개 뿐이고, 넣었는지 안넣었는지에 대한 true/false 값만 확인하기 때문에,
 *      비트 연산으로 빠르게 해결할 수 있다.
 *
 *  주의할 점:
 *      같은 수식을 여러 괄호가 감싸고 있으면 괄호 제거시 중복된 수식이 나올 수 있는데,
 *      HashSet을 이용해 중복 수식을 처리해야 한다.
 *
 *  시간 복잡도:
 *      괄호 갯수를 N이라 하면
 *      O(2**N)
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

    private static void dfs(int index, int stackBit, String formula, Stack<Character> formulaStack,
                            Set<String> result) {
        // 수식이 끝났다면, result에 수식 더하고
        if (index == formula.length()) {
            StringBuilder processedFormula = new StringBuilder();
            for (char c : formulaStack) {
                processedFormula.append(c);
            }
            result.add(processedFormula.toString());
            return;
        }
        // 여는 괄호라면, 넣은 경우와 안넣은 경우 탐색
        if (formula.charAt(index) == '(') {
            formulaStack.push('(');
            dfs(index + 1, (stackBit << 1) | 1, formula, formulaStack, result);
            formulaStack.pop();
            dfs(index + 1, (stackBit << 1) | 0, formula, formulaStack, result);
            return;
        }
        // 닫는 괄호라면, 이전에 여는 괄호를 넣었는지 확인해 닫는 괄호를 더함
        if (formula.charAt(index) == ')') {
            if ((stackBit & 1) == 1) {
                formulaStack.push(')');
            }
            dfs(index + 1, stackBit >> 1, formula, formulaStack, result);
            if ((stackBit & 1) == 1) {
                formulaStack.pop();
            }
            return;
        }
        // 그 외의 경우 == 일반 수식이면, 그냥 넣고 다음 탐색
        formulaStack.push(formula.charAt(index));
        dfs(index + 1, stackBit, formula, formulaStack, result);
        formulaStack.pop();
    }

    private static Set<String> removeParenthesis(String formula) {
        Set<String> result = new HashSet<>();
        int initIdx = 0;
        int stackBit = 0;
        dfs(initIdx, stackBit, formula, new Stack<>(), result);
        return result;
    }

    private static String solve(String formula) {
        Set<String> processedFormulas = removeParenthesis(formula);

        String sortedFormulas = processedFormulas.stream()
                .sorted()
                .skip(1) // 원본 문자열 제외, 괄호가 가장 많아 무조건 사전순 처음으로 오게 되어있음
                .collect(Collectors.joining("\n"));
        return sortedFormulas;
    }

    public static void main(String[] args) {
        try {
            String formula = br.readLine().strip();
            String answer = solve(formula);
            bw.write(answer);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
