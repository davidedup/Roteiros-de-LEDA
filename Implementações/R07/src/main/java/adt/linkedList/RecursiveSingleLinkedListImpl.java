package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {

	}

	public RecursiveSingleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {
		return (this.getData() == null);
			
	}

	@Override
	public int size() {
		if (isEmpty()) {
			return 0;
		} else {
			return 1 + this.next.size();
		}
	}

	@Override
	public T search(T element) {
		if (isEmpty()) {
			return null;
		} else {
			
			if (this.getData().equals(element)) {
				return this.getData();
			} else {
				return this.getNext().search(element);
			}
			
		}
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			
			if (isEmpty()) {
				this.setData(element);
				this.setNext(new RecursiveSingleLinkedListImpl<T>());
			} else {
				this.getNext().insert(element);
			}
			
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty() && element!= null) {
			
			if (this.getData().equals(element)) {
				this.setData(this.getNext().getData());
				this.setNext(this.getNext().getNext());
			} else {
				this.getNext().remove(element);
			}
			
		}
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] arrayOfElements = (T[]) new Object[this.size()];
		toArray(arrayOfElements, this, 0);
		return arrayOfElements;
	}

	private void toArray(T[] arrayOfElements, RecursiveSingleLinkedListImpl<T> currentNode, int indice) {
		if(!currentNode.isEmpty()){
			arrayOfElements[indice] = currentNode.getData();
			toArray(arrayOfElements,currentNode.getNext(), indice + 1);
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}