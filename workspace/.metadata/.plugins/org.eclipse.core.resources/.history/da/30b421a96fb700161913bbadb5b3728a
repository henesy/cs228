package edu.iastate.cs228.hw5.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;

import edu.iastate.cs228.hw5.ABTreeMap;
import edu.iastate.cs228.hw5.ABTreeSet;
import edu.iastate.cs228.hw5.BSTNode;

/**
 * @author Yuxiang Zhang
 */
public class ABTreeTest {

	static final int n = 1000; // test size
	static final Object o = new Object();
	static final Random rand = new Random();

	/**
	 * @return Example on PDF page 3
	 */
	static ABTreeSet<Integer> getExampleA() {
		ABTreeSet<Integer> set = new ABTreeSet<>();
		set.addAll(Arrays.asList(5, 4, 2, 16, 10, 7, 14, 12, 15, 20));
		return set;
	}

	/**
	 * @return Example on PDF page 6
	 */
	static ABTreeSet<Integer> getExampleB() {
		ABTreeSet<Integer> set = new ABTreeSet<>();

		set.addAll(Arrays.asList(-5, 29, 5, 4, 2, 16, 10, 7, 14, 12, 15, 20));
		// add 15 nodes <= -6
		for (int i = 0; i < 15; i++)
			set.add(-6 - i);
		set.rebalance(set.getBSTNode(-6));

		// add 5 nodes >= 30
		for (int i = 0; i < 5; i++)
			set.add(30 + i);
		set.rebalance(set.getBSTNode(30));

		return set;
	}

	/**
	 * @return Example on PDF page 9
	 */
	static ABTreeSet<Integer> getExampleC() {
		ABTreeSet<Integer> set = new ABTreeSet<>();
		set.addAll(Arrays.asList(50, 30, 55, 25, 35, 53, 60, 10, 31, 37, 62, 20)); // BFS
		return set;
	}

	static int[] getRandomArray(int n) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = i;
		shuffle(arr);
		return arr;
	}

	static void shuffle(int[] arr) {
		for (int i = arr.length; i > 0; i--) {
			swap(arr, i - 1, rand.nextInt(i));
		}
	}

	static void swap(int[] arr, int i, int j) {
		if ((i ^ j) > 0) {
			arr[i] ^= arr[j];
			arr[j] ^= arr[i];
			arr[i] ^= arr[j];
		}
	}

	@Test
	public void test_add() {
		ABTreeSet<Integer> set = getExampleB();

		BSTNode<Integer> root5 = set.getBSTNode(5);
		assertEquals("[2, 4, 5, 7, 10, 12, 14, 15, 16, 20]", set.inorderList(root5).toString());
		assertEquals("[5, 4, 2, 16, 10, 7, 14, 12, 15, 20]", set.preorderList(root5).toString());

		set.remove(12);

		assertEquals("[2, 4, 5, 7, 10, 14, 15, 16, 20]", set.inorderList(root5).toString());
		assertEquals("[5, 4, 2, 16, 10, 7, 14, 15, 20]", set.preorderList(root5).toString());

		set.setSelfBalance(true);
		set.add(12); // add(12) should trigger a re-balance

		BSTNode<Integer> root29 = set.getBSTNode(29);
		assertEquals("[2, 4, 5, 7, 10, 12, 14, 15, 16, 20]", set.inorderList(root29.left()).toString());
		assertEquals("[10, 4, 2, 5, 7, 15, 12, 14, 16, 20]", set.preorderList(root29.left()).toString());
	}

	@Test
	public void test_containsKey() {
		ABTreeMap<Integer, Object> map = new ABTreeMap<>();

		assertFalse(map.containsKey(null));

		int[] arr = getRandomArray(n);

		for (int i : arr) {
			assertFalse(map.containsKey(i));
			map.put(i, o);
			assertTrue(map.containsKey(i));
		}

		shuffle(arr);
		for (int i : arr) {
			assertTrue(map.containsKey(i));
			map.remove(i);
			assertFalse(map.containsKey(i));
		}
	}

	@Test
	public void test_count() {
		ABTreeSet<Integer> set = getExampleB();
		// triangle on the left
		assertEquals(15, set.getBSTNode(-5).left().count());
		// triangle on the right
		assertEquals(5, set.getBSTNode(29).right().count());

		assertEquals(10, set.getBSTNode(29).left().count());
		assertEquals(32, set.root().count());
		assertEquals(32, set.size());

		Integer arrInt[] = new Integer[set.size()];
		set.toArray(arrInt);

		int[] arr = new int[set.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = arrInt[i];
		shuffle(arr);

		for (int i = arr.length - 1; i > 0; i--) {
			set.remove(arr[i]);
			assertEquals(i, set.root().count());
			assertEquals(i, set.size());
		}
		set.remove(arr[0]);
		assertEquals(null, set.root());
		assertEquals(0, set.size());
	}

	@Test
	public void test_get() {
		ABTreeMap<Integer, Integer> map = new ABTreeMap<>();
		TreeMap<Integer, Integer> m = new TreeMap<>();

		for (int i = 0; i < n; i++) {
			int key = rand.nextInt(n), value = rand.nextInt(n);

			map.put(key, value);
			m.put(key, value);

			assertEquals(m.get(i), map.get(i)); // can return null
		}
	}

	@Test
	public void test_keySet() {
		ABTreeSet<Integer> set = getExampleA();
		ABTreeMap<Integer, Object> map = new ABTreeMap<>();

		for (BSTNode<Integer> n : set.preorderList(set.root()))
			map.put(n.data(), o);

		List<BSTNode<Integer>> inorderList = map.keySet().inorderList(map.keySet().root());
		assertEquals("[2, 4, 5, 7, 10, 12, 14, 15, 16, 20]", inorderList.toString());
	}

	@Test
	public void test_put() {
		ABTreeMap<Integer, Integer> map = new ABTreeMap<>();
		TreeMap<Integer, Integer> m = new TreeMap<>();

		for (int i = 0; i < n; i++) {
			int key = rand.nextInt(n), value = rand.nextInt(n);

			map.put(key, value);
			m.put(key, value);

			assertEquals(m.containsKey(key), map.containsKey(key));
			assertEquals(m.size(), map.size());
		}
	}

	@Test
	public void test_rebalance() {
		ABTreeSet<Integer> set = getExampleA();

		set.rebalance(set.root());

		assertEquals("[2, 4, 5, 7, 10, 12, 14, 15, 16, 20]", set.inorderList(set.root()).toString());
		assertEquals("[10, 4, 2, 5, 7, 15, 12, 14, 16, 20]", set.preorderList(set.root()).toString());
	}

	@Test
	public void test_remove() {
		ABTreeSet<Integer> set;

		set = getExampleA();
		assertTrue(set.remove(5));
		assertFalse(set.remove(5));
		assertEquals("[7, 4, 2, 16, 10, 14, 12, 15, 20]", set.preorderList(set.root()).toString());

		// remove leaf
		{
			set = getExampleA();
			assertTrue(set.remove(2));
			assertFalse(set.remove(2));
			assertEquals("[5, 4, 16, 10, 7, 14, 12, 15, 20]", set.preorderList(set.root()).toString());

			set = getExampleA();
			assertTrue(set.remove(20));
			assertFalse(set.remove(20));
			assertEquals("[5, 4, 2, 16, 10, 7, 14, 12, 15]", set.preorderList(set.root()).toString());
		}
		// remove node with one child
		{
			set = getExampleA();
			assertTrue(set.remove(4));
			assertFalse(set.remove(4));
			assertEquals("[5, 2, 16, 10, 7, 14, 12, 15, 20]", set.preorderList(set.root()).toString());
		}
		// remove node with two children
		{
			set = getExampleA();
			assertTrue(set.remove(5));
			assertFalse(set.remove(5));
			assertEquals("[7, 4, 2, 16, 10, 14, 12, 15, 20]", set.preorderList(set.root()).toString());

			set = getExampleA();
			set.rebalance(set.root());
			assertTrue(set.remove(10));
			assertFalse(set.remove(10));
			assertEquals("[12, 4, 2, 5, 7, 15, 14, 16, 20]", set.preorderList(set.root()).toString());
		}
		// remove with iterator
		{
			set = getExampleA();
			Iterator<Integer> iter = set.iterator();
			try {
				iter.remove();
			} catch (Exception e) {
				assertEquals(IllegalStateException.class, e.getClass());
			}
			while (iter.hasNext()) {
				iter.next();
				iter.remove();
			}
			try {
				iter.remove();
			} catch (Exception e) {
				assertEquals(IllegalStateException.class, e.getClass());
			}
			try {
				iter.next();
			} catch (Exception e) {
				assertEquals(NoSuchElementException.class, e.getClass());
			}
		}
	}

	@Test
	public void test_size() {
		ABTreeSet<Integer> set = new ABTreeSet<>();
		TreeSet<Integer> s = new TreeSet<>();

		for (int i = 0; i < n; i++) {
			int key = rand.nextInt(n);

			set.add(key);
			s.add(key);

			assertEquals(s.contains(key), set.contains(key));
			assertEquals(s.size(), set.size());
		}
	}

	@Test
	public void test_successor() {
		ABTreeSet<Integer> set = new ABTreeSet<>();
		int[] arr = getRandomArray(n);

		for (int i : arr)
			set.add(i);

		BSTNode<Integer> cur = set.root();
		while (cur.left() != null)
			cur = cur.left();
		for (int i = 0; i < n; i++) {
			assertEquals(i, cur.data().intValue());
			cur = set.successor(cur);
		}
		assertEquals(null, cur);
	}

	@Test
	public void test_toString() {
		ABTreeSet<Integer> set = getExampleC();
		Scanner sc = new Scanner(set.toString());
		assertEquals("50", sc.nextLine());
		assertEquals("    30", sc.nextLine());
		assertEquals("        25", sc.nextLine());
		assertEquals("            10", sc.nextLine());
		assertEquals("                null", sc.nextLine());
		assertEquals("                20", sc.nextLine());
		assertEquals("            null", sc.nextLine());
		assertEquals("        35", sc.nextLine());
		assertEquals("            31", sc.nextLine());
		assertEquals("            37", sc.nextLine());
		assertEquals("    55", sc.nextLine());
		assertEquals("        53", sc.nextLine());
		assertEquals("        60", sc.nextLine());
		assertEquals("            null", sc.nextLine());
		assertEquals("            62", sc.nextLine());
		sc.close();
	}

	@Test
	public void test_values() {
		ABTreeMap<Integer, Character> map = new ABTreeMap<>();
		int[] arr = getRandomArray(n);

		char cLowerCase = 'a';
		for (int i : arr)
			map.put(i, (char) ('a' + i));
		for (char v : map.values())
			assertEquals(cLowerCase++, v);
		assertEquals(n, map.size());

		char cUpperCase = 'A';
		for (int i : arr)
			map.put(i, (char) ('A' + i));
		for (char v : map.values())
			assertEquals(cUpperCase++, v);
		assertEquals(n, map.size());
	}
}
