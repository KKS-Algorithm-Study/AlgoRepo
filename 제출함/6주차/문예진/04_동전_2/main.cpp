#include <iostream>

using namespace std;

int coins[101];
int dp[10001];

int minn(int a, int b) {
    if (a < b) return a;
    else return b;
}

int main() {
    int n, k;
    cin >> n >> k;
    fill_n(dp, k + 1, 10001);
    dp[0] = 0;

    for (int i = 0; i < n; i++) {
        cin >> coins[i];
    }

    for (int i = 0; i < n; i++) {
        for (int j = coins[i]; j <= k; j++) {
            dp[j] = minn(dp[j], dp[j - coins[i]] + 1);
        }
    }

    if (dp[k] == 10001) cout << "-1";
    else cout << dp[k];
}
