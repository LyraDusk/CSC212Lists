package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;


public class DoublyLinkedList<T> extends ListADT<T> {
	private Node<T> start;
	private Node<T> end;
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	

	@Override
	public T removeFront() {
		checkNotEmpty();
		if (this.size() != 1) {
			T value = this.start.value;
			this.start = start.after;
			this.start.before = null;
			return value;
		} else {
			T value = this.start.value;
			this.start = this.end = null;
			return value;
		}
		
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		if (this.size() != 1) {
			T value = this.end.value;
			this.end = end.before;
			this.end.after = null;
			return value;
		} else {
			T value = this.end.value;
			this.start = this.end = null;
			return value;
		}
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		if (index < this.size() - 1 && index > 0) {
			int at = 0;
			for (Node<T> n = this.start; n != null; n = n.after) {
				if(at == index) {
					n.before.after = n.after;
					n.after.before = n.before;
					T value = n.value;
					n = null;
					return value;
				} 
				at++;
			} 
			throw new BadIndexError(index);
		} else if(index == this.size() - 1) {	
			return this.removeBack();
		} else if(index == 0) {
			return this.removeFront();
		} else {
			throw new BadIndexError(index);
		}
		
	}

	@Override
	public void addFront(T item) {
		Node<T> newfront = new Node<T>(item);
		newfront.before = null;
		newfront.after = this.start;
		this.start = newfront;
	}

	@Override
	public void addBack(T item) {
		if (end == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> secondLast = end;
			end = new Node<T>(item);
			end.before = secondLast;
			secondLast.after = end;
		}
	}

	@Override
	public void addIndex(int index, T item) {
		Node<T> n = new Node<T>(item);
		if (this.size() == 0 && index == 0) {
			this.start = this.end =  n;
			n.before = null;
			n.after = null;
			return;
		} else if (index < this.size() && index > 0) {
			int at = 0;
			for (Node<T> x = this.start; x != null; x = x.after) {
				if (at == index) {
					n.before = x.before;
					n.after = x;
					x.before = n;
					n.before.after = n;
					return;
				}
				at++;
			}
		} else if (index == 0) {
			this.addFront(item);
		} else if (index == this.size()){
			this.addBack(item);
		} else {
			throw new BadIndexError(index);
		}
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return this.start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return this.end.value;
	}
	
	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		if (index >= 0 && index <= this.size()) {
			int at = 0 ;
			for (Node<T> n = this.start; n != null; n = n.after) {
				if(at == index) {
					return n.value;
				} 
				at++;
			}
			throw new BadIndexError(index);
		} else {
			throw new BadIndexError(index);
		}
	}
	
	public void setIndex(int index, T value) {
		checkNotEmpty();
		if (index >= 0 && index < this.size()) {
			int at = 0 ;
			for (Node<T> n = this.start; n != null; n = n.after) {
				if(at == index) {
					n.value = value;
				} 
				at++;
			}
		} else {
			throw new BadIndexError(index);
		}
	}

	@Override
	public int size() {
		int size = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			size ++;
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		if (this.start == null && this.end == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}
