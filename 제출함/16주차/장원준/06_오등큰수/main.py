"""
1. 문제 접근 방식
  1) 수열을 뒤에서부터 순회한다.
  2) 스택을 사용하여 현재 인덱스의 숫자보다 갯수가 많은 수가 나올 때까지 pop한다.

2. 시간 복잡도
  1) for문 안의 while문의 시간 복잡도를 어떻게 처리할까여..

"""
import sys
from collections import deque

input = sys.stdin.readline
n = int(input().rstrip())
arr = [*map(int, input().split())]

queue = deque([-1])
stack = [arr[n - 1]]
dict = {}

# 수열의 각 숫자의 갯수를 dict에 담는다.
for i in arr:
  dict[i] = dict.get(i, 0) + 1

# 스택을 사용해 오른쪽에 위치한 오등큰수를 구한다.
for i in range(n - 2, -1, -1):
  num_now = dict[arr[i]]

  while stack and dict[stack[-1]] <= num_now:
    stack.pop()

  if not stack:
    queue.appendleft(-1)
  else:
    queue.appendleft(stack[-1])

  stack.append(arr[i])

print(*queue)