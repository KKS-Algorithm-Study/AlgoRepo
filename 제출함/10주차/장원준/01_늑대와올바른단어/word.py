"""
1. 문제 접근 방식
  1) 반례 땜에 터져서 답안을 봄
  2) 최대 길이가 50이므로, 50 전까지의 문자열을 체크하며 일치하면 성공한 문자열을 자른다.

2. 시간 복잡도
  1) 문자열 길이를 M이라고 할 때, O(M * 13) -> O(M)이다.

"""
word = input()

def check(word):
  while word:
    for i in range(1, 13):
      if len(word) < 4 * i:
        return 0
      if word[0:4 * i] == 'w' * i + 'o' * i + 'l' * i + 'f' * i:
        word = word[4*i:]
        break
    else:
      return 0
  return 1

print(check(word))
