string1 = input()
string2 = input()

n, m = len(string1), len(string2)

dp = [[0] * (m + 1) for _ in range(n + 1)]
answer = []

for i in range(1, n + 1):
    for j in range(1, m + 1):
        if string1[i - 1] == string2[j - 1]:
            dp[i][j] = dp[i - 1][j - 1] + 1
            continue

        dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])

idx1, idx2 = n, m

while idx1 >= 0 and idx2 >= 0:
    cur = dp[idx1][idx2]
    
    if cur == dp[idx1][idx2 - 1]:
        idx2 -= 1
        continue

    if cur == dp[idx1 - 1][idx2]:
        idx1 -= 1
        continue

    answer.append(string1[idx1 - 1])
    idx1 -= 1
    idx2 -= 1

print(len(answer))
print(''.join(reversed(answer)))
