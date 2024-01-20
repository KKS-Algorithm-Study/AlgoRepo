/**
 * !!답안 참고함
 * 접근 :
 *      1. 문자열이 들어오면 부모 노드부터 시작해서 삽입을 수행
 *      2. 만일 현재 노드의 자식노드인 Map에 이번 문자가 있으면?
 *          -현재 위치를 자식노드로 옮기고 이번 문자를 append함
 *      3. 현재 노드의 자식에 이번 문자가 없으면?
 *          -노드 하나를 생성하여 map에 put 해줌
 *          -그리고 현재 문자를 append하고, 더이상 append 하지 말라는 endFlag를 세움
 *      4. 문자열 전체를 순회하여 노드를 추가하고, 값을 얻었다면
 *      5. 현재 입력한 문자열의 끝에 isEnd를 붙이고 +1시킴, 만일 0이라면 붙이지 않음 (중복일 시 2, 3, .. 번호부여하는 로직)
 *      6. 완성한 sb를 리턴
 *
 */

package week10.n7_게임닉네임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {

    static HashMap<Character, Node> head = new HashMap<>();

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        Trie trie = new Trie();

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String str = st.nextToken();
            String result = trie.insert(str);
            System.out.println(result);
        }
    }
}

class Node {
    HashMap<Character, Node> child = new HashMap<>();
    int isEnd = 0;
}

class Trie {
    Node root = new Node();

    String insert(String in) {
        Node node = root;
        StringBuilder result = new StringBuilder();
        boolean endFlag = false;
        for(int i = 0; i < in.length(); i++) {
            if(node.child.containsKey(in.charAt(i))) {
                node = node.child.get(in.charAt(i));
                result.append(in.charAt(i));
            }
            else {
                Node next = new Node();
                node.child.put(in.charAt(i), next);
                node = next;
                if(!endFlag) {
                    result.append(in.charAt(i));
                    endFlag = true;
                }
            }
        }
        if(node.isEnd == 0) {
            node.isEnd = 1;
            return result.toString();
        }
        else {
            node.isEnd += 1;
            return result.append(node.isEnd).toString();
        }
    }
}
