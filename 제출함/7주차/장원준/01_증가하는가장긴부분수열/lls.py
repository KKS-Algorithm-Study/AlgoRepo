"""
1. 문제 접근 방식
  1) dp는 배열이 아닌 리스트로 구현되고, lis의 원소들을 저장한다.
  2) 현재 숫자와 dp의 마지막 요소를 비교한다.
  3) 현재 숫자가 들어갈 자리를 이분탐색으로 찾는다.
    a. 파이썬의 bisect 라이브러리를 사용하자.
    b. bisect_left(a, x) -> a리스트에 x를 삽입할 경우의 인덱스를 찾아준다. (이미 있으면 기존 항목의 인덱스를 반환한다.)
    b. bisect_right(a, x) -> a리스트에 x를 삽입할 경우의 인덱스를 찾아준다. (이미 있으면 기존 항목의 인덱스 + 1을 반환한다.)

2. 시간 복잡도
  1) for문으로 원소를 탐색한다 -> O(N)
  2) 이분 탐색으로 원소가 들어갈 자리를 찾는다 -> O(logN)
  3) 따라서 최종 시간 복잡도는 O(NlogN)이다
"""

from bisect import bisect_left

n = int(input())
arr = list(map(int, input().split()))
dp = [arr[0]]

for i in range(1, len(arr)):
  num = arr[i]

  if num > dp[-1]:
    dp.append(num)
  else:
    pos = bisect_left(dp, num)
    dp[pos] = num

print(len(dp))
