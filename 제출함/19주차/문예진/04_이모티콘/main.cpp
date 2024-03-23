#include <iostream>
#include <queue>
#include <tuple>
#include <algorithm>

using namespace std;

int S, res = 987654321;
bool visited[2000][2000];

void bfs() {
    queue<tuple<int, int, int>> q;
    visited[1][0] = true;
    q.push({1, 0, 0});

    while (!q.empty()) {
        int curDis = get<0>(q.front());
        int curClip = get<1>(q.front());
        int curTime = get<2>(q.front());
        if (curDis == S) {
            //res=get<2>(q.front());
            //break;//???
            res = min(res, curTime);
        }
        q.pop();
        if (curDis + curClip >= 2000 || curDis <= 0) continue;
        if (!visited[curDis][curDis]) {
            visited[curDis][curDis] = true;
            q.push({curDis, curDis, curTime + 1});
        }

        if (curClip != 0 && !visited[curDis + curClip][curClip]) {
            visited[curDis + curClip][curClip] = true;
            q.push({curDis + curClip, curClip, curTime + 1});
        }
        if (!visited[curDis - 1][curClip]) {
            visited[curDis - 1][curClip] = true;
            q.push({curDis - 1, curClip, curTime + 1});
        }
    }

}

int main() {
    cin >> S;
    bfs();
    cout << res;
}
