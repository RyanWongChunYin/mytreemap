package com.fdm.mytreemap;

public class TMap<K extends Comparable<K>, V> {
	private static boolean RED = true;
	private static boolean BLACK = false;
	private StringBuffer sb;
	Node<K, V> root = null;

	@Override
	public String toString() {
		sb = new StringBuffer();
		toString(root);
		return "{" + sb.toString().subSequence(0, sb.toString().length() - 2) + "}";
	}

	private void toString(Node<K, V> tempRoot) {
		if (tempRoot != null) {
			toString(tempRoot.left);
			sb.append(tempRoot.key + "=" + tempRoot.value);
			sb.append(", ");
			toString(tempRoot.right);
		}

	}
	public String getDetails() {
		sb = new StringBuffer();
		toString(root);
		if (root.left != null)
			System.out.println("left sub tree" + root.left.size);
		if (root.right != null)
			System.out.println("right sub tree" + root.right.size);
		return "{" + sb.toString().subSequence(0, sb.toString().length() - 2) + "}";
	}

	private static class Node<K, V> {
		private K key = null;
		private V value = null;
		private boolean color = BLACK;
		private Node<K, V> left = null;
		private Node<K, V> right = null;
		private Node<K, V> parent = null;
		private int size = 0;

		public Node(K key, V value, boolean color, int size) {
			super();
			this.key = key;
			this.value = value;
			this.color = color;
			this.size = size;
		}

	}

	/*
	 * Status related method
	 */
	public int size() {
		return size(root);
	}

	private int size(Node<K, V> root) {
		if (root == null)
			return 0;
		return root.size;
	}

	public boolean isEmpty() {
		return root == null;
	}

	/*
	 * Tree Search
	 */
	public V get(K key) {
		return get(root, key);
	}

	private V get(Node<K, V> node, K key) {
		// Start searching from the root node
		while (node != null) {
			int compared = key.compareTo(node.key);
			if (compared < 0) {
				node = node.left;
			} else if (compared > 0) {
				node = node.right;
			} else {
				return node.value;
			}
		}
		return null;
	}

	public boolean contains(K key) {
		return get(key) != null;
	}
	public boolean getColor(K key) throws TMapException {
		if(!contains(key)) throw new TMapException("Key not exist");
		return getColor(root,key);
	}
	private boolean getColor(Node<K, V> node, K key) {
		while (node != null) {
			int compared = key.compareTo(node.key);
			if (compared < 0) {
				node = node.left;
			} else if (compared > 0) {
				node = node.right;
			} else {
				return node.color;
			}
		}
		return false;
	}
	// serach and return the min key
    public K min() {
        return min(root).key;
    } 

    // the smallest key in subtree rooted at x; null if no such key
    private Node<K,V> min(Node<K,V> tempNode) { 
    	
        if (tempNode.left == null) {
        	return tempNode; 
        }
        else{
        	return min(tempNode.left); 
        }
    } 
	/*
	 * Tree insertion
	 */
	public void put(K key, V value) {

		root = put(root,key,value);
		root.color=BLACK;
	}

	private Node<K, V> put(Node<K, V> tempRoot, K key, V value) {
		
		if(tempRoot == null){
			return new Node<K,V>(key,value,RED,1);
		}
		int cmp = key.compareTo(tempRoot.key);
		if(cmp<0){
			tempRoot.left = put(tempRoot.left,key,value);
			tempRoot.left.parent = tempRoot;
		}else if(cmp>0){
			tempRoot.right = put(tempRoot.right,key,value);
			tempRoot.right.parent = tempRoot;
		}else{
			tempRoot.value = value;
		}
		//rotations 
		//rules are referencing http://algs4.cs.princeton.edu/33balanced/
        if (isRed(tempRoot.right) && !isRed(tempRoot.left)){
        	tempRoot = rotateLeft(tempRoot);
        }
        if (isRed(tempRoot.left)  &&  isRed(tempRoot.left.left)){
        	tempRoot = rotateRight(tempRoot);
        }
        if (isRed(tempRoot.left)  &&  isRed(tempRoot.right)){
        	flipColors(tempRoot);
        }
		//assign size
		tempRoot.size = size(tempRoot.left) + size(tempRoot.right) + 1;
		return tempRoot;
	}
	/*
	 *  Tree deletion
	 */
	public void remove(K key) {
		if (!contains(key)) return;
		// if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = remove(root, key);
        if (!isEmpty()) root.color = BLACK;
	}
	
	private Node<K, V> remove(Node<K, V> tempRoot, K key) {
		if (key.compareTo(tempRoot.key) < 0)  {
            if (!isRed(tempRoot.left) && !isRed(tempRoot.left.left))
            	tempRoot = moveRedLeft(tempRoot);
            tempRoot.left = remove(tempRoot.left, key);
        }
        else {
            if (isRed(tempRoot.left))
            	tempRoot = rotateRight(tempRoot);
            if (key.compareTo(tempRoot.key) == 0 && (tempRoot.right == null))
                return null;
            if (!isRed(tempRoot.right) && !isRed(tempRoot.right.left))
            	tempRoot = moveRedRight(tempRoot);
            if (key.compareTo(tempRoot.key) == 0) {
                Node<K,V> x = min(tempRoot.right);
                tempRoot.key = x.key;
                tempRoot.value = x.value;
                tempRoot.right = deleteMin(tempRoot.right);
            }
            else tempRoot.right = remove(tempRoot.right, key);
        }
        return balance(tempRoot);
	}
    public void deleteMin() {
        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    // delete the key-value pair with the minimum key from root
    private Node<K,V> deleteMin(Node<K,V> tempNode) { 
        if (tempNode.left == null)
            return null;
        //if both child and grandchild are black
        if (!isRed(tempNode.left) && !isRed(tempNode.left.left))
            tempNode = moveRedLeft(tempNode);

        tempNode.left = deleteMin(tempNode.left);
        return balance(tempNode);
    }
	/*
	 * Utils for tree
	 */
	private Node<K,V> rotateRight(Node<K,V> tempNode){
		Node<K,V> nextRoot = tempNode.left;
		nextRoot.parent = tempNode.parent;
		tempNode.left = nextRoot.right;
		if (tempNode.left!= null) tempNode.left.parent = tempNode;
		nextRoot.right = tempNode;
		tempNode.parent = nextRoot;
		//assign color and size
		nextRoot.color = nextRoot.right.color;
		nextRoot.right.color = RED;
		nextRoot.size = tempNode.size;
		tempNode.size = size(tempNode.left)+size(tempNode.right)+1;
		return nextRoot;
	}
	private Node<K,V> rotateLeft(Node<K,V> tempNode){
		Node<K,V> nextRoot = tempNode.right;
		nextRoot.parent = tempNode.parent;
		tempNode.right = nextRoot.left;
		if (tempNode.right!= null) tempNode.right.parent = tempNode;
		nextRoot.left = tempNode;
		tempNode.parent = nextRoot;
		//assign color and size
		nextRoot.color = nextRoot.left.color;
		nextRoot.left.color = RED;
		nextRoot.size = tempNode.size;
		tempNode.size = size(tempNode.left)+size(tempNode.right)+1;
		return nextRoot;
	}
	private boolean isRed(Node<K,V> tempNode){
		return tempNode==null?BLACK:tempNode.color==RED;
	}
    private void flipColors(Node<K,V> tempNode) {
        tempNode.color = !tempNode.color;
        tempNode.left.color = !tempNode.left.color;
        tempNode.right.color = !tempNode.right.color;
    }


    private Node<K,V> moveRedLeft(Node<K,V> node) {

        flipColors(node);
        if (isRed(node.right.left)) { 
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node<K,V> moveRedRight(Node<K,V> node) {
        flipColors(node);
        if (isRed(node.left.left)) { 
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    // like insertion change the color and rotate again
    private Node<K,V> balance(Node<K,V> node) {

        if (isRed(node.right)) {
        	node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)){
        	node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)){
        	flipColors(node);
        }

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }


}
