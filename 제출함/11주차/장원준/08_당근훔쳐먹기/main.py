"""
1. 문제 접근 방식
  1) 시간이 지날수록 맛있어지는 당근을 나중에 먹는다.
  2) 따라서 영양제에 따라 정렬 후 당근을 먹는다.

2. 시간 복잡도
  1) 당근 정보를 정렬하기 -> O(nlogn)
  2) 당근을 순회하며 먹기 -> O(n)
  3) 따라서 최종 시간 복잡도는 O(nlogn)이다.

"""
import sys

input = sys.stdin.readline

n, t = map(int, input().split())
carrot = []

for i in range(1, n + 1):
  w, p = map(int, input().split())
  carrot.append((w, p))

carrot.sort(key=lambda x: [-x[1], -x[0]])
taste = 0
days = t - 1

for w, p in carrot:
  taste += (p * days) + w
  days -= 1

print(taste)
"""
field 현황
** 1번을 먹고 시작!
  1일차
    오리 -> [3, 1]
    토끼 -> [0, 1]
  2일차
    오리 -> [3, 3]
    토끼 -> [3, 0]
  => 3 + 3 = 6먹음

** 2번을 먹고 시작!
  1일차
    오리 -> [3, 1]
    토끼 -> [3, 0]
  2일차
    오리 -> [7, 1]
    토끼 -> [0, 1]
  => 1 + 7 = 6먹음


1번당근 => [3, 7]
2번당근 => [1, 3]

1번당근 => [3, 7]
2번당근 => [1, 3]

1번당근 => [1, 4, 7, 10, 13]
2번당근 => [2, 11, 20, 29, 38]
3번당근 => [3, 10, 17, 24, 31]
"""