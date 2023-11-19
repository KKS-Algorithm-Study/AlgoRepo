"""
1. 문제 접근 방식
  1) 시간제한 6초, 메모리 제한 256MB 보고 경우의 수가 많다고 생각함
  2) D, S, L, R 중 1개의 연산을 했을 때 그 다음 연산이 또 같을 수가 있음
    ex) DDD.. 등
  3) 그래서 깊이 우선 탐색보다 너비 우선 탐색을 고려함
  4) 처음에는 visited 배열을 선언하지 않았었고, 따라서 테스트 케이스는 문제 없었음
  5) 그런데 제출 시 메모리 초과가 났었고 로그를 찍어보니 queue에 많은 데이터가 쌓임
  6) 헤매다가 답을 보고 visited 배열을 사용하면 된다는 걸 깨달음

2. 시간 복잡도
  1) 테스트 케이스는 T이며 1번의 테스트 케이스 마다 최대 10000번의 경우를 테스트해야 함
  2) 따라서 시간 복잡도는 O(T * 10000)이다.
"""
import sys
from collections import deque

operators = ['D', 'S', 'L', 'R']

def operate(num, operator):
    if operator == 'D':
        return num * 2 % 10000
    elif operator == 'S':
        return (num - 1) % 10000
    elif operator == 'L':
        return num // 1000 + (num % 1000) * 10

    return num // 10 + (num % 10) * 1000

def calculate(a, b, visited):
    queue = deque([(a, '')])
    visited[a] = True

    while queue:
        num, operations = queue.popleft()

        if num == b:
            return operations

        for i in range(4):
            result = operate(num, operators[i])

            # 방문하지 않은 숫자만 큐에 추가해줌
            # 왜냐면 숫자를 만드는 연산은 아무거나 되면 되니까 1번만 방문하면 됨
            if not visited[result]:
                queue.append((result, operations + operators[i]))
                visited[result] = True

t = int(sys.stdin.readline().rstrip())

for _ in range(t):
    a, b = map(int, sys.stdin.readline().rstrip().split())
    print(calculate(a, b, [False] * 10001))
