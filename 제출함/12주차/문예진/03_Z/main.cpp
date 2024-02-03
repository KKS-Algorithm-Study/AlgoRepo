#include <iostream>
#include <cmath>

using namespace std;

int N, r, c, res;

void sol(int x, int y, int sz) {
    if (c == x && r == y) {
        cout << res;
        return;
    }
    if (c < x + sz && r < y + sz && c >= x && r >= y) {
        sol(x, y, sz / 2);
        sol(x + sz / 2, y, sz / 2);
        sol(x, y + sz / 2, sz / 2);
        sol(x + sz / 2, y + sz / 2, sz / 2);
    } else res += sz * sz;
}

int main() {
    cin >> N >> r >> c;
    sol(0, 0, pow(2, N));
}
