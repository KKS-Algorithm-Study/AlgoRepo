#include <iostream>
#include <vector>
#include <queue>

using namespace std;
int s, t;
int dist[5001];
vector<pair<int, int>> graph[5001];

void dfs() {
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
    pq.push({0, s});
    dist[s] = 0;

    while (!pq.empty()) {
        int cur = pq.top().second;
        int cost = pq.top().first;
        pq.pop();

        if (cost > dist[cur]) continue;

        if (cur == t) {
            cout << cost << '\n';
            return;
        }

        for (int i = 0; i < graph[cur].size(); i++) {
            int next = graph[cur][i].first;
            int next_cost = cost + graph[cur][i].second;

            if (next_cost < dist[next]) {
                dist[next] = next_cost;
                pq.push({next_cost, next});
            }
        }
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    int N, M;
    cin >> N >> M;
    for (int i = 0; i < M; i++) {
        int a, b, c;
        cin >> a >> b >> c;
        graph[a].push_back({b, c});
        graph[b].push_back({a, c});
    }
    cin >> s >> t;
    fill_n(dist, N + 1, 987654321);
    dfs();
}
