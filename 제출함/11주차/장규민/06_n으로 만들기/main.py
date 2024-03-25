target = input().rstrip()
count = [0] * 10

for ch in target:
    count[int(ch)] += 1


def dfs(path):
    global target, answer, count

    if len(target) == len(path):
        if path == target:
            answer += 1
        return

    for i in range(10):
        if count[i] == 0:
            continue

        num1, num2 = path + str(i), str(i) + path

        count[i] -= 1
        dfs(num1)
        if num1 != num2:
            dfs(num2)
        count[i] += 1


answer = 0
dfs('')
print(answer)
        
