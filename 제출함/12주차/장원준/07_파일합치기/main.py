"""
1. 문제 접근 방식
  1) i ~ j번째까지의 합칠 수 있는 최소 합을 구하면서 dp를 채워나간다.

2. 시간 복잡도
  1) 테스트 케이스만큼 반복문을 수행한다. -> O(t)
  2) 1번의 테스트 케이스에서 삼중 for문을 수행한다 -> O(k**3)
  3) 따라서 최종 시간 복잡도는 O(t * k**3)이다.

"""
import sys

input = sys.stdin.readline
test_case = int(input())

for _ in range(test_case):
  k = int(input())
  arr = [0] + [*map(int, input().split())]
  dp = [[0] * (k + 1) for _ in range(k + 1)]

  prefix_sum = [0] * (k + 1)

  for i in range(1, k + 1):
    prefix_sum[i] = prefix_sum[i - 1] + arr[i]

  # i ~ j번째 파일을 합쳤을 때의 최소값이 dp에 저장된다.
  for i in range(1, k + 1):
    for j in range(1, k + 1):
      if j == i + 1:
        dp[i][j] = arr[i] + arr[j]

  # dp의 맨 밑에서부터 위로 올라오면서 dp를 채워 나간다. (왜냐하면 dp 배열에서 역삼각형 모양으로 채워나갈 것이기 떄문
  # dp[1][4]는 dp[1][1]+dp[2][4], dp[1][2]+dp[3][4], dp[1][3]+dp[4][4]와 같은 경우의 수를 가진다.
  for i in range(k - 1, 0, -1):
    for j in range(1, k + 1):
      # i ~ j 번째 파일까지의 합이니, i < j여야 한다.
      if dp[i][j] == 0 and i < j:
        # min에서 찾는 값은 dp[i][k]와 dp[k + 1][j]를 만들 때의 비용
        # 따라서 합칠 때의 비용안 sum(arr[i:j + 1])까지 더해줘야 한다.
        # sum(arr[i:j + 1])은 누적합 배열을 구해놓고 계산하면 더 빠를 것 같다.
        #dp[i][j] = min([dp[i][k] + dp[k + 1][j] for k in range(i, j)]) + sum(arr[i:j + 1])
        dp[i][j] = min([dp[i][k] + dp[k + 1][j] for k in range(i, j)]) + prefix_sum[j] - prefix_sum[i - 1]

  print(dp[1][k])
  #print(*dp, sep="\n")