#include <iostream>
#include <algorithm>

using namespace std;

int rgb[1001][3];
int dp[1001][3];

int main() {
    int N;
    cin >> N;
    for (int i = 1; i <= N; i++) {
        for (int j = 0; j < 3; j++) {
            cin >> rgb[i][j];
        }
    }
    dp[0][0] = dp[0][1] = dp[0][2] = 0;

    for (int i = 1; i <= N; i++) {
        dp[i][0] = min(dp[i - 1][1], dp[i - 1][2]) + rgb[i][0];
        dp[i][1] = min(dp[i - 1][0], dp[i - 1][2]) + rgb[i][1];
        dp[i][2] = min(dp[i - 1][0], dp[i - 1][1]) + rgb[i][2];
    }

    cout << min({dp[N][0], dp[N][1], dp[N][2]});
}
