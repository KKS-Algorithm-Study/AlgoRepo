N = int(input())

ropes = []
for _ in range(N):
    ropes.append(int(input()))

ropes.sort()
mav = -1
for i in range(N):
    mav = max(mav, ropes[i] * (N-i))

print(mav)