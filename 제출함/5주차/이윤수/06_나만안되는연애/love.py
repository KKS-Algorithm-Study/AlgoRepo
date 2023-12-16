import sys
from heapq import heapify, heappop
input = sys.stdin.readline
num_unis, num_roads = map(int, input().split())
uni_sex = [''] + input().split()
pq = list(map(lambda x: (x[2], x[0], x[1]), list(tuple(map(int, input().split())) for _ in range(num_roads))))
heapify(pq)
reps = [i for i in range(num_unis+1)]

def find_rep(num): 
    if reps[num] == num:
        return num
    reps[num] = find_rep(reps[num])
    return reps[num]

def union_set(num1, num2):
    reps[find_rep(num2)] = find_rep(num1)

def is_same_set(num1, num2):
    return find_rep(num1) == find_rep(num2)

def is_same_sex(num1, num2):
    return uni_sex[num1] == uni_sex[num2]

total_cost = 0
while pq:
    cost, uni1, uni2 = heappop(pq)
    if not is_same_set(uni1, uni2) and not is_same_sex(uni1, uni2):
        union_set(uni1, uni2)
        total_cost += cost

num_reps = 0
for i in range(1, num_unis+1):
    if reps[i] == i:
        num_reps+=1
print(total_cost) if num_reps == 1 else print(-1)