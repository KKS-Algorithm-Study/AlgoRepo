"""
1. 문제 접근 방식
  1) 합집합 연산 -> 서로소 집합을 구할 때 union 함수
  2) 두 원소가 같은 집합에 포함되어 있는지 확인하는 연산 (찾기 연산) -> 서로소 집합을 구할 때 find 함수

2. 시간 복잡도
  1) 합집합 연산 -> 합집합 연산은 다음과 같은 로직을 거친다.
    a. find 함수 2번 -> 2 * log(V)
    b. 비교 후 값 갱신 -> 배열에 접근하므로 상수 시간 복잡도

  2) 찾기 연산 -> 기본적으로 O(V) 시간 복잡도를 가진다.
    - 따라서 경로 압축 알고리즘을 통해 O(logN)시간 복잡도를 줄일 수 있다.
    - 경로 압축 알고리즘이란, 루트 노드까지 찾아가는 경로를, 부모를 찾으면서 갱신 해 나가는 알고리즘이다.

  3) 최종적인 시간 복잡도는, m * log(V)

3. 참고!
  1) find 함수에서 재귀로 찾는데, 파이썬에서는 setrecursionlimit 로 재귀 깊이 제한을 줄 수 있다. (기본은 1000)
  2) pypy를 사용하는 경우, 재귀에 대한 메모리를 미리 할당하므로
      setrecursionlimit를 너무 크게 주는 경우 메모리 초과가 날 수 있다.
  3) 파이썬을 사용하는 경우에는 문제가 되지 않는다.
"""
import sys

# pypy는 recursion을 위해 메모리 할당을 미리 하기 때문에 메모리 초과 주의해야 됨
sys.setrecursionlimit(10 ** 6)

input = sys.stdin.readline

def find(x):
  if groups[x] == x:
    return x

  groups[x] = find(groups[x])

  return groups[x]


def union(a, b):
  a = find(a)
  b = find(b)

  # 작은 것을 a로!
  if a > b:
    a, b = b, a

  groups[b] = a

n, m = map(int, input().split())

groups = list(range(n + 1))

for _ in range(m):
  operator, a, b = map(int, input().split())

  if operator == 0:
    union(a, b)
  else:
    if find(a) == find(b):
      print('YES')
    else:
      print('NO')
