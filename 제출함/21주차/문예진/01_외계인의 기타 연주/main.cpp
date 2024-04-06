#include <iostream>
#include <queue>

using namespace std;

int main() {
    int N, P, cnt = 0;
    cin >> N >> P;

    priority_queue<int> pq[7];
    for (int i = 0; i < N; i++) {
        int a, b;
        cin >> a >> b;
        if (pq[a].empty()) {
            pq[a].push(b);
            cnt++;
        } else {
            if (pq[a].top() > b) {
                while (!pq[a].empty() && pq[a].top() > b) {
                    pq[a].pop();
                    cnt++;
                }
            }
            if (pq[a].empty() || pq[a].top() < b) {
                pq[a].push(b);
                cnt++;
            }
        }
    }
    cout << cnt;

}
