package com.fdm.mytreemap;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MyTreeMap<K extends Comparable<K>, V> {
	/*
	 * at most one null
	 * 
	 * 
	 * http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html 1) Every
	 * node has a color either red or black.
	 * 
	 * 2) Root of tree is always black.
	 * 
	 * 3) There are no two adjacent red nodes (A red node cannot have a red
	 * parent or red child).
	 * 
	 * 4) Every path from root to a NULL node has same number of black nodes.
	 */

	// TODO: put remove size

	private int height;
	private int size;
	private Node<K, V> root;

	public MyTreeMap() {
		TreeMap tm = new TreeMap();

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
		return node.color == false ? true : false;
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

	private Node<K, V> put(Node<K, V> tempRoot, K key, V Value) {

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
