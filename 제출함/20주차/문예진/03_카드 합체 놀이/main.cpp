#include <iostream>
#include <queue>

using namespace std;

int main() {
    int N, M;
    long long res = 0;
    cin >> N >> M;
    priority_queue<long long, vector<long long>, greater<long long>> q; // 오름차순
    for (int i = 0; i < N; i++) {
        int a;
        cin >> a;
        q.push(a);
    }
    for (int i = 0; i < M; i++) {
        long long a = q.top();
        q.pop();
        long long b = q.top();
        q.pop();
        long long tmp = a + b;
        q.push(tmp);
        q.push(tmp);
    }
    while (!q.empty()) {
        res += q.top();
        q.pop();
    }
    cout << res;
}
