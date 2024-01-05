"""
1. 문제 접근 방식
  1) 바이토닉 수열은 특정 값을 기준으로 왼쪽은 증가하고, 오른쪽은 감소하는 수열이다.
  2) 따라서 기준이 되는 값을 1개 정하고, 좌우에 lis를 적용했을 때의 길이를 구한다.

2. 시간 복잡도
  1) for문으로 원소를 탐색한다 -> O(N)
  2) lis의 시간복잡도 -> O(NlogN)
  3) 따라서 최종 시간 복잡도는 O(N**2 * logN)이다

** lis를 for문 만큼 돌아도 계속 같은 부분 수열이 나올 것이다.
"""

from bisect import bisect_left

n = int(input())
arr = list(map(int, input().split()))
result = 0

def lis(arr):
  if not arr:
    return []

  dp = [arr[0]]

  for num in arr:
    if num > dp[-1]:
      dp.append(num)
    else:
      pos = bisect_left(dp, num)
      dp[pos] = num

  return dp


for i in range(n):
  # i가 바이토닉 부분 수열의 중심이 될 때
  left = lis(arr[0:i])
  right = lis(arr[i:len(arr)][::-1])
  length = len(left) + len(right)

  # 만약 왼쪽 결과의 마지막 값과 오른쪽 결과의 첫째 값이 같으면 길이에서 1빼줌
  if len(left) and len(right) and left[-1] == right[-1]:
    length -= 1

  result = max(result, length)

print(result)

