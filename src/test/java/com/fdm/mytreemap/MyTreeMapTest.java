package com.fdm.mytreemap;
import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

public class MyTreeMapTest {
	private MyTreeMap myTree;
	@Before
	public void setup(){
		myTree = new MyTreeMap();
	}
	
	@Test
	public void empty_Tree_return_0_size(){
		int size = myTree.size();
		assertEquals(size,0);
	}
	@Test
	public void empty_Tree_isEmpty_return_true(){
		boolean result = myTree.isEmpty();
		assertTrue(result);
	}
	@Test
	public void compare_treeMap_with_int_key_string_value_should_be_equal(){
		TreeMap<Integer,String> prebuild = new TreeMap();
		prebuild.put(1, "one");
		myTree.put(1, "one");
			
		assertEquals(prebuild.toString(),myTree.toString());
	}
	
}
