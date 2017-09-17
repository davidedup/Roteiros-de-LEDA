package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		if (element != null && this.indexOf(element) == -1) {

			if (this.isFull()) {
				throw new HashtableOverflowException();
			}

			int probe = 0;
			boolean found = false;
			int hashAtual = this.getHash(element, probe);

			while (!found) {

				if (this.table[hashAtual] == null || this.table[hashAtual].equals(super.deletedElement)) {
					found = true;
					this.table[hashAtual] = element;
				} else {
					probe++;
					this.COLLISIONS++;
					hashAtual = this.getHash(element, probe);
				}

			}
			this.elements++;
		}
	}

	@Override
	public void remove(T element) {
		if (element != null) {
			int indexOfElement = this.indexOf(element);

			if (indexOfElement != -1) {
				this.table[indexOfElement] = super.deletedElement;
				this.elements--;
			}

		}
	}

	@Override
	public T search(T element) {
		T wanted = null;
		if (element != null) {

			if (this.indexOf(element) != -1) {
				wanted = element;
			}

		}
		return wanted;
	}

	@Override
	public int indexOf(T element) {
		int indexOfElement = -1;
		if (element != null) {

			int prob = 0;
			boolean found = false;
			int hashAtual = this.getHash(element, prob);

			while (!found && prob < super.capacity() && this.table[hashAtual] != null) {

				if (this.table[hashAtual].equals(element)) {
					found = true;
					indexOfElement = hashAtual;
				} else {
					hashAtual = this.getHash(element, ++prob);
				}

			}
		}
		return indexOfElement;
	}

	public int getHash(T element, int prob) {
		return ((HashFunctionOpenAddress<T>) this.getHashFunction()).hash(element, prob);
	}
}