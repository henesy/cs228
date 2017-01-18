package edu.iastate.cs228.hw5;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sean Hinchee
 */
public class ABTreeMap<K extends Comparable<? super K>, V> {

	final class Entry implements Comparable<Entry>, Map.Entry<K, V> {
		private final K key; // immutable
		private V value; // mutable

		Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int compareTo(Entry o) {
			
			if(o.value.equals(this.value))
				return 0;
			
			if(o.compareTo(this) < 0)
				return -1;
			
			return 1;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V newValue) {

			return value = newValue;
		}

	}
	/* entry */

	private ABTreeSet<Entry> entrySet;

	/**
	 * Default constructor. Builds a map that uses a non-self-balancing tree.
	 */
	public ABTreeMap() {
		entrySet = new ABTreeSet<Entry>();
		entrySet.setSelfBalance(false);
	}

	/**
	 * If isSelfBalancing is true, <br>
	 * builds a map that uses self-balancing tree with alpha = 2/3. <br>
	 * If isSelfBalancing is false, <br>
	 * builds a map that uses a non-self-balancing tree.
	 * 
	 * @param isSelfBalancing
	 */
	public ABTreeMap(boolean isSelfBalancing) {
		if(isSelfBalancing) {
			entrySet = new ABTreeSet<Entry>(true, 2, 3);
			entrySet.setSelfBalance(true);
		} else {
			entrySet = new ABTreeSet<Entry>();
			entrySet.setSelfBalance(false);
		}
	}

	/**
	 * If isSelfBalancing is true, <br>
	 * builds a map that uses a self-balancing tree with alpha = top/bottom.
	 * <br>
	 * If isSelfBalancing is false, <br>
	 * builds a map that uses a non-self-balancing tree.
	 * 
	 * @param isSelfBalancing
	 * @param top
	 * @param bottom
	 * @throws IllegalArgumentException
	 *             if (1/2 < alpha < 1) is false
	 */
	public ABTreeMap(boolean isSelfBalancing, int top, int bottom) {
		if(isSelfBalancing) {
			entrySet = new ABTreeSet<Entry>(true, top, bottom);
			entrySet.setSelfBalance(true);
		} else {
			entrySet = new ABTreeSet<Entry>();
			entrySet.setSelfBalance(false);
		}
	}

	/**
	 * Returns true if this map contains a mapping for key; <br>
	 * otherwise, it returns false.
	 * 
	 * @param key
	 * @return true if this map contains a mapping for key; <br>
	 *         otherwise, it returns false.
	 */
	public boolean containsKey(K key) {
		
		return entrySet.contains(key);
	}

	/**
	 * Returns the value to which key is mapped, <br>
	 * or null if this map contains no mapping for key.
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {

		BSTNode<Entry> node = entrySet.getBSTNode(new Entry(key, null));
		
		if(node != null)
			return node.data().value;
		
		return null;
	}

	/**
	 * Returns a ABTreeSet storing the keys (not the values)
	 * 
	 * Example. Suppose this map consists of the following (key, value) pairs:
	 * (10, Carol), (21, Bill), (45, Carol), (81, Alice), (95, Bill). <br>
	 * Then, the ABTreeSet returned should consist of 10, 21, 45, 81, 91.
	 * <p>
	 * The keySet should have the same tree structure as entrySet.
	 * 
	 * @return a ABTreeSet storing the keys (not the values)
	 */
	public ABTreeSet<K> keySet() {

		ABTreeSet<K> tree = new ABTreeSet<K>();
		List<BSTNode<ABTreeMap<K, V>.Entry>> list = entrySet.inorderList(entrySet.root());
		int i;
		for(i = 0; i < list.size(); i++) {
			tree.add(list.get(i).data().key);
		}
		
		return tree;
	}

	/**
	 * Associates value with key in this map. <br>
	 * Returns the previous value associated with key, <br>
	 * or null if there was no mapping for key.
	 * 
	 * @param key
	 * @param value
	 * @return the previous value associated with key, <br>
	 *         or null if there was no mapping for key.
	 * @throws NullPointerException
	 *             if key or value is null.
	 */
	public V put(K key, V value) {
		
		if(key == null || value == null)
			throw new NullPointerException();
		
		BSTNode<Entry> node = entrySet.getBSTNode(new Entry(key, null));
		
		V oldv = node.data().value;
		
		node.data().value = value;
		
		return oldv;
	}

	/**
	 * Removes the mapping for key from this map if it is present. <br>
	 * Returns the previous value associated with key, <br>
	 * or null if there was no mapping for key.
	 * 
	 * @param key
	 * @return the previous value associated with key, <br>
	 *         or null if there was no mapping for key.
	 */
	public V remove(K key) {
		
		BSTNode<Entry> node = entrySet.getBSTNode(new Entry(key, null));
		
		V oldv = node.data().value;
		
		entrySet.remove(new Entry(key, null));
		
		return oldv;
	}

	/**
	 * Returns the number of key-value mappings in this map.
	 * 
	 * @return the number of key-value mappings in this map.
	 */
	public int size() {

		return entrySet.size();
	}

	@Override
	public String toString() {
		return entrySet.toString();
	}

	/**
	 * Returns an ArrayList storing the values contained in this map. <br>
	 * Note that there may be duplicate values. <br>
	 * The ArrayList should contain the values in ascending order of keys.
	 * <p>
	 * Example. <br>
	 * Suppose this map consists of the following (key, value) pairs: <br>
	 * (10, Carol), (21, Bill), (45, Carol), (81, Alice), (95, Bill). <br>
	 * Then, the ArrayList returned should consist of the strings <br>
	 * Carol, Bill, Carol, Alice, Bill, in that order.
	 * 
	 * @return
	 */
	public ArrayList<V> values() {
		
		List<BSTNode<ABTreeMap<K, V>.Entry>> list = entrySet.inorderList(entrySet.root());
		ArrayList<V> nlist = new ArrayList<V>();
		int i;
		for(i = 0; i < list.size(); i++) {
			nlist.add(list.get(i).data().value);
		}

		return nlist;
	}
}