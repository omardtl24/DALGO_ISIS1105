public class ParticionArray {

    private int [] sets;
    private int n;

    public Particion(int n){
        sets = new int[n];
        for(int i=0;i < n;i++)
            sets[i] = i;
        this.n = n;
    }

    public int find(int v){
        return sets[v];
    }

    public boolean sameSubset(int v1, int v2){
        return find(v1)==find(v2);
    }

    public void union(int v1, int v2){
        int s1 = find(v1);
        int s2 = find(v2);
        if(s1==s2) return;
        int s = Math.min(s1,s2);

        for(int i=0; i<n ; i++){
            if (sets[i] == s1 || sets[i] == s2){
                sets[i] = s;
            }
        }
    }
}