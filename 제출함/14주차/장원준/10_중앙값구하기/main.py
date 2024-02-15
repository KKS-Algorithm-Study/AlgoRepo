"""
1. 문제 접근 방식
  1) 숫자를 1개씩 받으면서 이분탐색으로 위치를 찾아 넣어주며 정렬된 배열을 유지한다.
    ** 그러나 숫자를 받으며 매번 sort해도 시간은 더 걸리지만 풀이는 가능하다.

2. 시간 복잡도
  1) 테스트 케이스만큼 반복문을 수행한다. -> O(t)
  2) 1번의 테스트 케이스에서 m번의 반복문을 수행한다. -> O(m)
  3) 1번의 반복문에서 이분 탐색을 수행한다. -> 최대 O(logm)
    ** 만약에 여기서 이분 탐색이 아닌 정렬을 한다면 O(mlogm)이다.
  4) 따라서 최종 시간 복잡도는 O(t * m * logm)이다.

"""
import sys
from bisect import bisect_left

input = sys.stdin.readline

n = int(input().rstrip())

for _ in range(n):
  m = int(input().rstrip())
  nums, input_nums, medians = [], [], []
  index = 0

  # 숫자를 1개씩 받으면서 중앙값 계산
  while index < m:
    if index % 10 == 0:
      input_nums = [*map(int, input().split())]

    pos = bisect_left(nums, input_nums[index % 10])
    nums.insert(pos, input_nums[index % 10])

    if index % 2 == 0:
      medians.append(nums[len(nums) // 2])

    index += 1

  # 중앙값을 10개씩 끊어서 출력하기
  print(len(medians))

  for i in range(len(medians)):
    if i > 0 and (i % 9 == 0 or i == len(medians) - 1):
      print(medians[i], end='\n')
    else:
      print(medians[i], end=' ')