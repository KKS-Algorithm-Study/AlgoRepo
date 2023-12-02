#include <iostream>

using namespace std;
int graph[101][101];
int inf = 987654321;

int minval(int a, int b) {
    if (a < b) return a;
    else return b;
}

int main() {
    int n, m;
    cin >> n >> m;
    fill_n(&graph[0][0], 101 * 101, inf);
    for (int i = 0; i < m; i++) {
        int a, b, c;
        cin >> a >> b >> c;
        if (graph[a][b] > c) graph[a][b] = c;
    }

    for (int k = 1; k <= n; k++) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (graph[i][k] != inf && graph[k][j] != inf) {
                    graph[i][j] = minval(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }
    }

    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            if (graph[i][j] == inf || i == j) cout << "0 ";
            else cout << graph[i][j] << " ";
        }
        cout << '\n';
    }
}
