"""
문제 접근 방식:
    - 동전 1의 풀이방식에서 힌트를 얻음
    - 동전 2는 원하는 k원을 만들 수 있는 최소 동전 갯수를 구하는 것
        - 동전은 중복이 가능하기 때문에 집합(set)으로 입력받음
        - 최솟값을 구해야하기 때문에 INF값으로 dp배열 초기화
        - 0원일 때는 아무 동전도 내지 않기 때문에 0값으로 초기화
        - 동전을 순회하면서 1~k원까지 최소 동전갯수를 메모이제이션 한다.
            - 메모이제이션을 할 때 비교값은 이전 동전을 사용했을 때 갯수와 현재 동전을 사용했을 때 갯수를 비교해 작은 값으로 넣는다.
"""

n,k = map(int,input().split(' '))

coins = set()
for _ in range(n):
    coins.add(int(input()))

dp = [[1e6 for _ in range(k+1)] for _ in range(len(coins))]

for i in range(len(coins)):
    dp[i][0] = 0

cnt = 1
for i in range(coins[0], k+1, coins[0]):
    dp[0][i] = cnt
    cnt+=1
    
for idx, coin in enumerate(coins[1:], 1):
    for krw in range(1, k+1):
        if krw >= coin:
            dp[idx][krw] = min(dp[idx-1][krw], dp[idx][krw-coin] + 1)
        else:
            dp[idx][krw] = dp[idx-1][krw]
            
if dp[len(coins)-1][k] == 1e6:
    print(-1)
else:
    print(dp[len(coins)-1][k])