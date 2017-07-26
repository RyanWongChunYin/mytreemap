package com.fdm.mytreemap;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MyTreeMap<K extends Comparable<K>, V> {
	private Node<K, V> root;
	public MyTreeMap() {
	}
	private int size(Node<K, V> x) {
		if (x == null)
			return 0;
		return x.size;
	}
	private boolean isRed(Node<K, V> node) {
		// check is node red, if node is null return false
		if (node == null) {
			return false;
		}
		return node.color == true;
	}
	/*
	 * Public method for checking the Tree status
	 */
	public int size() {
		return size(root);
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
				node = node.leftChild;
			} else if (compared > 0) {
				node = node.rightChild;
			} else {
				return node.value;
			}
		}
		return null;
	}

	public boolean contains(K key) {
		return get(key) != null;
	}

	/*
	 * Tree Insertion
	 */
	public void put(K key, V value) {
		root = put(root, key, value);
		root.color = false; // set the root color to black
	}

	private Node<K, V> put(Node<K, V> tempRoot, K key, V value) {
		if(tempRoot == null){return new Node<K,V>(key,value,true,1);}
        int cmp = key.compareTo(tempRoot.key);
        // recursive search
        if      (cmp < 0) {tempRoot.leftChild  = put(tempRoot.leftChild,  key, value); }
        else if (cmp > 0) {tempRoot.rightChild = put(tempRoot.rightChild, key, value); }
        else {tempRoot.value   = value; }// update the value if key is existed
        
        // Check and fix the rule of RB tree:
        if (isRed(tempRoot.rightChild) && !isRed(tempRoot.leftChild)){
        	tempRoot = rotateLeft(tempRoot);
        }
        if (isRed(tempRoot.leftChild)  &&  isRed(tempRoot.leftChild.leftChild)){
        	tempRoot = rotateRight(tempRoot);
        }
        if (isRed(tempRoot.leftChild)  &&  isRed(tempRoot.rightChild)) {
        	flipColors(tempRoot);
        }
        // assign size
        tempRoot.size = size(tempRoot.leftChild) + size(tempRoot.rightChild) + 1;
		return tempRoot;
	}

	/*
	 * Tree modify methods
	 */
	private Node<K, V> rotateRight(Node<K, V> node) {
		Node<K, V> nextRoot = node.leftChild;
		node.leftChild = nextRoot.rightChild;
		nextRoot.rightChild = node;
		nextRoot.color = nextRoot.leftChild.color;
		nextRoot.leftChild.color = true;
		nextRoot.size = node.size;
		node.size = size(node.leftChild) + size(node.rightChild) + 1;
		return nextRoot;
	}
	private Node<K,V> rotateLeft(Node<K,V> node){
		Node<K,V> nextRoot = node.rightChild;
		node.rightChild = nextRoot.leftChild;
		nextRoot.leftChild = node;
		// change color and update size
		nextRoot.color = nextRoot.leftChild.color;
		nextRoot.leftChild.color = true;
		nextRoot.size = node.size;
		node.size = size(node.leftChild) + size(node.rightChild) + 1;
		return nextRoot;
	}
	private void flipColors(Node<K,V> node){
		//Flip node color and children's to opposite color
		node.color = !node.color;
		node.leftChild.color = !node.leftChild.color;
		node.rightChild.color = !node.rightChild.color;
	}

	/*
	 * Custom Node for the TreeMap usage
	 */

	private class Node<K extends Comparable<K>, V> {
		/*
		 * color true -- red color false -- black
		 */
		private boolean color = true;
		private Node<K, V> parent = null;
		private K key = null;
		private V value = null;
		private Node<K, V> leftChild = null;
		private Node<K, V> rightChild = null;
		private int size;

		Node(K key, V value) {
			this.key = key;
			this.value = value;
		}

		Node(K key, V value, boolean color, int size) {
			this.key = key;
			this.value = value;
			this.color = color;
			this.size = size;
		}

		Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

		Node<K, V> getLeftChild() {
			return leftChild;
		}

		Node<K, V> getRightChild() {
			return rightChild;
		}

		// Can only parent node add child node; cannot child node set parent

		void addleftChild(Node<K, V> child) {
			child.parent = this;
			this.leftChild = child;
		}

		void addrightChild(Node<K, V> child) {
			child.parent = this;
			this.rightChild = child;
		}

		K getKey() {
			return this.key;
		}

		V getValue() {
			return this.value;
		}

		void changeColor() {
			this.color = this.color == false ? true : false;
		}

	}


	
}
