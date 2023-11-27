import sys

sys.setrecursionlimit(10**5)

def getParent(parents, a):
    if parents[a] == a:
        return a
    parent = getParent(parents, parents[a])
    parents[a] = parent
    return parent

def unionParent(parents, a, b):
    a = getParent(parents, a)
    b = getParent(parents, b)
    if a > b:
        parents[a] = b
    elif a < b:
        parents[b] = a

def findParent(parents, a, b):
    return getParent(parents, a) == getParent(parents, b)

n,m = map(int, input().split(' '))

parents = [i for i in range(n+1)]
for _ in range(m):
    cmd, a, b = map(int, input().split(' '))

    if cmd == 0:
        unionParent(parents, a, b)
    else:
        print('YES' if findParent(parents, a, b) else 'NO')