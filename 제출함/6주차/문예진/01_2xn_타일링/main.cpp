#include <iostream>

using namespace std;

int main() {
    int dp[1001] = {1, 2};

    int n;
    cin >> n;
    for (int i = 2; i < n; i++) {
        dp[i] = (dp[i - 1] + dp[i - 2]) % 10007;
    }
    cout << dp[n - 1];
}
