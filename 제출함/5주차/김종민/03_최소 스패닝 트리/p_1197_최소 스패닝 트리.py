def find(parents, a):
    if parents[a] == a:
        return a
    parent = find(parents, parents[a])
    parents[a] = parent
    return parent

def union(parents, a, b):
    a = find(parents, a)
    b = find(parents, b)
    if a > b:
        parents[a] = b
    elif a < b:
        parents[b] = a

N, M = map(int, input().split(' '))
parents = [i for i in range(N+1)]
d = {}

for _ in range(M):
    A, B, C = map(int, input().split(' '))

    key = (A, B)
    d[key] = min(d.get(key, 1e9), C)

cnt = 0
tot = 0
for key, value in sorted(d.items(), key = lambda item : item[1]):
    if find(parents, key[0]) != find(parents, key[1]):
        union(parents, key[0], key[1])
        cnt += 1
        tot += value

        if cnt == N-1:
            tot -= value
            print(tot)
            exit(0)