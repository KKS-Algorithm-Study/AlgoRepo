"""
1. 문제 접근 방식
  1) 이분탐색으로 풀이
  2) a배열의 특정 값을 기준으르 작은 값 중 가장 큰 값, 큰 값 중 가장 작은 값을 찾는다.
  3) 찾은 2개의 값을 비교하여 절대값이 더 작은 값을 c배열에 추가한다.

2. 시간 복잡도
  1) 테스트 케이스만큼 반복한다 -> O(t)
  2) 1개의 테스트 케이스 안에서 n번의 순회를 한다. -> O(n)
  3) 1번의 순회 당 2번씩 이분탐색을 진행한다. -> O(2 * logm)
  4) 최종 시간 복잡도는 O(t * n * logm)이다.

"""
def find_left(arr, x):
  left, right = 0, len(arr) - 1
  result = int(1e9)

  while left <= right:
    mid = (left + right) // 2

    if arr[mid] <= x:
      left = mid + 1
      result = arr[mid]
    else:
      right = mid - 1

  return result


def find_right(arr, x):
  left, right = 0, len(arr) - 1
  result = int(1e9)

  while left <= right:
    mid = (left + right) // 2

    if arr[mid] < x:
      left = mid + 1
    else:
      result = arr[mid]
      right = mid - 1

  return result


import sys

input = sys.stdin.readline

test_case = int(input().rstrip())

for _ in range(test_case):
  n, m = map(int, input().split())

  arr = list(map(int, input().split()))
  brr = list(map(int, input().split()))
  crr = [0] * n

  brr.sort()

  for i in range(n):
    left = find_left(brr, arr[i])
    right = find_right(brr, arr[i])

    # left, right 중 절댓값이 작은 것을 c에 추가
    if abs(arr[i] - left) <= abs(arr[i] - right):
      crr[i] = left
    else:
      crr[i] = right

  print(sum(crr))