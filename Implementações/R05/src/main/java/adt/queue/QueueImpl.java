 package adt.queue;
   
   public class QueueImpl<T> implements Queue<T> {
   
   	private T[] array;
   	private int tail;
   	final int POS = 0;
   
   	@SuppressWarnings("unchecked")
  	public QueueImpl(int size) {
  		array = (T[]) new Object[size];
  		tail = -1;
  	}
  
  	@Override
  	public T head() {
  		if (isEmpty()) {
  			return null;
  		}
  		return array[POS];
  	}
  
  	@Override
  	public boolean isEmpty() {
  		if (this.tail == -1) {
  			return true;
  		}
  		return false;
  	}
  
  	@Override
  	public boolean isFull() {
  		if (this.tail + 1 >= this.array.length) {
  			return true;
  		}
  		return false;
  	}
  
  	private void shiftLeft() {
  		for (int i = 0; i < array.length - 1; i++) {
  			array[i] = array[i + 1];
  		}
  		this.tail--;
  	}
  
  	@Override
  	public void enqueue(T element) throws QueueOverflowException {
  		if (isFull()) {
  			throw new QueueOverflowException();
  		}
  		if (element == null) {
  			return;
  		}
  		this.array[++this.tail] = element;
  	}
  
  	@Override
  	public T dequeue() throws QueueUnderflowException {
  		T elemento;
  		if (isEmpty()) {
  			throw new QueueUnderflowException();
  		}
  
  		elemento = array[POS];
  		shiftLeft();
  		return elemento;
  	}
  
  }