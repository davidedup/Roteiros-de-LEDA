package adt.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import util.Util;
 //TOP
/**
 * O comportamento de qualquer heap √© definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 3 > 2),
 * essa heap deixa os elementos maiores no topo. Essa compara√ß√£o n√£o √© feita
 * diretamente com os elementos armazenados, mas sim usando um comparator. Dessa
 * forma, dependendo do comparator, a heap pode funcionar como uma max-heap ou
 * min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	protected T[] heap;
	protected int index = -1;
	private static int ZERO = 0;
	/**
	 * O comparador √© utilizado para fazer as compara√ß√µes da heap. O ideal √©
	 * mudar apenas o comparator e mandar reordenar a heap usando esse
	 * comparator. Assim os metodos da heap n√£o precisam saber se vai funcionar
	 * como max-heap ou min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma
	 * min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {

		int x = (i - 1) / 2;
		return x;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento
	 * indexado pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento
	 * indexado pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		T[] resp = Util.makeArrayOfComparable(index + 1);
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode
	 * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		if (position == ZERO) { // Remove
			heapfyDown(ZERO);
		} else { // Insert
			heapfyUp(position);
		}
	}

	/**
	 * Faz o processo de Heapfy de cima para baixo, levando o elemento
	 * adicionado na raiz para aposiÁ„o correta
	 * 
	 * @param position
	 *            - posicao na qual o elemento foi adicionado
	 */
	private void heapfyDown(int index) {
		int rightIndex = this.right(index);
		int leftIndex = this.left(index);
		int largest;

		if (leftIndex <= this.index && this.biggerElement(leftIndex, rightIndex) == leftIndex) {
			largest = leftIndex;
		} else {
			largest = index;
		}

		if (rightIndex <= this.index && this.biggerElement(rightIndex, largest) == rightIndex) {
			largest = rightIndex;
		}

		if (largest != index) {
			Util.swap(this.getHeap(), index, largest);
			this.heapfyDown(largest);
		}
	}

	/**
	 * Faz o processo de Heapfy de baixo para cima, levando o elemento
	 * adicionado para aposiÁ„o correta
	 * 
	 * @param position
	 *            - posicao na qual o elemento foi adicionado
	 */
	private void heapfyUp(int position) {
		int currentIndex = position;

		while (this.biggerElement(currentIndex, this.parent(currentIndex)) == currentIndex
				&& this.parent(currentIndex) != currentIndex) {
			Util.swap(this.getHeap(), currentIndex, this.parent(currentIndex));
			currentIndex = this.parent(currentIndex);
		}

	}

	/**
	 * Verifica qual o maior elemento com base e seu indice e retorna o indice
	 * do mesmo
	 * 
	 * @param elem1
	 *            - indice do elemnto1 no array
	 * @param elem2
	 *            - indide do elemento2 no array
	 * @return - o indice do maior elemento;
	 */
	private int biggerElement(int IndexOfElem1, int IndexOfElem2) {
		if (this.comparator.compare(this.getHeap()[IndexOfElem1], this.getHeap()[IndexOfElem2]) > ZERO)
			return IndexOfElem1;
		else
			return IndexOfElem2;
	}

	@Override
	public void insert(T element) {
		if (element != null) {

			if (index == heap.length - 1) {
				heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
			}

			this.getHeap()[++this.index] = element;
			this.heapify(this.index);
		}
	}

	@Override
	public void buildHeap(T[] array) {
		if (array != null & array.length > 0) {
			this.heap = (T[]) new Comparable[array.length];

			this.index = -1;

			for (int i = 0; i < array.length; i++) {
				if (array[i] != null)
					this.insert(array[i]);
			}

		}
	}

	// for (int i = 0; i < this.getHeap().length; i++) { // apagar os null, zero
	// o array da heap
	// if(this.heap[i] != null){
	// heap[i] = null;
	// }
	// }
	//
	// this.index = -1;
	//
	// for (int i = 0; i < array.length; i++) {
	// if (array[i] != null)
	// this.insert(array[i]);
	// }
	//
	// }

	@Override
	public T extractRootElement() {
		T root = null;

		if (!this.isEmpty()) {
			root = this.getHeap()[ZERO];
			this.getHeap()[ZERO] = this.getHeap()[this.index];
			this.index--;
			this.heapify(ZERO);
		}

		return root;
	}

	@Override
	public T rootElement() {
		T root = null;

		if (!this.isEmpty()) {
			root = this.getHeap()[ZERO];
		}

		return root;
	}

	@Override
	public T[] heapsort(T[] array) {
		Comparator<T> comparatorOriginal = this.comparator;

		this.setComparator((o1, o2) -> o1.compareTo(o2));
		this.buildHeap(array);

		T[] heapSorted = Util.makeArrayOfComparable(this.size());

		for (int i = this.index; i >= 0; i--) {
			heapSorted[i] = this.extractRootElement();
		}

		this.setComparator(comparatorOriginal);
		return heapSorted;

	}

	@Override
	public int size() {
		return this.index + 1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}

	// ESTUDANDO PARA A PROVA:

	// minucia de Robson -- nao testei
	public T[] mergeWithHeap2(T[] array1, T[] array2) {
		T[] mergedArray = Util.makeArrayOfComparable(array1.length + array2.length);
		if (array1 != null && array2 != null) {
			int index1 = 0;
			int index2 = 0;
			while (index1 < array1.length - 1 && index2 < array2.length - 1) {
				if (array1[index1].compareTo(array2[index2]) > 0) {
					this.insert(array2[index2]);
					index2++;
				} else {
					this.insert(array1[index1]);
					index1++;
				}
			}

			if (index1 < array1.length - 1) {
				while (index1 < array1.length - 1) {
					this.insert(array1[index1]);
					index1++;
				}
			}

			if (index2 < array2.length - 1) {
				while (index2 < array2.length - 1) {
					this.insert(array2[index2]);
					index2++;
				}
			}
		}
		return this.getHeap();
	}

	/**
	 * Se um dos atributos forem null retorna um array vazio;
	 * 
	 * 1. Implemente um algoritmo que recebe dois arrays A e B ordenados como
	 * par‚metros e usa uma heap para produzir um outro array C que È o
	 * resultado do merge de A e B.
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public T[] mergeWithHeap(T[] array1, T[] array2) {
		T[] mergedArray = Util.makeArrayOfComparable(array1.length + array2.length);
		if (array1 != null && array2 != null) {
			int index = 0;
			HeapImpl<T> heap1 = new HeapImpl<T>(this.comparator); // tem que
																	// definir
																	// nos teste
																	// na hora
																	// de criar
			HeapImpl<T> heap2 = new HeapImpl<T>(this.comparator);

			for (int i = 0; i < array1.length; i++) {
				heap1.insert(array1[i]);
			}

			for (int i = 0; i < array2.length; i++) {
				heap2.insert(array2[i]);
			}

			while (!heap1.isEmpty() && !heap2.isEmpty()) {
				if (this.comparator.compare(heap1.rootElement(), heap2.rootElement()) > 0) {
					mergedArray[index] = heap1.extractRootElement();
				} else {
					mergedArray[index] = heap2.extractRootElement();
				}
				index++;
			}

			if (!heap1.isEmpty()) {
				while (!heap1.isEmpty()) {
					mergedArray[index] = heap1.extractRootElement();
					index++;
				}
			}

			if (!heap2.isEmpty()) {
				while (!heap2.isEmpty()) {
					mergedArray[index] = heap2.extractRootElement();
					index++;
				}
			}
		}
		return mergedArray;
	}

	// falta testa no JUnit;
	/**
	 * Implemente um mÈtodo ne heap que recebe o nÌvel da heap e retorna um
	 * array contendo todos os elementos daquele nÌvel.
	 * 
	 * @param level
	 * @return
	 */

	public T[] elementsByLevel(int level) {
		LinkedList<T> elementsAtlevel = new LinkedList<T>();
		if (level >= 0) {
			int startPoint = 0;

			for (int i = 0; i < level; i++) {
				startPoint += Math.pow(2, i);
			}

			int maxLevelSize = (int) Math.pow(2, level);
			int aux = 0;
			while (startPoint <= this.index && aux < maxLevelSize) {
				elementsAtlevel.add(this.getHeap()[startPoint]);
				aux++;
				startPoint++;
			}
		}
		return this.makeArrayFromList(elementsAtlevel);
	}

	protected T[] makeArrayFromList(LinkedList<T> list) {
		int size = list.size();
		T[] array = Util.makeArrayOfComparable(size);
		for (int i = 0; i != size; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	// public static void main(String[] args) {
	// Comparator<Integer> comparator = new Comparator<Integer>() {
	// @Override
	// public int compare(Integer o1, Integer o2) {
	// return o2.compareTo(o1);
	// }
	// };
	// HeapImpl<Integer> heap = new HeapImpl<Integer>(comparator);
	//
	// heap.index = 17;
	//
	// heap.heap = new Integer[] {1, 2, 3, 4,
	// 5,6,7,8,9,10,11,12,13,14,15,16,17,18};
	//
	// System.out.println(Arrays.toString(heap.elementsByLevel(0)));
	// System.out.println(Arrays.toString(heap.elementsByLevel(1)));
	// System.out.println(Arrays.toString(heap.elementsByLevel(2)));
	// System.out.println(Arrays.toString(heap.elementsByLevel(3)));
	// System.out.println(Arrays.toString(heap.elementsByLevel(4)));
	//
	// }

	public boolean isHeap() {
		boolean isHeap = true;

		for (int i = 0; i < this.index && isHeap; i++) {
			if (isLeaf(i)) {
				if (this.getComparator().compare(this.heap[i], this.heap[this.parent(i)]) > 0) {
					isHeap = false;
				}

			} else {
				if ((this.getComparator().compare(this.heap[i], this.heap[this.right(i)]) < 0)
						|| this.getComparator().compare(this.heap[i], this.heap[this.left(i)]) < 0) {
					isHeap = false;
				}
			}

		}
		return isHeap;

	}

	public boolean isLeaf(int index) {
		boolean resp = false;
		int rigth = this.right(index);
		int left = this.left(index);

		if (rigth < 0 || left < 0 || rigth > this.index || left > this.index)
			resp = true;

		return resp;
	}

	// public static void main(String[] args) {
	// Comparator<Integer> comparator = new Comparator<Integer>() {
	// @Override
	// public int compare(Integer o1, Integer o2) {
	// return o2.compareTo(o1);
	// }
	// };
	// HeapImpl<Integer> heap = new HeapImpl<Integer>(comparator);
	//
	// heap.index = 19;
	//
	// heap.heap = new Integer[] { 1, 2, 3,-4, 4, 5,-7, 6, 7, 8, 9, 10, 11, 12,
	// 13, 14, 15, 16, 17, 18 };
	//
	// System.out.println(heap.isHeap());
	//
	// }

	// Altura - usando tecnica da bst

	public int height() {
		int height = 0;
		if (!this.isEmpty())
			height = height(0, 0);
		return height;

	}

	private int height(int indexOfNode, int heigth) {
		if (isLeaf(indexOfNode)) {
			heigth = 0;
		} else {
			heigth = 1 + Math.max(this.height(this.left(indexOfNode), heigth),
					this.height(this.right(indexOfNode), heigth));
		}
		return heigth;
	}

	// public static void main(String[] args) {
	// Comparator<Integer> comparator = new Comparator<Integer>() {
	// @Override
	// public int compare(Integer o1, Integer o2) {
	// return o2.compareTo(o1);
	// }
	// };
	// HeapImpl<Integer> heap = new HeapImpl<Integer>(comparator);
	//
	// heap.buildHeap(new Integer[] { 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16});
	//
	// System.out.println(heap.height2());
	//
	// }

	// Outra forma
	public int height2() {
		int height = -1;
		if (!this.isEmpty()) {
			height = (int) (Math.log(this.index + 1) / Math.log(2));
		}
		return height;
	}

	// public int distanciaHorizontal(T value){
	// int distancia = 0;
	//
	// if(!this.isEmpty()){
	// distancia = distancia(0, value);
	// }
	// return distancia;
	// }
	//
	// private int distancia(int i, T value) {
	// int distancia = 0;
	// if(this.isLeaf(i)){
	// distancia = 0;
	// }else{
	//
	// }
	// }

	public int search(T value) {
		int wanted = -1;
		for (int i = 0; i <= this.index; i++) {
			if (heap[i].compareTo(value) == 0) {
				wanted = i;
			}
		}
		return wanted;
	}

	// Ta muito certo nao
	public int distanciaHorizontal(T value) {
		int distancia = 0;
		int aux = search(value);

		if (aux != -1) {
			while (hasParent(aux)) {
				if (aux == this.left(this.parent(aux))) {
					distancia--;
				} else {
					distancia++;
				}
				aux = this.parent(aux);
			}
		}

		return distancia;
	}

	private boolean hasParent(int aux) {
		if (aux != 0) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		};
		HeapImpl<Integer> heap = new HeapImpl<Integer>(comparator);

		heap.buildHeap(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 });

		System.out.println(heap.nodeLevel(15));

	}

	public int nodeLevel(T value) {
		int nodeLevel = -1;
		int indexOfNode = this.search(value);
		if(indexOfNode == 0){
			nodeLevel = 0;
		}else if (value != null && indexOfNode != -1) {
			nodeLevel = (int) (Math.log(indexOfNode) / Math.log(2));
		}
		return nodeLevel;
	}

}
