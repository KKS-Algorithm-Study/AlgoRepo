"""
1. 문제 접근 방식
  1) bfs로 풀기
  2) 1명의 사람을 기준으로, 모든 노드를 탐색할 때 depth가 얼마나 증가하는지 체크한다.

2. 시간 복잡도
  1) 총 친구가 50명이다.
  2) 1명 당 bfs로 풀 것이므로, 최대 50 * (50 * 49) 이므로 풀이 가능
  3) O(N**3)이다.

"""
from collections import deque

n = int(input())
graph = [[] for _ in range(n + 1)]
scores = [0] * (n + 1)
min_score = 50

# 친구 사이 입력받기
while True:
  friend1, friend2 = map(int, input().split())

  if friend1 == -1 and friend2 == -1:
    break

  graph[friend1].append(friend2)
  graph[friend2].append(friend1)


# 친구 1명당 bfs 수행
for i in range(1, n + 1):
  max_score = 0
  visited = [False] * (n + 1)

  queue = deque([(i, 0)])
  visited[i] = True

  while queue:
    now, score = queue.popleft()
    max_score = max(max_score, score)

    for next in graph[now]:
      if not visited[next]:
        visited[next] = True
        queue.append((next, score + 1))

  min_score = min(min_score, max_score)
  scores[i] = max_score


# 출력
print(min_score, scores.count(min_score))

for i in range(1, n + 1):
  if scores[i] == min_score:
    print(i, end=' ')
