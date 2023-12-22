#include <iostream>

using namespace std;

int maxx(int a, int b) {
    if (a > b) return a;
    else return b;
}

int main() {
    int T;
    cin >> T;

    for (int testcase = 0; testcase < T; testcase++) {
        int dp[2][100001];
        int arr[2][100001];

        int n;
        cin >> n;
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j <= n; j++) {
                cin >> arr[i][j];
            }
        }

        dp[0][0] = dp[1][0] = 0;
        dp[0][1] = arr[0][1];
        dp[1][1] = arr[1][1];

        for (int i = 2; i <= n; i++) {
            dp[0][i] = maxx(dp[1][i - 1], dp[1][i - 2]) + arr[0][i];
            dp[1][i] = maxx(dp[0][i - 1], dp[0][i - 2]) + arr[1][i];
        }
        cout << maxx(dp[1][n], dp[0][n]) << '\n';
    }
}
