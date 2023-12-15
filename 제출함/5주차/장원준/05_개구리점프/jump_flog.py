"""
1. 문제 접근 방식
  1) 통나무를 시작점 기준으로 정렬한다.
  2) 이전 통나무와, 다음 통나무의 겹치는 구간이 있으면
      MST의 union 함수를 사용하여 같은 그룹으로 묶어준다.
  3) 이전 통나무, 다음 통나무를 비교할 때 스위핑 알고리즘을 사용한다.
    ** 주의점은 다음 통나무가 이전 통나무에 완전히 포함될 때 주의해야 한다.

2. 시간 복잡도
  1) 통나무를 합칠 때 크루스칼 알고리즘을 사용하였으므로 O(NlogN) 시간 복잡도를 가진다.
"""

import sys

sys.setrecursionlimit(10**3)
input = sys.stdin.readline

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


n, q = map(int, input().split())
groups = list(range(n + 1))
trees = []

# 통나무 입력받기
for i in range(n):
  left, right, length = map(int, input().split())
  # 시작점, 끝점, 길이, 통나무 번호
  trees.append((left, right, length, i + 1))

# 통나무를 시작점 기준으로 정렬
trees.sort()

# 통나무 간 비교하여 겹치는 구간이 있다면 union
prev = trees[0]

for i in range(1, len(trees)):
  now = trees[i]
  # 스위핑 알고리즘 사용
  if prev[0] <= now[0] <= prev[1]:
    union(prev[3], now[3])

    # 만약 현재 통나무가 이전 통나무에 완전히 포함된다면 이전 통나무 유지
    if not (prev[0] <= now[0] and now[1] <= prev[1]):
      prev = now
  else:
    prev = now

# 통나무 번호를 가지고, 통나무 간 이동이 가능한지 계산
for _ in range(q):
  tree1, tree2 = map(int, input().split())

  if find(tree1) == find(tree2):
    print(1)
  else:
    print(0)