package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * The selection sort algorithm chooses the smallest element from the array and
 * puts it in the first position. Then chooses the second smallest element and
 * stores it in the second position, and so on until the array is sorted.
 */
public class SelectionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {

		// condi��es improprias do array para ordena��o:
		if (leftIndex < 0 || leftIndex < 0 || leftIndex == rightIndex || rightIndex > array.length || array.length == 0
				|| leftIndex > rightIndex) {
			return;
		}

		for (int i = leftIndex; i < rightIndex; i++) {
			int indexDoMin = i;
			for (int j = i + 1; j <= rightIndex; j++) {
				if (array[indexDoMin].compareTo(array[j]) > 0) {
					indexDoMin = j;
				}
			}
			if (indexDoMin != i) {
				Util.swap(array, indexDoMin, i);
			}
		}
	}
}
