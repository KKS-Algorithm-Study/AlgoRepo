"""
1. 문제 접근 방식
  1) k원까지 1원씩 증가하는 가상의 dp배열을 만든다.
  2) dp 배열을 순회하며 그 인덱스에 해당하는 금액을 만들 수 있는 경우의 수를 계산한다.
  3) 현재 바라보는 인덱스(금액)와 현재 가지고 있는 코인의 가치를 비교하여,
    [금액 - 코인의 가치]를 뺀 위치에서 코인의 가치를 더하면 현재 바라보는 인덱스까지 계산이 가능하다.

2. 시간 복잡도
  1) for문을 2번 수행하기 때문에 O(n * k)이다.
"""
n, k = map(int, input().split())
coin = []
for _ in range(0, n):
  coin.append(int(input()))

dp = [0] * (k + 1)
dp[0] = 1

for c in coin:
  for i in range(c, k + 1):
    dp[i] = dp[i] + dp[i - c]

print(dp[k])