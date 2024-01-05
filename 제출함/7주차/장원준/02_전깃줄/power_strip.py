"""
1. 문제 접근 방식
  1) 전깃줄을 연결하는 A, B 두 개의 기둥에서 모드 lis를 만족해야 한다.
  2) 앞의 전깃줄인 A를 기준으로 정렬한다.
  3) B의 요소만 추출하여 lis를 만족하도록 계산한다.

2. 시간 복잡도
  1) for문으로 원소를 탐색한다 -> O(N)
  2) 이분 탐색으로 원소가 들어갈 자리를 찾는다 -> O(logN)
  3) 따라서 최종 시간 복잡도는 O(NlogN)이다

3. a를 배열의 인덱스, b를 값으로 하면 정렬없이 가능
"""

from bisect import bisect_left

n = int(input())
arr = []
arr_b = []

for _ in range(n):
  a, b = map(int, input().split())
  arr.append((a, b))

# 앞의 전깃줄로 정렬
arr.sort()

# b의 전깃줄만 추출
for a, b in arr:
  arr_b.append(b)

# 뒤의 전깃줄이 lis를 만족하도록 만들기
dp = [arr_b[0]]

for num in arr_b:
  if num > dp[-1]:
    dp.append(num)
  else:
    pos = bisect_left(dp, num)
    dp[pos] = num

print(len(arr_b) - len(dp))