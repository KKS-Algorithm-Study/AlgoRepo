import sys
input = sys.stdin.readline
max_num, num_calc = map(int, input().split())
sets = list(range(max_num+1))

def find_rep(num):
    if sets[num] == num:
        return num
    sets[num] = find_rep(sets[num])
    return sets[num]

def union_set(num1, num2):
    sets[find_rep(num2)] = find_rep(num1)

def is_same_set(num1, num2):
    return find_rep(num1) == find_rep(num2)

for _ in range(num_calc):
    calc, set1, set2 = map(int, input().split())
    if calc == 0:
        union_set(set1, set2)
    else:
        print("YES") if is_same_set(set1, set2) else print("NO")
