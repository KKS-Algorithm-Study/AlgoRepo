n = int(input())

m = int(input())

max_cost = int(1e7)
costs = [[0 if i == j else max_cost for j in range(n+1)]  for i in range(n + 1)]

for _ in range(m):
    sour, des, cost = map(int, input().split())
    costs[sour][des] = min(costs[sour][des], cost)

for k in range(1, n + 1):
    for i in range(1, n + 1):
        for j in range(1, n + 1):
            costs[i][j] = min(costs[i][j], costs[i][k] + costs[k][j])

for i in range(1, n + 1):
    for j in range(1,n + 1):
        print(costs[i][j] if max_cost != costs[i][j] else 0, end=' ')
    print()
    
