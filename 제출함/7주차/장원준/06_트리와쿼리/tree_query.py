import sys

sys.setrecursionlimit(int(1e9))
input = sys.stdin.readline

def dfs(graph, visited, sub_trees, now):
  visited[now] = True

  for elem in graph[now]:
    if not visited[elem]:
      sub_trees[now] += dfs(graph, visited, sub_trees, elem)

  return sub_trees[now]


n, r, q = map(int, input().split())

# 자기 자신도 부분 트리에 들어가므로 1로 초기화
sub_trees = [1] * (n + 1)
visited = [False] * (n + 1)
graph = {}

for i in range(n + 1):
  graph[i] = []

for _ in range(n - 1):
  f, t = map(int, input().split())
  graph[f].append(t)
  graph[t].append(f)

dfs(graph, visited, sub_trees, r)

# 쿼리를 root로 취급하여 서브트리 탐색
for _ in range(q):
  query = int(input())
  print(sub_trees[query])
