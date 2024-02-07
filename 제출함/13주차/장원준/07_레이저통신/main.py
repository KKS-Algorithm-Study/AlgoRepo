"""
1. 문제 접근 방식 (답 보고 품)
  1)

2. 시간 복잡도
  1)

"""
import sys
import heapq
input = sys.stdin.readline


def shoot(cnt, y, x):
  for i in range(4):
    dirY, dirX = dir[i]
    curY, curX = y, x

    # 직선으로 쭉 나아감
    while True:
      nextY, nextX = curY + dirY, curX + dirX
      if 0 <= nextY < h and 0 <= nextX < w:
        if maps[nextY][nextX] == '.':
          # 만약 기존의 거울 개수보다 적거나 같게 사용한다면
          if mirrors[nextY][nextX] > cnt or (mirrors[nextY][nextX] == cnt and not visited[i % 2][nextY][nextX]):
            mirrors[nextY][nextX] = cnt
            visited[i % 2][nextY][nextX] = True        # 방문 표시
            heapq.heappush(hq, (cnt, nextY, nextX))
            curY, curX = nextY, nextX       # 한 쪽으로 쭉 나아가기 위해
          else:
            break
        elif maps[nextY][nextX] == '*':
          break
        else:
            print(cnt)
            exit(0)
      else:
        break


if __name__ == "__main__":
  INF = int(1e9)
  w, h = map(int, input().split())
  maps = [list(input().strip()) for _ in range(h)]
  mirrors = [[INF] * w for _ in range(h)]
  # map 방문 & 수평 수직으로 방문한 적이 있는지 체크하는 방문 배열
  visited = [[[False] * w for _ in range(h)] for _ in range(2)]

  hq = []
  # 하, 우, 상, 좌
  dir = [[1, 0], [0, 1], [-1, 0], [0, -1]]

  for i in range(h):
    for j in range(w):
      # 힙에 출발, 도착지를 저장함
      if maps[i][j] == 'C':
        heapq.heappush(hq, (-1, i, j))
        maps[i][j] = '*'

        # 다익스트라 알고리즘
        while hq:
          cnt, y, x = heapq.heappop(hq)
          # 벽을 만난 것이므로 +1해서 넣어줌
          shoot(cnt + 1, y, x)