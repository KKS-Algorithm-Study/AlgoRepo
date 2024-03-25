import sys

input = sys.stdin.readline

n, m = map(int, input().split())

numbers = list(map(int, input().split()))

prefix_mod = [0] * m
total = 0

for i in range(n):
    total += numbers[i]
    idx = total % m
    prefix_mod[idx] += 1

answer = prefix_mod[0]
for mod in prefix_mod:
    answer += (mod * (mod - 1)) // 2
print(answer)
