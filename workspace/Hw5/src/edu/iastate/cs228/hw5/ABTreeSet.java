package edu.iastate.cs228.hw5;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Sean Hinchee
 */
public class ABTreeSet<E extends Comparable<? super E>> extends AbstractSet<E> {

	final class ABTreeIterator implements Iterator<E> {
		Node cursor;
		BSTNode<E> previous;

		ABTreeIterator() {
			cursor = root;
			previous = cursor;
		}

		@Override
		public boolean hasNext() {
			if(successor(cursor) != null)
				return true;
						
			return false;
		}

		@Override
		public E next() {
			previous = cursor;
			cursor = (ABTreeSet<E>.Node) successor(cursor);
			return cursor.data();
		}

		@Override
		public void remove() {
			//same as tree remove, but doesn't search for a given node
			
			if(cursor != null) {
				size--;
			
				if(cursor.left() == null && cursor.right() == null) {
					if(cursor.parent.left.equals(cursor) && !cursor.equals(root))
						cursor.parent.left = null;
					else if(cursor.parent.right.equals(cursor) && !cursor.equals(root))
						cursor.parent.right = null;
					
					cursor = null;
				} else if(cursor.left() == null && cursor.right() != null) {
					if(cursor.parent.left.equals(cursor) && !cursor.equals(root))
						cursor.parent.left = cursor.right;
					else if(cursor.parent.right.equals(cursor) && !cursor.equals(root))
						cursor.parent.right = cursor.right;
					
					cursor = cursor.right;
				} else if(cursor.left() != null && cursor.right() == null) {
					if(cursor.parent.left.equals(cursor) && !cursor.equals(root))
						cursor.parent.left = cursor.left;
					else if(cursor.parent.right.equals(cursor) && !cursor.equals(root))
						cursor.parent.right = cursor.left;
					
					cursor = cursor.left;
				} else if(cursor.left() != null && cursor.right() != null) {
					//complicated, pages 16-17 of PDF
					//might be wrong
					unlinkNode(cursor);
				}
				
				//backup
				if(root.right != null)
					if(root.right.data.equals(cursor.data))
						root.right = null;
				
				if(root.left != null)
					if(root.left.data.equals(cursor.data))
						root.left = null;
			}
			if(balancing)
				rebalance(root);
		}
	}
	/* end iterator class */
	

	final class Node implements BSTNode<E> {
		private int count;
		private E data;

		private Node parent;
		private Node left;
		private Node right;

		Node(E data) {
			count = 0;
			this.data = data;
		}

		/**
		 * determine which,  if any,  nodes have become unbalanced as a result of an update,
		 * and is used to find the root of the subtree at which the re-balance operation must be applied
		 */
		@Override
		public int count() {
			//do math under the node in the tree
			
			if(root == null)
				return 0;
			
			count = walkCount(this);
			
			return count;
		}
		
		public int getCount() {
			return count;
		}

		@Override
		public E data() {

			return data;
		}

		@Override
		public BSTNode<E> left() {

			return left;
		}

		@Override
		public BSTNode<E> parent() {

			return parent;
		}

		@Override
		public BSTNode<E> right() {

			return right;
		}

		@Override
		public String toString() {
			return data.toString();
		}
	}
	/* end Node class */
	

	private Node root;
	private boolean balancing;
	private int alphaTop;
	private int alphaBot;
	private int size;

	/**
	 * Default constructor. Builds a non-self-balancing tree.
	 */
	public ABTreeSet() {
		balancing = false;
		root = null;
		alphaTop = 0;
		alphaBot = 0;
		size = 0;
	}

	/**
	 * If <code>isSelfBalancing</code> is <code>true</code> <br>
	 * builds a self-balancing tree with the default value alpha = 2/3.
	 * <p>
	 * If <code>isSelfBalancing</code> is <code>false</code> <br>
	 * builds a non-self-balancing tree.
	 * 
	 * @param isSelfBalancing
	 */
	public ABTreeSet(boolean isSelfBalancing) {
		
		balancing = isSelfBalancing;
		//alpha is 2/3
		if(balancing) {
			alphaTop = 2;
			alphaBot = 3;
		} else {
			alphaTop = 0;
			alphaBot = 0;
		}
		
		root = null;
		size = 0;
	}

	/**
	 * If <code>isSelfBalancing</code> is <code>true</code> <br>
	 * builds a self-balancing tree with alpha = top / bottom.
	 * <p>
	 * If <code>isSelfBalancing</code> is <code>false</code> <br>
	 * builds a non-self-balancing tree (top and bottom are ignored).
	 * 
	 * @param isSelfBalancing
	 * @param top
	 * @param bottom
	 * @throws IllegalArgumentException
	 *             if (1/2 < alpha < 1) is false
	 */
	public ABTreeSet(boolean isSelfBalancing, int top, int bottom) {

		balancing = isSelfBalancing;
		//alpha is 2/3
		
		if((1/2 < top / bottom && top/bottom < 1) == false)
			throw new IllegalArgumentException();
		
		if(balancing) {
			alphaTop = top;
			alphaBot = bottom;
		} else {
			alphaTop = 0;
			alphaBot = 0;
		}
		
		root = null;
		size = 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException
	 *             if e is null.
	 */
	@Override
	public boolean add(E e) {
		
		if(e == null)
			throw new NullPointerException();
		
		size++; 
		
		if(root != null) {
			
			Node cursor = root;
			while(true) {
				if(e.compareTo(cursor.data) <= 0) {
					if(cursor.left != null) {
						//go left
						cursor = cursor.left;
					} else {
						cursor.left = new Node(e);
						cursor.left.parent = cursor;
						if(balancing)
							rebalance(root);
						return true;
					}
					
				} else {
					if(cursor.right != null) {
						//go right
						cursor = cursor.right;
					} else {
						cursor.right = new Node(e);
						cursor.right.parent = cursor;
						if(balancing)
							rebalance(root);
						return true;
					}
				}
					
			}
			
		} else {
			root = new Node(e);
			root.parent = null;
			return true;
		}

	}

	@Override
	public boolean contains(Object o) {
		//search tree for match of object
		if(root.data.equals(o))
			return true;
		
		boolean res = walkContains(root, o);
		return res;
	}

	/**
	 * @param e
	 * @return BSTNode that contains e, null if e does not exist
	 */
	public BSTNode<E> getBSTNode(E e) {
		//search tree for match of object's data
		Node node = walkFind(root, e);
		
		//System.out.println("GOT NODE: " + node);

		return node;
	}

	/**
	 * Returns an in-order list of all nodes in the given sub-tree.
	 * 
	 * @param root
	 * @return an in-order list of all nodes in the given sub-tree.
	 */
	public List<BSTNode<E>> inorderList(BSTNode<E> root) {
		//walk entire list
		List<BSTNode<E>> list = new ArrayList<BSTNode<E>>();
		
		inorderWalk(root, list);

		return list;
	}
	
	private void inorderWalk(BSTNode<E> top, List<BSTNode<E>> list) {
		if(top == null)
			return;
		
		inorderWalk(top.left(), list);
		list.add(top);
		inorderWalk(top.right(), list);
	}

	@Override
	public Iterator<E> iterator() {

		return new ABTreeIterator();
	}

	/**
	 * Returns an pre-order list of all nodes in the given sub-tree.
	 * 
	 * @param root
	 * @return an pre-order list of all nodes in the given sub-tree.
	 */
	public List<BSTNode<E>> preorderList(BSTNode<E> root) {
		//walk entire list
		List<BSTNode<E>> list = new ArrayList<BSTNode<E>>();
		
		preorderWalk(root, list);

		return list;
	}
			
	private void preorderWalk(BSTNode<E> top, List<BSTNode<E>> list) {
		if(top == null)
			return;
		
		list.add(top);
		preorderWalk(top.left(), list);
		preorderWalk(top.right(), list);
	}

	/**
	 * Performs a re-balance operation on the subtree rooted at the given node.
	 * 
	 * @param bstNode
	 */
	public void rebalance(BSTNode<E> bstNode) {
		//MISSING
		List<BSTNode<E>> list = inorderList(bstNode);
		BSTNode<E> newRoot = list.get(list.size()/2);
		list.remove( list.get(list.size()/2));
		root = (ABTreeSet<E>.Node) newRoot;
		int i;
		for(i = 0; i < list.size(); i++) {
			this.add(list.get(i).data());
		}
	}
	
	private boolean shouldRebalance() {
		//maybe loop through entire tree? look for subtrees?
		root.count();
		if(root.left.count * alphaBot > size * alphaTop)
			return true;
		if(root.right.count * alphaBot > size * alphaTop)
			return true;
		return false;
	}

	@Override
	public boolean remove(Object o) {
		//search tree for object then un-pin object
		Node node = (ABTreeSet<E>.Node) getBSTNode((E) o);
		
		//System.out.println("Node to remove: " + node.data);
		
		if(node == null)
			return false;
		
		size--;
		
		if(node.left() == null && node.right() == null) {
			if(node.parent.left.equals(node) && !node.equals(root))
				node.parent.left = null;
			else if(node.parent.right.equals(node) && !node.equals(root))
				node.parent.right = null;
			
			node = null;
		} else if(node.left() == null && node.right() != null) {
			if(node.parent.left.equals(node) && !node.equals(root))
				node.parent.left = node.right;
			else if(node.parent.right.equals(node) && !node.equals(root))
				node.parent.right = node.right;
			
			node = node.right;
		} else if(node.left() != null && node.right() == null) {
			if(node.parent.left.equals(node) && !node.equals(root))
				node.parent.left = node.left;
			else if(node.parent.right.equals(node) && !node.equals(root))
				node.parent.right = node.left;
			
			node = node.left;
		} else if(node.left() != null && node.right() != null) {
			//complicated, pages 16-17 of PDF
			//might be wrong
			unlinkNode(node);
		}
		
		//backup
		if(root.right != null)
			if(root.right.data.equals(o))
				root.right = null;
		
		if(root.left != null)
			if(root.left.data.equals(o))
				root.left = null;
		
		if(balancing)
			rebalance(root);
		
		return true;
	}
	
	private void unlinkNode(Node n) {
		if(n.left != null && n.right != null) {
			//2 children
			BSTNode<E> s = successor(n);
			n.data = s.data();
			n = (ABTreeSet<E>.Node) s;
			
			Node replacement = null;
			if(n.left != null) 
				replacement = n.left;
			else if(n.right != null)
				replacement = n.right;
			
			if(n.equals(root))
				root = replacement;
			else
				if(n == n.parent.left)
					n.parent.left = replacement;
				else
					n.parent.right = replacement;
			if(replacement != null)
				replacement.parent = n.parent;
		}
	}

	/**
	 * Returns the root of the tree.
	 * 
	 * @return the root of the tree.
	 */
	public BSTNode<E> root() {

		return root;
	}

	public void setSelfBalance(boolean isSelfBalance) {

		balancing = isSelfBalance;
	}

	@Override
	public int size() {
		//walk through entire tree (probably)
		
		return size;
	}

	public BSTNode<E> successor(BSTNode<E> node) {
		
		BSTNode<E> cursor = node;
		
		//go right first if we can
		if(node.right() != null) {
			cursor = cursor.right();
			while(cursor.left() != null)
				cursor = cursor.left();
			return cursor;
		}
		
		//do the go up to root of subtree
		BSTNode<E> last = node;
		cursor = cursor.parent();
		if(cursor == null)
			return null;
		
		while(cursor.left() != last) {
			last = cursor;
			cursor = cursor.parent();
			if(cursor == null)
				return null;
		}
		
		return cursor;
	}

	@Override
	public String toString() {
		root.count();
		ArrayList<BSTNode<E>> list = new ArrayList<BSTNode<E>>();
		ArrayList<Integer> dists = new ArrayList<Integer>();
		int dist = 0;
		preorderWalkTrack(root, list, dists, dist);
		//System.out.println("Size: " + dists.size() + " " + list.size());
		
		int i;
		for(i = 0; i < dists.size() && i < list.size(); i++) {
			int j;
			for(j = 0; j < dists.get(i); j++) {
				System.out.print("    ");
			}
			if(list.get(i) != null)
				System.out.print(list.get(i).data());
			else {
				System.out.print("null");
			}
			System.out.println();
		}

		return null;
	}
	
	private void preorderWalkTrack(BSTNode<E> top, List<BSTNode<E>> list , List<Integer> dists, int dist) {
		if(top == null) {
			//dist--;
			list.add(top);
			dists.add(dist);
			return;
		}
		dist++;
		
		list.add(top);
		dists.add(dist-1);
		if(top.left() != null || top.right() != null) {
			preorderWalkTrack(top.left(), list, dists, dist);
			preorderWalkTrack(top.right(), list, dists, dist);
		}
	}
	
	/***
	 * Walks through the nodes of a tree
	 * @param top
	 */
	private int walkCount(Node top) {
		int cnt = 1;
		if(top.left != null)
			cnt += walkCount(top.left);
		if(top.right != null)
			cnt += walkCount(top.right);
		return cnt;
	}
	
	private boolean walkContains(Node top, Object target) {
		
		if(top.data.equals(target))
			return true;

			if(top.left != null)
				walkContains(top.left, target);
		
			if(top.right != null)
				walkContains(top.right, target);
		
		return false;
	}
	
	private Node walkFind(Node top, E e) {
		Node cursor = top;
		Node res = null;
		while(res == null) {
			if(cursor == null)
				break;
			
			//System.out.println("WALKED: " + cursor.data);
			
			if(e.compareTo(cursor.data) < 0) {
				//left
				cursor = cursor.left;
				
			} else if(e.compareTo(cursor.data) > 0) {
				//right
				cursor = cursor.right;
				
			} else {
				//equals 
				res = cursor;
			}
		}
		
		return res;
	}
	
	private Node walkFindOld(Node top, E e) {
		System.out.println("WALKED NODE: " + top.toString() + " WITH [E, DATA]: " + e.toString() + ",  " + top.data.toString() + " WHICH: " + e.equals(top.data));
		if(e.equals(top.data))
			return top;
		
		if(top.left != null)
			walkFind(top.left, e);
		if(top.right != null)
			walkFind(top.right, e);
		
		return null;
	}

}