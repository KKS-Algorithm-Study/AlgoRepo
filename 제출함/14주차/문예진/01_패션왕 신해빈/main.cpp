#include <iostream>
#include <map>
#include <string>
#include <vector>
using namespace std;

int main()
{
    ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);
    int n;
    cin>>n;

    for(int i=0; i<n; i++)
    {
        int m;
        cin>>m;
        map<string,int> m1;
        for(int j=0; j<m; j++)
        {
            string s1,s2;
            cin>>s1>>s2;
            if(m1.find(s2) == m1.end())
            {
                m1.insert({s2,1});
            }
            else m1[s2]++;
        }
        int ans=1;
        for(auto i:m1)
        {
            ans=ans*(i.second+1);
        }
        ans=ans-1;
        cout<<ans<<'\n';
    }
}
