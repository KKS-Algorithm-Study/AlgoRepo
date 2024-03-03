#include <iostream>
#include <stack>

using namespace std;

int arr[1000001];
int ans[1000001];
int cnt[1000001];

int main() {
    stack<pair<int, int>> s;
    int N;
    cin >> N;

    for (int i = 0; i < N; i++) {
        cin >> arr[i];
        cnt[arr[i]]++;
    }

    s.push({arr[N - 1], cnt[arr[N - 1]]});
    ans[N - 1] = -1;
    for (int i = N - 2; i >= 0; i--) {
        if (s.top().second > cnt[arr[i]]) ans[i] = s.top().first;
        else {
            while (!s.empty() && s.top().second <= cnt[arr[i]]) s.pop();
            if (s.empty()) ans[i] = -1;
            else ans[i] = s.top().first;
        }
        s.push({arr[i], cnt[arr[i]]});
    }
    for (int i = 0; i < N; i++) cout << ans[i] << " ";
}
