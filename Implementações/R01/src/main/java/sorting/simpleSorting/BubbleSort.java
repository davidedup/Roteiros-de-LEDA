package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * The bubble sort algorithm iterates over the array multiple times, pushing big
 * elements to the right by swapping adjacent elements, until the array is
 * sorted.
 */
public class BubbleSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {

		// condi��es improprias do array para ordena��o:
		if (leftIndex < 0 || leftIndex < 0 || leftIndex == rightIndex || rightIndex > array.length || array.length == 0
				|| leftIndex > rightIndex) {
			return;
		}

		// isNotSorted = variavel flag pois se o for n�o fizer nenhuma troca �
		// pq ja esta ordenado e n�o precisa mais intera��es.
		boolean isNotSorted = true;
		while (isNotSorted) {
			isNotSorted = false;
			for (int i = leftIndex; i < rightIndex; i++) {
				if (array[i].compareTo(array[i + 1]) > 0) {
					Util.swap(array, i, i + 1);
					isNotSorted = true;
				}
			}
		}
	}
}