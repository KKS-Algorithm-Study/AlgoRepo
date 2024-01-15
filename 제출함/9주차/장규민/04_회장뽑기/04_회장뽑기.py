n = int(input())

friends = [[0] * (n + 1) for _ in range(n + 1)]

while True:
    sour, des = map(int, input().split())

    if sour == -1 and des == -1:
        break

    friends[sour][des] = 1
    friends[des][sour] = 1

INF = 100
costs = [[INF] * (n + 1) for _ in range(n + 1)]

for i in range(1, n + 1):
    for j in range(1, n + 1):
        if i == j:
            costs[i][j] = 0
            continue
        
        if friends[i][j]:
            costs[i][j] = 1

for k in range(1, n + 1):
    for i in range(1, n + 1):
        for j in range(1, n + 1):
            costs[i][j] = min(costs[i][j], costs[i][k] + costs[k][j])

min_value = INF
answer = []

for i in range(1, n + 1):
    temp = max(costs[i][1:])

    if temp > min_value:
        continue
    
    if min_value > temp:
        min_value = temp
        answer = [str(i)]
        continue
    
    answer.append(str(i))

print(min_value, len(answer))
print(' '.join(answer))
