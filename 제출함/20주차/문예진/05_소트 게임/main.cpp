#include <iostream>
#include <vector>
#include <algorithm>
#include <queue>
#include <set>

using namespace std;

int N, K;
set<string> visited;

string s = "";

int bfs(string str) {
    queue<pair<string, int>> q;
    visited.insert(str);
    q.push({str, 0});
    string target = str;
    sort(target.begin(), target.end());

    while (!q.empty()) {
        string cur = q.front().first;
        int cnt = q.front().second;
        q.pop();

        if (cur == target) return cnt;

        for (int i = 0; i <= N - K; i++) {
            string tmp = cur.substr(i, K);
            reverse(tmp.begin(), tmp.end());
            tmp = cur.substr(0, i) + tmp + cur.substr(i + K, N - i + K);

            if (visited.find(tmp) == visited.end()) {
                visited.insert(tmp);
                q.push({tmp, cnt + 1});
            }
        }
    }
    return -1;
}

int main() {
    cin >> N >> K;
    for (int i = 0; i < N; i++) {
        char c;
        cin >> c;
        s += c;
    }
    cout << bfs(s);
}
