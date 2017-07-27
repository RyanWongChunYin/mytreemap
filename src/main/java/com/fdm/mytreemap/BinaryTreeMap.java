package com.fdm.mytreemap;

public class BinaryTreeMap<K extends Comparable<K>, V> {
	private Node<K, V> root;
	private StringBuffer sb;

	/*
	 * Custom Node for the TreeMap usage
	 */
	private class Node<K extends Comparable<K>, V> {

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

		Node(K key, V value, int size) {
			this.key = key;
			this.value = value;
			this.size = size;
		}

		Node(K key, V value, Node<K, V> parent, int size) {
			this.key = key;
			this.value = value;
			this.parent = parent;

			this.size = size;
		}

		Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
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

	}

	@Override
	public String toString() {
		sb = new StringBuffer();
		toString(root);
		return "{" + sb.toString().subSequence(0, sb.toString().length() - 2) + "}";
	}

	private void toString(BinaryTreeMap<K, V>.Node<K, V> tempRoot) {
		if (tempRoot != null) {
			toString(tempRoot.leftChild);
			sb.append(tempRoot.key + "=" + tempRoot.value);
			sb.append(", ");
			toString(tempRoot.rightChild);
		}
	}

	public BinaryTreeMap() {
		root = null;
	}

	private int size(Node<K, V> x) {
		if (x == null)
			return 0;
		return x.size;
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
	}

	private Node<K, V> put(Node<K, V> tempRoot, K key, V value) {
		if (tempRoot == null) {
			return new Node<K, V>(key, value, 1);
		}
		int cmp = key.compareTo(tempRoot.key);
		// recursive search
		if (cmp < 0) {
			tempRoot.leftChild = put(tempRoot.leftChild, key, value);
			tempRoot.leftChild.parent = tempRoot;
		} else if (cmp > 0) {
			tempRoot.rightChild = put(tempRoot.rightChild, key, value);
			tempRoot.rightChild.parent = tempRoot;
		} else {
			tempRoot.value = value; // update the value if key is existed
		}
		// assign size
		tempRoot.size = size(tempRoot.leftChild) + size(tempRoot.rightChild) + 1;

		return tempRoot;
	}

	public void remove(K key) {
		if (!contains(key))
			return;
		root = remove(root, key);
	}

	private Node<K, V> remove(Node<K, V> node, K key) {
		int compared = key.compareTo(node.key);
		if (compared < 0) {
			node.leftChild = remove(node.leftChild,key);
		} else if (compared > 0) {
			node.rightChild = remove(node.rightChild,key);
		} else {
            if (node.leftChild == null) return node.rightChild;
            else if (node.rightChild == null) return node.leftChild;
            node.key = minValue(root.rightChild);
            node.rightChild = remove(node.rightChild,node.key);
		}
		return node;

	}

	private K minValue(BinaryTreeMap<K, V>.Node<K, V> root) {
		K minv = root.key;
        while (root.leftChild != null)
        {
            minv = root.leftChild.key;
            root = root.leftChild;
        }
        return minv;
	}

}
