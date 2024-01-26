"""
1. 문제 접근 방식
  1) 괄호 쌍의 인덱스를 구하여 리스트로 뽑아낸다.
  2) 리스트를 가지고 조합을 구하여 괄호를 삭제 후, set에 저장 (다른 괄호를 제거해도 같은 문자열이 나올 수 있음)

2. 시간 복잡도
  1) 괄호는 최대 10개이다.
  2) 10C1 + 10C2 + ... + 10C9 + 10C10 = 2**10 이다. (조합의 합 공식)
  3) 따라서 괄호 쌍의 갯수를 n개라고 할 때 최종 시간복잡도는 2**n 이다.

"""
from itertools import combinations

string = input()
stack = []
brackets = []
result = set()

# 괄호쌍을 인덱스로 저장하기
for i in range(len(string)):
  if string[i] == '(':
    stack.append(i)
  elif string[i] == ')':
    # (왼쪽 괄호의 인덱스, 오른쪽 괄호의 인덱스)
    brackets.append((stack.pop(), i))

# 저장한 괄호쌍을 기준으로 조합을 구하여 문자열 구하기
copied_string = list(string[:])

for i in range(1, len(brackets) + 1):
  for combs in combinations(brackets, i):
    for left, right in combs:
      copied_string[left] = ''
      copied_string[right] = ''

    result.add(''.join(copied_string))

    for left, right in combs:
      copied_string[left] = '('
      copied_string[right] = ')'

print(*sorted(list(result)), sep="\n")

