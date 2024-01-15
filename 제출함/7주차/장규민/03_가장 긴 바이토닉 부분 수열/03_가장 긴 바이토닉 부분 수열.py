from bisect import bisect_left

n = int(input())

numbers = list(map(int, input().split()))


def LIS(_numbers, last_number):
    dp = []
    
    for number in _numbers:
        if not dp or dp[-1] < number:
            dp.append(number)
            continue

        idx = bisect_left(dp, number)
        dp[idx] = number

    if not dp:
        return (0, -1)

    if last_number != -1:
        idx = bisect_left(dp, last_number)
        return (idx, last_number)

    return (len(dp), dp[-1])


answer = 0
for i in range(n):
    dp1 = LIS(numbers[:i], -1)
    dp2 = LIS(reversed(numbers[i:]), dp1[1])
    answer = max(answer, dp1[0] + dp2[0])

print(answer)
