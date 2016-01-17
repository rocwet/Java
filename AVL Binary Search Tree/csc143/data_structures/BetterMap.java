package csc143.data_structures;

/**
 * A subinterface of BasicMap for Assignment 11. 
 * This interface enhances the functionality of the 
 * map by providing additional standard map operations.
 * @author Dan
 */
public interface BetterMap<K, V> extends BasicMap<K, V> {
    
    /**
     * Returns true if the current map contains one or more
     * keys mapped to the specified value.
     * @param value The value whose presence in the current 
     * map is being tested.
     * @return true if the current map contains one or more
     * keys mapped to the specified value.
     */
    public boolean containsValue(Object value);
    
    /**
     * Remove the mapping for the specified key from this map
     * if present. Returns the value to which the specified key
     * was mapped, null if the current map contains no mapping 
     * for the specified key.
     * @param key The key whose mapping is to be removed from 
     * the map.
     * @return The value associated with the key, or null if the
     * map contained no mapping for the key.
     */
    public V remove(Object key);
    
    /**
     * Compares the specified object with the current map for equality.
     * Returns true if the specified object is a map (BasicMap or 
     * BetterMap) and the two maps contain the same key-value mappings.
     * @param o The object to compare to the current map
     * @return true if the specified object is equal to the current map
     */
    public boolean equals(Object o);
    
    /**
     * Returns a hash code for the current map. Recall that if 
     * obj1.equals(obj2) then obj1.hashCode() == obj2.hashCode().
     * @return The hash code for the current map.
     */
    public int hashCode();
    
}

