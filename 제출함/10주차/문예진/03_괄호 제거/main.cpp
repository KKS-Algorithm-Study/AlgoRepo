#include <iostream>
#include <vector>
#include <algorithm>
#include <stack>
#include <set>

using namespace std;

int vidx;
bool except[201];

int main() {
    string tmpres = "";
    stack<int> st;
    set<string> result;
    vector<pair<int, int>> v(10);

    string s;
    cin >> s;

    for (int i = 0; i < s.length(); i++) {
        if (s[i] == '(') st.push(i);
        else if (s[i] == ')') {
            v[vidx].first = st.top();
            v[vidx].second = i;
            st.pop();
            vidx++;
        }
    }
    for (int i = 1; i <= vidx; i++) {
        vector<bool> tmp(vidx);
        fill(tmp.end() - i, tmp.end(), true);

        do {
            for (int j = 0; j < v.size(); j++) {
                if (tmp[j]) {
                    except[v[j].first] = true;
                    except[v[j].second] = true;
                }
            }
            for (int j = 0; j < s.size(); j++) {
                if (except[j]) continue;
                tmpres += s[j];
            }
            result.insert(tmpres);
            fill_n(except, 201, false);
            tmpres = "";
        } while (next_permutation(tmp.begin(), tmp.end()));
    }


    for (auto it = result.begin(); it != result.end(); it++) {
        cout << *it << '\n';
    }
}
