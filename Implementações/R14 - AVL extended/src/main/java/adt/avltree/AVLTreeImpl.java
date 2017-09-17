package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {
	
	@Override
	public void insert(T element) {
		if (element != null) {
			this.insert(this.root, element);
		}
	}

	private void insert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		} else {
			if (node.getData().compareTo(element) > 0) {
				this.insert((BSTNode<T>) node.getLeft(), element);
			} else if (node.getData().compareTo(element) < 0) {
				this.insert((BSTNode<T>) node.getRight(), element);
			}
			this.rebalance(node);
		}
	}

	@Override
	public void remove(T element) {
		if (element != null) {
			BSTNode<T> toRemove = search(element);

			if (!toRemove.isEmpty()) {
				remove(toRemove);
			}

		}
	}

	private void remove(BSTNode<T> node) {
		// Se for folha - Caso 1
		if (node.isLeaf()) {
			node.setData(null);
			this.rebalanceUp(node);
		// Se tem um filho - Caso 2
		} else if ((!node.getLeft().isEmpty() && node.getRight().isEmpty())
				|| node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
			BSTNode<T> currentNode;

			if (!node.getLeft().isEmpty() && node.getRight().isEmpty()) {
				currentNode = (BSTNode<T>) node.getLeft();
			} else {
				currentNode = (BSTNode<T>) node.getRight();
			}

			if (node.getParent() == null) {
				currentNode.setParent(null);
				this.root = currentNode;

			} else {
				// se node eh filho da esquerda de eu pai
				if (!node.getParent().isEmpty() && !node.getParent().getLeft().isEmpty()
						&& node.getParent().getLeft().getData().equals(node.getData())) {
					node.getParent().setLeft(currentNode);
				} else {
					node.getParent().setRight(currentNode);
				}
				currentNode.setParent(node.getParent());
			}

			this.rebalance(node);
		// Se tem dois fillhos - Caso 3
		} else {
			BSTNode<T> auxNode = minimum((BSTNode<T>) node.getRight());

			T aux = node.getData();

			node.setData(auxNode.getData());
			auxNode.setData(aux);

			remove(auxNode);
		}
	}

	protected int calculateBalance(BSTNode<T> node) {
		int balance = 0;
		if (node != null && !node.isEmpty()) {
			balance = this.height(node.getLeft()) - this.height(node.getRight());
		}
		return balance;
	}

	protected void rebalance(BSTNode<T> node) {
		if (node != null && !node.isLeaf() && !node.isEmpty()) {
			int balance = this.calculateBalance(node);
			if (Math.abs(this.calculateBalance(node)) > 1) {
				
				if (balance > 1) {
					
					int leftTreeBalance = this.calculateBalance((BSTNode<T>) node.getLeft());
					if (leftTreeBalance < 0) {
						leftRotation((BSTNode<T>) node.getLeft());
					}
					rightRotation(node);
					
				} else {
					
					int rightTreeBalance = this.calculateBalance((BSTNode<T>) node.getRight());
					if (rightTreeBalance > 0) {
						rightRotation((BSTNode<T>) node.getRight());
					}
					leftRotation(node);
					
				}
				
			}
		}
	}

	protected void rebalanceUp(BSTNode<T> node) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (parent != null) {
			rebalance(parent);
			parent = (BSTNode<T>) parent.getParent();
		}
	}

	protected void leftRotation(BSTNode<T> node) {
		BSTNode<T> aux = Util.leftRotation(node);
		if (root.equals(node)) {
			root = aux;
		}
	}

	protected void rightRotation(BSTNode<T> node) {
		BSTNode<T> aux = Util.rightRotation(node);
		if (this.root.equals(node)) {
			root = aux;
		}
	}

}
