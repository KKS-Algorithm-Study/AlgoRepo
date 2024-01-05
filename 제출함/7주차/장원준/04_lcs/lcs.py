"""
1. 문제 접근 방식
  1) dp의 행과 열은 각 문자열의 1개 문자를 가리키는 인덱스가 된다.
  2) 이중 for문을 순회하며 각 인덱스의 문자가 같은지 확인한다.
    a. 같은 경우 -> 이전에 계산한 대각선 위의 결과에 + 1만 해준다.
    b. 다른 경우 -> 이전에 계산한 위, 왼쪽 결과 중 큰 값을 저장한다.

2. 시간 복잡도
  1) 이중 for문으로 탐색하므로 O(n * m)이다.
"""
str1 = input()
str2 = input()
dp = [[0] * (len(str2) + 1) for _ in range(len(str1) + 1)]

for i in range(1, len(str1) + 1):
  for j in range(1, len(str2) + 1):
    if str1[i - 1] == str2[j - 1]:
      dp[i][j] = dp[i - 1][j - 1] + 1
    else:
      dp[i][j] = max(dp[i][j - 1], dp[i - 1][j])

print(dp[len(str1)][len(str2)])