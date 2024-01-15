n = int(input())

tree = {}

for _ in range(n):
    parent, left, right = input().split()
    tree[parent] = []
    tree[parent].append(left)
    tree[parent].append(right)


def pre_order(root):
    global tree

    L, R = 0, 1

    print(root, end='')
    
    if tree[root][L] != '.':
        pre_order(tree[root][L])
        
    if tree[root][R] != '.':
        pre_order(tree[root][R])


def in_order(root):
    global tree

    L, R = 0, 1

    
    if tree[root][L] != '.':
        in_order(tree[root][L])

    print(root, end='')
        
    if tree[root][R] != '.':
        in_order(tree[root][R])


def post_order(root):
    global tree

    L, R = 0, 1

    if tree[root][L] != '.':
        post_order(tree[root][L])
    
    if tree[root][R] != '.':
        post_order(tree[root][R])

    print(root, end='')
        

pre_order('A')
print()
in_order('A')
print()
post_order('A')
