import sys

input = sys.stdin.readline

def find(parents, a):
    if parents[a] == a:
        return a
    parents[a] = find(parents, parents[a])
    return parents[a]

def union(parents, counts, a):
    a = find(parents, a)
    #첫 번째 도킹
    if counts[a] == 0:
        counts[a] = 1
        return
    
    tot = counts[a]
    tmp = a

    # 두번째 도킹을 하는데 내가 이미 상위 부모가 존재해
    # 내가 들어갈 자리가 있는지 확인해(counts[a] < a)
    # 들어갈 수 있다면 지금 도킹할 수 있는 가장 큰 게이트를 찾아가 우선
    if tot < tmp:
        curBig = a - counts[a]
        while counts[curBig] != 0: # 도킹 가능한 가장 큰 게이트에 비행기를 더 댈 수 있는지 확인해볼까?
            tot += counts[curBig]
            if tot == tmp:
                print(sum(counts) - counts[tmp] + tot - counts[curBig])
                exit(0)
            parents[curBig] = tmp
            curBigtmp = curBig
            curBig -= counts[curBig]
            counts[curBigtmp] = 0
        parents[curBig] = tmp
        counts[curBig] = 0
    else:
        print(sum(counts))
        exit(0)
    
    counts[tmp] = tot + 1
    

G = int(input())
P = int(input())

parents = [i for i in range(G+1)]
counts = [0 for i in range(G+1)]

for _ in range(P):
    gi = int(input())
    union(parents, counts, gi)

print(sum(counts))


