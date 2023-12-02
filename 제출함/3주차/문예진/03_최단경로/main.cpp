#include <iostream>
#include <vector>
#include <queue>

using namespace std;

int inf = 987654321;

vector<pair<int, int>> a[20001];
int d[20001];

void dijkstra(int start) {
    d[start] = 0;
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
    pq.push({0, start});

    while (!pq.empty()) {
        int cur = pq.top().second;
        int dis = pq.top().first;
        pq.pop();

        if (d[cur] < dis) continue;
        for (int i = 0; i < a[cur].size(); i++) {
            int next = a[cur][i].first;
            int nextDis = dis + a[cur][i].second;

            if (nextDis < d[next]) {
                d[next] = nextDis;
                pq.push({nextDis, next});
            }
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int V, E, K;
    cin >> V >> E >> K;

    fill_n(d, 20001, inf);

    for (int i = 0; i < E; i++) {
        int u, v, w;
        cin >> u >> v >> w;
        a[u].push_back({v, w});
    }
    dijkstra(K);

    for (int i = 1; i <= V; i++) {
        if (d[i] == inf) cout << "INF\n";
        else cout << d[i] << "\n";
    }
}
