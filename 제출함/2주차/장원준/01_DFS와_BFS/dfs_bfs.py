"""
1. 문제 접근 방식
  1) dfs는 재귀 or 스택으로 풀이 가능 -> 재귀는 어느 정도 손에 익어서 스택으로 품
      a) 스택으로 풀 때는 노드를 방문 처리하며 print하고 스택에 넣는다.
      b) 스택의 peek 요소를 기준으로 방문하지 않은 노드가 있는지 체크
      c) 방문할 수 있는 노드가 없으면 스택에서 뺀다.
  2) bfs는 큐로 풀이 가능

2. 시간 복잡도
  1) dfs, bfs의 시간 복잡도
    a) 그래프는 인접 행렬, 인접 리스트 형태로 나타낼 수 있다.
    b) 인접 행렬인 경우 모든 정점을 다 찾아봐야 하므로 O(V ** 2)이다. (V는 노드의 갯수)
    c) 인접 리스트인 경우 O(V + E)이다. (V는 노드의 갯수, E는 간선의 갯수)
"""
import sys
from collections import deque

def dfs(graph, start):
    # 스택을 이용한 dfs
    visited = [False] * (len(graph) + 1)

    # 시작 노드를 방문 처리하고 스택에 추가
    stack = [start]
    visited[start] = True

    print(start, end = ' ')

    while stack:
        now = stack[-1]
        exists_visited_node = False

        for elem in graph[now]:
            # now의 요소 중 방문하지 않은 요소가 있다면 방문 처리 후 스택에 추가
            if not visited[elem]:
                stack.append(elem)
                visited[elem] = True
                exists_visited_node = True
                print(elem, end = ' ')
                break

        if not exists_visited_node:
            stack.pop()

def bfs(graph, start):
    # 큐를 이용한 bfs
    visited = [False] * (len(graph) + 1)

    # 시작 노드를 방문 처리하고 큐에 추가
    queue = deque([start])
    visited[start] = True

    while queue:
        now = queue.popleft()

        print(now, end = ' ')

        for elem in graph[now]:
            if not visited[elem]:
                queue.append(elem)
                visited[elem] = True

# 1. 입력 받기
n, m, start = map(int, sys.stdin.readline().rstrip().split())

# 2. 그래프 초기화
graph = dict()

for i in range(1, n + 1):
    graph[i] = []

# 3. 그래프 입력 받기
for i in range(m):
    f, t = map(int, sys.stdin.readline().rstrip().split())
    graph[f].append(t)
    graph[t].append(f)

# 4. 그래프 내부의 리스트를 정렬하기
for key in graph.keys():
    graph[key].sort()

# 5. dfs (깊이 우선 탐색)
dfs(graph, start)
print()

# 6. bfs (너비 우선 탐색)
bfs(graph, start)