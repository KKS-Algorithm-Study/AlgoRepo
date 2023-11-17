#include <iostream>
#include <vector>
using namespace std;

bool visited[20][20][4][2];
int graph[20][20];

int lr[4] = {0,1,1,-1};
int ud[4] = {1,0,1,1};

int dfs(int x, int y, int dir, int color, int cnt)
{
    visited[x][y][dir][color-1] = true;

    int nx = x+lr[dir];
    int ny = y+ud[dir];

    if(nx<0 || ny <0 || nx>=19 || ny >= 19 || graph[nx][ny] != color) {
        if(cnt==5) return color;
        else return 0;
    }
    return dfs(nx,ny,dir,color,cnt+1);
}

int main() {
    for(int i=0; i<19; i++) {
        for(int j=0; j<19; j++) {
            cin>>graph[i][j];
        }
    }
    for(int k=0; k<19; k++) {
        for(int m=0; m<19; m++) {
            if(graph[m][k]!=0) {
                for(int i=0; i<4; i++) {
                    if(visited[m][k][i][graph[m][k]-1]) continue;
                    if(dfs(m,k,i,graph[m][k],1)!=0) {
                        cout<<graph[m][k] <<'\n'<<m+1<<" "<<k+1;
                        return 0;
                    }
                }
            }
        }
    }
    cout<<0;
    return 0;
}
