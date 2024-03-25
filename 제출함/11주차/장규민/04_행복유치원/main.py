import sys

input = sys.stdin.readline

n, m = map(int, input().split())

numbers = list(map(int, input().split()))

diff = []

for i in range(n - 1):
    diff.append(numbers[i + 1] - numbers[i])

print(sum(sorted(diff)[:n-m]))
