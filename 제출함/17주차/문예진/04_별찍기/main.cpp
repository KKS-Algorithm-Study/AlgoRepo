#include <iostream>

using namespace std;

void star(int i, int j, int a) {
    if ((j / a) % 3 == 1 && (i / a) % 3 == 1) {
        cout << " ";
        return;
    } else {
        if (a == 1) {
            cout << "*";
            return;
        } else {
            star(i, j, a / 3);
            return;
        }
    }
}

int main() {
    int a;
    cin >> a;
    for (int i = 0; i < a; i++) {
        for (int j = 0; j < a; j++) {
            star(i, j, a);
        }
        cout << '\n';
    }
}
