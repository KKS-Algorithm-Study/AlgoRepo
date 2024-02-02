"""
1. 문제 접근 방식
  1) 누적합을 구하여 누적합을 m으로 나눴을 때의 경우를 센다.
  2) 나머지 별 갯수에서 2개를 뽑는 조합의 수를 구한다.

2. 시간 복잡도
  1) for문을 1번 수행하므로 O(n)이다.

"""
import sys

input = sys.stdin.readline
n, m = map(int, input().split())
nums = [*map(int, input().split())]

remainders = [0] * m
prefix_sum = 0

for i in range(n):
  prefix_sum += nums[i]
  remainders[prefix_sum % m] += 1

# 기본으로 나머지가 0인 요소들은 1개만 뽑아도 조건을 만족한다.
answer = remainders[0]

for i in range(m):
  # remainders[i]개 중 2개를 뽑는 조합의 수
  # 같은 나머지를 가지는 숫자끼리 빼면 m으로 나누었을 때 나누어 떨어진다.
  answer += remainders[i] * (remainders[i] - 1) // 2

print(answer)