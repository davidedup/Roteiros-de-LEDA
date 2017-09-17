package adt.btree;

public class BNodePosition<T extends Comparable<T>> {
	protected BNode<T> node;
	protected int position;

	public BNodePosition(BNode<T> node, int position) {
		this.node = node;
		this.position = position;
	}

	public BNodePosition() {
	}
	
	
	/**
	 * Verifica se eh um BNodePosition Vazio ou seja com node == null e position == 0
	 * @return
	 */
	protected boolean isEmpty(){
		if(this.node == null && this.position == 0){
			return true;
		}
		return false;
	}
}
