#include <iostream>
#include <deque>

using namespace std;

deque<int> Backstack;
deque<int> Frontstack;
int curP;

void Back() {
    if (Backstack.empty()) return;
    Frontstack.push_front(curP);
    curP = Backstack.front();
    Backstack.pop_front();
}

void Front() {
    if (Frontstack.empty()) return;
    Backstack.push_front(curP);
    curP = Frontstack.front();
    Frontstack.pop_front();
}

void Access(int page) {
    if (!Frontstack.empty()) Frontstack.clear();
    if (curP != 0) {
        Backstack.push_front(curP);
    }
    curP = page;
}

void Compress() {
    if (Backstack.empty()) return;
    int idx = 0;
    while (1) {
        if (idx + 1 == Backstack.size()) break;
        if (Backstack[idx] == Backstack[idx + 1]) {
            Backstack.erase(Backstack.begin() + idx + 1);
        } else idx++;
    }

}

int main() {
    int N, M;
    cin >> N >> M;
    for (int i = 0; i < M; i++) {
        char C;
        cin >> C;
        if (C == 'B') Back();
        else if (C == 'F') Front();
        else if (C == 'A') {
            int p;
            cin >> p;
            Access(p);
        } else Compress();
    }
    cout << curP << '\n';
    if (Backstack.empty()) cout << "-1\n";
    else {
        for (int i = 0; i < Backstack.size(); i++) {
            cout << Backstack[i] << " ";
        }
        cout << '\n';
    }
    if (Frontstack.empty()) cout << "-1\n";
    else {
        for (int i = 0; i < Frontstack.size(); i++) {
            cout << Frontstack[i] << " ";
        }
    }
}
