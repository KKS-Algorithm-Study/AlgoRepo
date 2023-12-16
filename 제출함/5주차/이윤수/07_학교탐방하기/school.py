import sys
input = sys.stdin.readline
num_buildings, num_roads = map(int, input().split())
#입구
reps = [i for i in range(num_buildings+1)]
roads = list(map(lambda x: (x[0], x[1], (x[2]+1)%2), [list(map(int, input().split())) for _ in range(num_roads+1)]))

def find_rep(num):
    if reps[num] == num:
        return num
    reps[num] = find_rep(reps[num])
    return reps[num]

def union_set(num1, num2):
    rep1, rep2 = find_rep(num1), find_rep(num2)
    if rep1 > rep2:
        reps[rep1] = rep2
    else:
        reps[rep2] = rep1
    
def is_same_set(num1, num2):
    return find_rep(num1) == find_rep(num2)

#최악
wc = 0
roads.sort(key=lambda x: x[2])
for road in roads:
    b1, b2, weight = road
    if not is_same_set(b1, b2):
        union_set(b1, b2)
        wc += weight

#최선
bc = 0
reps = [i for i in range(num_buildings+1)]
roads.sort(key=lambda x: -x[2])
for road in roads:
    b1, b2, weight = road
    if not is_same_set(b1, b2):
        union_set(b1, b2)
        bc += weight
        
print(abs((wc**2) - (bc**2)))