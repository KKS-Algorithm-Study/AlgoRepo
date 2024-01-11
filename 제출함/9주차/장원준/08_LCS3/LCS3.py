"""
1. 문제 접근 방식
  1) 3차원 배열인 dp의 행과 열은 각 문자열의 1개 문자를 가리키는 인덱스가 된다.
  2) 삼중 for문을 순회하며 각 인덱스의 문자가 같은지 확인한다.
    a. 같은 경우 -> 이전에 계산한 대각선 위의 결과에 + 1만 해준다.
    b. 다른 경우 -> 이전에 계산한 3가지 결과 중 큰 값을 저장한다.

2. 시간 복잡도
  1) 삼중 for문으로 탐색하므로 O(n * m * k)이다.
"""
str1, str2, str3 = input(), input(), input()
len1, len2, len3 = len(str1), len(str2), len(str3)

dp = [[[0] * (len1 + 1) for _ in range(len2 + 1)] for _ in range(len3 + 1)]

for i in range(1, len1 + 1):
  for j in range(1, len2 + 1):
    for k in range(1, len3 + 1):
      if str1[i - 1] == str2[j - 1] == str3[k - 1]:
        dp[k][j][i] = dp[k - 1][j - 1][i - 1] + 1
      else:
        dp[k][j][i] = max(dp[k - 1][j][i], dp[k][j - 1][i], dp[k][j][i - 1])

print(dp[len3][len2][len1])
