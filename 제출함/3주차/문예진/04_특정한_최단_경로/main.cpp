#include <iostream>
#include <queue>

using namespace std;

int inf = 987654321;

vector<pair<int, int>> a[801];
int d[801];

int minval(int d1, int d2) {
    if (d1 < d2) return d1;
    else return d2;
}

void dijkstra(int start) {
    fill_n(d, 801, inf);
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
    int N, E;
    cin >> N >> E;
    for (int i = 0; i < E; i++) {
        int A, B, C;
        cin >> A >> B >> C;
        a[A].push_back({B, C});
        a[B].push_back({A,C});
    }
    int v1, v2;
    cin >> v1 >> v2;

    // start -> v1 -> v2 -> end
    // start -> v2 -> v1 -> end
    dijkstra(1);
    int StartToV1 = d[v1];
    int StartToV2 = d[v2];

    dijkstra(v1);
    int V1ToEnd = d[N];
    int V1ToV2 = d[v2]; //V2ToV1과 같다

    dijkstra(v2);
    int V2ToEnd = d[N];

    int res = inf;
    res = minval(res, StartToV1 + V1ToV2 + V2ToEnd);
    res = minval(res, StartToV2 + V1ToV2 + V1ToEnd);

    if (res == inf || V1ToV2 == inf) cout << "-1";
    else cout << res;
}
