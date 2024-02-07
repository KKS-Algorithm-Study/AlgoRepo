"""
1. 문제 접근 방식
  1) 가장 큰 숫자부터 작은 숫자까지 탐색한다.
  2) 숫자를 1개 선택하고, 그 숫자에 대해 dp를 수행한다.
    ** dp는 금액을 기준으로 수행한다.
    ** min(이전 숫자 + 현재 숫자, 현재 숫자, 현재 dp값)

2. 시간 복잡도
  1) O(n * m)이다.

"""

INF = 5001
n = int(input())
room = list(map(int, input().split()))
m = int(input())
dp = [-INF for _ in range(m + 1)]

# 가장 큰 숫자부터 1개씩 탐색
for i in range(n - 1, -1, -1):
  x = room[i]

  # 1개의 숫자를 가지고 dp 처리
  # 이전 값을 참조하여, 현재 숫자를 맨 뒷자리에 붙여준다.
  for j in range(x, m + 1):
    dp[j] = max(dp[j - x] * 10 + i, i, dp[j])

print(dp[m])


