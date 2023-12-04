"""
문제 접근 방식
2차원 배열의 DP로 진행을 한다.
1. 1,2번 열의 값을 초기화 해준다.
2. for 문으로 진행하며
    2-1. max((내 현재 값 + 이전 열의 다른 행), (내 현재 값 + 이전 이전 열의 Max값))을 현재 칸에 넣어준다.
3. 최종 열의 Max값 리턴
"""

T = int(input())

for _ in range(T):
    n = int(input())

    dp = [[0 for _ in range(n)] for _ in range(2)]
    
    stickers = [list(map(int, input().split(' '))) for _ in range(2)]

    if n == 1:
        print(max(stickers[0][0], stickers[1][0]))
        continue

    dp[0][0] = stickers[0][0]
    dp[0][1] = stickers[1][0] + stickers[0][1]
    dp[1][0] = stickers[1][0]
    dp[1][1] = stickers[0][0] + stickers[1][1]

    for i in range(2, n):
        dp[0][i] = stickers[0][i] + max(dp[1][i-1], dp[1][i-2])
        dp[1][i] = stickers[1][i] + max(dp[0][i-1], dp[0][i-2])

    print(max(dp[0][n-1], dp[1][n-1]))