package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {

		// condi��es improprias do array para ordena��o:
		if (leftIndex < 0 || leftIndex < 0 || leftIndex == rightIndex || rightIndex > array.length || array.length == 0
				|| leftIndex > rightIndex) {
			return;
		}
		final int MAIOR = getMaior(array, leftIndex, rightIndex);
		Integer[] arrayOrdenado = new Integer[rightIndex - leftIndex + 1];
		int[] contadores = new int[MAIOR + 1];

		for (int i = leftIndex; i <= rightIndex; i++) {
			contadores[array[i]]++;
		}
		for (int i = 1; i < contadores.length; i++) {
			contadores[i] += contadores[i - 1];
		}
		for (int i = rightIndex; i >= 0; i--) {
			arrayOrdenado[contadores[array[i]] - 1] = array[i];
			contadores[array[i]]--;
		}

		// Copia o trecho do array que foi ordenado para dentro do array:
		for (int i = leftIndex, j = 0; i <= rightIndex; i++, j++) {
			array[i] = arrayOrdenado[j];
		}
	}

	// Retorna o maior elemtento da lista:
	private int getMaior(Integer[] array, int leftIndex, int rigthIndex) {

		int maior = array[leftIndex];

		for (int i = leftIndex + 1; i <= rigthIndex; i++) {
			if (array[i].compareTo(maior) > 0) {
				maior = array[i];
			}
		}
		return maior;
	}

}