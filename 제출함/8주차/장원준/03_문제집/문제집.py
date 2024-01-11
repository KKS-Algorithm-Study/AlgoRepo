"""
1. 문제 접근 방식
  1) 위상 정렬을 사용한다.
  2) 같은 레벨이라면 쉬운 문제부터 풀어야하므로 우선순위 큐를 이용한다.

2. 시간 복잡도
  1) 위상 정렬을 사용하였으므로 O(N + M)이다.
  2) 우선순위 큐에 요소를 넣으므로 O(logN)이다. (요소의 갯수만큼 힙정렬이 수행된다.)
  3) 따라서 최종 시간 복잡도는 O(N*logN + M) = O(N*logN)이다.
"""
import sys
from heapq import heappush, heappop

input = sys.stdin.readline

n, m = map(int, input().split())

graph = [[] for _ in range(n + 1)]
indegree = [0] * (n + 1)

for _ in range(m):
  a, b = map(int, input().split())
  graph[a].append(b)

  indegree[b] += 1

queue = []

# 진입 차수가 0인 노드를 큐에 입력
for i in range(1, n + 1):
  if indegree[i] == 0:
    heappush(queue, i)

while queue:
  now = heappop(queue)
  print(now, end=' ')

  for next in graph[now]:
    indegree[next] -= 1

    if indegree[next] == 0:
      heappush(queue, next)