"""
1. 문제 접근 방식
최소 비용을 구하는 문제여서
입력으로 출발도시 -> 도착도시를 받을 때 최솟값인 경우에만 저장한다.
그리고 태민이 형이 알려준 플로이드 방식을 사용해 답을 구한다.
2. 시간복잡도 : O(n^3)
3. 특이사항 : 
그 수업들을 때 준연이가 얘기했던 부분이 바로 나왔다.
stt - end - mid 순으로 for 문을 돌리게 되면 갱신이 안된 부분을 만났을 때 갱신이 안되기 때문에
mid - stt - end 순으로 for 문을 돌려야 한다.
mid - stt - end 순으로 for 문을 돌리면 stt - end로 갈 때 각 노드를 거쳐서 가는 모든 경로를 볼 수 있다.
"""

INF = 100000 * 100 + 1

N = int(input())
M = int(input())

mp = [[INF for _ in range(N)] for _ in range(N)]

for _ in range(M):
    src, dst, cost = map(int, input().split(' '))
    src-=1
    dst-=1
    mp[src][dst] = min(mp[src][dst], cost)

for mid in range(N):
    for stt in range(N):
        for end in range(N):
            if stt == end:
                continue
            if mp[stt][end] > mp[stt][mid] + mp[mid][end]:
                mp[stt][end] = mp[stt][mid] + mp[mid][end]

for i in range(N):
    for j in range(N):
        print(mp[i][j] if mp[i][j] != INF else 0, end=' ')
    print()