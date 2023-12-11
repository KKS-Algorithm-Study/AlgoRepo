from sys import stdin

input = stdin.readline

n, m, k = map(int, input().split())

money = [0] + list(map(int, input().split()))

parents = [i for i in range(n + 1)]


def find_parent(x):
    global parents

    if parents[x] == x:
        return x

    parents[x] = find_parent(parents[x])
    return parents[x]


def union(x, y):
    global money, parents
    
    px, py = find_parent(x), find_parent(y)

    if py == px:
        return

    if money[py] < money[px]:
        py, px = px, py

    parents[py] = px

for a, b in [list(map(int, input().split())) for _ in range(m)]:
    union(a, b)

answer = 0
for i in range(1, n + 1):
    if parents[i] == i:
        answer += money[i]

print('Oh no' if answer > k else answer)
