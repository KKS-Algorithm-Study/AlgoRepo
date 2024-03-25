import sys

input = sys.stdin.readline

n = int(input())

ropes = sorted([int(input()) for _ in range(n)])

answer = 0

for idx, rope in enumerate(ropes[::-1]):
    answer = max(answer, (idx + 1) * rope)

print(answer)
