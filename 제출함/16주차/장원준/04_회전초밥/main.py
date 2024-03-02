"""
1. 문제 접근 방식
  1) 투 포인터로 접근

2. 시간 복잡도
  1) 최대 초밥의 접시 갯수만큼 반복하므로 O(n)이다.

"""
import sys
from collections import deque

input = sys.stdin.readline
n, d, k, c = map(int, input().split())
sushi = [int(input().rstrip()) for _ in range(n)]
left, right, answer = 0, 0, 0

queue = deque()
dict = {c: 1}

# k개의 초밥을 처음에 담는다.
for i in range(k):
  queue.append(sushi[i])
  dict[sushi[i]] = dict.get(sushi[i], 0) + 1

  right += 1

# k개의 초밥을 유지하면서 종류를 센다.
while left < n:
  # 초밥의 종류 구하기 (쿠폰 포함)
  num_type = len(dict)
  answer = max(answer, num_type)

  # 맨 앞의 스시 빼기
  poped = queue.popleft()

  if dict[poped] == 1:
    dict.pop(poped)
  else:
    dict[poped] -= 1

  # 맨 뒤의 스시 더해주기
  next = sushi[right % n]
  queue.append(next)
  dict[next] = dict.get(next, 0) + 1

  left += 1
  right += 1

print(answer)