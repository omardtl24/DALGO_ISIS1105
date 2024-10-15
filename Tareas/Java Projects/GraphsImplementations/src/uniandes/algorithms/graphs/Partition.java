package uniandes.algorithms.graphs;

public class Partition {

    private int[] parents; // Array to hold the parent of each node
    private int[] h; // Array to hold the height of each node's tree

    /**
     * Initializes a partition for a set of n elements.
     * Each element starts in its own subset.
     * 
     * @param n The number of elements in the partition.
     */
    public Partition(int n) {
        parents = new int[n];
        h = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i; // Each node is its own parent
            h[i] = 1; // Initial height of each node is 1
        }
    }

    /**
     * Finds the root of the set that contains the specified element.
     * Implements path compression for efficiency.
     * 
     * @param v The element to find the root for.
     * @return The root element of the set containing v.
     */
    public int find(int v) {
        if (v == parents[v]) return v; // If v is its own parent, return it
        int root = find(parents[v]);  // Recursively find the root
        parents[v] = root; // Path compression
        return root;
    }

    /**
     * Determines whether two elements are in the same subset.
     * 
     * @param v1 The first element.
     * @param v2 The second element.
     * @return True if v1 and v2 are in the same subset, false otherwise.
     */
    public boolean sameSubsets(int v1, int v2) {
        return find(v1) == find(v2); // Check if both elements have the same root
    }

    /**
     * Unites the subsets that contain the two specified elements.
     * Uses union by height to maintain a balanced tree.
     * 
     * @param v1 The first element to unite.
     * @param v2 The second element to unite.
     */
    public void union(int v1, int v2) {
        int s1 = find(v1); // Find the root of the first element
        int s2 = find(v2); // Find the root of the second element
        if (s1 == s2) return; // If they are already in the same subset, do nothing

        // Union by height
        if (h[s1] < h[s2]) {
            parents[s1] = s2; // Make s2 the parent of s1
        } else if (h[s1] > h[s2]) {
            parents[s2] = s1; // Make s1 the parent of s2
        } else {
            parents[s2] = s1; // Make s1 the parent of s2
            h[s1]++; // Increase the height of the resulting tree
        }
    }
}
