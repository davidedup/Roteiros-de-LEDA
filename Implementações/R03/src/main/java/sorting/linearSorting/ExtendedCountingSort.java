package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {

		// condi��es improprias do array para ordena��o:
		if (leftIndex < 0 || leftIndex < 0 || leftIndex == rightIndex || rightIndex > array.length || array.length == 0
				|| leftIndex > rightIndex) {
			return;
		}

		final Integer[] maiorAndMenor = getMaiorAndMenor(array, leftIndex, rightIndex);
		final int MAIOR = maiorAndMenor[0];
		final int MENOR = maiorAndMenor[1];
		Integer[] arrayOrdenado = new Integer[rightIndex - leftIndex + 1];
		int[] contadores = new int[MAIOR - MENOR + 1];

		for (int i = leftIndex; i <= rightIndex; i++) {
			contadores[array[i] - MENOR]++;
		}
		for (int i = 1; i < contadores.length; i++) {
			contadores[i] += contadores[i - 1];
		}
		for (int i = rightIndex; i >= 0; i--) {
			arrayOrdenado[contadores[array[i] - MENOR] - 1] = array[i];
			contadores[array[i] - MENOR]--;
		}

		// Copia o trecho do array que foi ordenado para dentro do array:
		for (int i = leftIndex, j = 0; i <= rightIndex; i++, j++) {
			array[i] = arrayOrdenado[j];
		}
	}

	// Retorna um array de duas posi��es com o maior na primeira posi��o e menor
	// na segunda:
	private Integer[] getMaiorAndMenor(Integer[] array, int leftIndex, int rigthIndex) {

		int maior = array[leftIndex];
		int menor = array[leftIndex];
		for (int i = leftIndex + 1; i <= rigthIndex; i++) {
			if (array[i].compareTo(maior) > 0) {
				maior = array[i];
			}
			if (array[i].compareTo(menor) < 0) {
				menor = array[i];
			}
		}
		Integer[] valores = { maior, menor };
		return valores;
	}

}