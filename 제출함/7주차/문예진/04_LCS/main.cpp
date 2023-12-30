#include <iostream>
#include <string>
#include <algorithm>

using namespace std;

int dp[1001][1001];

int main() {
    string s1, s2;
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
    cout << dp[i - 1][j - 1];
}
