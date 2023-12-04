import sys

input = sys.stdin.readline

def find(parents, a):
    if parents[a] == a:
        return a
    parent = find(parents, parents[a])
    parents[a] = parent
    return parent

"""
x 축의 길이를 저장하면서 합쳐야 하나?
근데 이러면 N^2 비교해서 무조건 시간초과긴 해
일단 해?
결국 정답을 봤음
그냥 x1값으로 정렬하면 되는 거였음.........
"""
def union(parents, distances, a, b, end):
    ax1, ax2, a = distances[a]
    bx1, bx2, b = distances[b]
    if bx1 <= end:
        a = find(parents, a)
        if a > b:
            parents[a] = b
        else:
            parents[b] = a
        return max(bx2, end)
    return bx2

N, Q = map(int, input().split(' '))
parents = [i for i in range(N+1)]
distances = [(0,0,0) for _ in range(N+1)]

for i in range(1, N+1):
    x1, x2, y = map(int, input().split(' '))
    distances[i] = (x1, x2, i)

distances.sort(key = lambda x : x[0])
end = distances[1][1]
for i in range(2, N+1):
    end = union(parents, distances, i-1, i, end)

for _ in range(Q):
    a, b = map(int, input().split(' '))
    if find(parents, a) == find(parents, b):
        print(1)
    else:
        print(0)