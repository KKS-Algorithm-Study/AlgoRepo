"""
1. 문제 접근 방식
  1) 발전소가 루트가 되어야 하므로 다음과 같은 로직으로 풀이한다.
    - 발전소의 위치를 가장 작은 인덱스로 만들기
    - 크루스칼 알고리즘의 union 함수에서 인덱스가 작은 요소를 루트로 지정

2. 시간 복잡도
  1) 크루스칼 알고리즘을 사용하였으므로 O(MlogN) 시간 복잡도를 가진다.
  2) 그리고 건물을 union 할 때마다 모든 건물이 연결되었는지 체크할 떄 O(N) 시간 복잡도를 가진다.
  3) 따라서 최종 시간 복잡도는 O(N * M * logN)의 시간 복잡도를 가진다.
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


# 모든 건물에 전기가 들어오는지 체크
def is_connected():
  for i in range(1, len(groups)):
    if groups[i] != 0:
      return False

  return True


n, m, k = map(int, input().split())
generators = list(map(int, input().split()))
groups = list(range(n + 1))
graph = []
result = 0

for _ in range(m):
  u, v, w = map(int, input().split())
  graph.append((w, u, v))

# 발전소를 루트로 가지도록 가장 작은 값을 넣어줌
# union에서 함수에서 작은 값을 우선으로 루트 취급하기 때문이다.
for generator in generators:
  groups[generator] = 0

graph.sort()

for w, u, v in graph:
  u = find(u)
  v = find(v)

  if u != v:
    union(u, v)
    result += w

    if is_connected():
      break

print(result)