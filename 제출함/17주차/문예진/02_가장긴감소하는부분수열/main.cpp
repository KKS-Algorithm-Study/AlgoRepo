#include <iostream>

using namespace std;

int arr[1001];
int dp[1001];

int main() {
    int n;
    cin >> n;
    for (int i = 1; i <= n; i++) cin >> arr[i];

    for (int i = 1; i <= n; i++) {
        dp[i] = 1;
        for (int j = 1; j <= i; j++) {
            if (arr[i] < arr[j] && dp[j] + 1 > dp[i]) dp[i] = dp[j] + 1;
        }
    }
    int Max = 0;
    for (int i = 1; i <= n; i++) {
        if (Max < dp[i]) Max = dp[i];
    }
    cout << Max;
}
