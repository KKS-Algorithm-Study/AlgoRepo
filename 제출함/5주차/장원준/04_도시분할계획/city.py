"""
1. 문제 접근 방식
  1) 두 개의 분리된 마을로 분할해야 하기 때문에
      MST를 구한 후, 가장 비용이 많이 드는 도로를 빼준다.

2. 시간 복잡도
  1) 크루스칼 알고리즘을 사용하였으므로 O(MlogN) 시간 복잡도를 가진다.
"""
import sys

sys.setrecursionlimit((int(1e9)))
input = sys.stdin.readline

def find(a):
  if groups[a] == a:
    return a

  groups[a] = find(groups[a])
  return groups[a]


def union(a, b):
  a = find(a)
  b = find(b)

  if a > b:
    a, b = b, a

  groups[b] = a


n, m = map(int, input().split())
groups = list(range(n + 1))
graph = []
costs = []
result = 0

for _ in range(m):
  a, b, c = map(int, input().split())
  graph.append((c, a, b))

# MST를 만들기 위해 비용이 적은 순으로 정렬
graph.sort()

for c, a, b in graph:
  if find(a) != find(b):
    union(a, b)
    costs.append(c)

# 비용이 가장 큰 도로를 빼기 위해 비용이 큰 순으로 정렬
costs.sort(reverse=True)

for i in range(1, len(costs)):
  result += costs[i]

print(result)