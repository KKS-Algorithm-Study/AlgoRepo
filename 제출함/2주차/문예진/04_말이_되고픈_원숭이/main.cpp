#include <iostream>
#include <queue>
#include <tuple>
using namespace std;
int k,w,h;
int map[201][201];
int visited[201][201][31];
int dxm[4] = {-1,0,1,0}; // 원숭이의 움직임
int dym[4] = {0,1,0,-1};
int dxh[8] = {-2,-1,1,2,-2,-1,1,2}; // 말의 움직임
int dyh[8] = {1,2,2,1,-1,-2,-2,-1};

void bfs(int startx, int starty, int startk) {
    visited[startx][starty][startk]=1;
    queue<tuple<int,int,int>> q; // 큐에 좌표와 k를 함께 추가
    q.push({startx,starty,startk});

    while(!q.empty()) {
        int x = get<0>(q.front());
        int y = get<1>(q.front());
        int mk = get<2>(q.front());
        q.pop();
        if(x==h-1 && y==w-1) {
            cout<<visited[x][y][mk]-1;
            return;
        }
        if(mk!=0) { // 한도가 남았다면 말처럼 움직이는 경우를 큐에 추가
            for(int i=0; i<8; i++) {
                int rx = x+dxh[i];
                int ry = y+dyh[i];
                
                if(rx<0 || rx>=h || ry<0 || ry>= w) continue;
                
                if(!visited[rx][ry][mk-1] && map[rx][ry]==0) {
                    visited[rx][ry][mk-1] = visited[x][y][mk]+1;
                    q.push({rx,ry,mk-1});
                }
            }
        }
        for(int i=0; i<4; i++) { // 원숭이처럼 움직이는 경우를 큐에 추가
            int rx = x+dxm[i];
            int ry = y+dym[i];

            if(rx<0 || rx>=h || ry<0 || ry>= w) continue;

            if(!visited[rx][ry][mk] && map[rx][ry]==0) {
                visited[rx][ry][mk] = visited[x][y][mk]+1;
                q.push({rx,ry,mk});
            }
        }
    }
    cout<<"-1";
}

int main() {
    cin>>k>>w>>h;
    for(int i=0; i<h; i++) {
        for(int j=0; j<w; j++) {
            cin>>map[i][j];
        }
    }

    bfs(0,0,k);
}
