#include <iostream>
#include <string>
#include <algorithm>

using namespace std;

int dp[1001][1001];

int main() {
    string s1, s2, ans;
    cin >> s1 >> s2;
    s1 = '0' + s1;
    s2 = '0' + s2;
    int i = 1, j = 1;
    for (i = 1; s1[i]; i++) {
        for (j = 1; s2[j]; j++) {
            if (s1[i] == s2[j]) dp[i][j] = dp[i - 1][j - 1] + 1;
            else dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
        }
    }

    int col = j - 1;
    int row = i - 1;

    while (dp[row][col]) {
        if (dp[row][col] == dp[row - 1][col]) row--;
        else if (dp[row][col] == dp[row][col - 1]) col--;
        else {
            ans += s1[row];
            row--;
            col--;
        }
    }
    cout << dp[i - 1][j - 1] << '\n';
    if (ans.size() > 0) {
        reverse(ans.begin(), ans.end());
        cout << ans << '\n';
    }
}
