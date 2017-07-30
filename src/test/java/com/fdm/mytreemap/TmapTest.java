package com.fdm.mytreemap;

import static org.junit.Assert.*;

import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;
public class TmapTest {
	private TMap myMap;
	private TreeMap prebuild;
	@Before
	public void setup(){
		myMap = new TMap();
		prebuild = new TreeMap();
	}
	
	@Test
	public void empty_Tree_return_0_size() {
		int size = myMap.size();
		assertEquals(size, 0);
	}
	@Test
	public void empty_Tree_isEmpty_return_true() {
		boolean result = myMap.isEmpty();
		assertTrue(result);
	}
	@Test
	public void if_contain_key_as_root_return_true() {
		myMap.put("Key","Value");
		boolean result = myMap.contains("Key");
		assertTrue(result);
	}
	@Test
	public void if_doesnt_contain_key_as_root_return_false() {
		boolean result = myMap.contains("Key");
		assertFalse(result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void if_contain_key_which_is_not_root_return_true() {
		myMap.put(0,"Value");
		myMap.put(1,"Value1");
		myMap.put(2,"Value2");
		boolean result = myMap.contains(1);
		assertTrue(result);
	}
	//test rotate right
	@Test
	public void after_rotate_right_Node1_should_be_black() throws TMapException {
		myMap.put(2,"Value");
		myMap.put(1,"Value1");
		myMap.put(0,"Value2");
		boolean color = myMap.getColor(1);
		assertTrue(color==false);
	}
	@Test
	public void after_rotate_right_color_of_Node210_should_be_all_black() throws TMapException {
		myMap.put(2,"Value");
		myMap.put(1,"Value1");
		myMap.put(0,"Value2");
		assertTrue(myMap.getColor(2)==false);
		assertTrue(myMap.getColor(1)==false);
		assertTrue(myMap.getColor(0)==false);
	}
	@Test
	public void prebuild_TreeMap_and_myTeeMap_should_return_same_toString(){
		myMap.put(2,"Value");
		myMap.put(1,"Value1");
		myMap.put(0,"Value2");
		prebuild.put(2,"Value");
		prebuild.put(1,"Value1");
		prebuild.put(0,"Value2");
		assertEquals(myMap.toString(),prebuild.toString());
	}
	//test rotate left
	@Test
	public void after_rotate_left_Node1_should_be_black() throws TMapException {
		myMap.put(0,"Value");
		myMap.put(1,"Value1");
		myMap.put(2,"Value2");
		boolean color = myMap.getColor(1);
		assertTrue(color==false);
	}
	@Test
	public void after_rotate_right_color_of_Node012_should_be_all_black() throws TMapException {
		myMap.put(0,"Value");
		myMap.put(1,"Value1");
		myMap.put(2,"Value2");
		assertTrue(myMap.getColor(2)==false);
		assertTrue(myMap.getColor(1)==false);
		assertTrue(myMap.getColor(0)==false);
	}
	@Test
	public void On_rotate_left_prebuild_TreeMap_and_myTeeMap_should_return_same_toString(){
		myMap.put(0,"Value");
		myMap.put(1,"Value1");
		myMap.put(2,"Value2");
		prebuild.put(0,"Value");
		prebuild.put(1,"Value1");
		prebuild.put(2,"Value2");
		assertEquals(myMap.toString(),prebuild.toString());
	}
	
	// test for remove
	@Test
	public void after_delete_the_only_should_return_isEmpty_true(){
		myMap.put(0,"Value");
		myMap.remove(0);
		assertTrue(myMap.isEmpty());
	}
	@Test
	public void On_deleteMin_the_min_key_should_be_remove_and_return_contains_false(){
		myMap.put(0,"Value");
		myMap.put(1,"Value1");
		myMap.deleteMin();
		assertFalse(myMap.contains(0));
	}
	@Test
	public void after_remove_same_key_on_both_trees_should_return_same_size(){
		myMap.put(0,"Value");
		myMap.put(1,"Value1");
		myMap.put(2,"Value2");
		prebuild.put(0,"Value");
		prebuild.put(1,"Value1");
		prebuild.put(2,"Value2");
		prebuild.remove(2);
		myMap.remove(2);
		assertEquals(myMap.size(),prebuild.size());
	}
	@Test
	public void after_remove_same_key_on_both_trees_should_return_same_toString(){
		myMap.put(0,"Value");
		myMap.put(1,"Value1");
		myMap.put(2,"Value2");
		prebuild.put(0,"Value");
		prebuild.put(1,"Value1");
		prebuild.put(2,"Value2");
		prebuild.remove(2);
		myMap.remove(2);
		assertEquals(myMap.toString(),prebuild.toString());
	}
}
