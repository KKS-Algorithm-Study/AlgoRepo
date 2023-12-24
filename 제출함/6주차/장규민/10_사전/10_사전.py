a_count, z_count, k = map(int, input().split())

answer = ''

word = [[0] * (z_count + 1) for _ in range(a_count + 1)]

for i in range(a_count + 1):
    word[i][0] = 1

for j in range(z_count + 1):
    word[0][j] = 1

for i in range(1, a_count + 1):
    for j in range(1, z_count + 1):
        word[i][j] += word[i][j - 1] + word[i - 1][j]

if word[a_count][z_count] < k:
    print('-1')
    exit(0)

while True:
    
    if a_count == 0 or z_count == 0:
        answer += ('a' * a_count + 'z' * z_count)
        break

    if word[a_count - 1][z_count] < k:
        k -= word[a_count - 1][z_count]
        z_count -= 1
        answer += 'z'
        continue

    answer += 'a'
    a_count -= 1

print(answer)
