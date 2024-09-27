public class Partition {

    private int [] parents;
    private int [] h;

    public Partition(int n){
        parents = new int[n];
        h = new int[n];
        for(int i=0;i < n;i++){
            parents[i] = i;
            h[i]=1;
        }   
    }

    public int find(int v){
        if (v==parents[v]) return v;
        int root = find(parents[v]);
        parents[v] = root;
        return root;
    }

    public boolean sameSubsets(int v1, int v2){
        return find(v1)==find(v2);
    }

    public void union(int v1, int v2){
        int s1 = find(v1);
        int s2 = find(v2);
        if(s1==s2) return;
        if(h[s1]<h[2]) parents[s1]=s2;
        parents[s2] = s1;
        if(h[s1]==h[s2]) h[s1]++;
    }
}