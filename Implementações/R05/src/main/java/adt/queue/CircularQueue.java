package adt.queue;

public class CircularQueue<T> implements Queue<T> {

	final int ZERO = 0;
	private T[] array;
	private int tail;
	private int head;
	private int elements;

	public CircularQueue(int size) {
		array = (T[]) new Object[size];
		head = -1;
		tail = -1;
		elements = 0;
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull()) {
			throw new QueueOverflowException();
		}

		if (isEmpty()) {
			this.head = ZERO;
			this.tail = ZERO;
			this.array[ZERO] = element;
		} else {
			this.tail = (this.tail + 1) % array.length;
			this.array[this.tail] = element;
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException();
		}

		T primeiroNaFila = this.array[this.head];

		if (this.head == this.tail) {
			this.head = -1;
			this.tail = -1;
		} else {
			this.head = (head + 1) % this.array.length;
		}
		return primeiroNaFila;
	}

	@Override
     public T head() {
        if (isEmpty()) {
           return null;
        }
        return this.array[head];
     }

	@Override
	public boolean isEmpty() {
		if (this.head == -1 && this.tail == -1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isFull() {
		if ((this.tail + 1) % array.length == this.head) {
			return true;
		}
		return false;
	}

}