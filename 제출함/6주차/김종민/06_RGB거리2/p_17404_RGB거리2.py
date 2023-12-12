"""
문제 접근 방식:
    - RGB 1이랑 비슷한데 살짝 응용한 문제
    - 첫 집과 마지막 집의 색이 같으면 안되기 때문에 총 6가지 경우만 고려하면 됨
    - 기존 로직은 RGB 1과 똑같이 가되 첫 집의 색을 선택했을 때 마지막 집에서 다른 색을 선택해서 값을 구하면 됨
"""

N = int(input())

houses = [list(map(int, input().split(' '))) for _ in range(N)]

minVal = 1e9

if N == 2:
    for case in range(3):
        val = houses[0][case]
        minVal = min(minVal, val + min(houses[1][(case + 1) % k], houses[1][(case + 2) % k]))
    print(minVal)
    exit(0)

for case in range(3):    
    dp = [[1e9 for _ in range(3)] for _ in range(N)]
    dp[0][case] = houses[0][case]
    for i in range(1,N-1):
        for j in range(3):
            dp[i][j] = houses[i][j] + min(dp[i-1][(j+1) % 3], dp[i-1][(j+2) % 3])

    # 0 => R 1 => G 2 => B
    for k in range(1, 3):
        dp[N-1][(case + k) % 3] = houses[N-1][(case + k) % 3] + \
            min(dp[N-2][(((case + k) % 3) + 1) % 3], dp[N-2][(((case + k) % 3) + 2) % 3]) 
    minVal = min(minVal, min(dp[N-1]))
print(minVal)