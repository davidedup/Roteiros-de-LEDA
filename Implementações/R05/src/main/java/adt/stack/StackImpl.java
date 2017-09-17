package adt.stack;
   
   public class StackImpl<T> implements Stack<T> {
   
      private T[] array;
      private int top;
   
      @SuppressWarnings("unchecked")
      public StackImpl(int size) {
        array = (T[]) new Object[size];
        top = -1;
     }
  
     @Override
     public T top() {
        if (isEmpty()) {
           return null;
        }
        return array[top];
     }
  
     @Override
     public boolean isEmpty() {
        if (top == -1) {
           return true;
        }
        return false;
     }
  
     @Override
     public boolean isFull() {
        if (top + 1 >= array.length) {
           return true;
        }
        return false;
     }
  
     @Override
     public void push(T element) throws StackOverflowException {
        if (isFull()) {
           throw new StackOverflowException();
        }
        this.array[++this.top] = element;
     }
  
     @Override
     public T pop() throws StackUnderflowException {
        if (isEmpty()) {
           throw new StackUnderflowException();
        }
        return this.array[this.top--];
     }
  
  }