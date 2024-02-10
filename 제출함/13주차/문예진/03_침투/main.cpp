#include <iostream>
#include <queue>

using namespace std;

int N, M;
bool visited[1001][1001];
int map[1001][1001];
queue<pair<int, int>> q;
int dx[4] = {0, 0, 1, -1};
int dy[4] = {-1, 1, 0, 0};

int main() {
    cin >> N >> M;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            scanf("%1d", &map[i][j]);
        }
    }

    for (int i = 0; i < M; i++) {
        if (map[0][i] == 0) {
            q.push({0, i});
            visited[0][i] = true;
        }
    }

    while (!q.empty()) {
        int x = q.front().second;
        int y = q.front().first;
        q.pop();

        if (y == N - 1) {
            cout << "YES";
            return 0;
        }
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || ny < 0 || nx >= M || ny >= N) continue;
            if (map[ny][nx] == 0 && !visited[ny][nx]) {
                visited[ny][nx] = true;
                q.push({ny, nx});
            }
        }
    }
    cout << "NO";
}
