N, M = map(int, input().split(' '))
nums = list(map(int, input().split(' ')))

left = 0
right = max(nums)
answer = right

while left <= right:
    mid = (left + right) // 2

    minVal = nums[0] # 구간 내 최솟값
    maxVal = nums[0] # 구간 내 최댓값
    cnt = 1 # 구간 갯수

    for num in nums: # 지금 탐색하고 있는 최댓값 차이보다 크면 구간 생성
        minVal = min(minVal, num)
        maxVal = max(maxVal, num)

        if maxVal - minVal > mid:
            cnt += 1
            minVal = num
            maxVal = num
        
    if cnt > M: # 구간 갯수가 제시한 갯수보다 많다면 탐색 최댓값을 높여야함
        left = mid + 1
    else:
        answer = min(answer, mid)
        right = mid - 1

print(answer)