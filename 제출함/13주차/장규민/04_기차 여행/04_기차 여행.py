n, m = map(int, input().split())

path = list(map(int, input().split()))

prices = [list(map(int, input().split())) for _ in range(n - 1)]

path_count = [0] * (n + 1)

for i in range(m - 1):
    start, end = path[i], path[i + 1]

    if start > end:
        start, end = end, start

    path_count[start] += 1
    path_count[end] -= 1

for i in range(1, n + 1):
    path_count[i] += path_count[i - 1]

answer = 0
for i in range(n - 1):
    if path_count[i + 1] == 0:
        continue
    answer += min(path_count[i + 1] * prices[i][0], path_count[i + 1] * prices[i][1] + prices[i][2])
print(answer)
