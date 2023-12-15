"""
1. 문제 접근 방식
  1) 친구의 친구는 친구이며, 친구비를 가장 적게 쓰고 싶으므로
      친구비가 가장 적게 드는 친구를 분리집합의 루트로 지정

2. 시간 복잡도
  1) 입력을 받으며 합집합 연산 -> m * log(n)
  2) 친구비 계산 -> n * log(n)
  3) 따라서 (n + m) * log(n)이 된다.
"""

import sys

sys.setrecursionlimit(int(1e9))
input = sys.stdin.readline

def solve():
  n, m, k = map(int, input().split())
  fees = [0] + list(map(int, input().split()))
  groups = list(range(n + 1))
  result = 0

  # 입력을 받으면서 친구 중 비용이 가장 적게 드는 친구를 구해줌
  for _ in range(m):
    group, b = map(int, input().split())
    union(groups, fees, group, b)

  # 모두와 친구가 되기 위해 비용 계산
  for elem in groups:
    group = find(groups, elem)
    if fees[group] > 0:
      result += fees[group]
      fees[group] = 0

  if k < result:
    return "Oh no"

  return result


def find(groups, elem):
  if groups[elem] == elem:
    return elem

  groups[elem] = find(groups, groups[elem])

  return groups[elem]


def union(groups, fees, a, b):
  a = find(groups, a)
  b = find(groups, b)

  if a != b:
    # 친구비가 적은 쪽으로 갱신하여, 루트로 만들어줌
    if fees[a] <= fees[b]:
      groups[b] = a
    else:
      groups[a] = b

print(solve())