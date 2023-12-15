"""
1. 문제 접근 방식
  1) 크루스칼 알고리즘을 사용할 때, 남초 <-> 여초가 연결된 간선만 사용하므로
      입력 받을 때 조건에 맞게 간선을 필터링한다.

2. 시간 복잡도
  1) 크루스칼 알고리즘을 사용하였으므로 O(MlogN) 시간 복잡도를 가진다.
"""

import sys

input = sys.stdin.readline
sys.setrecursionlimit(int(1e9))

def find(x):
  if groups[x] == x:
    return x

  groups[x] = find(groups[x])

  return groups[x]


def union(a, b):
  a = find(a)
  b = find(b)

  if a > b:
    a, b = b, a

  groups[b] = a


n, m = map(int, input().split())

# 학교 번호와 성별을 담은 학교 정보 리스트
schools = [(0, '')]

genders = [''] + list(input().split())
groups = list(range(n + 1))
graphs = []
result = 0

for i in range(1, n + 1):
  schools.append((i, genders[i]))

for _ in range(m):
  # 남초 <-> 여초 간 연결된 간선만 저장
  a, b, c = map(int, input().split())

  if genders[a] != genders[b]:
    graphs.append((c, a, b))

# 그래프 간선을 비용 순서대로 정렬
graphs.sort()

for c, a, b in graphs:
  a = find(a)
  b = find(b)

  if a != b:
    union(a, b)
    result += c

# 모든 학교를 연결할 수 있는지 계산
# MST의 groups가 모두 동일한 루트를 가지는지 계산한다.
prev = find(groups[1])
flag = True

for i in range(2, len(groups)):
  if prev != find(groups[i]):
    flag = False
    break

if flag:
  print(result)
else:
  print(-1)
