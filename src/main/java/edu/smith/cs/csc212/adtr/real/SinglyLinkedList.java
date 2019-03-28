package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;

public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list.
	 * Node is defined at the bottom of this file.
	 */
	Node<T> start;
	
	@Override
	public T removeFront() {
		checkNotEmpty();
		Node<T> next = start.next;
		T value = start.value;
		this.start = next;
		return value;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		T value = null;
		int index = 0;
		for(Node<T> n = this.start; n != null; n = n.next) {
			if(n.next == null) {
				value = n.value;
				n = null;
				break;
			}
			index++;
		} 
		int at = 0;
		if(index != 0) {
		for(Node<T> n = this.start; n != null; n = n.next) {
			if (at == index - 1) {
				n.next = null;
			}
			at++;
		}
		} else {
			this.start = null;
		}
		return value;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		T value = null;
		Node<T> next = null;
		if(index == 0) {
			value = this.removeFront();
			return value;
		}
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at == index) {
				value = n.value;
				next = n.next;
			}
			at++;
		}
		at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			at++;
			if (at == index) {
				n.next = next;
			}
		}
		if(value != null) {
		return value;
		}
		throw new BadIndexError(index);
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@Override
	public void addBack(T item) {
		Node<T> node = new Node<T>(item, null);
		if(this.isEmpty()) {
			this.start = node;
		} else {
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (n.next == null) {
				n.next = node;
				break;
			}
		}
		}
		
	}

	@Override
	public void addIndex(int index, T item) {
		if (this.isEmpty()) {
			Node<T> node = new Node<T>(item,null);
			this.start = node;
		} else if (index > 0 && index <= this.size()) {
			int at = 0;
			Node<T> next = null;
		
			for (Node<T> n = this.start; n != null; n = n.next) {
				if (at == index - 1) {
					next = n.next;
					Node<T> node = new Node<T>(item,next);
					n.next = node;
				}
				at++;
			}	
		} else if (index == 0) {
			this.addFront(item);
		} else if (index == this.size()) {
			this.addBack(item);
		} else {
			throw new BadIndexError(index);
		}
	}
	
	
	
	@Override
	public T getFront() {
		checkNotEmpty();
		return start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (n.next == null) {
				return n.value;
			}
		}
		return null;
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}
	

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		int at = 0;
		if (index < 0 || index > this.size()) {
			throw new BadIndexError(index);
		}
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at == index) {
				n.value = value;
				return;
			}
			at++;
		}
		throw new BadIndexError(index);
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of SinglyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

}
