package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm.
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {

		// condicoes improprias do array para ordenacao:
		if (leftIndex < 0 || rightIndex < 0 || leftIndex == rightIndex || rightIndex >= array.length
				|| array.length == 0 || leftIndex > rightIndex) {
			return;
		}

		final double FATOR = 1.25;
		int tamanhoDoArray = rightIndex - leftIndex + 1;
		int gap = (int) (tamanhoDoArray / FATOR);
		int i = leftIndex;

		while (gap >= 1) {
			if ((gap + i) > rightIndex) {
				gap = (int) (gap / FATOR);
				i = 0;
			} else if (array[i].compareTo(array[gap + i]) > 0) {
				Util.swap(array, i, gap + i);
				i++;
			} else {
				i++;
			}
		}
	}
}