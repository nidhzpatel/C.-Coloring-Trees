import java.util.Scanner;

public class Problem1 {

    public static void main(String[] args) {

        // n->trees
        // m->colors available
        // k->beuty of tree we should not make more than this group
        int n,m,k;
        Scanner input = new Scanner(System.in);
        n = input.nextInt();
        m = input.nextInt();
        k = input.nextInt();
        System.out.println();
        //System.out.println(n+" "+m+" "+k);

        // now we take array for the tree colored or not colored
        int treeColor[] = new int[n];
        boolean uncoloredTree = false;
        boolean coloredTree = false;
        int count=0;
        for(int i = 0; i<n; i++){
          treeColor[i] = input.nextInt();

          // tree is uncolored when all the trees are color[i]==0
          if(treeColor[i]!=0){
              count++;
          }
          //  System.out.print(treeColor[i]+" ");
        }
        System.out.println();

        //setting flag
        if(count == n){
            coloredTree = true;
        }
       else{
            uncoloredTree = true;
        }

        // painting cost
        int cost[][] = new int[n][m];
        for(int i = 0; i<n;i++){
            for(int j = 0; j<m;j++)
            {
                cost[i][j]=input.nextInt();
                //System.out.print(cost[i][j]+" ");
            }
            //System.out.println();
        }
        System.out.println();

        double answer = Double.POSITIVE_INFINITY;

        if(uncoloredTree)
        {
            answer=coloringTreesWithbeauty(n,m,k,treeColor,cost);
            System.out.println(answer);
        }
        else{
            checkforBeauty(n,k,treeColor);
        }
            answer = coloringTreesWithbeauty(n,m,k,treeColor,cost);
            System.out.println(answer);


    }

    private static void checkforBeauty(int n,int k, int[] treeColor) {
        int beauty =1;
        for(int i = 1;i < n;i++){
            if(treeColor[i]!=treeColor[i-1]){
                beauty++;
            }
        }
        if(beauty == k){
            System.out.println(0);
        }
        else {
            System.out.println("-1");
        }
    }

    private static double coloringTreesWithbeauty(int n, int m, int k, int[] treeColor, int[][] cost) {
        double ans = Double.POSITIVE_INFINITY;
        // dp matrix denotes first n trees paint with m beauty and by using k color 1st dimensional is tree
        double dp[][][] = new double[n+1][k+1][m+1];

        // intialize dp matrix with infinity
        for(int i=0;i<n+1;i++)
        {
            for(int j=0;j<k+1;j++)
            {
                for(int l=0;l<m+1;l++)
                {
                    dp[i][j][l]=Double.POSITIVE_INFINITY;
                }
            }
        }

        // we check first tree is colored or not if not colored then we set the cost
        // otherwise it is 0 cost
        if(treeColor[0]==0)
        {
            for(int i=0;i<m;i++) dp[1][1][i+1]=cost[0][i];
        }
        else dp[1][1][treeColor[0]]=0;

        // now we color tree
        for(int i=2;i<=n;i++)
        {
            for(int j=1;j<=k;j++)
            {
                if(treeColor[i-1]==0)
                {
                    for(int K=1;K<=m;K++)
                    {
                        // we color tree with same as before so beauty will be same
                        dp[i][j][K]=Math.min(dp[i][j][K],dp[i-1][j][K]+cost[i-1][K-1]);
                        for(int K1=1;K1<=m;K1++)
                        {
                            if(K1==K) continue;
                            // we color tree with not same as before so beauty will be compared to j-1 and colour K1
                            dp[i][j][K]=Math.min(dp[i][j][K],dp[i-1][j-1][K1]+cost[i-1][K-1]);
                        }
                    }
                }
                else
                {
                    dp[i][j][treeColor[i-1]]=Math.min(dp[i][j][treeColor[i-1]],dp[i-1][j][treeColor[i-1]]);

                    for(int K1=1;K1<=m;K1++)
                    {
                        if(K1==treeColor[i-1]) continue;
                        // tree color is not same as before so beauty will be compared to j-1.
                        dp[i][j][treeColor[i-1]]=Math.min(dp[i][j][treeColor[i-1]],dp[i-1][j-1][K1]);
                    }

                }
            }
        }

        ans=Double.POSITIVE_INFINITY;

        for(int i=1;i<=m;i++) ans=Math.min(ans,dp[n][k][i]);

        if(ans==Double.POSITIVE_INFINITY) ans=-1;

        return ans;
    }
}
