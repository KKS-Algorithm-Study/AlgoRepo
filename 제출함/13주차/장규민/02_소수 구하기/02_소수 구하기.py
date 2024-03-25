def find_primes(end):
    is_prime = [True] * (end + 3)
    ret = {}
    
    for i in range(2, end + 1):
        if not is_prime[i]:
            continue
        
        ret[i] = True
        for j in range(i, end + 1, i):
            is_prime[j] = False

    return ret


n, m = map(int, input().split())
primes = find_primes(m)

answer = []
for i in range(n, m + 1):
    if primes.get(i) is None:
        continue

    answer.append(str(i))
    
print('\n'.join(answer))
