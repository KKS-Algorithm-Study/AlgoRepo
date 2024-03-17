#include <iostream>

using namespace std;

int arr[1001][1001];
int sumArr[1001][1001];

int main() {
    int R, C, Q;
    cin >> R >> C >> Q;
    for (int i = 1; i <= R; i++) {
        for (int j = 1; j <= C; j++) {
            cin >> arr[i][j];
        }
    }

    for (int i = 1; i <= R; i++) {
        for (int j = 1; j <= C; j++) {
            sumArr[i][j] = arr[i][j] + sumArr[i - 1][j] + sumArr[i][j - 1] - sumArr[i - 1][j - 1];
        }
    }

    for (int i = 0; i < Q; i++) {
        int a, b, c, d;
        cin >> a >> b >> c >> d;

        int sumVal = sumArr[c][d] + sumArr[a - 1][b - 1] - sumArr[c][b - 1] - sumArr[a - 1][d];
        int tmp = (c - a + 1) * (d - b + 1);
        cout << sumVal / tmp << '\n';
    }
}
