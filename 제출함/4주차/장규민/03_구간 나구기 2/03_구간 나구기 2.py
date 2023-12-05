n, m = map(int, input().split())

array = list(map(int, input().split()))
    
left, right = 0, 10000
answer = 0

while left <= right:
    mid = (left + right) // 2

    min_value = max_value = array[0]

    section_count = 1

    for data in array:
        
        min_value = min(min_value, data)
        max_value = max(max_value, data)

        if mid < (max_value - min_value):
            min_value = max_value = data
            section_count += 1

    if section_count <= m:
        answer = mid
        right = mid - 1
    else:
        left = mid + 1
        

print(answer)
        
        
