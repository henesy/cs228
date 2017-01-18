package edu.iastate.cs228.hw5;

public interface BSTNode<E> {
	int count();

	E data();

	BSTNode<E> left();

	BSTNode<E> parent();

	BSTNode<E> right();

	String toString();
}
