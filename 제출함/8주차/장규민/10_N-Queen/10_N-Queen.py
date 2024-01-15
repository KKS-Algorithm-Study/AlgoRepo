n = int(input())


def check(count):
    global board, n, pos

    for y in range(count):
        if pos[y] == pos[count] or count - y == abs(pos[y] - pos[count]):
            return False

    return True


def backtracking(count):
    global board, n, answer, pos

    if count == n:
        answer += 1
        return

    for x in range(n):
        pos[count] = x
        if not check(count):
            continue
        
        backtracking(count + 1)


answer = 0
pos = [-1] * n
backtracking(0)
print(answer)

