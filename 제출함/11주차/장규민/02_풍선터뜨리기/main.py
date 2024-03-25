from collections import deque

n = int(input())
numbers = list(map(int, input().split()))

balloons = deque()

for i in range(n):
    balloons.append((numbers[i], (i + 1)))

while balloons:
    move_cnt, idx = balloons.popleft()
    
    print(idx, end=' ')
    
    if not balloons:
        break

    if move_cnt > 0:
        for _ in range(move_cnt - 1):
            balloons.append(balloons.popleft())
        continue
    
    for _ in range(-move_cnt):
        balloons.appendleft(balloons.pop())
