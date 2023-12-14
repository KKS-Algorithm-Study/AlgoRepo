N, M = map(int, input().split(' '))

memorys = list(map(int, input().split(' ')))
costs = list(map(int, input().split(' ')))
total = sum(costs)

prevDp = [0 for _ in range(total+1)]

for i in range(costs[0], total+1):
    prevDp[i] = memorys[0]

for appIdx in range(1, N):
    curDp = [0 for _ in range(total+1)]
    for costIdx in range(total+1):
        if costIdx >= costs[appIdx]: # 비용을 지불하고 현재 앱의 바이트를 확보
            curDp[costIdx] = max(prevDp[costIdx - costs[appIdx]] + memorys[appIdx], prevDp[costIdx])
        else:
          curDp[costIdx] = prevDp[costIdx]
                    
    prevDp = curDp

for cost in range(total+1):
    if curDp[cost] >= M:
        print(cost)
        break