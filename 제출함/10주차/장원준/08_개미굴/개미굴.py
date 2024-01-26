"""
1. 문제 접근 방식
  1) 개미굴에서 진입 순서에 따라 문자열을 가지므로 트라이 알고리즘 사용
  2) 출력 시에는 dfs를 사용해서 출력한다.

2. 시간 복잡도
  1) n개의 문자열을 입력받으며 트라이 알고리즘 수행 -> O(n)
  2) 모든 개미굴의 갯수를 m개라고 할 때 출력을 위해 m번 탐색 -> O(m)
  3) 1번, 2번은 순차적으로 수행되므로 O(m)이다.

"""
def trie(trie_dict, index, foods):
  if index == len(foods):
    trie_dict['*'] = trie_dict.get('*', 0) + 1
    return

  ch = foods[index]

  if not ch in trie_dict:
    trie_dict[ch] = {}

  trie(trie_dict[ch], index + 1, foods)


def print_trie(trie_dict, index):
  if '*' in trie_dict:
    return

  for key in sorted(trie_dict.keys()):
    print('--' * index + key)
    print_trie(trie_dict[key], index + 1)


import sys

input = sys.stdin.readline

n = int(input().rstrip())
trie_dict = {}

for _ in range(n):
  info = list(input().split())

  num_food = int(info[0])
  foods = info[1: len(info)]

  trie(trie_dict, 0, foods)

print_trie(trie_dict, 0)