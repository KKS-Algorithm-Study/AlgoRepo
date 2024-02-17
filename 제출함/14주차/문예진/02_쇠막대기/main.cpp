#include <iostream>
#include <string>
using namespace std;

int main()
{
    int cnt=0;
    int res=0;
    string s;
    cin>>s;
    for(int i=0; i<=s.length(); i++)
    {
        if(s[i]=='(')
        {
            if(s[i+1]==')')
            {
                res+=cnt;
                i=i+1;
                continue;
            }
            cnt++;
        }
        else if(s[i]==')')
        {
            res++;
            cnt--;
        }
    }
    cout<<res;
}
