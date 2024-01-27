"""
1. 문제 접근 방식
  1) 주어진 숫자에서 양 옆을 1개씩 지워가자.

2. 시간 복잡도
  1) 최대 문자열 길이만큼 탐색하므로 O(N)이다.

"""
def dfs(string):
  global cnt
  if len(n) == 1:
    cnt += 1
    return

  L = set(list(string))
  # 111 깉이 1개의 숫자로만 이루어진 경우는 만들 수 있는 경우가 1이기 때문이다.
  if len(L) == 1:
    cnt += 1
    return
  else:
    dfs(string[1:])
    dfs(string[:-1])

n = input()
cnt = 0
dfs(n)
print(cnt)