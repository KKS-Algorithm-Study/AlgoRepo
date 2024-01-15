board = [list(map(int, input().split())) for _ in range(9)]


zero_loc = []

for y in range(9):
    for x in range(9):
        if board[y][x]:
            continue

        zero_loc.append((y, x))


def check_square(cur_y, cur_x, target):
    global board

    start_y, start_x = cur_y // 3 * 3, cur_x // 3 * 3
    
    for y in range(start_y, start_y + 3):
        for x in range(start_x, start_x + 3):
            if target == board[y][x]:
                return False

    return True


def check_h_v(y, x, target):
    global board

    for i in range(9):
        if board[y][i] == target or board[i][x] == target:
            return False

    return True
    

def backtracking(depth):
    global zero_loc, board

    if len(zero_loc) == depth:
        for b in board:
            print(*b)
        exit(0)

    y, x = zero_loc[depth]
    for number in range(1, 10):
        if not (check_square(y, x, number) and check_h_v(y, x, number)):
            continue

        board[y][x] = number
        backtracking(depth + 1)
        board[y][x] = 0


backtracking(0)
