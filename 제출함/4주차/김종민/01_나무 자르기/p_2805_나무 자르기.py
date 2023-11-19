n, m = map(int, input().split())

woods = list(map(int, input().split()))


left, right = 0, int(1e9)
answer = 0

while left <= right:
    mid = (left + right) // 2

    total = 0

    for wood in woods:
        if mid >= wood:
            continue
        
        total += wood - mid

    if m > total:
        right = mid - 1
    else:
        answer = mid
        left = mid + 1

print(answer)
        
