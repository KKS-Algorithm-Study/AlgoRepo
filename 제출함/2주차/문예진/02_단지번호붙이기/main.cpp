#include <iostream>
#include <vector>
#include <algorithm>
#include <string>
using namespace std;

bool visited[26][26];
int graph[26][26];
//lurd
int lr[4] = {-1,0,1,0};
int ud[4] = {0,1,0,-1};

int n, cnt;

void dfs(int x, int y)
{
    for(int i=0; i<4; i++)
    {
        int r = x+lr[i];
        int d = y+ud[i];

        if(r>=n || r<0 || d>=n || d<0) continue;

        if(visited[r][d]==false && graph[r][d]==1)
        {
            visited[r][d]=true;
            cnt++;
            dfs(r,d);
        }
    }
}

int main()
{
    int res=0;
    cin>>n;
    string str;
    for(int i=0; i<n; i++)
    {
        cin>>str;
        for(int j=0; j<n; j++)
        {
            if(str[j]=='1') graph[i][j]=1;
            else graph[i][j]=0;
        }
    }
    vector<int> v;
    for(int i=0; i<n; i++)
    {
        for(int j=0; j<n; j++)
        {
            if(graph[i][j]==1 && visited[i][j]==false)
            {
                visited[i][j]=true;
                cnt=1;
                dfs(i,j);
                v.push_back(cnt);
                res++;
            }
        }
    }
    sort(v.begin(),v.end());
    cout<<res<<endl;
    for(auto i:v)
    {
        cout<<i<<endl;
    }
}
