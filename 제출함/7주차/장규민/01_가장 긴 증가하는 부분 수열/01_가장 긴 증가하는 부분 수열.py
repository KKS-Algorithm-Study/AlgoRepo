from bisect import bisect_left

n = int(input())

numbers = list(map(int, input().split()))
    
dp = [numbers[0]]

for number in numbers[1:]:
    if dp[-1] < number:
        dp.append(number)
        continue
    
    idx = bisect_left(dp, number)
    dp[idx] = number
    
print(len(dp))
