n = int(input())

numbers = list(map(int, input().split()))

operator = list(map(int, input().split()))
answer = [str(-int(1e9)), str(int(1e9))]

def calculator(num1, num2, op):
    if op == 0:
        return num1 + num2

    if op == 1:
        return num1 - num2

    if op == 2:
        return num1 * num2

    return num1 // num2
    


def backtracking(path, depth):
    global numbers, operator, n

    if depth == n:
        total = 0
        
        for num, op in path:
            total = calculator(total, num, op)

        answer[0] = str(max(total, int(answer[0])))
        answer[1] = str(min(total, int(answer[1])))
        
        return

    for i in range(4):
        
        if operator[i] == 0:
            continue

        operator[i] -= 1

        if i == 2 or i == 3:
            number, op = path.pop()
            
            temp = calculator(number, numbers[depth], i)
            
            path.append((temp, op))
            
            backtracking(path, depth + 1)
            
            path.pop()
            path.append((number, op))

        else:
            path.append((numbers[depth], i))           
            backtracking(path, depth + 1)
            path.pop()
        
        operator[i] += 1


backtracking([(numbers[0], 0)], 1)
print('\n'.join(answer))
