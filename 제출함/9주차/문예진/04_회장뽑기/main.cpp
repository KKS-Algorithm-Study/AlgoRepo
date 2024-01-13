#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

vector<int> v[51];
vector<pair<int, int>> result;
bool visited[51];

bool compare(pair<int, int> &v1, pair<int, int> &v2) {
    if (v1.first == v2.first) return v1.second < v2.second;
    return v1.first < v2.first;
}

void bfs(int node) {
    int node_cnt = 0;
    queue<pair<int, int>> q;

    visited[node] = true;
    q.push({0, node});

    while (!q.empty()) {
        int cur = q.front().second;
        int cnt = q.front().first;
        if (cnt > node_cnt) node_cnt = cnt;
        q.pop();

        for (int i = 0; i < v[cur].size(); i++) {
            int next = v[cur][i];

            if (!visited[next]) {
                visited[next] = true;
                q.push({cnt + 1, next});
            }
        }
    }
    result.push_back({node_cnt, node});
    return;
}

int main() {
    int N;
    cin >> N;
    while (1) {
        int a, b;
        cin >> a >> b;
        if (a == -1 && b == -1) break;
        v[a].push_back(b);
        v[b].push_back(a);
    }

    for (int i = 1; i <= N; i++) {
        fill_n(visited, 51, false);
        bfs(i);
    }

    sort(result.begin(), result.end(), compare);

    int rescnt = 0;
    for (int i = 0; i < N; i++) {
        rescnt++;
        if (result[i].first != result[i + 1].first) break;
    }
    cout << result[0].first << " " << rescnt << '\n';
    for (int i = 0; i < rescnt; i++) cout << result[i].second << " ";
}
