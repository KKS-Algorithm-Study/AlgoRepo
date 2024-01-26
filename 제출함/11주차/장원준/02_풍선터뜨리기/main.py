"""
1. 문제 접근 방식
  1) 파이썬의 rotate를 사용한다.

2. 시간 복잡도
  1) 반복문을 수행하는데 최대 N만큼 수행하므로 O(N)이다.

"""
import sys
from collections import deque

input = sys.stdin.readline

n = int(input())
q = deque(enumerate(map(int, input().split())))
ans = []

while q:
  idx, paper = q.popleft()
  ans.append(idx + 1)

  if paper > 0:
    q.rotate(-(paper - 1))
  elif paper < 0:
    q.rotate(-paper)

print(' '.join(map(str, ans)))

