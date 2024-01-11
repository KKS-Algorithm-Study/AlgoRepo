"""
1. 문제 접근 방식
  1) 조합을 구하면 됨
  2) 백트래킹을 사용할 때, 가지치기를 하는 기준은 사용할 알파벳이 남아있는지를 보면 됨

2. 시간 복잡도
  1) O(n!)
"""

def dfs(word, anagram, depth, visited):
  if depth == len(word):
    print(anagram)
    return

  for ch in visited:
    if visited[ch] != 0:
      visited[ch] -= 1
      dfs(word, anagram + ch, depth + 1, visited)
      visited[ch] += 1


n = int(input())
strs = []

for _ in range(n):
  word = sorted(input())
  visited = {}

  # visited dict를 초기화
  for i in range(len(word)):
    ch = word[i]
    visited[ch] = visited.get(ch, 0) + 1

  dfs(word, "", 0, visited)


