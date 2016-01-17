package csc143.data_structures;

/**
 * MyBasicMap class is an AVL BST Map.  It is a data structure that will allow the user put keys and values in.
 * @author Minhhue H. Khuu
 * @version Assignment 11: Basic Map STANDARD
 */
public class MyBasicMap<K extends Comparable<K> ,E extends Comparable<E>> implements BasicMap<K, E> {
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                                            FIELDS
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private Node<K,E> root;
  private int size;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                                       CONSTRUCTOR
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Constructs a new Tree Map, intalizes root to null and size to 0.
   */
  public MyBasicMap() {
    root = null;
    size = 0;
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                         PUBLIC OVERRIDDEN METHODS
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Associated the specified value with the specified key in the current map.
   * @param key The key with which the specified value is to be associated.
   * @param value The value to be associated with the specified key.
   */
  @Override
  public void put(K key, E value) { 
    if( key == null || value == null ) {
      throw new IllegalArgumentException("YOU CANNOT ADD NULLS");
    }
    // insert a new node with the element
    root = insert(key, value, root);
    size++;
    
  }
  
  /**
   * Returns the value to which the specified key is mapped, or <tt>null</tt> if the specified key is not mapped.
   * @param key The key whose associated value is to be returned
   * @return The value to which the specified key is mapped, or <tt>null</tt> if the specified key is not mapped.
   */
  @Override
  @SuppressWarnings("unchecked")
  public E get(Object key) {
    // cast key to compare, if it cant be casted then return null
    try {
      K k = (K) key;
      if (key == null) return null;
      // a reference to the root
      Node<K, E> current = root;
      // goes through until key matches to a traversed key
      while ((current.key).compareTo(k) != 0) {
        // move left
        if ((current.key).compareTo(k) > 0) {
          current = current.left;
          // move right
        } else if ((current.key).compareTo(k) < 0) {
          current = current.right;
        }
        // no match
        if (current == null) return null;
      }
      // the matched item with the key
      return current.item;
    } catch (ClassCastException ex) {
      return null;
    }
  }
  
  
  /**
   * Returns true if the current map contains a mapping for the
   * specified key.
   * @param key The key whose presence in the map is being tested.
   * @return true if this map contains a mapping for the specified
   * key.
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean containsKey(Object key) {
    // cast key to compare, if it cant be casted return false
    try {
      K k = (K) key;
      if (key == null) return false;
      // a reference to the root
      Node<K, E> current = root;
      // goes through until key matches to a traversed key
      while ((current.key).compareTo(k) != 0) {
        // move to the left
        if ((current.key).compareTo(k) > 0) {
          current = current.left;
          // move to the right
        } else if ((current.key).compareTo(k) < 0) {
          current = current.right;
        }
        // no match
        if (current == null) return false;
      }
      // match
      return true;
    } catch (ClassCastException ex) {
      return false;
    }
  }
  
  /**
   * Returns true if the current map contains no key-value mappings.
   * @return true if the current map contains no key-value mappings.
   */
  @Override
  public boolean isEmpty() {
    // if root is null
    if ( root == null) {
      return root == null;
      // if root not null, checks key and item
    } else {
      return (root.key == null || root.item == null);
    }
  }
  
  /**
   * Removes all of the mappings from the current map.
   */
  @Override
  public void clear() {
    // resets root and size back to default
    root = null;
    size = 0;
  }
  
  /**
   * Returns the number of key-value mappings in this map
   * @return The number of key-value mappings in this map
   */
  @Override
  public int size() {
    return size;
  }
  
  /**
   * Returns a String representation of the contents of the set.
   * @return  the String representation of the set.
   */
  public String toString() {
    Node<K, E> current = root;
    return preOrder(current);
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                            PRIVATE HELPER METHODS
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * The insert method will insert a key, and item to a given node.
   * @param k The key
   * @param e The item
   * @param node The node that the key and item will be associated with
   * @return The node with the new key and item.
   */
  private Node<K, E> insert(K k, E e, Node<K, E> node) {
   
    // if the node is null put the new node with values in
    if( node == null ) {
      node = new Node<K, E>( e, k, null, null);
      // replace value of replicated keys
    } else if( k.compareTo (node.key) == 0) {
      node.key = k;
      node.item = e;
      size--;
      // left rotations
    } else if( k.compareTo( node.key ) < 0 ) {
      node.left = insert( k, e, node.left );
      if( height(node.left) - height(node.right) == 2 ) {
        if( k.compareTo( node.left.key ) < 0 ) {
          node = rotateWithLeftChild( node );
        } else {
          node = doubleWithLeftChild( node );
        } 
      }
      // right rotations
    } else if( k.compareTo( node.key ) > 0 ) {
      node.right = insert( k, e, node.right );
      if( height(node.right) - height(node.left) == 2 ) {
        if( k.compareTo( node.right.key ) > 0 ) {
          node = rotateWithRightChild( node );
        } else {
          node = doubleWithRightChild( node );
        }
      }
    }
    // update height
    node.height = max(height(node.left) , height(node.right)) + 1;
    return node;
  }
  
  /**
   * Rotate binary tree node with left child.
   * Update heights, then return new root.
   * @param k2 The node to be rotated.
   * @return The node to rotate.
   */
  private Node<K, E> rotateWithLeftChild( Node<K, E> k2 ) {
    // rotate
    Node<K, E> k1 = k2.left;
    k2.left = k1.right;
    k1.right = k2;
    // update heights
    k2.height = max( height(k2.left), height(k2.right) ) + 1;
    k1.height = max( height(k1.left), k2.height ) + 1;
    return k1;
  }
  
  /**
   * Rotate binary tree node with right child.
   * Update heights, then return new root.
   * @param k1 The node to be rotated.
   * @return The rotated node.
   */
  private Node<K, E> rotateWithRightChild( Node<K, E> k1 ) {
    // rotate
    Node<K, E> k2 = k1.right;
    k1.right = k2.left;
    k2.left = k1;
    // update heights
    k1.height = max( height(k1.left), height(k1.right) ) + 1;
    k2.height = max( height(k2.right), k1.height ) + 1;
    return k2;
  }
  
  /**
   * Double rotate binary tree node: first left child with its right child; then node k3 with new left child.
   * Update heights, then return new root.
   * @param k3 The node to be rotated.
   * @return The rotated node.
   */
  private Node<K, E> doubleWithLeftChild( Node<K, E> k3 ) {
    k3.left = rotateWithRightChild( k3.left );
    return rotateWithLeftChild( k3 );
  }
  
  /**
   * Double rotate binary tree node: first right child with its left child; then node k1 with new right child.
   * Update heights, then return new root.
   * @param k1 The node to be rotated.
   * @return The new node that is doubly rotated.
   */
  private Node<K, E> doubleWithRightChild( Node<K, E> k1 ) {
    k1.right = rotateWithLeftChild( k1.right );
    return rotateWithRightChild( k1 );
  }
  
  /**
   * Return maximum of lhs and rhs.
   * @param lhs Left height
   * @param rhs Right height
   * @return The larger height
   */
  private int max( int lhs, int rhs ) {
    return lhs > rhs ? lhs : rhs;
  }
  
  /**
   * Return the height of node t, or -1, if null.
   * @param t The node to see height.
   * @return The height of the given node.
   */
  private int height( Node<K, E> t ) {
    return t == null ? -1 : t.height;
  }
  
  /**
   * Creates a string from a given node in preOrder.
   * @param subTree a subTree that will be changed to a string in preOrder form.
   * @return A string of the subTree and all of its sub components in pre order.
   */
  private String preOrder(Node<K, E> subTree) {
    String s = "(";
    if (subTree.key != null) {
      s += subTree.key;
      s += ":" + subTree.item;
    } else {
      s += ")";
    }
    // to the left
    if (subTree.left != null) {
      s += " " + preOrder(subTree.left) ;
    } else {
      s += " ()";
    }
    // to the right
    if (subTree.right != null) {
      s += " " + preOrder(subTree.right) ;
    } else {
      s += " ()";
    }
    s += ")";
    return s;
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                                  NODE CLASS
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * The Node class will contains a generic object, and references to the left and right child as well as the parent.
   */
  class Node<K extends Comparable<K>, E extends Comparable<E>> {
    
    /** Generic object of the Node */
    public E item;
    /** Generic key of the Node */
    public K key;
    /** Reference to the left child */
    public Node<K, E> left;
    /** Reference to the left child */
    public Node<K, E> right;
    /** Height of the Node */
    public int height;
    
    /**
     * Constructs a node containing a generic object, reference to the left, right and parent nodes.
     * @param item The generic object the node will contain.
     * @param left The reference to the left child.
     * @param right The reference to the right child.
     */
    public Node(E item, K key, Node<K, E> left, Node<K, E> right) {
      this.item = item;
      this.left = left;
      this.right = right;
      this.key = key;
      height = 0;
    }
    
  }  // end of Node inner class
  
} // end of the class