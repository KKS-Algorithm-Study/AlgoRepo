import sys

input = sys.stdin.readline

n, m = map(int, input().split())

population = [list(map(int, input().split())) for _ in range(n)]

prefix_sum = [[0] * (m + 1) for _ in range(n + 1)]

for i in range(1, n + 1):
    for j in range(1, m + 1):
        prefix_sum[i][j] = prefix_sum[i - 1][j] + prefix_sum[i][j - 1] - prefix_sum[i - 1][j - 1] + population[i - 1][j - 1]

k = int(input())

for _ in range(k):
    y1, x1, y2, x2 = map(int, input().split())
    print(prefix_sum[y2][x2] + prefix_sum[y1 - 1][x1 - 1] - prefix_sum[y1 - 1][x2] - prefix_sum[y2][x1 - 1])
