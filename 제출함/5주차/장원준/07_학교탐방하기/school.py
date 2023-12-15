"""
1. 문제 접근 방식
  1) 내리막길, 오르막길로 정렬하여 최선의 경우, 최악의 경우를 계산한다.

2. 시간 복잡도
  1) 크루스칼 알고리즘을 사용하였으므로 O(MlogN) 시간 복잡도를 가진다.
"""

import sys

sys.setrecursionlimit(int(1e9))
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
graph = []

for _ in range(m + 1):
  a, b, c = map(int, input().split())
  graph.append((c, a, b))


# 최선의 결과 구하기 (간선의 비용이 낮은 기준으로 정렬한다.)
graph.sort()
groups = list(range(n + 1))
best_uphill = 0

for c, a, b in graph:
  group_a = find(a)
  group_b = find(b)

  if group_a != group_b:
    union(group_a, group_b)
    if c == 0:
      best_uphill += 1


# 최악의 결과 구하기 (간선의 비용이 높은 기준으로 정렬한다.)
graph.sort(reverse=True)
groups = list(range(n + 1))
worst_uphill = 0

for c, a, b in graph:
  group_a = find(a)
  group_b = find(b)

  if group_a != group_b:
    union(group_a, group_b)
    if c == 0:
      worst_uphill += 1

print(best_uphill ** 2 - worst_uphill ** 2)

