#include <iostream>

using namespace std;

char arr[26][2];

void preorder(char cur) {
    if (cur == '.') return;

    cout << cur;
    preorder(arr[cur - 'A'][0]);
    preorder(arr[cur - 'A'][1]);
}

void inorder(char cur) { // 중간부터
    if (cur == '.') return;

    inorder(arr[cur - 'A'][0]);
    cout << cur;
    inorder(arr[cur - 'A'][1]);
}

void postorder(char cur) {
    if (cur == '.') return;

    postorder(arr[cur - 'A'][0]);
    postorder(arr[cur - 'A'][1]);
    cout << cur;
}

int main() {
    int n;
    cin >> n;

    for (int i = 0; i < n; i++) {
        char node, left, right;
        cin >> node >> left >> right;
        arr[node - 'A'][0] = left;
        arr[node - 'A'][1] = right;
    }

    preorder('A');
    cout << '\n';
    inorder('A');
    cout << '\n';
    postorder('A');
    cout << '\n';
}
