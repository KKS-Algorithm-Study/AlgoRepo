"""
1. 문제 접근 방식 (답안 참고)
  1) 꽃이 피는 날짜, 지는 날짜를 기준으로 오름차순 정렬을 한다.
  2) 특정 날짜를 기준으로 그 날짜 이전에 피면서 가장 늦게 지는 꽃을 선택한다.

2. 시간 복잡도
  1) 최대 n개 만큼 반복하므로 O(n)이다.

"""
import sys
from collections import deque

input = sys.stdin.readline
n = int(input().rstrip())
flowers = []
answer = 0

for _ in range(n):
  start_m, start_d, end_m, end_d = map(int, input().split())
  flowers.append((start_m * 100 + start_d, end_m * 100 + end_d))

# 꽃이 피는 순, 지는 순서대로 정렬
flowers.sort(key=lambda x:(x[0], x[1]))
queue = deque(flowers)

# 시작 날짜를 3월 1로 지정 후 탐색 시작
end_date = 301

while queue:
  # 마지막 꽃의 지는 날짜가 12월 1일을 지났거나
  # 다음 꽃의 시작 날짜가 end_date를 지나서 문제의 조건을 만족하지 못할 때
  if end_date >= 1201 or queue[0][0] > end_date:
    break

  temp_end = -1

  while queue and queue[0][0] <= end_date:
    # 피는 날짜는 늦지만, 더 빨리 지는 꽃이 있을 수 있음
    temp_end = max(temp_end, queue.popleft()[1])

    # 만약 더 늦게 지는 꽃이 있더라도 12월 1일을 넘어버리면 바로 종료
    if temp_end >= 1201:
      break

  end_date = temp_end
  answer += 1


if end_date < 1201:
  print(0)
else:
  print(answer)
