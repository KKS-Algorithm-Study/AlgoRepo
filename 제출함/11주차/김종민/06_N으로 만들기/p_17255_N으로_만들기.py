"""
문제 접근 방식
1. 입력받은 숫자를 인덱스로 나열한다
    -> ex) 562453 => 012345
2. 중복되는 숫자는 같은 인덱스로 나열한다.
    -> ex) 555333222 => 000333666
3. 각 위치 마다 dfs를 돌면서 초기화 시킨 인덱스를 붙여 나간다
    -> ex) 512
            0 -> 01 -> 012
            1 -> 10 -> 102
            1 -> 12 -> 120
            2 -> 21 -> 210
4. 중복값시에는 dfs를 돌지 않도록 set에 저장하며 진행한다.

40 퍼에서 틀림 ㅠㅠ
"""

given = input()
n = len(given)
if n == 1:
    print(1)
    exit()

answer = set()
sum = [0]
for _ in range(n):
    sum[0] = sum[0] * 2 + 1
def dfs(left, right, cur):
    global cnt, answer
    if len(cur) == sum[0]:
        answer.add(cur)
        return
    
    if left > 0:
        next_word = cur + given[left-1] + cur
        dfs(left-1, right, next_word)
        
    if right < n-1:
        next_word = cur + cur + given[right+1]
        dfs(left, right+1, next_word)


for i in range(n):
    dfs(i,i,given[i])

print(len(answer))