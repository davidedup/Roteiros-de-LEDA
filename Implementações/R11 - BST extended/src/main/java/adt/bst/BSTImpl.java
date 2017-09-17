package adt.bst;
//top
import java.awt.List;
import java.util.LinkedList;
import java.util.Stack;

//top
import adt.bt.BTNode;
import util.Util;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return this.height(this.getRoot());
	}

	private int height(BTNode<T> node) {
		if (node.isEmpty()) {
			return -1;
		}
		if (this.height(node.getRight()) > this.height(node.getLeft())) {
			return 1 + this.height(node.getRight());
		} else {
			return 1 + this.height(node.getLeft());
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
		} else if (node.getData().compareTo(element) > 0) {
			return this.search((BSTNode<T>) node.getLeft(), element);
		} else {
			return this.search((BSTNode<T>) node.getRight(), element);
		}
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
			if (node.getData().compareTo(element) > 0) {
				this.insert((BSTNode<T>) node.getLeft(), element);
			} else if (node.getData().compareTo(element) < 0) {
				this.insert((BSTNode<T>) node.getRight(), element);
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		BSTNode<T> maximum = null;

		if (!this.getRoot().isEmpty()) {
			maximum = maximum(this.getRoot());
		}

		return maximum;
	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		if (node.isEmpty()) {
			return null;
		} else if (node.getRight().isEmpty()) {
			return node;
		} else {
			return maximum((BSTNode<T>) node.getRight());
		}
	}

	@Override
	public BSTNode<T> minimum() {
		BSTNode<T> minimum = null;

		if (!this.getRoot().isEmpty()) {
			minimum = minimum(this.getRoot());
		}

		return minimum;
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		if (node.isEmpty()) {
			return null;
		} else if (node.getLeft().isEmpty()) {
			return node;
		} else {
			return minimum((BSTNode<T>) node.getLeft());
		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = this.search(element);

		if (node.isEmpty())
			return null;

		return sucessor(node);
	}

	private BSTNode<T> sucessor(BSTNode<T> node) {
		BSTNode<T> sucessor = minimum((BSTNode<T>) node.getRight());

		if (sucessor != null) {
			return sucessor;
		} else {
			sucessor = (BSTNode<T>) node.getParent();

			while (sucessor != null && sucessor.getData().compareTo(node.getData()) < 0) {
				sucessor = (BSTNode<T>) sucessor.getParent();
			}

			return sucessor;
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(element);

		if (node.isEmpty())
			return null;

		return predecessor(node);
	}

	private BSTNode<T> predecessor(BSTNode<T> node) {
		BSTNode<T> predecessor = maximum((BSTNode<T>) node.getLeft());

		if (predecessor != null) {
			return predecessor;
		} else {
			predecessor = (BSTNode<T>) node.getParent();

			while (predecessor != null && predecessor.getData().compareTo(node.getData()) > 0) {
				predecessor = (BSTNode<T>) predecessor.getParent();
			}

			return predecessor;
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
			// Se tem dois fillhos - Caso 3
		} else {
			BSTNode<T> auxNode = minimum((BSTNode<T>) node.getRight());

			T aux = node.getData();

			node.setData(auxNode.getData());
			auxNode.setData(aux);

			remove(auxNode);
		}
	}

	@Override
	public T[] preOrder() {
		T[] array = Util.makeArrayOfComparable(this.size());
		this.preOrder(array, 0, this.getRoot());
		return array;
	}

	private int preOrder(T[] array, int index, BSTNode<T> node) {
		if (!node.isEmpty()) {
			array[index++] = node.getData();
			index = this.preOrder(array, index, (BSTNode<T>) node.getLeft());
			index = this.preOrder(array, index, (BSTNode<T>) node.getRight());
		}
		return index;
	}

	@Override
	public T[] order() {
		T[] array = Util.makeArrayOfComparable(this.size());
		this.order(array, 0, this.getRoot());
		return array;
	}

	private int order(T[] array, int index, BSTNode<T> node) {
		if (!node.isEmpty()) {
			index = this.order(array, index, (BSTNode<T>) node.getLeft());
			array[index++] = node.getData();
			index = this.order(array, index, (BSTNode<T>) node.getRight());
		}
		return index;
	}

	@Override
	public T[] postOrder() {
		T[] array = Util.makeArrayOfComparable(this.size());
		this.postOrder(array, 0, this.getRoot());
		return array;
	}

	private int postOrder(T[] array, int index, BSTNode<T> node) {
		if (!node.isEmpty()) {
			index = this.preOrder(array, index, (BSTNode<T>) node.getLeft());
			index = this.preOrder(array, index, (BSTNode<T>) node.getRight());
			array[index++] = node.getData();
		}
		return index;
	}

	public int distance(T value1, T value2) {
		int distance = -1;

		BSTNode<T> nodeOfValue1 = this.search(value1);
		if (!nodeOfValue1.isEmpty()) {
			BSTNode<T> nodeOfValue2 = this.search(value2);
			if (!nodeOfValue2.isEmpty()) {
				Stack<BSTNode<T>> pilha1, pilha2;
				pilha1 = toArrayParent(nodeOfValue1);
				pilha2 = toArrayParent(nodeOfValue2);

				while (!pilha1.isEmpty() && !pilha2.isEmpty() && pilha1.peek().equals(pilha2.peek())) {
					pilha1.pop();
					pilha2.pop();
				}
				distance = pilha1.size() + pilha2.size();
			}
		}
		return distance;
	}

	private Stack<BSTNode<T>> toArrayParent(BSTNode<T> node) {
		Stack<BSTNode<T>> pilha = new Stack<BSTNode<T>>();
		while (node.getParent() != null) {
			pilha.push(node);
			node = (BSTNode<T>) node.getParent();
		}
		return pilha;
	}

	/**  codigo para imprimir um nivel by Juan B&B
	 * @author Juan 
	 * @param level
	 * @return
	 */

	public T[] arrayLevel(int level) {
		LinkedList<T> list = arrayLevel(root, new LinkedList<T>(), level);
		return makeArrayFromList(list);
	}

	private LinkedList<T> arrayLevel(BSTNode<T> node, LinkedList<T> list, int level) {
		if (level >= 0 && !node.isEmpty()) {
			arrayLevel((BSTNode<T>) node.getLeft(), list, level - 1);
			if (level == 0) {
				list.add(node.getData());
			}
			arrayLevel((BSTNode<T>) node.getRight(), list, level - 1);
		}
		return list;
	}

	protected T[] makeArrayFromList(LinkedList<T> list) {
		int size = list.size();
		T[] array = Util.makeArrayOfComparable(size);
		for (int i = 0; i != size; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}
}
