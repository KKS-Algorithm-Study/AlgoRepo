def find(parents, a):
    if parents[a] == a:
        return a
    parent = find(parents, parents[a])
    parents[a] = parent
    return parent

def union(parents, origin, a, b):
    a = find(parents, a)
    b = find(parents, b)
    if origin[a] > origin[b]:
        parents[a] = b
    else:
        parents[b] = a

N, M, k = map(int, input().split(' '))
parents = [i for i in range(N)]
fees = list(map(int, input().split(' ')))

for _ in range(M):
    v, w = map(int, input().split(' '))
    v -= 1
    w -= 1
    union(parents, fees, v, w)

sumSet = set()
for unit in parents:
    sumSet.add(find(parents, unit))
tot = 0
for element in sumSet:
    tot += fees[element]

answer = str(tot) if tot <= k else 'Oh no'
print(answer)