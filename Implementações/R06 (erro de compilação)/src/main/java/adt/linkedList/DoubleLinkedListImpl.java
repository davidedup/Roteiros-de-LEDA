package adt.linkedList;
//top
public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	public DoubleLinkedListNode<T> last;

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			if (this.isEmpty()) {
				DoubleLinkedListNode<T> toAdd = new DoubleLinkedListNode<>(element, this.newNodeNIL(),
						this.newNodeNIL());
				this.setHead(toAdd);
				this.setLast(toAdd);
			} else {
				DoubleLinkedListNode<T> aux = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) getHead(),
						this.newNodeNIL());
				((DoubleLinkedListNode<T>) this.getHead()).setPrevious(aux);
				this.setHead(aux);
			}
		}
	}

	@Override
	public void removeLast() {
		if (!this.isEmpty()) {
			this.getLast().getPrevious().setNext(newNodeNIL());

			if (this.size() == 1) {
				this.setHead(this.getLast().getPrevious());
			}
			this.setLast(this.getLast().getPrevious());

		}

	}

	@Override
	public void insert(T element) {
		if (element != null) {
			if (isEmpty()) {
				DoubleLinkedListNode<T> toAdd = new DoubleLinkedListNode<>(element, newNodeNIL(), newNodeNIL());
				this.setHead(toAdd);
				this.setLast(toAdd);
			} else {
				DoubleLinkedListNode<T> toAdd = new DoubleLinkedListNode<T>(element, newNodeNIL(), this.getLast());
				this.getLast().setNext(toAdd);
				setLast(toAdd);
			}

		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			((DoubleLinkedListNode<T>) this.getHead().getNext()).setPrevious(newNodeNIL());
			if (this.size() == 1) {
				this.setLast((DoubleLinkedListNode<T>) getHead().getNext());
			}
			this.setHead(getHead().getNext());
		}
	}

	public DoubleLinkedListNode<T> newNodeNIL() {
		return new DoubleLinkedListNode<T>();
	}
	
	@Override
	public void remove(T element) {
		if (element != null && !this.isEmpty()) {
			if (this.getHead().equals(element)) {
				DoubleLinkedListNode<T> aux = (DoubleLinkedListNode<T>) this.head;
				aux.getPrevious().setNext(super.head.getNext());
			} else {
				@SuppressWarnings("unchecked")
				DoubleLinkedListNode<T> aux = (DoubleLinkedListNode<T>) super.search(element);
				if (aux != null) {
					aux.getPrevious().setNext(aux.getNext());
					((DoubleLinkedListNode<T>) aux.getNext()).setPrevious(aux.getPrevious());
					if (aux.getNext().isNIL()) {
						setLast(aux.getPrevious());
					}
				}
			}
		}
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

	public SingleLinkedListNode<T> getHead() {
		return super.head;
	}

}