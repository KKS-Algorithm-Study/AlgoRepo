import sys
input = sys.stdin.readline
num_students, num_relations, money_limit = map(int, input().split())
friend_costs = [0] + list(map(int, input().split()))
reps = [i for i in range(num_students+1)]

def find_rep(num):
    if reps[num] == num:
        return num
    reps[num] = find_rep(reps[num])
    return reps[num]

def union_set(num1, num2):
    rep1, rep2 = find_rep(num1), find_rep(num2)
    if friend_costs[rep1] < friend_costs[rep2]:
        reps[rep2] = rep1
    else:
        reps[rep1] = rep2

def is_same_set(num1, num2):
    return find_rep(num1) == find_rep(num2)

for _ in range(num_relations):
    friend1, friend2 = map(int, input().split())
    if not is_same_set(friend1, friend2):
        union_set(friend1, friend2)

min_friend_cost = 0
for i in range(1, num_students+1):
    if reps[i] == i:
        min_friend_cost += friend_costs[i]

print(min_friend_cost) if min_friend_cost <= money_limit else print("Oh no")