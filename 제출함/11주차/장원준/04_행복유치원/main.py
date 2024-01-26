"""
1. 문제 접근 방식
  1) 그리디로 풀이
  2) 각 인접한 원생끼리 조가 될 수 있으므로 인접한 원생의 키 차이를 배열에 저장한다.
  3) 저장한 배열을 정렬하고, 0 ~ n-k 까지 더한다.

2. 시간 복잡도
  1) 원생의 배열 순회 -> n
  2) 정렬 -> nlogn
  3) 따라서 최종 시간 복잡도는 O(nlogn)이다.

"""
import sys

input = sys.stdin.readline

n, k = map(int, input().split())
kids = [*map(int, input().split())]

diff = []

for i in range(n - 1):
  diff.append(kids[i + 1] - kids[i])

diff.sort()

result = 0

# 비용이 최소인 애들부터 조로 묶어준다.
for i in range(n - k):
  result += diff[i]

print(result)
