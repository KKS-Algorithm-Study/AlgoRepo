"""
1. 문제 접근 방식
  1) 숫자는 0 ~ 1이며 5 * 3 행렬로 표현되므로 각 숫자를 표현하는 좌표를 dict에 넣어준다.
  2) 입력을 받고 숫자를 파싱하여 올바른 숫자인지 체크한다.

2. 시간 복잡도
  1) 코드의 숫자 자릿수를 n이라고 할 때 n//4 만큼 반복문을 수행한다. -> O(n)
  2) 1번의 반복문 안에서 15번의 고정된 반복문을 수행한다. -> O(1)
  3) 따라서 최종 시간 복잡도는 O(n)이다.

"""
number_dict = {}
number_dict[((0, 0), (0, 1), (0, 2), (1, 0), (1, 2), (2, 0), (2, 2), (3, 0), (3, 2), (4, 0), (4, 1), (4, 2))] = 0
number_dict[((0, 2), (1, 2), (2, 2), (3, 2), (4, 2))] = 1
number_dict[((0, 0), (0, 1), (0, 2), (1, 2), (2, 0), (2, 1), (2, 2), (3, 0), (4, 0), (4, 1), (4, 2))] = 2
number_dict[((0, 0), (0, 1), (0, 2), (1, 2), (2, 0), (2, 1), (2, 2), (3, 2), (4, 0), (4, 1), (4, 2))] = 3
number_dict[((0, 0), (0, 2), (1, 0), (1, 2), (2, 0), (2, 1), (2, 2), (3, 2), (4, 2))] = 4
number_dict[((0, 0), (0, 1), (0, 2), (1, 0), (2, 0), (2, 1), (2, 2), (3, 2), (4, 0), (4, 1), (4, 2))] = 5
number_dict[((0, 0), (0, 1), (0, 2), (1, 0), (2, 0), (2, 1), (2, 2), (3, 0), (3, 2), (4, 0), (4, 1), (4, 2))] = 6
number_dict[((0, 0), (0, 1), (0, 2), (1, 2), (2, 2), (3, 2), (4, 2))] = 7
number_dict[((0, 0), (0, 1), (0, 2), (1, 0), (1, 2), (2, 0), (2, 1), (2, 2), (3, 0), (3, 2), (4, 0), (4, 1), (4, 2))] = 8
number_dict[((0, 0), (0, 1), (0, 2), (1, 0), (1, 2), (2, 0), (2, 1), (2, 2), (3, 2), (4, 0), (4, 1), (4, 2))] = 9

code = [list(' ' + input()) for _ in range(5)]
code_digits = []
is_number = True

for i in range((len(code[0]) // 4)):
  col_index = 1 + 4 * i
  num = []

  for row in range(5):
    for col in range(col_index, col_index + 3):
      if not code[row][col] == ' ':
        num.append((row, col - col_index))

  digit = number_dict.get(tuple(num), -1)

  if digit == -1:
    is_number = False

  code_digits.append(str(digit))

if not is_number:
  print("BOOM!!")
else:
  code_num = int(''.join(code_digits))

  if code_num % 6 == 0:
    print("BEER!!")
  else:
    print("BOOM!!")