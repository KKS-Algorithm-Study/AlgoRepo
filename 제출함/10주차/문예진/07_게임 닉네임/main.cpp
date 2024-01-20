#include <iostream>
#include <map>
#include <set>

using namespace std;

int main() {
    ios_base::sync_with_stdio(0); cin.tie(0);
    map<string, int> user;
    set<string> nickname;
    int N;
    cin >> N;

    for (int i = 0; i < N; i++) {
        string s;
        cin >> s;

        user[s]++;
        bool flag = false;
        for (int i = 1; i <= s.size(); i++) {
            string t = s.substr(0, i);
            if (!flag && nickname.find(t) == nickname.end()) {
                cout << t << '\n';
                flag = true;
            }
            nickname.insert(t);
        }
        if (!flag) {
            if (user[s] == 1) {
                cout << s << '\n';
                nickname.insert(s);
            } else {
                string t = s + to_string(user[s]);
                cout << t << '\n';
                nickname.insert(t);
            }
        }
    }
}
