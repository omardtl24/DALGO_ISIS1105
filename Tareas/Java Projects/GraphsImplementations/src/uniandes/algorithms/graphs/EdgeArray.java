package uniandes.algorithms.graphs;

import java.util.Arrays;

/**
 * Inner class representing a key for an edge using an array of two integers.
 * This key is useful for identifying edges in a map or other data structures.
 */
public class EdgeArray {
	 // Edge represented as a pair of integers (nodes)
    private final int[] key; 

    /**
     * Constructor for the EdgeArray class.
     * Ensures that the edge contains exactly two elements (representing the two nodes).
     * 
     * @param key Array representing the edge (two nodes).
     * @throws Exception if the array length is not 2.
     */
    public EdgeArray(int[] key) throws Exception {
        if (key.length != 2) throw new Exception("Invalid edge");
        this.key = key;
    }
    
    public int[] getEdge() {
    	return key;
    }

    /**
     * Checks if this EdgeArray is equal to another object.
     * The edges are considered equal if their node pairs are the same.
     * 
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EdgeArray)) return false;
        EdgeArray other = (EdgeArray) obj;
        
        return Arrays.equals(this.key, other.key);
    }

    /**
     * Generates a hash code for the EdgeArray.
     * This is used when storing the edge in hash-based data structures like HashMap.
     * 
     * @return Hash code based on the edge array.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(key);
    }
}
