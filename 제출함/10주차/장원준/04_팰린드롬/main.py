import sys

input = sys.stdin.readline

n = int(input())
arr = [0] + list(map(int, input().split()))
dp = [[0] * (n + 1) for _ in range(n + 1)]

for right in range(n + 1):
  for left in range(right + 1):
    if left == right:
      dp[right][left] = 1
    elif right - left == 1 and arr[right] == arr[left]:
      dp[right][left] = 1
    else:
      if dp[right - 1][left + 1] == 1 and arr[right] == arr[left]:
        dp[right][left] = 1
      else:
        dp[right][left] = 0

m = int(input())
answer = []

for _ in range(m):
  left, right = map(int, input().split())
  answer.append(str(dp[right][left]))

print("\n".join(answer))