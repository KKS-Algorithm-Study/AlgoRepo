#include <iostream>

using namespace std;

int dp[21];

int main() {
    int n;
    cin >> n;

    dp[0] = dp[1] = 1;
    dp[2] = 2;
    dp[3] = 4;
    dp[4] = 7;

    for (int i = 5; i <= n; i++) {
        if (i % 2 == 0) {
            dp[i] = dp[i - 1] * 2 - dp[i - 5] - dp[i - 4];
        } else dp[i] = dp[i - 1] * 2;
    }
    cout << dp[n];
}
