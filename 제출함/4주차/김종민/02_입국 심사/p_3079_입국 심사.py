import sys

input = sys.stdin.readline

N,M = map(int, input().split(' '))

times = []
for _ in range(N):
    times.append(int(input()))
    
times.sort()

# 시간초
left = 1
right = 1000000000000000000
answer = 1000000000000000000

while left <= right:
    mid = (left + right) // 2
    
    tot = 0
    for time in times:
        tot += mid // time 
        
    if tot >= M:
        answer = mid
        right = mid - 1
    else:
        left = mid + 1
print(answer)