from collections import deque

N = list(input())
N = deque(N)
result=0
def dfs(cur):
    global result
    if len(cur) == 1:
        result += 1
        return
    tmp = cur.popleft()
    dfs(cur)
    cur.appendleft(tmp)

    if cur[0] == cur[-1]:
        string = ''
        for i in range(1,len(cur)-1):
            string+=cur[i]
        if cur[0]+string == string+cur[-1]:
            return
    
    tmp = cur.pop()
    dfs(cur)
    cur.append(tmp)
    
dfs(N)
print(result)