package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	@Override
	public void insert(int key, T newValue, int height) {
		if (newValue != null && height <= this.maxHeight) {
			@SuppressWarnings("unchecked")
			SkipListNode<T>[] update = new SkipListNode[this.maxHeight];
			SkipListNode<T> currentNode = this.root;

			for (int i = height; i >= 0; i--) {

				while (currentNode.getForward(i) != null && currentNode.getForward(i).getKey() < key) {
					currentNode = currentNode.getForward(i);
				}

				update[i] = currentNode;
			}

			currentNode = currentNode.getForward(0);

			if (currentNode.getKey() == key) {
				if (currentNode.height() == height) {
					currentNode.setValue(newValue);
				} else {
					remove(key);
					insert(key, newValue, height);
				}
			} else {
				SkipListNode<T> auxNode = new SkipListNode<T>(key, height, newValue);
				for (int i = 0; i < height; i++) {
					auxNode.forward[i] = update[i].forward[i];
					update[i].forward[i] = auxNode;
				}
			}
		}
	}

	@Override
	public void remove(int key) {
		SkipListNode<T>[] update = new SkipListNode[this.maxHeight];
		SkipListNode<T> currentNode = this.root;

		for (int i = this.maxHeight - 1; i >= 0; i--) {

			while (currentNode.getForward(i) != null && currentNode.getForward(i).getKey() < key) {
				currentNode = currentNode.getForward(i);
			}

			update[i] = currentNode;
		}

		currentNode = currentNode.forward[0];

		if (currentNode.getKey() == key) {
			for (int i = 0; i < this.maxHeight; i++) {
				if (!update[i].getForward(i).equals(currentNode)) {
					break;
				}
				update[i].forward[i] = currentNode.forward[i];
			}
		}
	}

	@Override
	public int height() {
		int height = 0;
		SkipListNode<T> currentNode = this.root.getForward(0);

		while (currentNode != this.NIL) {

			if (currentNode.height() > height) {
				height = currentNode.height();
			}
			currentNode = currentNode.getForward(0);
		}

		return height;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> currentNode = this.root;
		for (int i = this.maxHeight - 1; i >= 0; i--) {

			while (currentNode.forward[i].getKey() < key) {
				currentNode = currentNode.forward[i];
			}

		}

		currentNode = currentNode.forward[0];

		if (currentNode.getKey() != key) {
			currentNode = null;
		}

		return currentNode;
	}

	@Override
	public int size() {
		int size = 0;
		SkipListNode<T> currentNode = this.root;

		while (currentNode != null) {
			size++;
			currentNode = currentNode.getForward(0);
		}

		return size - 2;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		@SuppressWarnings("unchecked")
		SkipListNode<T>[] array = new SkipListNode[size() + 2];
		int index = 0;
		SkipListNode<T> currentNode = this.root;

		while (currentNode != null) {
			array[index++] = currentNode;
			currentNode = currentNode.getForward(0);
		}

		return array;
	}
}
