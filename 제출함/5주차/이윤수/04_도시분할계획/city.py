import sys
from heapq import heapify, heappop
input = sys.stdin.readline
num_houses, num_roads = map(int, input().split())
villages = [i for i in range(num_houses+1)]
num_village = num_houses
pq = list(map(lambda x: (x[2], x[0], x[1]), list(tuple(map(int, input().split())) for _ in range(num_roads))))
heapify(pq)

def find_rep(num):
    if villages[num] == num:
        return num
    villages[num] = find_rep(villages[num])
    return villages[num]

def union_set(num1, num2):
    villages[find_rep(num2)] = find_rep(num1)

def is_same_set(num1, num2):
    return find_rep(num1) == find_rep(num2)

min_cost = 0
while pq and num_village > 2:
    cost, house1, house2 = heappop(pq)
    if not is_same_set(house1, house2):
        union_set(house1, house2)
        min_cost += cost
        num_village-=1
        
print(min_cost)