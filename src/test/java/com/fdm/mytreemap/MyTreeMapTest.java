package com.fdm.mytreemap;

import static org.junit.Assert.*;

import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;

public class MyTreeMapTest {
	private MyTreeMap myTree;
	private BinaryTreeMap btmap;

	@Before
	public void setup() {
		myTree = new MyTreeMap();
		btmap = new BinaryTreeMap();
	}

	@Test
	public void empty_Tree_return_0_size() {
		int size = myTree.size();
		assertEquals(size, 0);
	}

	@Test
	public void empty_Tree_isEmpty_return_true() {
		boolean result = myTree.isEmpty();
		assertTrue(result);
	}

	@Test
	public void compare_treeMap_with_int_key_3412_should_be_equal() {
		TreeMap<Integer, String> prebuild = new TreeMap();

		prebuild.put(3, "two");
		prebuild.put(4, "two");
		prebuild.put(1, "one");
		prebuild.put(2, "two");
		myTree.put(3, "two");
		myTree.put(4, "two");
		myTree.put(1, "one");
		myTree.put(2, "two");
		// System.out.println(prebuild.toString());
		System.out.println(myTree.toString());
		assertEquals(prebuild.toString(), myTree.toString());
	}

	@Test
	public void compare_treeMap_with_int_key_3124_should_be_equal() {
		TreeMap<Integer, String> prebuild = new TreeMap();

		prebuild.put(3, "two");
		prebuild.put(1, "one");
		prebuild.put(2, "two");
		// prebuild.put(4, "two");
		myTree.put(3, "two");
		myTree.put(1, "one");
		myTree.put(2, "two");
		// myTree.put(4, "two");
		// System.out.println(prebuild.toString());
		System.out.println(myTree.toString());
		assertEquals(prebuild.toString(), myTree.toString());
	}

	/*
	 * Test Case for binary tree
	 */
	@Test
	public void compare_btMap_with_int_key_3124_should_be_equal() {
		TreeMap<Integer, String> prebuild = new TreeMap();
		prebuild.put(3, "two");
		prebuild.put(1, "one");
		prebuild.put(2, "two");
		prebuild.put(4, "two");
		btmap.put(3, "two");
		btmap.put(1, "one");
		btmap.put(2, "two");
		btmap.put(4, "two");
		assertEquals(prebuild.toString(), btmap.toString());
	}

	@Test
	public void remove_leaf_compare_btMap_with_int_key_312_should_be_equal() {
		TreeMap<Integer, String> prebuild = new TreeMap();
		prebuild.put(3, "two");
		prebuild.put(1, "one");
		prebuild.put(2, "two");
		prebuild.put(4, "two");
		prebuild.remove(4);
		btmap.put(3, "two");
		btmap.put(1, "one");
		btmap.put(2, "two");
		btmap.put(4, "two");
		btmap.remove(4);
		System.out.println(btmap.contains(4));
		System.out.println(btmap.toString());
		assertEquals(btmap.toString(), prebuild.toString());
	}

}
