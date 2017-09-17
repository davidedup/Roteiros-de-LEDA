   package sorting.divideAndConquer.threeWayQuicksort;
   
   import sorting.AbstractSorting;
   import util.Util;
   
   public class ThreeWayQuickSort<T extends Comparable<T>> extends
   		AbstractSorting<T> {
   
   	/**
  	 * No algoritmo de quicksort, selecionamos um elemento como pivot,
  	 * particionamos o array colocando os menores a esquerda do pivot 
  	 * e os maiores a direita do pivot, e depois aplicamos a mesma estrategia 
  	 * recursivamente na particao a esquerda do pivot e na particao a direita do pivot. 
  	 * 
  	 * Considerando um array com muitoe elementos repetidos, a estrategia do quicksort 
  	 * pode ser otimizada para lidar de forma mais eficiente com isso. Essa melhoria 
  	 * eh conhecida como quicksort tree way e consiste da seguinte ideia:
  	 * - selecione o pivot e particione o array de forma que
  	 *   * arr[l..i] contem elementos menores que o pivot
  	 *   * arr[i+1..j-1] contem elementos iguais ao pivot.
  	 *   * arr[j..r] contem elementos maiores do que o pivot. 
  	 *   
  	 * Obviamente, ao final do particionamento, existe necessidade apenas de ordenar
  	 * as particoes contendo elementos menores e maiores do que o pivot. Isso eh feito
  	 * recursivamente. 
  	 **/
  	@Override
  	public void sort(T[] array, int leftIndex, int rightIndex) {
  		//condi��es improprias do array para ordena��o:	
  		if ( leftIndex < 0 || leftIndex < 0 || leftIndex == rightIndex || rightIndex > array.length || array.length == 0 ||	
  			leftIndex > rightIndex){
  				return; 
  		}
  				
  		if(leftIndex < rightIndex){
  			int[] indices = particiona(array, leftIndex, rightIndex);
  			int j = indices[0];
  			int i = indices[1];	
  			sort(array, leftIndex, j - 1);
  			sort(array, i + 1, rightIndex);		
  		}
  	}
  
  	public int[] particiona(T[] array , int inicio , int fim) {
  		int[] indices = new int[2];
  		T valorDoPivot = array[inicio];
  		int j = inicio;
  		int i = fim; 
  		int k = inicio+1;
  		int pivot = inicio;
  		
  		while(k <= i){
  			if(valorDoPivot.compareTo(array[k]) > 0){
  				Util.swap(array, j++, k++);
  			} else if (array[k].compareTo(valorDoPivot) > 0){
  				Util.swap(array, k, i--);
  			} else {
  				k++;
  			}
  		}
  		
  		indices[0] = j;
  		indices[1] = i;
  		return indices;	
  	}
  }