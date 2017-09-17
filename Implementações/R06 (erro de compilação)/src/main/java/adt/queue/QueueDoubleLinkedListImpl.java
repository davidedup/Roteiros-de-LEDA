package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T> implements Queue<T> {

   protected DoubleLinkedList<T> list;
   protected int size;

   public QueueDoubleLinkedListImpl(int size) {
      this.size = size;
      this.list = new DoubleLinkedListImpl<T>();
   }

   @Override
   public void enqueue(T element) throws QueueOverflowException {
      if (isFull()) {
         throw new QueueOverflowException();
      }
      this.list.insert(element);
      this.size++;
   }

   @Override
   public T dequeue() throws QueueUnderflowException {
      if (isEmpty()) {
         throw new QueueUnderflowException();
      }

      T aux = this.list.toArray()[0];

      this.list.removeFirst();

      this.size--;
      return aux;

   }

   @Override
   public T head() {
      if (isEmpty()) {
         return null;
      }
      return this.list.toArray()[0];
   }

   @Override
   public boolean isEmpty() {
      if (this.size == 0) {
         return true;
      }
      return false;
   }

   @Override
   public boolean isFull() {
      if (this.size >= this.list.size()) {
         return true;
      }
      return false;
   }

}
