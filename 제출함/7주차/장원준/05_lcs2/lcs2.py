"""
1. 문제 접근 방식
  1) dp의 행과 열은 각 문자열의 1개 문자를 가리키는 인덱스가 된다.
  2) 이중 for문을 순회하며 각 인덱스의 문자가 같은지 확인한다.
    a. 같은 경우 -> 이전에 계산한 대각선 위의 결과에 문자열을 추가 해준다.
    b. 다른 경우 -> 이전에 계산한 위, 왼쪽 결과 중 큰 값을 가지는 문자열을 추가해준다.

2. 시간 복잡도
  1) 이중 for문으로 탐색하므로 O(n * m)이다.

3. lcs 구해서 최대 길이를 가지고 역탐색해서 풀어도 됨
"""

str1 = input()
str2 = input()

dp = [[''] * (len(str1) + 1) for _ in range(len(str2) + 1)]

for i in range(1, len(str2) + 1):
  for j in range(1, len(str1) + 1):
    if str2[i - 1] == str1[j - 1]:
      dp[i][j] = dp[i - 1][j - 1] + str1[j - 1]
    else:
      if len(dp[i][j - 1]) > len(dp[i - 1][j]):
        dp[i][j] = dp[i][j] + dp[i][j - 1]
      else:
        dp[i][j] = dp[i][j] + dp[i - 1][j]

result = dp[len(str2)][len(str1)]

print(len(result))

if result:
  print(result)