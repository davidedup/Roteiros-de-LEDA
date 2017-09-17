package adt.hashtable.closed;

import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;
import util.Util;

public class HashtableClosedAddressImpl<T> extends AbstractHashtableClosedAddress<T> {

	/**
	 * A hash table with closed address works with a hash function with closed
	 * address. Such a function can follow one of these methods: DIVISION or
	 * MULTIPLICATION. In the DIVISION method, it is useful to change the size
	 * of the table to an integer that is prime. This can be achieved by
	 * producing such a prime number that is bigger and close to the desired
	 * size.
	 * 
	 * For doing that, you have auxiliary methods: Util.isPrime and
	 * getPrimeAbove as documented bellow.
	 * 
	 * The length of the internal table must be the immediate prime number
	 * greater than the given size. For example, if size=10 then the length must
	 * be 11. If size=20, the length must be 23. You must implement this idea in
	 * the auxiliary method getPrimeAbove(int size) and use it.
	 * 
	 * @param desiredSize
	 * @param method
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashtableClosedAddressImpl(int desiredSize, HashFunctionClosedAddressMethod method) {
		int realSize = desiredSize;

		if (method == HashFunctionClosedAddressMethod.DIVISION) {
			realSize = this.getPrimeAbove(desiredSize); // real size must the
			// the immediate prime
			// above
		}
		initiateInternalTable(realSize);
		HashFunction function = HashFunctionFactory.createHashFunction(method, realSize);
		this.hashFunction = function;
	}

	// AUXILIARY
	/**
	 * It returns the prime number that is closest (and greater) to the given
	 * number. You can use the method Util.isPrime to check if a number is
	 * prime.
	 */

	int getPrimeAbove(int number) {
		int i = number;
		boolean isPrime = false;

		while (!isPrime) {

			if (Util.isPrime(i)) {
				isPrime = true;
			} else {
				i++;
			}

		}

		return i;
	}

	@Override
	public void insert(T element) {
		if (element != null && this.search(element) == null) {

			int hashCode = this.getHash(element);

			if (this.table[hashCode] == null) {
				this.table[hashCode] = new LinkedList<T>();
				this.getCell(hashCode).add(element);
			} else {
				this.getCell(hashCode).add(element);
				this.COLLISIONS++;
			}

			this.elements++;
		}

	}

	@Override
	public void remove(T element) {
		if (element != null && !this.isEmpty()) {

			int hashCode = this.getHash(element);

			if (this.getCell(hashCode) != null && this.getCell(hashCode).contains(element)) {
				this.getCell(hashCode).remove(element);
				this.COLLISIONS--;
				this.elements--;
			}

		}
	}

	@Override
	public T search(T element) {
		T wanted = null;
		if (element != null) {

			int hashCode = this.getHash(element);

			if (this.getCell(hashCode) != null && this.getCell(hashCode).contains(element)) {
				wanted = element;
			}
			
		}
		return wanted;
	}

	@Override
	public int indexOf(T element) {
		int indexOfElemente = -1;
		
		if (element != null) {
			
			int hashCode = this.getHash(element);
			
			if (this.getCell(hashCode) != null && this.getCell(hashCode).contains(element)) {
					indexOfElemente = hashCode;
			}
			
		}
		return indexOfElemente;
	}

	// Metodo auxiliar para fazer o cast e retorna o numero Hash do Objeto element.
	public int getHash(T element) {
		int hashCode = ((HashFunctionClosedAddress<T>) this.getHashFunction()).hash(element);
		return hashCode;
	}

	// Faz o cast e retorna a celular a qual pertence o hashCode que é passado
	// como parametro
	@SuppressWarnings("unchecked")
	public LinkedList<T> getCell(int hashCode) {
		return ((LinkedList<T>) this.table[hashCode]);
	}

}
