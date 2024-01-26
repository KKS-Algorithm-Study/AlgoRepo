"""
문제 접근 방식
가장 작은애 두개 뽑아서 누적합 구하면 되는듯
큐쓰면 되겠다
"""
import sys
from heapq import heapify, heappop, heappush

input = sys.stdin.readline

T = int(input())

for _ in range(T):
    N = int(input())
    papers = list(map(int, input().split()))

    heapify(papers)
    
    S = 0
    while len(papers) > 1:
        cur = 0
        cur += heappop(papers)
        cur += heappop(papers)
        S += cur
        heappush(papers, cur)

    print(S)
    