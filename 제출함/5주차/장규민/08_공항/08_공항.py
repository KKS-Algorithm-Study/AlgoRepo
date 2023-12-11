import sys

input = sys.stdin.readline

G = int(input())
P = int(input())

airplanes =  [int(input()) for _ in range(P)]
parents = [i for i in range(G + 1)]


def find_parent(x):
    global parents

    if parents[x] == x:
        return x

    parents[x] = find_parent(parents[x])

    return parents[x]


def union(x, y):
    global parents

    px, py = find_parent(x), find_parent(y)
    if px == py:
        return

    parents[px] = py


answer = 0
for airplane in airplanes:
    parent = find_parent(airplane)
    
    if parent == 0:
        break
    union(parent, parent - 1)
    answer += 1
print(answer)
    
