#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    vector<int> v;
    vector<int> v2;
    int N, K, res = 0;
    cin >> N >> K;

    for (int i = 0; i < N; i++) {
        int a;
        cin >> a;
        v.push_back(a);
    }

    for (int i = 1; i < N; i++) {
        v2.push_back(v[i] - v[i - 1]);
    }
    sort(v2.begin(), v2.end());

    for (int i = 0; i < N - K; i++) {
        res += v2[i];
    }

    cout << res;
}
