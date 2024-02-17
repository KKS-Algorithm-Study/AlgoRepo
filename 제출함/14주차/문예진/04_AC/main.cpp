#include <iostream>
#include <string>
#include <sstream>
using namespace std;

typedef struct node
{
    int i;
    struct node*next;
    struct node*prev;
}node;

node*head;
node*cur;
bool revflag = false;
bool errflag = false;

void init()
{
    head=0;
    cur=0;
    revflag=false;
    errflag=false;
}


void addToAC(int num)
{
    node*newnode = (node*)malloc(sizeof(node));
    newnode->i = num;
    newnode->prev = newnode->next=0;

    if(head==0)
    {
        head=newnode;
        cur=head;
        return;
    }
    cur->next=newnode;
    newnode->prev=cur;
    cur=newnode;
}

int removeNum()
{
    if(head==0) return -1;
    if(revflag) //뒤집힌 상태
    {
        node*del = cur;
        if(del->prev==0) head=0;
        else
        {
            cur=cur->prev;
            cur->next=0;
        }
        free(del);
    }
    else
    {
        node*del = head;
        head=head->next;
        if(head!=0) head->prev=0;
        free(del);
    }
    return 0;
}

void printAC()
{
    if(errflag) 
    {
        cout<<"error"<<endl;
        errflag = false;
        return;
    }
    if(head==0) 
    {
        cout<<"[]"<<endl;
        return;
    }

    cout<<'[';
    if(revflag) //뒤집힌 상태
    {
        while(cur->prev!=0)
        {
            cout<<cur->i<<",";
            cur=cur->prev;
        }
        cout<<cur->i;
    }
    else
    {
        node*tmp = head;
        while(tmp->next!=0)
        {
            cout<<tmp->i<<",";
            tmp=tmp->next;
        }
        cout<<tmp->i;
    }
    cout<<']'<<endl;
}

int main()
{
    ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);
    int T;
    cin>>T;
    for(int i=0; i<T; i++)
    {
        init();
        string p; // 수행할 함수
        cin>>p;
        int n; // 배열에 들어있는 수의 개수
        cin>>n;
        string tx; // 배열
        cin>>tx;
        string x = tx.substr(1,tx.length()-2);
        stringstream ss(x);
        string num;
        while(getline(ss,num,','))
        {
            int in=0;
            stringstream ssInt(num);
            ssInt>>in;
            addToAC(in);
        }
        ss.clear();
        int len = p.length();
        for(int j=0; j<len; j++)
        {
            if(p[j]=='R') revflag = revflag ? false : true;
            else if(p[j]=='D') 
            {
                int res = removeNum();
                if(res==-1) 
                {
                    errflag=true;
                    break;
                }
            }
        }
        printAC();
    }
    return 0;
}
