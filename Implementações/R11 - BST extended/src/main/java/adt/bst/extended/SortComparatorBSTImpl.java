package adt.bst.extended;

import java.util.Comparator;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import util.Util;

/**
 * Implementacao de SortComparatorBST, uma BST que usa um comparator interno em
 * suas funcionalidades e possui um metodo de ordenar um array dado como
 * parametro, retornando o resultado do percurso desejado que produz o array
 * ordenado.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class SortComparatorBSTImpl<T extends Comparable<T>> extends BSTImpl<T> implements SortComparatorBST<T> {

	private Comparator<T> comparator;

	public SortComparatorBSTImpl(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public T[] sort(T[] array) {
		T[] sortedArray;
		
		if (array != null) {
			
			
			if (!this.getRoot().isEmpty()) { // Se a arvore nao estiver vazia, vai esvaziar.
				remove(this.getRoot().getData());
				sortedArray = sort(array);
			
			} else { // se a arvore esta vazia, preenche com os dados do Array;
				
				for (int i = 0; i < array.length; i++) {
					insert(array[i]);
				}
				
				sortedArray = order(); 
			}
		}else
			sortedArray = null;
		
		return sortedArray;
	}

	@Override
	public T[] reverseOrder() {
		T[] array = Util.makeArrayOfComparable(size());
		reverseOrder(this.getRoot(), array, 0);
		return array;
	}

	private int reverseOrder(BSTNode<T> node, T[] array, int index) {
		if (!node.isEmpty()) {
			index = reverseOrder((BSTNode<T>) node.getRight(), array, index);
			array[index++] = node.getData();
			index = reverseOrder((BSTNode<T>) node.getLeft(), array, index);
		}

		return index;
	}

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
			if (this.getComparator().compare(node.getData(), element) > 0) {
				this.insert((BSTNode<T>) node.getLeft(), element);
			} else if (this.getComparator().compare(node.getData(), element) < 0) {
				this.insert((BSTNode<T>) node.getRight(), element);
			}
		}
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> wanted = new BSTNode<T>();

		if (element != null) {
			wanted = this.search(this.getRoot(), element);
		}

		return wanted;
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		if (node.isEmpty() || node.getData().equals(element)) {
			return node;
		} else if (this.getComparator().compare(node.getData(), element) > 0) {
			return this.search((BSTNode<T>) node.getLeft(), element);
		} else {
			return this.search((BSTNode<T>) node.getRight(), element);
		}
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

}
