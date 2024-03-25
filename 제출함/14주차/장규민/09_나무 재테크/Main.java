import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static class Tree{
        int y, x, age;

        public Tree(int y, int x, int age){
            this.y = y;
            this.x = x;
            this.age = age;
        }
    }

    private static int[][] board, add;
    private static int[] dy = {1,1,1,0,-1,-1,-1,0}, dx = {-1,0,1,1,1,0,-1,-1};
    private static Deque<Tree> trees, deadTrees;
    private static List<int[]> treeList;
    private static int n, m, k;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());
        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());
        k = Integer.parseInt(stk.nextToken());

        board = new int[n + 1][n + 1];
        add = new int[n + 1][n + 1];

        trees = new LinkedList<>();

        for(int i=1 ; i<=n ; i++){
            stk = new StringTokenizer(br.readLine());
            for(int j=1 ; j<=n; j++){
                add[i][j] = Integer.parseInt(stk.nextToken());
                board[i][j] = 5;
            }
        }

        for(int i=0 ; i<m ; i++){
            stk = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(stk.nextToken()), x = Integer.parseInt(stk.nextToken()), age = Integer.parseInt(stk.nextToken());
            trees.add(new Tree(y, x, age));
        }

        deadTrees = new LinkedList<>();
        while(k-- > 0){
            treeList = new ArrayList<>();
            spring();
            summer();
            fall();
            winter();
        }

        System.out.println(trees.size());

    }
    
    private static void spring() {
        int size = trees.size();
        
        for(int i=0 ; i<size ; i++){
            Tree tree = trees.poll();
            int y = tree.y, x = tree.x, age = tree.age;

            if(age > board[y][x]){
                deadTrees.add(tree);
                continue;
            }

            trees.add(new Tree(y, x, age + 1));
            treeList.add(new int[]{y, x, age + 1});
            board[y][x] -= age;
        }
    }

    private static void summer() {
        while(!deadTrees.isEmpty()){
            Tree tree = deadTrees.poll();
            board[tree.y][tree.x] += tree.age / 2;
        }
    }

    private static void fall() {
        for(int[] tree : treeList){
            int y = tree[0], x = tree[1], age = tree[2];

            if(age % 5 != 0)continue;
            
            for(int i=0 ; i<8 ; i++){
                int ny = y + dy[i], nx = x + dx[i];
                if(0 >= ny || ny > n || 0 >= nx || nx > n)continue;
                trees.addFirst(new Tree(ny, nx, 1));
            }
        }
    }

    private static void winter() {
        for(int i=1; i<=n ; i++){
            for(int j=1; j<=n ; j++){
                board[i][j] += add[i][j];
            }
        }
    }
}
