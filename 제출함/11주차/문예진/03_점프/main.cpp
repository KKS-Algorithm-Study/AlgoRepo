#include <iostream>

using namespace std;

int map[100][100];
long long dp[100][100];

int main() {
    int n;
    cin >> n;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cin >> map[i][j];
        }
    }
    dp[0][0] = 1;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (dp[i][j] == 0 || (i == n - 1 && j == n - 1)) continue;

            int d = map[i][j] + i;
            int r = map[i][j] + j;
            if (d < n) dp[d][j] = dp[d][j] + dp[i][j];
            if (r < n) dp[i][r] = dp[i][r] + dp[i][j];
        }
    }
    cout << dp[n - 1][n - 1];
}
