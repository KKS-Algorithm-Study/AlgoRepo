expression = input().rstrip()

idx = [-1] * len(expression)
stack = []
total = 0

for i, ch in enumerate(expression):
    if ch == '(':
        stack.append(i)
        total += 1
        continue

    if ch == ')':
        idx[i] = stack[-1]
        stack.pop()


def backtracking(depth, end, path):
    global expression, idx, visit


    if depth == len(expression):
        answer.add(''.join(path))
        return

    ch = expression[depth]

    if ch != '(' and ch != ')':
        path.append(ch)
        
        backtracking(depth + 1, end, path)
        
        path.pop()
        return

    if ch == '(':
        if len(visit) < end:
            visit[depth] = True
            path.append(ch)
            
            backtracking(depth + 1, end, path)

            visit.pop(depth)
            path.pop()
            
        backtracking(depth + 1, end, path)
        return

    if ch == ')':
        if visit.get(idx[depth]) is not None:
            path.append(ch)
            
            backtracking(depth + 1, end, path)
            
            path.pop()

            return

        backtracking(depth + 1, end, path)


answer = set()
for i in range(total):
    visit = {}
    backtracking(0, i, [])

print('\n'.join(sorted(answer)))

