#include <iostream>
#include <algorithm>

using namespace std;

int rect[1001][1001];

int main() {
    int N, M, res = 0;
    cin >> N >> M;
    for (int i = 1; i <= N; i++) {
        string s;
        cin >> s;

        int len = s.length();
        for (int j = 0; j < len; j++) rect[i][j + 1] = s[j] - '0';
    }

    for (int i = 1; i <= N; i++) {
        for (int j = 1; j <= M; j++) {
            if (rect[i][j] != 0) {
                rect[i][j] = min(rect[i - 1][j - 1], min(rect[i - 1][j], rect[i][j - 1])) + 1;
                if (res < rect[i][j]) res = rect[i][j];
            }
        }
    }
    cout << res * res << '\n';
}
