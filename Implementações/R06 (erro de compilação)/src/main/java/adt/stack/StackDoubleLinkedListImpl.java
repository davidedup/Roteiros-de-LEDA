package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class StackDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if(isFull()){
			throw new StackOverflowException();
		}
		top.insert(element);
	}

	@Override
	public T pop() throws StackUnderflowException {
		if(isEmpty()){
			throw new StackUnderflowException();
		}
		T[] array = this.top.toArray();
		T aux = array[array.length -1];
		
		this.top.removeLast();
		
		return aux;
	}

	@Override
	public T top() {
		if(isEmpty()){
			return null;
		}
		T[] array = this.top.toArray();
		return array[array.length -1];
	}

	@Override
	public boolean isEmpty() {
		if(this.size == 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean isFull() {
		if(this.size >= top.size()){
			return true;
		}
		return false;
	}

}
