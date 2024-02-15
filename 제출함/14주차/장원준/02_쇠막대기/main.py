"""
1. 문제 접근 방식
  1) 스택을 이용한다.
  2) '('를 만나면 스택에 넣는데, 만약 레이저라면 스택에 넣지않고 쇠막대기를 자른다.

2. 시간 복잡도
  1) 문자열의 길이를 n이라고 할 때 최대 문자열의 갯수만큼 순회하므로 O(n)이다.

"""
sticks = list(input())
stack = []
index = 0
answer = 0

while index < len(sticks):
  if sticks[index] == '(':
    if sticks[index + 1] == ')':
      answer += len(stack)

      # 레이저 발사 후 포인터 이동
      index += 1
    else:
      stack.append('(')
  else:
    # 갯수만큼 빼줌
    answer += 1
    stack.pop()

  index += 1

print(answer)