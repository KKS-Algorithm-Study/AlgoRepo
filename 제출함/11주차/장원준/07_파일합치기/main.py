"""
1. 문제 접근 방식
  1) 매번 가장 작은 파일 2개를 합친다
  2) 합친 파일을 다시 우선순위 큐에 넣어준다.

2. 시간 복잡도
  1) 테스트 케이스만큼 반복 -> O(t)
  2) 1개의 테스트 케이스 안에서 최대 n개의 요소가 logn으로 정렬됨 -> O(nlogn)
  3) 최종 시간 복잡도는 O(t * nlogn)이다.

"""
from heapq import heappop, heappush
import sys

input = sys.stdin.readline
test_case = int(input().rstrip())

for _ in range(test_case):
  n = int(input().rstrip())
  nums = list(map(int, input().split()))
  cost = 0
  q = []

  for num in nums:
    heappush(q, num)

  while len(q) > 1:
    a, b = heappop(q), heappop(q)
    cost += a + b
    heappush(q, a + b)

  print(cost)

