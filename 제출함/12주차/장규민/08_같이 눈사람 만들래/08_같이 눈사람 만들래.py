n = int(input())

snow = sorted(list(map(int, input().split())))

answer = int(1e9)

for i in range(n - 3):
    for j in range(i + 3, n):
        snowman1 = snow[i] + snow[j]
        left, right = i + 1, j - 1

        while left < right:
            snowman2 = snow[left] + snow[right]

            temp = snowman1 - snowman2

            answer = min(answer, abs(temp))

            if temp > 0:
                left += 1
            elif temp < 0:
                right -= 1
            else:
                print(0)
                exit(0)
                
print(answer)
