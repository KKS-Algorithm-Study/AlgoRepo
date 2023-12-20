A = list(input())
B = list(input())

n = len(A)
m = len(B)

dp = [([[0,''] for _ in range(m+1)]) for _ in range(n+1)]

for i in range(1, n+1):
    for j in range(1, m+1):
        if A[i-1] == B[j-1]:
            dp[i][j][0] = dp[i-1][j-1][0]+1
            dp[i][j][1] = dp[i-1][j-1][1] + A[i-1]
        else:
            if dp[i-1][j][0] > dp[i][j-1][0]:#A거 가져오는거
                dp[i][j][0] = dp[i-1][j][0]
                dp[i][j][1] = dp[i-1][j][1]
            else:
                dp[i][j][0] = dp[i][j-1][0]
                dp[i][j][1] = dp[i][j-1][1]

print(*dp[n][m], sep='\n')