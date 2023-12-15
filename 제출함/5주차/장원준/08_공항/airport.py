"""
1. 문제 접근 방식
  1) 도킹할 수 있는 범위 중 가장 번호가 큰 게이트에 도킹한다.
  2) 1 ~ gi까지가 1개의 그룹이 되며, 비행기를 1개 도킹 시 앞 인덱스와 같은 그룹으로 만들어준다.

2. 시간 복잡도
  1) 크루스칼 알고리즘을 사용하였으므로 O(PlogG) 시간 복잡도를 가진다.
"""

import sys

input = sys.stdin.readline
sys.setrecursionlimit(int(1e9))

def union(a, b):
  a = find(a)
  b = find(b)

  if a > b:
    a, b = b, a

  groups[b] = a


def find(a):
  if groups[a] == a:
    return a

  groups[a] = find(groups[a])

  return groups[a]


g = int(input())
p = int(input())
groups = list(range(g + 1))
result = 0

for _ in range(p):
  gi = int(input())

  # gi의 부모를 찾기 (0이면 더이상 도킹할 수 없다면 공항 폐쇄)
  if find(gi) == 0:
    break

  result += 1
  union(find(gi), find(gi) - 1)

print(result)
