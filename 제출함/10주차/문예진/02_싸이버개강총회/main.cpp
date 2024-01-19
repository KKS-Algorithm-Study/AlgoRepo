#include <iostream>
#include <map>

using namespace std;

int main() {
    map<string, int> member;
    int res = 0;
    string S, E, Q;
    cin >> S >> E >> Q;

    string s1, s2;
    while (cin >> s1 >> s2) {
        if (s1 <= S) member.insert({s2, 1});
        else {
            if (s1 >= E && s1 <= Q) {
                if (member.find(s2) != member.end()) member[s2]++;
            }
        }
    }

    for (auto it = member.begin(); it != member.end(); it++) {
        if (it->second >= 2) res++;
    }
    cout << res;
}
