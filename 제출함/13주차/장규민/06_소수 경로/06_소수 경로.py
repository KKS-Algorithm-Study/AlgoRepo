from collections import deque


def find_prime():
    is_prime = [True] * 10001
    ret = {}

    for i in range(2, 10001):
        if not is_prime[i]:
            continue
        
        ret[i] = True
        for j in range(i, 10001, i):
            is_prime[j] = False

    return ret


def bfs(start, end):
    global primes

    s = int(''.join(start))
    
    if s == end:
        return 0
    
    que = deque()
    que.append((start, 0))
    visit = {''.join(start): True}
    
    while que:
        cur, cnt = que.popleft()
        
        for i in range(4, -1, -1):
            for j in range(10):
                next_str = cur[:i] + [str(j)] + cur[i + 1:]
                next_num = int(''.join(next_str))
                
                if next_num < 1000 or visit.get(next_num) is not None or primes.get(next_num) is None:
                    continue

                if next_num == end:
                    return cnt + 1
                
                visit[next_num] = True
                que.append((next_str, cnt + 1))
                
    return -1


primes = find_prime()

for _ in range(int(input())):
    start, end = input().rstrip().split()
        
    print(bfs(list(start), int(end)))

