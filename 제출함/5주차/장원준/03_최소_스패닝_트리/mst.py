"""
1. 문제 접근 방식
  1) 최소 스패닝 트리(MST) 구하기
    a. 최소 스패닝 트리란?
      - 그래프 내에서 모든 정점을 포함한다.
      - 정점 간 연결이 되어 있다.
      - 싸이클이 존재하지 않는다. (트리의 조건)

    b. MST를 구하는 대표적인 알고리즘인 크루스칼 알고리즘
      - 그래프 간선을 오름차순 정렬한다.
      - 정렬한 간선을 순회하며, find 연산으로 두 정점의 부모를 찾는다.
      - 찾은 두 부모가 다르면 union 연산을 수행한다.
      - 크루스칼 알고리즘의 기본 시간복잡도는 O(ElogV)이다. (E은 간선의 갯수, V는 정점의 갯수)

2. 시간 복잡도
  1) 크루스칼 알고리즘을 사용하였으므로 O(ElogV) 시간 복잡도를 가진다.
"""
import sys

sys.setrecursionlimit(int(1e9))
input = sys.stdin.readline

def solve():
  v, e = map(int, input().split())
  groups = list(range(v + 1))
  graphs = []
  result = 0

  for _ in range(e):
    # a, b 로 가는 비용이 c이다.
    a, b, c = map(int, input().split())
    graphs.append((c, a, b))

  graphs.sort()

  for c, a, b in graphs:
    a = find(groups, a)
    b = find(groups, b)

    if a != b:
      union(groups, a, b)
      result += c

  print(result)


def find(groups, x):
  if groups[x] == x:
    return x

  groups[x] = find(groups, groups[x])

  return groups[x]


def union(groups, a, b):
  a = find(groups, a)
  b = find(groups, b)

  if a > b:
    a, b = b, a

  groups[b] = a


solve()