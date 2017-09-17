package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The implementation of the algorithm must be in-place!
 */
public class GnomeSort<T extends Comparable<T>> extends AbstractSorting<T> {
	public void sort(T[] array, int leftIndex, int rightIndex) {

		// condicoes improprias do array para ordenacao:
		if (leftIndex < 0 || rightIndex < 0 || leftIndex == rightIndex || rightIndex >= array.length
				|| array.length == 0 || leftIndex > rightIndex) {
			return;
		}

		int indexPivot = leftIndex;

		while (indexPivot < rightIndex) {

			int i = indexPivot + 1;

			if (indexPivot < leftIndex) {
				indexPivot++;
			} else if (array[i].compareTo(array[indexPivot]) >= 0) {
				indexPivot++;
			} else {
				Util.swap(array, i, indexPivot);
				indexPivot--;
			}
		}
	}
}