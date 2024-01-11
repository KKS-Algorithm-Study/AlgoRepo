"""
1. 문제 접근 방식 (답보고 품)
  1) R을 만날 때 양 옆의 K의 갯수를 비교하여, 작은 K를 선택하여 계산한다.
  2) 처음에는 R의 갯수만큼 그 R을 만날 때까지의 K 갯수를 저장한다.
  3) right ~ left 거리가 r의 갯수가 되며, 더 작은 k를 선택하여 두 배로 곱해준다.

2. 시간 복잡도
  1) O(len(s))이다.
"""

import sys

s = sys.stdin.readline().strip()

left_k = []
right_k = []

# 왼쪽부터 시작할 때 K갯수를 누적해서 저장
# ex. 왼쪽 끝부터 시작하여 첫 번째 R을 만나면 left_k[0]을 보면 된다.
num_k = 0
for i in s:
  if i == 'K':
    num_k += 1
  else:
    left_k.append(num_k)

# 오른쪽부터 시작할 때 K갯수를 누적해서 저장
num_k = 0
for i in s[::-1]:
  if i == 'K':
    num_k += 1
  else:
    right_k.append(num_k)

right_k.reverse()

left, right = 0, len(left_k) - 1
ans = 0

while left <= right:
  num_r = right - left + 1
  ans = max(ans, num_r + 2 * min(left_k[left], right_k[right]))

  if left_k[left] < right_k[right]:
    left += 1
  else:
    right -= 1

print(ans)


