n = int(input())

split = [0] * 21
dead = [0] * 21

split[1] = 1
dead[4] = 1


for i in range(2, n + 1):
    split[i] = split[i - 1] * 2 - dead[i]

    if i % 2 == 0 and i + 4 < 21:
        dead[i + 4] += split[i - 1]

    elif i % 2 == 1 and i + 3 < 21:
        dead[i + 3] += split[i - 1]

print(split[n])
