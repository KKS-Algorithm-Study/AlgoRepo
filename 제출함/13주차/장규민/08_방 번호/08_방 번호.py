n = int(input())

prices = list(map(int, input().split()))

money = int(input())
remind = [0] * (money + 1)
answer = 0

def dfs(remind_money, total):
    global prices, n, answer

    for i in range(n - 1, -1, -1):
        if remind_money < prices[i]:
            continue
            
        temp = total + str(i)
        
        if remind[remind_money - prices[i]] > int(temp):
            continue

        remind[remind_money - prices[i]] = int(temp)
        answer = max(int(temp), answer)
        dfs(remind_money - prices[i], temp)


dfs(money, '')
print(answer)
