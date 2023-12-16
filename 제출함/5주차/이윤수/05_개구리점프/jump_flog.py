import sys
from heapq import heapify, heappop
input = sys.stdin.readline
num_trees, num_queries = map(int, input().split())
pq = list(map(lambda x: (*x[0], x[1]), zip([list(map(int, input().split())) for _ in range(num_trees)], [i for i in range(1, num_trees+1)])))
heapify(pq)
reps = [i for i in range(num_trees+1)]

def find_rep(num):
    if reps[num] == num:
        return num
    reps[num] = find_rep(reps[num])
    return reps[num]

def union_set(num1, num2):
    reps[find_rep(num2)] = find_rep(num1)

def is_same_set(num1, num2):
    return find_rep(num1) == find_rep(num2)

pre_min, pre_max, pre_y, pre_num = heappop(pq)
while pq:
    cur_min, cur_max, cur_y, cur_num = heappop(pq)
    if cur_min <= pre_max and pre_y != cur_y:
        union_set(pre_num, cur_num)
    if pre_max < cur_max:
        pre_max = cur_max
        pre_y = cur_y
    pre_num = cur_num
    
for _ in range(num_queries):
    tree1, tree2 = map(int, input().split())
    print(1) if is_same_set(tree1, tree2) else print(0)