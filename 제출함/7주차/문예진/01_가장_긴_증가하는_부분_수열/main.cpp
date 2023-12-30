#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int arr[1001];

int main() {
    int n;
    cin >> n;
    for (int i = 0; i < n; i++) {
        cin >> arr[i];
    }
    vector<int> dp(1, 0);
    dp.emplace_back(arr[0]);
    for (int i = 1; i < n; i++) {
        if (arr[i] > dp.back()) {
            dp.emplace_back(arr[i]);
        } else {
            int t = distance(dp.begin(), lower_bound(dp.begin(), dp.end(), arr[i]));
            dp[t] = arr[i];
        }
    }

    cout << dp.size() - 1;
}
