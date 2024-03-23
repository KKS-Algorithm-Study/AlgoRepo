#include <iostream>
#include <vector>
#include <deque>
#include <algorithm>

using namespace std;

int main() {
    deque<pair<int, int>> dq;
    int N, M;
    cin >> N >> M;
    for (int i = 0; i < M; i++) {
        int a;
        cin >> a;
        bool flag = false;
        if (!dq.empty()) {
            for (int j = 0; j < dq.size(); j++) {
                if (dq[j].first == a) {
                    dq[j].second++;
                    flag = true;
                }
            }
        }

        if (!flag) {
            if (dq.size() < N) {
                dq.push_back({a, 1});
            } else {

                int minidx = 0, minval = dq[0].second;
                for (int k = 1; k < dq.size(); k++) {
                    if (dq[k].second < minval) {
                        minidx = k;
                        minval = dq[k].second;
                    }
                }
                dq.erase(dq.begin() + minidx);
                dq.push_back({a, 1});
            }
        }
    }

    vector<int> res;
    for (int i = 0; i < dq.size(); i++) {
        res.push_back(dq[i].first);
    }
    sort(res.begin(), res.end());
    for (auto i: res) cout << i << " ";
}
