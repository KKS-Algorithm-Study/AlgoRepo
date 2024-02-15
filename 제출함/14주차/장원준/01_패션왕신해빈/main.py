"""
1. 문제 접근 방식
  1) 의상의 종류의 가짓수에 1을 더한 값을 누적으로 곱해준다.
    ** 1을 더하는 이유는 입지 않는 경우도 있기 때문
  2) 전체 누적곱에서 알몸인 경우의 수 1을 빼준다.

2. 시간 복잡도
  1) 테스트 케이스만큼 반복문을 수행한다. -> O(t)
  2) 1번의 테스트 케이스에서 n번의 반복문을 수행한다. -> O(n)
  3) 따라서 최종 시간 복잡도는 O(t * n)이다.

"""
import sys

input = sys.stdin.readline

test_cases = int(input().rstrip())

for _ in range(test_cases):
  n = int(input().rstrip())
  clothes_dict = {}
  answer = 1

  for _ in range(n):
    clothes, type = input().split()
    clothes_dict[type] = clothes_dict.get(type, 0) + 1

  for key in clothes_dict.keys():
    answer *= clothes_dict[key] + 1

  # 알몸인 경우를 빼준다.
  print(answer - 1)