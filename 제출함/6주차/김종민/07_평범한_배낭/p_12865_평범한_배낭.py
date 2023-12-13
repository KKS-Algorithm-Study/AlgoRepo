N, K = map(int, input().split(' ')) # N = 물건 갯수, K = 들 수 있는 무게
items = []
for _ in range(N):
    items.append(tuple(map(int, input().split(' '))))

dp = [[0 for _ in range(K+1)] for _ in range(N+1)]

# 가상의 가방을 만든다.
# 각 행은 현재 물건의 인덱스이다.
# 각 열은 가상의 가방에 들어갈 수 있는 무게이다.
# 가상의 가방에 들어갈 수 있는 물건 중 가장 높은 값어치를 선택해 나간다.
for idx, item in enumerate(items, 1): # 아이템을 순회한다.
    for virtualBag in range(K+1): # 가상의 가방을 만든다.
        itemW = item[0]
        itemP = item[1]
        if itemW > virtualBag: # 현재 아이템 무게가 가상 가방에 안들어갈 때
            dp[idx][virtualBag] = dp[idx-1][virtualBag]
        else: # 현재 물건이 들어갔을 때의 값과 안넣었을 때 값을 비교해서 큰걸로 넣는다
            dp[idx][virtualBag] = max(dp[idx-1][virtualBag-itemW] + itemP, dp[idx-1][virtualBag]) 

print(dp[N][K])