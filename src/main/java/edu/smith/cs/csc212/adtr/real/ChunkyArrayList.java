package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.EmptyListError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;

/**
 * This is a data structure that has an array inside each node of an ArrayList.
 * Therefore, we only make new nodes when they are full. Some remove operations
 * may be easier if you allow "chunks" to be partially filled.
 * 
 * @author jfoley
 * @param <T> - the type of item stored in the list.
 */
public class ChunkyArrayList<T> extends ListADT<T> {
	private int chunkSize;
	private GrowableList<FixedSizeList<T>> chunks;

	public ChunkyArrayList(int chunkSize) {
		this.chunkSize = chunkSize;
		chunks = new GrowableList<>();
	}
	
	private FixedSizeList<T> makeChunk() {
		return new FixedSizeList<>(chunkSize);
	}

	@Override
	public T removeFront() {
		checkNotEmpty();
		T value = this.chunks.getFront().getFront();
		FixedSizeList<T> firstChunk = this.chunks.getFront();
		if (firstChunk.size() > 1) {
			firstChunk.removeFront();
			chunks.removeFront();
			chunks.addFront(firstChunk);
		} else {
			chunks.removeFront();
		}
		
		return value;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		T value = this.chunks.getBack().getBack();
		FixedSizeList<T> lastChunk = this.chunks.getBack();
		if(lastChunk.size() > 1) {
			lastChunk.removeBack();
			chunks.removeBack();
			chunks.addBack(lastChunk);
		} else {
			chunks.removeBack();
		}
		return value;
	}

	@Override
	public T removeIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				T value = chunk.getIndex(index - start);
				int l_index = index - start;
				chunk.removeIndex(l_index);
				return value;
			}
			
			// update bounds of next chunk.
			start = end;
		}
		throw new BadIndexError(index);
	}

	@Override
	public void addFront(T item) {
		if (this.isEmpty()) {
			FixedSizeList<T> chunk = makeChunk();
			chunk.addFront(item);
			chunks.addFront(chunk);
			return;
		}
		FixedSizeList<T> firstChunk = chunks.getFront();
		if (firstChunk.size() == chunkSize) {
			FixedSizeList<T> chunk = makeChunk();
			chunk.addFront(item);
			chunks.addFront(chunk);
		} else {
			firstChunk.addFront(item);
			chunks.removeFront();
			chunks.addFront(firstChunk);
		}

	}

	@Override
	public void addBack(T item) {
		if (this.isEmpty()) {
			FixedSizeList<T> chunk = makeChunk();
			chunk.addFront(item);
			chunks.addFront(chunk);
			return;
		}
		FixedSizeList<T> lastChunk = this.chunks.getBack();
		if (lastChunk.size() == chunkSize) {
			FixedSizeList<T> newChunk = makeChunk();
			newChunk.addFront(item);
			chunks.addBack(newChunk);
		} else {
			lastChunk.addBack(item);
			chunks.removeBack();
			chunks.addBack(lastChunk);
		}
	}

	@Override
	public void addIndex(int index, T item) {
		// THIS IS THE HARDEST METHOD IN CHUNKY-ARRAY-LIST.
		// DO IT LAST.
		
		int chunkIndex = 0;
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index <= end) {
				if (chunk.isFull()) {
					// check can roll to next
					// or need a new chunk
					throw new TODOErr();
				} else {
					// put right in this chunk, there's space.
					throw new TODOErr();
				}	
				// upon adding, return.
				// return;
			}
			
			// update bounds of next chunk.
			start = end;
			chunkIndex++;
		}
		throw new BadIndexError(index);
	}
	
	@Override
	public T getFront() {
		return this.chunks.getFront().getFront();
	}

	@Override
	public T getBack() {
		return this.chunks.getBack().getBack();
	}


	@Override
	public T getIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				return chunk.getIndex(index - start);
			}
			
			// update bounds of next chunk.
			start = end;
		}
		throw new BadIndexError(index);
	}
	
	@Override
	public void setIndex(int index, T value) {
		if (this.isEmpty()) {
			FixedSizeList<T> chunk = makeChunk();
			chunk.addFront(value);
			chunks.addFront(chunk);
			return;
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				chunk.setIndex(index, value);
			}
			
			// update bounds of next chunk.
			start = end;
		}
		throw new BadIndexError(index);
	}

	@Override
	public int size() {
		int total = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			total += chunk.size();
		}
		return total;
	}

	@Override
	public boolean isEmpty() {
		return this.chunks.isEmpty();
	}
}