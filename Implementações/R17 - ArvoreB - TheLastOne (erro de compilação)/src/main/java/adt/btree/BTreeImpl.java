package adt.btree;

import java.util.LinkedList;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		int height = -1;
		if (!this.isEmpty()) {
			height = height(this.root);
		}
		return height;
	}

	private int height(BNode<T> node) {
		int height = 0;
		if (!node.isLeaf()) {
			height = 1 + height(node.children.getFirst());
		}
		return height;
	}

	@Override
	public BNode<T>[] depthLeftOrder() {
		LinkedList<BNode<T>> listAux = new LinkedList<BNode<T>>();
		depthLeftOrder(listAux, this.root);
		@SuppressWarnings("unchecked")
		BNode<T>[] arrayOfNodes = (BNode<T>[]) new BNode[this.countNodes(this.root)];
		return listAux.toArray(arrayOfNodes);
	}

	private void depthLeftOrder(LinkedList<BNode<T>> listAux, BNode<T> node) {
		listAux.add(node);
		for (BNode<T> currentNode : node.getChildren()) {
			depthLeftOrder(listAux, currentNode);
		}
	}

	/**
	 * Conta a quantidade de nodes da BTree;
	 * 
	 * @param node
	 * @return - int com quantidade de nodes
	 */
	private int countNodes(BNode<T> node) {
		int qutNodes = 0;
		if (!node.isEmpty()) {
			LinkedList<BNode<T>> childrenOfNode = node.getChildren();
			
			for (int i = 0; i < childrenOfNode.size(); i++) {
				qutNodes = 1 + this.countNodes(childrenOfNode.get(i));
			}
			
		}
		return qutNodes;
	}

	@Override
	public int size() {
		return size(this.root);
	}

	private int size(BNode<T> node) {
		int size = 0;
		if (!node.isEmpty()) {
			LinkedList<BNode<T>> childrenOfNode = node.getChildren();
			size += node.size();
			
			for (int i = 0; i < childrenOfNode.size(); i++) {
				size += this.size(childrenOfNode.get(i));
			}
			
		}
		return size;
	}

	@Override
	public BNodePosition<T> search(T element) {
		BNodePosition<T> wanted = null;
		if (element != null) {
			wanted = search(this.root, element);
		}
		return wanted;
	}

	private BNodePosition<T> search(BNode<T> node, T element) {
		BNodePosition<T> wanted = null;
		int index = 0;

		while (index < node.getElements().size() && element.compareTo(node.getElementAt(index)) > 0) {
			index++;
		}

		if (index < node.getElements().size() && element.compareTo(node.getElementAt(index)) == 0) {
			wanted = new BNodePosition<T>(node, index);
		}

		if (node.isLeaf()) {
			wanted = new BNodePosition<T>();
		}

		if (wanted == null) {
			wanted = search(node.getChildren().get(index), element);
		}

		return wanted;
	}

	@Override
	public void insert(T element) {
		if (element != null && this.search(element).isEmpty()) {
			insert(this.root, element);
		}
	}

	private void insert(BNode<T> node, T element) {
		if (node.isFull()) {
			split(node);
			if (node.getParent() != null)
				node = node.getParent();
		}
		if (node.getChildren().isEmpty()) {
			node.addElement(element);
		} else {
			int i;
			for (i = 0; i < node.size(); i++) {
				T e = node.getElements().get(i);
				if (e.compareTo(element) > 0) {
					insert(node.getChildren().get(i), element);
					break;
				}
			}
			if (i == node.size()) {
				while (node.getChildren().size() <= i) {
					BNode<T> nNode = new BNode<T>(order);
					nNode.setParent(node);
					node.getChildren().add(nNode);
				}
				insert(node.getChildren().get(i), element);
			}
		}
	}

	private void split(BNode<T> node) {
		BNode<T> right = new BNode<>(order);
		BNode<T> left = new BNode<>(order);

		T middle = node.getElements().get((order - 1) / 2);
		boolean addInLeft = true;
		for (T e : node.getElements()) {
			if (e.equals(middle)) {
				addInLeft = false;
			} else if (addInLeft) {
				left.addElement(e);
			} else {
				right.addElement(e);
			}
		}

		if (!node.equals(this.root)) {
			promote(node, left, right);
		} else {
			for (int i = 0; i < node.getChildren().size(); i++) {
				if (i <= (order - 1) / 2) {
					left.addChild(i, node.getChildren().get(i));
				} else {
					right.addChild(i - ((order - 1) / 2) - 1, node.getChildren().get(i));
				}
			}
			left.setParent(node);
			right.setParent(node);

			node.getChildren().clear();
			node.addChild(0, left);
			node.addChild(1, right);

			node.getElements().clear();
			node.addElement(middle);
		}
	}

	private void promote(BNode<T> node, BNode<T> leftChild, BNode<T> rightChild) {

		BNode<T> parent = node.getParent();
		T element = node.getElementAt((order - 1) / 2);
		parent.addElement(element);

		int position = parent.getElements().indexOf(element);
		parent.removeChild(node);

		leftChild.setParent(parent);
		rightChild.setParent(parent);

		node.parent.removeChild(node);
		node.parent.addChild(position, rightChild);
		node.parent.addChild(position, leftChild);
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
