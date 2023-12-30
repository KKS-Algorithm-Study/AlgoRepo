import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/92343
 *
 * 문제 접근 방법:
 *      트리 모양의 초원에 양과 늑대가 퍼져있고, 노드를 방문하면 해당 노드에 있는 동물이 따라오는데,
 *      양의 숫자가 늑대 숫자보다 크도록 유지하는 조건으로 모을 수 있는 양 마릿수의 최대 값을 출력하는 문제
 *
 *      그래프 탐색이니 평범한 BFS/DFS로 풀 수 있을 것 같지만,
 *      같은 노드를 다시 방문할 수 있으므로 평범한 방문배열은 사용할 수 없다.
 *
 *          O  왼쪽위부터 차례대로 1번 ~ 6번 노드라고 할 때,
 *         /\  1번을 방문했으면 다음 차례에 {2, 3}번을 방문할 수 있고
 *        O O  1번 방문 상태에서 2번을 방문하면, 다음 차례에 {3, 4}번을 방문할 수 있고
 *       / /\  1번 방문 상태에서 3번을 방문하면, 다음 차례에 {2, 5, 6}번을 방문할 수 있다.
 *      O O O  즉, a번을 방문한 상태에서 b번을 방문하면,
 *      {a에서 갈 수 있는 노드들} | {b에서 갈 수 있는 노드들} - {b 노드} 를 방문할 수 있다.
 *
 *      dp[여태 방문한 노드 집합] = {다음 방문할 수 있는 노드 집합} 으로 저장하고,
 *      (여태 방문한 노드 집합, 모은 양의 수, 모은 늑대 수)를 큐에 저장해 BFS를 돌려
 *      그 중 모았던 최대 양의 수를 출력하면 된다.
 *
 *      이 때, 최대 노드 수는 17개이므로,
 *      여태 방문한 노드 집합과 방문 가능한 노드 집합을 비트마스킹으로 관리하면
 *      효율적으로 풀 수 있다.
 *
 * 시간 복잡도:
 *      BFS이므로 O(V+E)인데, 실제 V의 갯수 == 노드 집합의 갯수 == 2**N 이다.
 *      각 노드를 탐색할 때마다 연결된 노드들 == 간선을 복사하므로,
 *      O(E * 2**N)이 된다
 */
class Solution {
    public int solution(int[] info, int[][] edges) {
        int SHEEP = 0;
        int WOLF = 1;

        int nodeCount = info.length;
        List<Set<Integer>> field = new ArrayList<>();
        int[] visitableNodesDp = new int[1 << info.length];
        for (int i = 0; i < info.length; i++) {
            field.add(new HashSet<>());
        }

        for (int[] edge : edges) {
            int parentNode = edge[0];
            int childNode = edge[1];
            field.get(parentNode).add(childNode);
        }

        int initNode = 0;
        int initVisitedBit = 1 << initNode;
        int initSheepCount = 1;
        int initWolfCount = 0;
        for (int node : field.get(0)) {
            visitableNodesDp[initVisitedBit] |= 1 << node;
        }
        List<Entry> curQue = new ArrayList<>();
        curQue.add(new Entry(initVisitedBit, initSheepCount, initWolfCount));

        int answer = 1;
        while (true) {
            List<Entry> nxtQue = new ArrayList<>();
            for (Entry entry : curQue) {
                int currVisitedBit = entry.visitedBit;
                int currSheepCount = entry.sheepCount;
                int currWolfCount = entry.wolfCount;
                int currVisitableNodes = visitableNodesDp[currVisitedBit];
                for (int nextNode = 0; nextNode < nodeCount; nextNode++) {
                    // 방문 할 수 없는 노드라면 스킵 || 이 노드가 늑대인데, 늑대 수 == 양 수가 되면 스킵
                    if ((currVisitableNodes & (1 << nextNode)) == 0 ||
                            (info[nextNode] == WOLF && currSheepCount == currWolfCount + 1)) {
                        continue;
                    }
                    int nextVisitedBit = currVisitedBit | (1 << nextNode);
                    // 1>2>3 순서로 방문했을 때 갈 수 있는 노드들 == 1>3>2 일때 갈 수 있는 노드들
                    // 이미 DP에 반영된 경우는 스킵
                    if (visitableNodesDp[nextVisitedBit] != 0) {
                        continue;
                    }
                    // 원래 노드에서 방문할 수 있었던 노드들을 합집합 해주고,
                    visitableNodesDp[nextVisitedBit] |= currVisitableNodes;
                    // 다음 노드에서 방문할 수 있는 노드들을 합집합 해주고,
                    visitableNodesDp[nextVisitedBit] |= field.get(nextNode).stream()
                            .reduce(0, (acc, node) -> acc | (1 << node));
                    // 다음 노드는 이미 방문했으니 차집합 해준다.
                    visitableNodesDp[nextVisitedBit] &= ~(1 << nextNode);
                    if (info[nextNode] == SHEEP) {
                        nxtQue.add(new Entry(nextVisitedBit, currSheepCount + 1, currWolfCount));
                        answer = Math.max(answer, currSheepCount + 1);
                    } else {
                        nxtQue.add(new Entry(nextVisitedBit, currSheepCount, currWolfCount + 1));
                    }
                }
            }

            if (nxtQue.isEmpty()) {
                break;
            }
            curQue = nxtQue;
        }

        return answer;
    }
}

class Entry {
    int visitedBit;
    int sheepCount;
    int wolfCount;

    public Entry(int visitedBit, int sheepCount, int wolfCount) {
        this.visitedBit = visitedBit;
        this.sheepCount = sheepCount;
        this.wolfCount = wolfCount;
    }
}