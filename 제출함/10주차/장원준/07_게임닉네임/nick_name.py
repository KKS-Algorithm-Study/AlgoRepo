"""
1. 트라이 알고리즘
  1) 문자열을 트리형태로 저장하여 중복되는 "접두사"가 있는 문자열들을 효율적으로 탐색하기 위한 알고리즘이다.
    [이미 문자열을 모두 트라이로 만들었을 때 다시 탐색하는 경우 효율적임]

  2) 알고리즘 순서
    - 기본적으로 리스트 안에 리스트, dict 안에 dict의 형태로 저장된다.
    - 각 1개의 자료구조에는 1개 문자가 들어있다. (리스트에는 (ch, children), dict에는 {ch: children} 형식
    - dfs로 접근한다.
    - 만약 현재 자료구조에 현재 문자가 없으면 새로운 자료구조를 추가한다.
    - 문자열에 끝에 다다랐을 때는 '*' 같은 걸로 표시할 수 있다.

  3) 그럼 왜 해시맵, 해시셋으로 처리하면 안될까?
    ABC ,ABD, ABE -> 트라이를 사용하는 경우 ch 기준으로 중복을 제거하기 때문에 공간을 더 아낄 수 있다.

  4) 리스트로 표현하는 경우 [0] * 26 인 리스트가 리스트의 요소로 들어간다. (재귀처럼 들어감)

2. 문제 접근 방식
  1) 입력받은 아이디를 트라이 알고리즘을 통해 1개의 트리로 만든다.
  2) 처음 트리의 브랜치가 생기는 지점까지가 회원의 닉네임이 된다.
  3) 같은 회원 아이디가 있을 수 있으므로, 트리의 끝에 * 표시를 한다.

3. 시간 복잡도
  1) 입력받은 id의 갯수만큼 반복한다 -> O(n)
  2) 1번 반복 시 트라이 알고리즘을 수행한다 -> O(len(k)) [단, k는 문자열의 길이이며 10이하]
  3) 따라서 최종 시간 복잡도는 O(n * 10) 이므로 O(n)이 된다.

"""

def trie(trie_dict, index, id, has_nick_name):
  # 각 단어를 key로, 하위 dict를 trie_dict에 저장하자.
  if len(id) == index:
    trie_dict['*'] = trie_dict.get('*', 0) + 1

    if not has_nick_name:
      if trie_dict['*'] > 1:
        nick_names.append(id + str(trie_dict['*']))
      else:
        nick_names.append(id)

    return

  ch = id[index]

  if ch not in trie_dict:
    trie_dict[ch] = {}

    if not has_nick_name:
      nick_names.append(id[0:index + 1])
      has_nick_name = True

  trie(trie_dict[ch], index + 1, id, has_nick_name)


import sys

input = sys.stdin.readline

n = int(input().rstrip())
ids = []
nick_names = []

for _ in range(n):
  ids.append(input().rstrip())

trie_dict = {}

for id in ids:
  trie(trie_dict, 0, id, False)

print(*nick_names, sep='\n')
