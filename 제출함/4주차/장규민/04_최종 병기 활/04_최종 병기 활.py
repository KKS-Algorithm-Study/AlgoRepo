n, m, k = map(int, input().split())

rubber_band_slots = [int(input()) for _ in range(m)]

left, right = 0, n
answer = -1

while left <= right:
    mid = (left + right) // 2
    max_value = 0
    
    for i in range(m):
        first = prev = rubber_band_slots[i]
        total = 0
        
        for slot in rubber_band_slots[i:]:
            if slot - prev >= mid:
                total += 1
                prev = slot

        if mid <= (n - (rubber_band_slots[-1] - first)):
            total += 1
                
        max_value = max(max_value, total)
    
    if max_value >= k:
        answer = mid
        left = mid + 1
    else:
        right = mid - 1

print(answer)
