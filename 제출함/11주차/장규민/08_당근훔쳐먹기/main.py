import sys

input = sys.stdin.readline

n, t = map(int, input().split())

carrots = sorted([list(map(int, input().split())) for _ in range(n)], key=lambda x: x[1])

time = t - n
answer = 0

for i in range(n):
    answer += carrots[i][0] + carrots[i][1] * time
    time += 1
    
print(answer)
