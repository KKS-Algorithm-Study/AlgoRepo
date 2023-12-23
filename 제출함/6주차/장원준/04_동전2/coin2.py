"""
1. 문제 접근 방식 (동전1 문제와 동일)
  1) k원까지 1원씩 증가하는 가상의 dp배열을 만든다.
  2) dp 배열을 순회하며 그 인덱스에 해당하는 금액을 만들 수 있는 경우의 수를 계산한다.
  3) 현재 바라보는 인덱스(금액)와 현재 가지고 있는 코인의 가치를 비교하여,
    [금액 - 코인의 가치]를 뺀 위치에서 코인의 가치를 더하면 현재 바라보는 인덱스까지 계산이 가능하다.

2. 시간 복잡도
  1) for문을 2번 수행하기 때문에 O(n * k)이다.
"""
n, k = map(int, input().split())

coins = []
INF = int(1e9)
dp = [0] + [INF] * k
for _ in range(n):
  coins.append(int(input()))


"""
      0 1 2 3 4 5 6 7 8 9 10 (동전 몇원인지?)
1원    0 1 1 1 1 1 1 1 1 1 1
3원    0 0 0 1 0 0 1 0 0 1 0 
5원
"""
for coin in set(coins):
  for i in range(coin, len(dp)):
    # 이전에 계산한 값에서, 나의 동전 가치를 더했을 때와
    # 비교하여 최솟값으로 계산해줌
    dp[i] = min(dp[i], dp[i - coin] + 1)

if dp[k] == INF:
  print(-1)
else:
  print(dp[k])