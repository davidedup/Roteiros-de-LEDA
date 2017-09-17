package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;
	final int ZERO = 0;
	
	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (element != null) {

			if (isFull()) {
				throw new StackOverflowException();
			}
			top.insertFirst(element);
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (isEmpty()) {
			throw new StackUnderflowException();
		}
		
		T topValue = top.toArray()[ZERO];
		top.removeFirst();
		return topValue;
	}

	@Override
	public T top() {
		if(isEmpty()){
			return null; 
		}
		return top.toArray()[ZERO];
	}

	@Override
	public boolean isEmpty() {
		if(top.size() == ZERO){
			return true;
		}
		return false;
	}

	@Override
	public boolean isFull() {
		if(this.size <= top.size()){
			return true;
		}
		return false;
	}
	
}
