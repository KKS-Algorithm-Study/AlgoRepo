"""
1. 문제 접근 방식 (답안 참고)
  1) 툭정 위치까지 올 때 최대의 변의 길이를 dp에 저장한다.
    ** 모두 1일 때를 가정했을 때 규칙을 찾는다.
    ** 그리고 0인 경우에는 그 지점까지의 최대 변의 길이를 0으로 넣어준다.

2. 시간 복잡도
  1) O(n * m)이다.

"""
n, m = map(int, input().split())
board = [list(map(int, input().rstrip())) for _ in range(n)]
dp = [[0] * m for _ in range(n)]
answer = 0

for i in range(n):
  for j in range(m):
    if i == 0 or j == 0:
      dp[i][j] = board[i][j]
    elif board[i][j] == 1:
      dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1
    else:
      dp[i][j] = 0

    answer = max(answer, dp[i][j])

print(answer**2)

