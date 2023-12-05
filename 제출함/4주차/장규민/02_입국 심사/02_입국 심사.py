from sys import stdin

input = stdin.readline

n, m = map(int, input().split())

enterances = [int(input()) for _ in range(n)]

INF = int(1e18)
left, right = 0, INF
answer = INF

while left <= right:
    mid = (left + right) // 2 # 걸리는 시간

    total = 0
    for enterance in enterances:
        total += (mid // enterance)

    if total >= m:
        right = mid - 1
        answer = min(answer, mid)
    else:
        left = mid + 1

print(answer)
