"""
1. 문제 접근 방식 (답 보고 품)
  1) dfs로 각 경로를 탐방하여 값을 구함
  2) 각 경로를 비트 마스킹으로 처리하여 dp에 값 저장
  3) 순환이 가능하므로, 특정 점에서 시작해도 무방
    ex) 0 -> 1 -> 2 -> 0 과 1 -> 2 -> 0 -> 1 은 같은 경로이다.

2. 시간 복잡도
  1) dfs가 최대 2**n * n번 만큼 수행됨 (배열 크기만큼)
  2) 1번의 dfs에서 for문으로 n번 탐색
  3) 최종 시간복잡도는 O(2**n * n**2)이다.
"""
import sys
input = sys.stdin.readline

n = int(input())
INF = int(1e9)
dp = [[-1] * (1 << n) for _ in range(n)]

def dfs(current_node, visited): # visited => 비트 마스킹을 한 값
  # 모든 도시를 방문했다면
  if visited == (1 << n) - 1:
    if graph[current_node][0]:
      return graph[current_node][0]
    else:
      return INF

  # 이미 최소비용이 계산되어 있다면
  if dp[current_node][visited] != -1:
    return dp[current_node][visited]

  # 이미 길이 없다는 걸 어떻게 처리하면 좋을까요?
  min_cost = INF
  for i in range(1, n):
    if not graph[current_node][i] or visited & (1 << i):
      continue

    min_cost = min(min_cost, dfs(i, visited | (1 << i)) + graph[current_node][i])

  dp[current_node][visited] = min_cost
  return dp[current_node][visited]


graph = []
for i in range(n):
  graph.append(list(map(int, input().split())))

print(dfs(0, 1))