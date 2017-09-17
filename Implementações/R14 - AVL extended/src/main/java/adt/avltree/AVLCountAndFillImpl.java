package adt.avltree;

import java.util.ArrayList;
import java.util.Collections;

import adt.bst.BSTNode;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {

	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}

	@Override
	protected void rebalance(BSTNode<T> node) {
		if (node != null && !node.isLeaf() && !node.isEmpty()) {
			int balance = this.calculateBalance(node);
			if (Math.abs(this.calculateBalance(node)) > 1) {

				if (balance > 1) {

					int leftTreeBalance = this.calculateBalance((BSTNode<T>) node.getLeft());
					if (leftTreeBalance < 0) {
						leftRotation((BSTNode<T>) node.getLeft());
						rightRotation(node);
						this.LRcounter++;
					} else {
						rightRotation(node);
						this.LLcounter++;
					}

				} else {

					int rightTreeBalance = this.calculateBalance((BSTNode<T>) node.getRight());
					if (rightTreeBalance > 0) {
						rightRotation((BSTNode<T>) node.getRight());
						leftRotation(node);
						this.RLcounter++;
					} else {
						leftRotation(node);
						this.RRcounter++;
					}

				}

			}
		}
	}

	/**
	 * Este metodo cria uma nova arvore(auxTree) adiciona todos os elementos de
	 * um arrayList(treeAndArrayElements) fazendo os devido balancemanetos
	 * despois pegamos o nivel da arvore balanceada e adiciona na bst sem causar
	 * rotações
	 */
	@Override
	public void fillWithoutRebalance(T[] array) {
		if (array != null && array.length != 0) {
			AVLCountAndFillImpl<T> auxTree = new AVLCountAndFillImpl<T>();
			T[] treeElements = this.order();
			ArrayList<T> treeAndArrayElements = twoArraysInOne(treeElements, array);
			this.root = new BSTNode<T>();

			for (int i = 0; i < treeAndArrayElements.size(); i++) {
				auxTree.insert(treeAndArrayElements.get(i));
			}

			T[] aux = null;
			int auxTreeHeight = auxTree.height();
			for (int j = 0; j < auxTreeHeight; j++) {
				aux = auxTree.arrayLevel(j);
				for (int i = 0; i < aux.length; i++) {
					this.insert(aux[i]);
				}

			}
		}
	}

	/**
	 * Recebe dois arrays e adiciona o conteudo dos mesmos e um Arraylist e
	 * ordena o conteudo
	 * 
	 * @param treeElements
	 * @param array
	 * @return Arraylist ordenado com o conteudo dos dois array
	 */
	private ArrayList<T> twoArraysInOne(T[] treeElements, T[] array) {
		ArrayList<T> elementsToInsert = new ArrayList<T>();
		for (int i = 0; i < array.length; i++) {
			elementsToInsert.add(array[i]);
		}
		for (int j = 0; j < treeElements.length; j++) {
			elementsToInsert.add(treeElements[j]);
		}
		Collections.sort(elementsToInsert);
		return elementsToInsert;
	}
}
