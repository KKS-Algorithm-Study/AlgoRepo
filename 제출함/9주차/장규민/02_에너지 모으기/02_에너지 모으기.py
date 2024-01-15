n = int(input())
energy = list(map(int, input().split()))

answer = 0
remove = [False] * n

def solution(depth, total):
    global answer, remove, energy, n

    if depth == n - 2:
        answer = max(answer, total)
        return

    for i in range(1, n - 1):
        if remove[i]:
            continue

        remove[i] = True
        mul = 1
        
        idx = i
        while remove[idx] and idx >= 0:
            idx -= 1
        mul *= energy[idx]

        idx = i
        while remove[idx] and idx < n:
            idx += 1
        mul *= energy[idx]
            
        solution(depth + 1, total + mul)

        remove[i] = False


solution(0, 0)
print(answer)
