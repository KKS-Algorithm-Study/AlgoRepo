"""
1. 문제 접근 방식
  1) 매번 뒤집기를 실행하면 시간 초과가 나므로, R의 갯수가 홀수, 짝수인지에 따라 앞 뒤에서 요소를 제거한다.
    ** 큐를 리스트로 만들 때는 list(queue)
    ** 리스트를 뒤집기만 할 때는 arr.reverse()

2. 시간 복잡도
  1) 테스트 케이스만큼 반복문을 수행한다. -> O(t)
  2) 1번의 테스트 케이스에서 배열을 파싱한다. -> O(n)
  3) 1번의 테스트 케이스에서 함수의 길이만큼 순회한다. -> O(len(p))
  4) 따라서 함수의 길이를 l이라고 할 때 최종 시간 복잡도는 O(t * (n + l)이다.
    ** 리스트의 앞 요소, 뒤 요소를 삭제할 때 del arr[o), del arr[len(arr) - 1]을 하면 O(n)이 걸린다.
    ** 따라서 deque를 이용하여 O(1)으로 앞 뒤 요소를 삭제하면 더욱 시간이 빨라진다.

"""
from collections import deque

def parse_arr(arr_str):
  arr_str = arr_str[1:len(arr_str) - 2]
  arr = []

  if arr_str:
    arr = arr_str.split(',')

  return deque(arr)


import sys

input = sys.stdin.readline
test_cases = int(input().rstrip())

for _ in range(test_cases):
  p = input()
  n = int(input().rstrip())

  queue = parse_arr(input()) # 입력받은 배열을 숫자 배열로 파싱
  num_R = 0
  is_error = False

  for i in range(len(p) - 1):
    if p[i] == 'R':
      num_R += 1
    else:
      if not queue:
        is_error = True
        break

      if num_R % 2 == 0:
        # 누적된 R의 갯수가 짝수면 배열의 앞에서 숫자 제거
        queue.popleft()
      else:
        # 누적된 R의 갯수가 홀수면 배열의 뒤에서 숫자 제거
        queue.pop()

  # 모든 연산이 끝났을 떄 R의 갯수가 홀수이면 배열을 뒤집어서 출력한다.
  if is_error:
    print('error')
  else:
    arr = list(queue)

    if num_R % 2 == 1:
      arr.reverse()

    print('[' + ','.join(arr) + ']')