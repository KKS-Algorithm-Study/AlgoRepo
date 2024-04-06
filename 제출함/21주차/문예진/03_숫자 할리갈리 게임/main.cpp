#include <iostream>
#include <deque>

using namespace std;

deque<int> dodeq;
deque<int> sudeq;
deque<int> doground;
deque<int> suground;

void merdeque(string dosu) {
    if (dosu == "do") {
        while (suground.size() > 0) {
            dodeq.push_back(suground.front());
            suground.pop_front();
        }
        while (doground.size() > 0) {
            dodeq.push_back(doground.front());
            doground.pop_front();
        }
    }
    if (dosu == "su") {
        while (doground.size() > 0) {
            sudeq.push_back(doground.front());
            doground.pop_front();
        }
        while (suground.size() > 0) {
            sudeq.push_back(suground.front());
            suground.pop_front();
        }
    }
}

int main() {
    int N, M;
    cin >> N >> M;
    for (int i = 0; i < N; i++) {
        int a, b;
        cin >> a >> b;
        dodeq.push_front(a);
        sudeq.push_front(b);
    }
    for (int i = 0; i < M; i++) {
        if (i % 2 == 0) {
            doground.push_back(dodeq.front());
            dodeq.pop_front();
        } else {
            suground.push_back(sudeq.front());
            sudeq.pop_front();
        }

        if (dodeq.empty()) {
            cout << "su";
            return 0;
        } else if (sudeq.empty()) {
            cout << "do";
            return 0;
        }

        if (!doground.empty() && doground.back() == 5 || !suground.empty() && suground.back() == 5) {
            merdeque("do");
        } else if (!doground.empty() && !suground.empty()) {
            if (doground.back() + suground.back() == 5) {
                merdeque("su");
            }
        }
    }
    if (dodeq.size() > sudeq.size()) {
        cout << "do";
    } else if (dodeq.size() < sudeq.size()) {
        cout << "su";
    } else cout << "dosu";
}
