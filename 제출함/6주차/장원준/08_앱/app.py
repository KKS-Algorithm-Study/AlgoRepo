"""
1. 문제 접근 방식
  1) 행의 인덱스는 앱을, 열의 인덱스는 비용울 의미하는 2차원 배열을 만든다.
  2) 배열의 값은 특정 비용으로 (열의 인덱스) 확보할 수 있는 최대 메모리를 의미한다.

2. 시간 복잡도
  1) 앱의 갯수와 비용의 최대값 만큼 for문을 2번 수행므로 O(n * max(costs))이다.
"""

n, m = map(int, input().split())

apps = [0] + list(map(int, input().split()))
costs = [0] + list(map(int, input().split()))
sum_costs = sum(costs)

dp = [[0] * (sum_costs + 1) for _ in range(n + 1)]
result = int(1e9)

for app_idx in range(1, n + 1):
    memory = apps[app_idx]
    cost = costs[app_idx]

    for i in range(sum_costs + 1):
        if i < cost:
            dp[app_idx][i] = dp[app_idx - 1][i]
        else:
            dp[app_idx][i] = max(memory + dp[app_idx - 1][i - cost], dp[app_idx - 1][i])

for i, memory in enumerate(dp[n]):
    if memory >= m:
        print(i)
        break