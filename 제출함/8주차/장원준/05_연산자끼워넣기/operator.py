"""
연산자를 배치하는 경우를 순열로 만들자.
>> 스왑 순열로도 만들어보자.

후위 연산으로 바꾸기
1. 숫자를 먼저 한군데에 몰아 넣는다.
2.
"""
import sys

input = sys.stdin.readline

n = int(input().rstrip())
numbers = list(map(int, input().split()))
operators = list(map(int, input().split()))

PLUS = 101
MINUS = 102
MUL = 103
DIV = 104

operands = []

def calculate():
  prior = [] # 숫자와 연산을 쌓아가는 리스트
  stack = []


  # 후위 연산으로 바꾸는 방법
  # 스택에다 연산자를 넣어주는데, 우선순위에 따라 pop하는 기준이 달라진다.
  for i in range(n):
    # 숫자 먼저 담기
    prior.append(numbers[i])

    # 연산자는 숫자보다 1개 작기 때문에 if문으로 처리
    if i < n - 1:
      cur_oper = operands[i]

      # [] -> 후위 표기식을 담을 리스트 (123+-x)
      # stack은 후위 표기식을 만들기 위한 스택

      # 우선순위 1 -> X , /
      # 우선순위 2 -> +, -
      # 더하기 빼기는 우선순위가 제일 낮으므로, 연산자를 다 빼서 prior에 넣어준다.
      # 그리고 지금 현재의 연산자를 넣어주면 된다.
      if cur_oper == PLUS or cur_oper == MINUS:
        while stack:
          prior.append(stack.pop())
      else:
        # 곱하기, 나누기인 경우에는 더하기, 빼기보다 연산자 순위가 높기 때문에
        # 높은 것까지만 스텍에서 빼서 prior에 넣어주고
        # 현재의 연산자를 넣어주면 된다.
        while stack and (stack[-1] == MUL or stack[-1] == DIV):
          prior.append(stack.pop())

      # 현재 연산자를 넣어준다.
      stack.append(cur_oper)

  # 남아있는 연사자들을 다 넣어주기
  while stack:
    prior.append(stack.pop())

  # 후위 연산자로 바꾼 수식을 계산하기
  for ex in prior:
    # 연산자인 경우
    if ex > 100:
      num1 = stack.pop()
      num2 = stack.pop()

      if ex == PLUS:
        stack.append(num1 + num2)
      elif ex == MINUS:
        stack.append(num2 - num1)
      elif ex == MUL:
        stack.append(num2 * num1)
      else:
        stack.append(num2 // num1)
    # 숫자인 경우
    else:
      stack.append(ex)

  return stack[0]


INF = int(1e9)
min_ans = INF
max_ans = -INF

def permutation(depth):
  global min_ans, max_ans

  if depth == n - 1:
    result = calculate() # 후위표기법을 이용해서 수식을 계산한다.
    min_ans = min(min_ans, result)
    max_ans = max(max_ans, result)
    return

  # 연산자 조합 만들기
  for i in range(4):
    if operators[i] > 0:
      operators[i] -= 1
      operands.append(i + 101)
      permutation(depth + 1)
      operands.pop()
      operators[i] += 1

permutation(0)

print(max_ans)
print(min_ans)