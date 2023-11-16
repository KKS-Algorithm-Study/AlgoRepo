"""
1. 문제 접근 방식
  1) 6 <= R, C <= 50 이고 1 <= T <= 1000 조건인데 T가 약간 커서 단순 구현인지 좀 헷갈렸음
  2) 딱히 방법이 떠오르지 않아 구현으로 풀려고 함
  3) 총 함수를 3개로 나눔
    a) room을 순회하며 미세먼지를 퍼뜨리는 함수
    b) 공기청정기 기준 윗 부분을 반시계 방향으로 도는 함수
    c) 공기청정기 기준 아랫 부분을 시계 방향으로 도는 함수

  4) 시계, 반시계 방향으로 회전할 때는 다음 2가지 방법이 있다고 판단됨
    a) 메모리를 아낄 때 -> 네 귀퉁이의 값을 임시로 저장 후 회전
    b) 메모리를 조금 더 쓸 때 -> 배열을 깊은 복사를 하여 회전 후 값을 매핑
  5) 회전 시 다음과 같은 요소 및 방식이 필요함
    a) 각 변을 기준으로 회전할 for문 4개
    b) for문의 범위가 되는 upperbound, lowerbound
    c) 인덱스 헷갈릴 수 있으니까 조심해야 됨

2. 시간 복잡도
  1) T의 갯수는 최대 1000개
  2) 1초가 지날 때마다 다음과 같은 순서로 로직이 수행됨
    a) spread 함수에서 r * c * 4만큼 시간 소요
    b) circulate_upper 함수에서 최대 2((r - 2) + c)만큼 시간 소요
    c) circulate_lower 함수에서 최대 2((r - 2) + c)만큼 시간 소요
  3) T * max(r * c * 4, 2((r - 2) + c)) 이므로
  4) O(T * r * c)의 시간복잡도로 계산할 수 있다.
"""

# 상, 하, 좌, 우
dx = [-1, +1, +0, +0]
dy = [+0, +0, -1, +1]

# 1초가 지난 뒤 미세먼지 확산을 계산하는 함수
# 확산된 양을 구한 다음, 남은 양을 더해준다.
def spread(room, r, c):
    copied_room = [[0] * c for _ in range(r)]

    for i in range(r):
        for j in range(c):
            if room[i][j] < 1:
                continue

            spread_amount = room[i][j] // 5
            spread_count = 0
            # 사방으로 돌면서 미세먼지 확산
            for k in range(4):
                nx = i + dx[k]
                ny = j + dy[k]

                if 0 <= nx < r and 0 <= ny < c and room[nx][ny] != -1:
                    copied_room[nx][ny] += spread_amount
                    spread_count += 1

            # room에 남은 먼지 계산
            room[i][j] = room[i][j] - (spread_amount * spread_count)

    # 확산된 양이 구해진 copied_room에 남은 미세먼지 양을 더해준다.
    for i in range(r):
        for j in range(c):
            copied_room[i][j] += room[i][j]

    return copied_room

# 공기청정기 윗쪽을 순환하며, 반시계방향으로 회전
def circulate_upper(room, start, r, c):
    x, y = start
    r -= 1
    c -= 1

    copied_room = [row[:] for row in room]

    # 좌 -> 우 [시작점(x, 0) -> 끝점 (x, c)]
    for i in range(c):
        if i == 0:
            copied_room[x][i + 1] = 0
        else:
            copied_room[x][i + 1] = room[x][i]

    # 하 -> 상 [시작점(x, c) -> 끝점 (0, c)]
    for i in range(x, 0, -1):
        copied_room[i - 1][c] = room[i][c]

    # 우 -> 좌 [시작점(0, c) -> 끝점 (0, 0)]
    for i in range(c, 0, -1):
        copied_room[0][i - 1] = room[0][i]

    # 상 -> 하 [시작점(0, 0) -> 끝점 (x, 0)]
    for i in range(x):
        if room[i + 1][0] == -1:
            continue
        copied_room[i + 1][0] = room[i][0]

    return copied_room

# 공기청정기 아랫쪽을 순환하며, 시계방향으로 회전
def circulate_lower(room, start, r, c):
    x, y = start
    r -= 1
    c -= 1

    copied_room = [row[:] for row in room]

    # 좌 -> 우 [시작점(x, 0) -> 끝점 (x, c)]
    for i in range(c):
        if i == 0:
            copied_room[x][i + 1] = 0
        else:
            copied_room[x][i + 1] = room[x][i]

    # 상 -> 하 [시작점(x, c) -> 끝점 (r, c)]
    for i in range(x, r):
        copied_room[i + 1][c] = room[i][c]

    # 우 -> 좌 [시작점(r, c) -> 끝점 (r, 0)]
    for i in range(c, 0, -1):
        copied_room[r][i - 1] = room[r][i]

    # 하 -> 상 [시작점(r, 0) -> 끝점 (x, 0)]
    for i in range(r, x, -1):
        if room[i - 1][0] == -1:
            continue
        copied_room[i - 1][0] = room[i][0]

    return copied_room

# 입력을 받기
r, c, t = map(int, input().split())
room = [list(map(int, input().split())) for _ in range(r)]

cleaner_upper, cleaner_lower = (0, 0), (0, 0)

# 공기청정기의 윗부분, 아랫부분을 찾아 좌표로 저장
for i in range(r):
    if room[i][0] == -1:
        cleaner_upper = (i, 0)
        cleaner_lower = (i + 1, 0)
        break

# t초 동안 미세먼지 확산 & 공기청정기 가동을 진행
for i in range(t):
    room = spread(room,r, c)
    room = circulate_upper(room, cleaner_upper, r, c)
    room = circulate_lower(room, cleaner_lower, r, c)

# 남아있는 미세먼지 출력
# 공기 청정기의 위치만큼 보정해주기 위해 초기값을 2로 선언
result = 2

for row in room:
    result += sum(row)

print(result)