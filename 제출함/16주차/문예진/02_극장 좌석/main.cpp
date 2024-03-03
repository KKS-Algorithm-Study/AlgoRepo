#include <iostream>

using namespace std;

int dp[41];
int vip[41];

int main() {
    int N, M;
    cin >> N >> M;

    for (int i = 0; i < M; i++) {
        cin >> vip[i];
    }

    dp[0] = 1;
    dp[1] = 1;
    dp[2] = 2;

    for (int i = 3; i <= N; i++) dp[i] = dp[i - 2] + dp[i - 1];

    int res = 1;
    int startIdx = 0;
    for (int i = 0; i < M; i++) {
        int endIdx = vip[i];
        res = res * (dp[endIdx - startIdx - 1]);
        startIdx = endIdx;
    }
    res = res * (dp[N - startIdx]);
    cout << res;
}
