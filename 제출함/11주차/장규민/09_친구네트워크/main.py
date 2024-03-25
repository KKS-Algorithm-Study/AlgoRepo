import sys

input = sys.stdin.readline


def find_parent(x, parents):

    if x == parents[x]:
        return x

    parents[x] = find_parent(parents[x], parents)

    return parents[x]


def union(x, y, parents, count):

    px, py = find_parent(x, parents), find_parent(y, parents)

    if px == py:
        return count[px]
    
    if count[py] > count[px]:
        py, px = px, py

    count[px] += count[py]

    parents[py] = px

    return count[px]


def solution():
    for _ in range(int(input())):
        f = int(input())
        count = {}
        parents = {}
    
        for sour, des in [list(input().rstrip().split()) for i in range(f)]:
            if parents.get(sour) is None:
                parents[sour] = sour
                count[sour] = 1

            if parents.get(des) is None:
                parents[des] = des
                count[des] = 1
            
            union(sour, des, parents, count)
            print(count[find_parent(sour, parents)])

            
solution()
