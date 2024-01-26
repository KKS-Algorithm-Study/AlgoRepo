"""
1, 2세트라면
-> 7:6이나 6:7은 ok
-> 다른 스코어에서 승부가 나려면 적어도 1명은 6점 이상
  -> 1명이 6점이면 2점차 이상일 때 승부남
  -> 1명이 6점 이상인데 차이가 2점이상 나면 안됨

나머지 세트라면
-> 무승부 가능
-> 다른 스코어에서 승부가 나려면 적어도 1명은 6점 이상
  -> 1명이 6점이면 2점차 이상일 때 승부남
  -> 1명이 6점 이상인데 차이가 2점이상 나면 안됨

1. 문제 접근 방식
  1)
2. 시간 복잡도
  1)

정인 vs 상근

정인 -> 삼덕마을 (윗쪽, 중간, 아랫) 3개마을

주말에 정인이는 집에서 TV봄

테니스 규칙

1. 세트 승리 조건
  1) 6게임 이상 승리
  2) 상대보다 2게임 이상 차이나야 됨

2. 1번째 혹은 2번째 세트가 6:6이면 듀스 가야 됨
>> 얘는 1, 2만 유효, 3번째 경기는 무승부 ok

2. 2세트를 먼저 이긴사람이 경기의 승자

3. 로저 페더러는 항상 이긴다.

[입력]
1줄당 최대 5개까지 세트스코어가 주어진다.

6:2 -> 1번 조건, 2번 조건 ok, 3번 조건은 2세트 차니까 ok
7:6은 ok


* 필요한 것 ->
  세트 스코어가 유효하니?
  세트 스코어 모였을 때 경기결과가 무승부로 안끝나고 승패가 갈렸니?

6:2 6:4 -> A가 2:0으로 이겨서 끝남
3:6 7:5 2:6 -> B가 2:1로 이겨서 끝남
6:5 7:4 -> 세트 스코어에서 탈락 (6:5는 2점이상 차이 안남, 7:4는 6:4에서 끝났어야 됨)
7:6 7:6 -> A가 2:0으로 이겨서 끝남
6:2 3:6 -> 아직 1:1인데 끝남
6:2 1:6 6:8 -> 6:8도 맞음
"""

# 경기 결과가 올바르다면 승자를 반환
# 규칙에 맞지 않다면 None을 반환

def before_set_two(set_result):
  score_a, score_b = map(int, set_result.split(':'))

  # 1, 2 세트에서는 최대 점수가 7점이다.
  if score_a > 7 or score_b > 7: return None

  # 일단 둘 중 1명의 점수는 6점 이상인 경우에 승부가 난다.
  if score_a < 6 and score_b < 6: return None

  larger = (score_a, 'A') if score_a > score_b else (score_b, 'B')
  smaller = (score_a, 'A') if score_a < score_b else (score_b, 'B')

  # 7:6이라면 승자 반환
  if larger[0] == 7 and smaller[0] == 6:
    return larger[1]
  else:
    # 2명 중 1명은 6점이잖아.
    # 점수찾가 2점 이상 나야 승무가 난다.
    if larger[0] == 6:
      # larger가 6점 인 경우에는 차이가 2점 이상 나도 됨
      if larger[0] - smaller[0] >= 2:
        return larger[1]
    else:
      # larger가 6점 초과 경우에는 차이가 2만큼 나야 됨
      if larger[0] - smaller[0] == 2:
        return larger[1]

  return None

def after_set_two(set_result):
  score_a, score_b = map(int, set_result.split(':'))

  # 일단 둘 중 1명의 점수는 6점 이상인 경우에 승부가 난다.
  if score_a < 6 and score_b < 6: return None

  larger = (score_a, 'A') if score_a > score_b else (score_b, 'B')
  smaller = (score_a, 'A') if score_a < score_b else (score_b, 'B')

  if larger[0] == 6:
    # larger가 6점 인 경우에는 차이가 2점 이상 나도 됨
    if larger[0] - smaller[0] >= 2:
      return larger[1]
  else:
    # larger가 6점 초과 경우에는 차이가 2만큼 나야 됨
    if larger[0] - smaller[0] == 2:
      return larger[1]

  return None


def whos_win(num_set, set_result):
  if num_set <= 2:
    return before_set_two(set_result)
  else:
    return after_set_two(set_result)


player1, player2 = input().split()
n = int(input())
federer = ''

if player1 == 'federer':
  federer = 'A'
elif player2 == 'federer':
  federer = 'B'


for _ in range(n):
  set_scores = list(input().split())
  win_a, win_b = 0, 0
  is_correct_game = True

  for i in range(len(set_scores)):
    # 이미 2경기를 이겨서 승패가 결정되었는데 더 경기를 진행했으면 안됨
    if win_a == 2 or win_b == 2:
      is_correct_game = False
      break

    result = whos_win(i + 1, set_scores[i])

    if federer and result != federer:
      is_correct_game = False
      break

    if result == None:
      is_correct_game = False
      break

    if result == 'A':
      win_a += 1
    else:
      win_b += 1

  if win_a < 2 and win_b < 2:
    is_correct_game = False

  if is_correct_game and win_a != win_b:
    print('da')
  else:
    print('ne')
