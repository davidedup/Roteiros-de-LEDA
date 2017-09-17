package adt.bt;

import adt.bst.BSTNode;

public class Util {

	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> rightChildren = null;
		if (node != null && !node.isEmpty() && !node.isLeaf()) {
			BSTNode<T> right = (BSTNode<T>) node.getRight();
			node.setRight(right.getLeft());
			node.getRight().setParent(node);
			right.setParent(node.getParent());
			
			if (node.getParent() != null) {
				
				if (node.getParent().getLeft() == node) {
					node.getParent().setLeft(right);
				} else {
					node.getParent().setRight(right);
				}
				
			}
			
			right.setLeft(node);
			node.setParent(right);
			rightChildren = right;
		}
		return rightChildren;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> leftChildren = null;
		if (node != null && !node.isEmpty() && !node.isLeaf()) {
			BSTNode<T> left = (BSTNode<T>) node.getLeft();
			node.setLeft(left.getRight());
			node.getLeft().setParent(node);
			left.setParent(node.getParent());
			
			if (node.getParent() != null) {
				
				if (node.getParent().getLeft() == node) {
					node.getParent().setLeft(left);
				} else {
					node.getParent().setRight(left);
				}
				
			}

			left.setRight(node);
			node.setParent(left);
			leftChildren = left;
		}
		return leftChildren;
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}

}