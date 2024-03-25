word = input()
count = {'w': 0, 'o': 0, 'l': 0, 'f': 0}
idx = {'w': 0, 'o': 1, 'l': 2, 'f': 3}

def count_character():
    global count

    temp = count['w']

    for key in count.keys():
        if temp != count[key]:
            return 0

    return 1


def solution():
    global count, word, idx
    
    prev = 'w'
    
    for ch in word:
        if prev == 'f' and ch != prev:
            if not count_character():
                return 0
            
            count = {'w': 0, 'o': 0, 'l': 0, 'f': 0}
            prev = 'w'
            count[ch] += 1
            
            continue

        
        if idx[ch] - idx[prev] > 1 or idx[ch] - idx[prev] < 0:
            return 0

             
        count[ch] += 1
        prev = ch
    
    return count_character()


print(solution())
