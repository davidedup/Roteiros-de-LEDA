package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;
	final int ONE = 1;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			RecursiveDoubleLinkedListImpl<T> toAdd = new RecursiveDoubleLinkedListImpl<T>(this.getData(),
					this.getNext(), this);
			this.setNext(toAdd);
			this.setData(element);
		}	
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			
			if (this.size() == ONE) {
				this.setData(null);
				this.setNext(new RecursiveSingleLinkedListImpl<T>());
				this.setPrevious(new RecursiveDoubleLinkedListImpl<T>());
			} else {
				this.setData(this.getNext().getData());
				RecursiveDoubleLinkedListImpl<T> auxCast = (RecursiveDoubleLinkedListImpl<T>) this.getNext().getNext();
				auxCast.setPrevious(this);
				this.setNext(this.getNext().getNext());
			}
		}
		
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			
			if(this.getNext().isEmpty()){
				this.setData(null);
				this.getNext().setNext(new RecursiveDoubleLinkedListImpl<T>());;
			}else{
				RecursiveDoubleLinkedListImpl<T> auxCast = (RecursiveDoubleLinkedListImpl<T>) this.getNext();
				auxCast.removeLast();
			}
			
		}
	}
			
	
	@Override
	public void insert(T element) {
		if (element != null) {
			
			if (isEmpty()) {
				this.data = element;
				this.setNext(new RecursiveDoubleLinkedListImpl<T>());
			} else {
				this.next.insert(element);
			}
			
		}
	}
	
	@Override
	public void remove(T element) {
		if (!isEmpty()) {

			if (this.data.equals(element)) {
				
				if (this.getPrevious() != null && this.getPrevious().isEmpty() && this.getNext().isEmpty()) { // se so tiver apenas um elemento
					this.setNext(null);
					this.setPrevious(null);
					this.setData(null);
				} else {
					this.setData(this.getNext().getData());
					this.setNext(this.getNext().getNext());
				
					if (this.getNext()!= null) {
						RecursiveDoubleLinkedListImpl<T> auxCast = (RecursiveDoubleLinkedListImpl<T>) this.getNext();
						auxCast.setPrevious(this);
					}
					
				}
			} else {
				this.getNext().remove(element);
			}
		}
	}
	
	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}