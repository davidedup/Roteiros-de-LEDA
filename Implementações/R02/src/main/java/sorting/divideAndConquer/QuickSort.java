
package sorting.divideAndConquer;

import sorting.AbstractSorting;
import util.Util;

/**
 * Quicksort is based on the divide-and-conquer paradigm. The algorithm chooses
 * a pivot element and rearranges the elements of the interval in such a way
 * that all elements lesser than the pivot go to the left part of the array and
 * all elements greater than the pivot, go to the right part of the array. Then
 * it recursively sorts the left and the right parts. Notice that if the list
 * has length == 1, it is already sorted.
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		// condi��es improprias do array para ordena��o:
		if (leftIndex < 0 || leftIndex < 0 || leftIndex == rightIndex || rightIndex > array.length || array.length == 0
				|| leftIndex > rightIndex) {
			return;
		}

		if (leftIndex < rightIndex) {
			int posPivot = particiona(array, leftIndex, rightIndex);
			sort(array, leftIndex, posPivot - 1);
			sort(array, posPivot + 1, rightIndex);
		}
	}

	public int particiona(T[] array, int inicio, int fim) {
		T valorPivot = array[inicio];
		int ultimaPosPivot = inicio;

		for (int i = inicio + 1; i <= fim; i++) {
			if (array[i].compareTo(valorPivot) < 0) {
				ultimaPosPivot++;
				Util.swap(array, i, ultimaPosPivot);
			}
		}
		Util.swap(array, inicio, ultimaPosPivot);
		return ultimaPosPivot;
	}
}