"""
위상 정렬이란?
- 방향 그래프의 모든 노드를 방향성에 거스르지 않도록 순서대로 나열하는 것이다.
- 실생활 예로는 어떤 것을 선행해야만 다음 것으로 넘어가는 커리큘럼 만들기 같은 것이 있을 것이다.

알고리즘
1. 진입 차수 (나에게 들어오는 노드의 갯수)가 0인 노드를 큐에 넣는다.
2. 큐가 빌 때까지 다음 과정을 반복한다.
  1) 큐에서 원소를 꺼내 해당 노드에서 출발하는 간선을 그래프에서 제거한다.
  2) 새롭게 진입차수가 0이 된 노드를 큐에 넣는다.

시간복잡도
1. 위상 정렬의 시간 복잡도는 O(V+E)이다.
"""

from collections import deque

v, e = map(int, input().split())

# 모든 노드에 대한 진입차수를 0으로 초기화
indegree = [0] * (v + 1)

# 연결 리스트 형태의 그래프 초기화
graph = [[] for _ in range(v + 1)]

# 방향 그래프의 간선 정보 입력받기
for _ in range(e):
  a, b = map(int, input().split())
  graph[a].append(b)
  # 진입 차수를 1증가
  indegree[b] += 1

# 위상 정렬 함수
def topology_sort():
  result = []
  q = deque()

  for i in range(1, v + 1):
    if indegree[i] == 0:
      q.append(i)

  while q:
    now = q.popleft()

    result.append(now)

    for i in graph[now]:
      indegree[i] -= 1

      if indegree[i] == 0:
        q.append(i)

  # 결과 출력
  for i in result:
    print(i, end = ' ')

topology_sort()
