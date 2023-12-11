"""
문제 접근 방식:
    - 전형적인 dp 문제인 것 같다.
    - 옛날에 Ted가 해준 강의에서 보여줬던 예제와 비슷한 종류였기에 쉽게 접근 가능했다.
    - 1번 배열을 초기화 시키고 dp를 진행하면서 이전 집에서 색칠하지 않은 두 가지 색비용 중 작은걸 더해주는 방식으로 진행
"""
N = int(input())

houses = [list(map(int, input().split(' '))) for _ in range(N)]

dp = [[0 for _ in range(3)] for _ in range(N)]

for i in range(3):
    dp[0][i] = houses[0][i]

for i in range(1,N):
    for j in range(3):
        dp[i][j] = houses[i][j] + min(dp[i-1][(j+1) % 3], dp[i-1][(j+2) % 3])

print(min(dp[N-1]))