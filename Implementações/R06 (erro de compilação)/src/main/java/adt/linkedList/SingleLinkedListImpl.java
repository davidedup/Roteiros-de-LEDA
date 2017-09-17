package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return this.head.isNIL();
	}

	@Override
	public int size() {
		int size = 0;
		if (!isEmpty()) {
			SingleLinkedListNode<T> currentNode = this.getHead();
			while (!currentNode.isNIL()) {
				size++;
				currentNode = currentNode.getNext();
			}
		}
		return size;
	}

	@Override
	public T search(T element) {
		T wanted = null;
		if (element != null) {
			SingleLinkedListNode<T> currentNode = this.getHead();
			while (currentNode != null && !currentNode.isNIL() && !currentNode.getData().equals(element)) {
				currentNode = currentNode.getNext();
			}
			if (currentNode != null && !currentNode.isNIL() && currentNode.getData().equals(element)) {
				wanted = currentNode.getData();
			}
		}
		return wanted;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			SingleLinkedListNode<T> currentNode = this.getHead();
			while (!currentNode.isNIL() && currentNode.getNext() != null) {
				currentNode = currentNode.getNext();
			}
			currentNode.setData(element);
			currentNode.setNext(new SingleLinkedListNode<T>());
		}

	}

	@Override
	public void remove(T element) {
		if (!isEmpty() && element != null && this.search(element) != null) {
			
			SingleLinkedListNode<T> previous = new SingleLinkedListNode<T>();
			SingleLinkedListNode<T> current = this.getHead();
			boolean aux = true;

			while (aux) {
				
				if (current.getData() != element) {
					previous = current;
					current = current.getNext();
				} else {
					previous.setNext(current.getNext());
					aux = false;
				}
			
			}
		}
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] arrayOfElements = (T[]) new Object[this.size()];
		if (!this.isEmpty()) {
			int i = 0;
			SingleLinkedListNode<T> currentNode = this.head;
			while (!currentNode.isNIL() && currentNode.getNext() != null) {
				arrayOfElements[i] = currentNode.data;
				i++;
				currentNode = currentNode.next;
			}
		}
		return arrayOfElements;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
