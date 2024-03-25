import sys
from heapq import heappush, heappop, heapify

input = sys.stdin.readline

for _ in range(int(input())):
    n = int(input())

    heap = list(map(int, input().split()))
    heapify(heap)

    answer = 0
    while True:
        f1 = heappop(heap)
        
        if not heap:
            break

        f2 = heappop(heap)

        total = f1 + f2
        answer += total
        heappush(heap, total)

    print(answer)
