package sorting.divideAndConquer;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm. The algorithm
 * consists of recursively dividing the unsorted list in the middle, sorting
 * each sublist, and then merging them into one single sorted list. Notice that
 * if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		// condi��es improprias do array para ordena��o:
		if (leftIndex < 0 || leftIndex < 0 || leftIndex == rightIndex || rightIndex > array.length || array.length == 0
				|| leftIndex > rightIndex) {
			return;
		}

		if (leftIndex < rightIndex) {
			int med = (leftIndex + rightIndex) / 2;
			sort(array, leftIndex, med);
			sort(array, med + 1, rightIndex);
			merge(array, leftIndex, rightIndex, med);
		}
	}

	public void merge(T[] array, int inicio, int fim, int med) {

		T[] helper = Arrays.copyOf(array, array.length);

		int i = inicio;
		int j = med + 1;
		int k = inicio;

		while (i <= med && j <= fim) {
			if (helper[i].compareTo(helper[j]) > 0) {
				array[k] = helper[j];
				j++;
			} else {
				array[k] = helper[i];
				i++;
			}
			k++;
		}

		// Adiciona o resto dos elementos da lista que n�o foram adicionados na
		// primeira intera��o
		while (i <= med) {
			array[k] = helper[i];
			k++;
			i++;
		}
		while (j <= fim) {
			array[k] = helper[j];
			k++;
			j++;
		}
	}
}