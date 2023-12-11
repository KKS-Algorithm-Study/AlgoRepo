from sys import stdin, setrecursionlimit

input = stdin.readline
setrecursionlimit(int(1e5))

n, m = map(int, input().split())
parents = [i for i in range(n + 1)]

def find_parent(x):
    global parents

    if x == parents[x]:
        return x
    parents[x] = find_parent(parents[x])
    return parents[x]


def union(x, y):
    global parents
    
    px, py = find_parent(x), find_parent(y)

    if px == py:
        return

    parents[px] = py


for opt, a, b in [list(map(int, input().split())) for _ in range(m)]:
    if not opt:
        union(a, b)
    else:
        pa, pb = find_parent(a), find_parent(b)
        print('YES' if pa == pb else 'NO')

