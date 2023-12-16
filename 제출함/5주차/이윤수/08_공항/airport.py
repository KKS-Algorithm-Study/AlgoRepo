import sys
input = sys.stdin.readline
num_gates = int(input())
num_planes = int(input())
gates_to_dock = [int(input()) for _ in range(num_planes)]
reps = [i for i in range(num_gates+1)]
def find_rep(num):
    if reps[num] == num:
        return num
    reps[num] = find_rep(reps[num])
    return reps[num]

def union_set(num1, num2):
    reps[find_rep(num2)] = find_rep(num1)

plane_cnt=0
for gate_num in gates_to_dock:
    actual_gate = find_rep(gate_num)
    if actual_gate == 0:
        break
    plane_cnt+=1
    union_set(actual_gate-1, actual_gate)
    
print(plane_cnt)