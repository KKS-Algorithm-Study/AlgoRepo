import sys
from heapq import heapify, heappop
input = sys.stdin.readline

num_vertices, num_edges = map(int, input().split())
pq = list(map(lambda x: (x[2], x[0], x[1]), list(tuple(map(int, input().split())) for _ in range(num_edges))))
heapify(pq)
reps = [i for i in range(num_vertices+1)]

def find_rep(num):
    if reps[num] == num:
        return num
    reps[num] = find_rep(reps[num])
    return reps[num]

def union_set(num1, num2):
    reps[find_rep(num2)] = find_rep(num1)

def is_same_set(num1, num2):
    return find_rep(num1) == find_rep(num2)

total_cost = 0
while pq:
    cost, v1, v2 = heappop(pq)
    if not is_same_set(v1, v2):
        union_set(v1, v2)
        total_cost += cost
print(total_cost)