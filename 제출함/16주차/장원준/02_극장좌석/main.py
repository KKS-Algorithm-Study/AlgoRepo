"""
1. 문제 접근 방식 (백트래킹인가 싶었는데 안돼서 답안 참고)
  1) 규칙을 찾아 dp로 풀이 가능하다.
  2) vip가 존재하는 경우는 vip를 기준으로 자른 영역들의 경우의 수를 곱해주면 된다.
    ** 문제의 (9,2,4,7)을 보자
    ** 먼저 vip가 없는 경우를 계산하면 dp = [1, 1, 2, 3, 5, 8, 13, 21, 34, 55]가 나온다.
    ** 그리고 vip 자리인 4, 7을 기준으로 인원을 자르면 3명 / 2명 / 2명 (vip 2명 제외)로 나뉜다.
    ** 따라서 그 안에서만 경우의 수를 구하면 되므로 dp[3] * dp[2] * dp[2]을 해주면 된다.

2. 시간 복잡도
  1) 최대 인원 수만큼 반복하므로 o(n)이다.

"""
n = int(input())
m = int(input())
vip = [int(input()) for _ in range(m)]

dp = [1, 1, 2] + [0] * (n - 2)

for i in range(3, n + 1):
  dp[i] = dp[i - 1] + dp[i - 2]

answer = 1

# vip가 있을 때
if m > 0:
  prev = 0

  for i in range(m):
    answer *= dp[vip[i] - 1 - prev]
    prev = vip[i]

  answer *= dp[n - prev]

else:
  answer = dp[n]

print(answer)


