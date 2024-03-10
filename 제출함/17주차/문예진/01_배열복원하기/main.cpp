#include <iostream>

using namespace std;

int arrB[601][601];

int main() {
    int n, m, x, y;
    cin >> n >> m >> x >> y;

    for (int i = 0; i < n + x; i++) {
        for (int j = 0; j < m + y; j++) cin >> arrB[i][j];
    }
    for (int i = x; i < n; i++) {
        for (int j = y; j < m; j++) {
            arrB[i][j] -= arrB[i - x][j - y];
        }
    }
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) cout << arrB[i][j] << " ";
        cout << '\n';
    }
}
