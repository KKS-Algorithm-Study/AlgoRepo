#include <iostream>
#include <deque>

using namespace std;

int main() {
    deque<pair<int, int>> deq;

    int N;
    cin >> N;
    for (int i = 1; i <= N; i++) {
        int a;
        cin >> a;
        deq.push_back({a, i});
    }

    while (1) {
        if (deq.empty())break;
        int idx = deq.front().first;
        cout << deq.front().second << " ";
        deq.pop_front();

        if (idx > 0) { // idx가 양수면 오른쪽으로 회전
            for (int i = 0; i < idx - 1; i++) {
                deq.push_back(deq.front());
                deq.pop_front();
            }
        } else { // idx가 음수면 왼쪽으로 회전
            for (int i = idx; i < 0; i++) {
                deq.push_front(deq.back());
                deq.pop_back();
            }
        }
    }
}
