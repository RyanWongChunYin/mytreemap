package com.fdm.mytreemap;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MyTreeMap<K extends Comparable<K>, V> {
	private Node<K, V> root;
	private StringBuffer sb;

	@Override
	public String toString() {
		sb = new StringBuffer();
		toString(root);
		if (root.leftChild != null)
			System.out.println("left tree" + root.leftChild.size);
		if (root.rightChild != null)
			System.out.println("right tree" + root.rightChild.size);
		return "{" + sb.toString().subSequence(0, sb.toString().length() - 2) + "}";
	}

	private void toString(MyTreeMap<K, V>.Node<K, V> tempRoot) {
		if (tempRoot != null) {
			toString(tempRoot.leftChild);
			sb.append(tempRoot.key + "=" + tempRoot.value);
			sb.append(", ");
			toString(tempRoot.rightChild);
		}

	}

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
		System.out.println("putting..." + key);
		root = put(root, key, value);
		setNodeParent(root);
		root.color = false; // set the root color to black
	}

	private void setNodeParent(MyTreeMap<K, V>.Node<K, V> tempRoot) {
		if (tempRoot.leftChild != null) {
			tempRoot.addleftChild(tempRoot.leftChild);
			setNodeParent(tempRoot.leftChild);
		}
		if (tempRoot.rightChild != null) {
			tempRoot.addrightChild(tempRoot.rightChild);
			setNodeParent(tempRoot.rightChild);
		}
	}

	private Node<K, V> put(Node<K, V> tempRoot, K key, V value) {
		
		Node<K, V> retNode = null;
		if (tempRoot == null) {
			return new Node<K, V>(key, value, true, 1);
		}

		int cmp = key.compareTo(tempRoot.key);
		// recursive search
		if (cmp < 0) {
			retNode = put(tempRoot.leftChild, key, value);
			System.out.println(retNode.key + " return node");

			tempRoot.leftChild = retNode;
			tempRoot.leftChild.parent = tempRoot;

		} else if (cmp > 0) {
			tempRoot.rightChild = put(tempRoot.rightChild, key, value);
			tempRoot.rightChild.parent = tempRoot;

		} else {
			tempRoot.value = value; // update the value if key is existed
		}
		System.out.println("NODE will enter validate"+tempRoot.key);
		tempRoot = validate(tempRoot);
		System.out.println("after valid, temp = " + tempRoot.key);
		// assign size
		tempRoot.size = size(tempRoot.leftChild) + size(tempRoot.rightChild) + 1;

		return tempRoot;
	}

	private Node<K, V> validate(MyTreeMap<K, V>.Node<K, V> tempRoot) {
		// if tempRoot has parent --> has uncle
		if (tempRoot.parent != null) {

			// if right unlce is black
			if (isRed(tempRoot.parent.leftChild) && !isRed(tempRoot.parent.rightChild)) {
				if (tempRoot.rightChild != null && isRed(tempRoot.rightChild)) {
					System.out.println("rotating");
					tempRoot = rotateLeft(tempRoot);
					tempRoot.parent = tempRoot.leftChild.parent;
					tempRoot.leftChild.parent = tempRoot;
					tempRoot.parent.leftChild = tempRoot;

				}
				tempRoot = rotateRight(tempRoot.parent);
				tempRoot.parent = tempRoot.rightChild.parent;
				if (tempRoot.parent == null) root = tempRoot;
				tempRoot.rightChild.parent = tempRoot;
				System.out.println("after rot, temp = "+tempRoot.key);
				
			}
			// if left uncle is black
			else if (!isRed(tempRoot.parent.leftChild) && isRed(tempRoot.parent.rightChild)) {

				if (tempRoot.leftChild != null && isRed(tempRoot.leftChild)) {
					tempRoot = rotateRight(tempRoot);
				}
				tempRoot = rotateLeft(tempRoot.parent);

			}
			// if uncle is also red

			if (tempRoot.parent != null && isRed(tempRoot.parent.leftChild) && isRed(tempRoot.parent.rightChild)) {

				flipColors(tempRoot.parent);
			}

		}
		return tempRoot;
	}

	/*
	 * Tree modify methods
	 */
	private Node<K, V> rotateRight(Node<K, V> node) {
		Node<K, V> nextRoot = node.leftChild;
		node.leftChild = nextRoot.rightChild;
		nextRoot.rightChild = node;
		// change color and update size
		nextRoot.color = nextRoot.rightChild.color;
		nextRoot.leftChild.color = true;
		nextRoot.size = node.size;
		node.size = size(node.leftChild) + size(node.rightChild) + 1;
		return nextRoot;
	}

	private Node<K, V> rotateLeft(Node<K, V> node) {
		Node<K, V> nextRoot = node.rightChild;
		node.rightChild = nextRoot.leftChild;
		nextRoot.leftChild = node;
		// change color and update size
		nextRoot.color = nextRoot.leftChild.color;
		nextRoot.leftChild.color = true;
		nextRoot.size = node.size;
		node.size = size(node.leftChild) + size(node.rightChild) + 1;
		return nextRoot;
	}

	private void flipColors(Node<K, V> node) {
		// Flip node color and children's to opposite color
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

		Node(K key, V value, Node<K, V> parent, boolean color, int size) {
			this.key = key;
			this.value = value;
			this.parent = parent;
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

		Node<K, V> getParent() {
			return parent;
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
