"""
1. 문제 접근 방식
  1) 로프가 버틸 수 있는 중량을 정렬한다.
  2) 현재 인덱스의 로프가 견딜 수 있는 중량은 뒤에 있는 로프도 모두 버틸 수 있는 중량이므로
    반복문 1번을 수행하면 구할 수 있다.

2. 시간 복잡도
  1) 반복문을 1번만 수행하므로 O(N)이다.

"""
import sys

input = sys.stdin.readline

n = int(input().rstrip())
ropes = []
max_weight = 0

for _ in range(n):
  length = int(input().rstrip())

  ropes.append(length)
  max_weight = max(max_weight, length)

ropes.sort()

for i in range(len(ropes)):
  max_weight = max(max_weight, ropes[i] * (len(ropes) - i))

print(max_weight)