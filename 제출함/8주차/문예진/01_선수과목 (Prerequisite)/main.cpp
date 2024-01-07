#include <iostream>
#include <vector>
#include <queue>

using namespace std;

int indegree[1001];
int res[1001];

int maxx(int a, int b) {
    if (a > b) return a;
    else return b;
}

int main() {
    vector<int> graph[1001];
    queue<int> q;

    int n, m;
    cin >> n >> m;
    for (int i = 0; i < m; i++) {
        int a, b;
        cin >> a >> b;
        graph[a].push_back(b);
        indegree[b]++;
    }

    for (int i = 1; i <= n; i++) {
        if (indegree[i] == 0) {
            q.push(i);
            res[i] = 1;
        }
    }

    while (!q.empty()) {
        int cur = q.front();
        q.pop();

        for (int i = 0; i < graph[cur].size(); i++) {
            int next = graph[cur][i];
            indegree[next]--;

            res[next] = maxx(res[next], res[cur] + 1);
            if (indegree[next] == 0) {
                q.push(next);
            }
        }
    }

    for (int i = 1; i <= n; i++) {
        cout << res[i] << " ";
    }
}
