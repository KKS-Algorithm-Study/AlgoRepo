import sys

input = sys.stdin.readline


def binary_search(target, b):
    left, right = 0, len(b) - 1

    while left <= right:
        mid = (left + right) // 2
        
        if target == b[mid]:
            return b[mid]
                    
        if b[mid] > target:
            right = mid - 1
            
        elif b[mid] < target:
            left = mid + 1

    if left >= len(b):
        left -= 1
        
    if right < 0:
        right += 1
        
    if left > right:
        left, right = right, left
    
    return b[left] if abs(target - b[right]) >= abs(target - b[left]) else b[right]
    


for _ in range(int(input())):
    n, m = map(int, input().split())

    a = list(map(int, input().split()))
    b = sorted(list(map(int, input().split())))

    answer = 0
    for target in a:
        answer += binary_search(target, b)
    print(answer)
