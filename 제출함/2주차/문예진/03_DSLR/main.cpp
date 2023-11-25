#include <iostream>
#include <queue>
#include <cstring>

using namespace std;
int a, b;
bool visited[10000];

void bfs() {
    queue<pair<int, string>> q;
    q.push({a, ""});
    visited[a]= true;

    while (!q.empty()) {
        int tmp;
        int x = q.front().first;
        string op = q.front().second;
        cout<<"x : "<<x<<", op : "<<op<<'\n';
        q.pop();

        if (x == b) {
            cout << op << '\n';
            return;
        }

        tmp = (2 * x) % 10000;
        if(!visited[tmp]) {
            visited[tmp]= true;
            q.push({tmp, op + "D"});
        }

        tmp = x - 1;
        if (tmp == -1) tmp = 9999;
        if(!visited[tmp]) {
            visited[tmp]= true;
            q.push({tmp, op + "S"});
        }

        tmp = (x % 1000) * 10 + x / 1000;
        if(!visited[tmp]) {
            visited[tmp]= true;
            q.push({tmp, op + "L"});
        }

        tmp = (x % 10) * 1000 + x / 10;
        if(!visited[tmp]) {
            visited[tmp]= true;
            q.push({tmp, op + "R"});
        }
        cout<<'\n';
    }
}

int main() {
    int n;
    cin >> n;
    for (int i = 0; i < n; i++) {
        cin >> a >> b;
        bfs();
        fill_n(visited,10000,false);
    }
}
