import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author BrandonBurr
 *
 * @param <T>
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {

	private Node<T> head, tail;
	private int size;
	private int modCount;

	public IUDoubleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		ListIterator<T> lit = listIterator();
		lit.add(element);
	}

	@Override
	public void addToRear(T element) {
		
		Node<T> newNode = new Node<T>(element);
		if (tail != null) { // tail can only be null if the list is empty!
			tail.setNext(newNode);
			newNode.setPrevious(tail);

		} else {
			head = newNode;

		}
		tail = newNode;
		size++;
		modCount++;

	}

	@Override
	public void add(T element) {
		addToRear(element);

	}

	@Override
	public void addAfter(T element, T target) {
		
		ListIterator<T> lit = listIterator();
		boolean foundIt = false;
		while(lit.hasNext() && !foundIt) {
			if(lit.next().equals(target)) {
				foundIt = true;
			}
		}
		if(!foundIt) {
			throw new NoSuchElementException();
		}
		lit.add(element);
		
	}

	
	@Override
	public void add(int index, T element) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		ListIterator<T> lit = listIterator(index); 
		lit.add(element);
		
	}

	@Override
	public T removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = head.getElement();
		head = head.getNext();

		if (head == null) {
			tail = null;
		}
		if (size == 1) {
			head = tail = null;
		} else {
			head.setPrevious(null);
		}
		modCount++;
		size--;
		return retVal;
	}

	@Override
	public T removeLast() {

		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();
		tail = tail.getPrevious();
		if (tail == null) {
			head = null;
		} else {
			tail.setNext(null);
		}
		modCount++;
		size--;
		return retVal;

	}
	
	@Override
	public T remove(T element) {

		Node<T> targetNode = head;
		while (targetNode != null && !targetNode.getElement().equals(element)) {
			targetNode = targetNode.getNext();
		}
		if (targetNode == null) {
			throw new NoSuchElementException();
		}
		T retVal = targetNode.getElement();
		if (targetNode != head) {
			targetNode.getPrevious().setNext(targetNode.getNext());
		} else {
			head = head.getNext();
		}
		if (targetNode != tail) {
			targetNode.getNext().setPrevious(targetNode.getPrevious());

		} else {
			tail = tail.getPrevious();
		}

		size--;
		modCount++;

		return retVal;
		
	}

	@Override
	public T remove(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		T retVal = null;
		ListIterator<T> lit = listIterator(index);
		retVal = lit.next();
		lit.remove();
		
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		T retVal = null;
		ListIterator<T> lit = listIterator(index);
		lit.next();
		lit.set(element);
		/*Node<T> current = head;
		int marker = 0;
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();

		}
		if (marker == index) {
			head.setElement(element);
		} else {
			while (marker < index) {
				marker++;
				current = current.getNext();
			}
			current.setElement(element);
		}

		modCount++;*/

	}

	@Override
	public T get(int index) {
		Node<T> current = head;
		T retVal = null;
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i <= index; i++) {
			if (i == index) {
				retVal = current.getElement();

				i = index + 2;
			}

			current = current.getNext();

		}
		return retVal;
	}

	@Override
	public int indexOf(T element) {

		int index = -1;

		Node<T> current = head;
		for (int i = 0; i < size; i++) {
			if (current.getElement().equals(element)) {
				index = i;
				i = size + 1;
			}
			current = current.getNext();
		}
		if (index > -1) {
			return index;
		} else {
			return -1;
		}
	}

	@Override
	public T first() {
		T retVal = null;
		if (size == 0) {
			throw new NoSuchElementException();
		}
		retVal = head.getElement();
		return retVal;
	}

	@Override
	public T last() {
		T element = null;
		if (size == 0) {
			throw new NoSuchElementException();
		}
		element = tail.getElement();
		return element;
	}

	@Override
	public boolean contains(T target) {
		if (isEmpty()) {
			return false;
		}

		Node<T> current = head;
		while (current != null && !current.getElement().equals(target)) {
			current = current.getNext();
		}
		if (current == null) {
			return false;
		}

		else {
			return true;
		}
	}

	@Override
	public boolean isEmpty() {
		return (head == null);
	}

	public String toString() {
		Node<T> newNode = head;
		StringBuilder str = new StringBuilder();
		str.append("[");
		for (int i = 0; i < size; i++) {
			if (i > 0 || i % 2 != 0) {
				str.append(", ");
			}
			str.append(newNode.toString() + "/t");
			
			newNode = newNode.getNext();
		}
		str.append("]");
		return str.toString();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		return new DLLIterator(startingIndex);
	}

	/**
	 * Iterator for IUDoubleLinkedList
	 * 
	 */
	private class DLLIterator implements ListIterator<T> {
		// set and remove are going to modify the last returned element
		// add method insets element before the returned element from a call to next
		private Node<T> nextNode;
		private int nextIndex;
		private int iterModCount;
		private Node<T> lastReturned;
		

		public DLLIterator() {
			this(0);

		}

		DLLIterator(int startingIndex) {
			if (startingIndex < 0 || startingIndex > size) {
				throw new IndexOutOfBoundsException();
			}
			nextNode = head;
			for (int i = 0; i < startingIndex; i++) {
				nextNode = nextNode.getNext();
			}
			nextIndex = startingIndex;
			lastReturned = null;
			iterModCount = modCount;

		}
		
		
		@Override
		public boolean hasNext() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}

			return (nextNode != null);

		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastReturned = nextNode;
			nextNode = nextNode.getNext();
			nextIndex++;
			
			return lastReturned.getElement();
		}

		@Override
		public boolean hasPrevious() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}

			return (nextNode != head);
		}

		@Override
		public T previous() {

			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}

			if (nextNode == null) {
				nextNode = tail;
			} else {
				nextNode = nextNode.getPrevious();
			}

			lastReturned = nextNode;
			nextIndex--;
			
			return lastReturned.getElement();
		}

		@Override
		public int nextIndex() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex - 1;
		}

		@Override
		public void remove() {

			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			if (head == tail) {
				head = tail = null;
			} else if (lastReturned == head) {
				head = head.getNext();
				head.setPrevious(null);

			} else if (lastReturned == tail) { 
				tail = tail.getPrevious();
				tail.setNext(null);
			} else {  
				lastReturned.getPrevious().setNext(lastReturned.getNext());
				lastReturned.getNext().setPrevious(lastReturned.getPrevious());
			}
			if(nextNode ==lastReturned) {  
				nextNode = nextNode.getNext();
			}else {
				nextIndex--;  
			}
			lastReturned = null;
			
			
			modCount++;
			iterModCount++;
			size--;
		}

		@Override
		public void set(T element) {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			
			lastReturned.setElement(element);
			modCount++;
			iterModCount++;

		}

		@Override
		public void add(T element) {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			Node<T> newNode = new Node<T>(element);
			
			
			if (size == 0) {
				head = tail = newNode;
			} else if (nextIndex == size) {
				tail.setNext(newNode);
				newNode.setPrevious(tail);
				tail = newNode;
			} else if (nextIndex == 0) {
				head.setPrevious(newNode);
				newNode.setNext(head);
				head = newNode;
			} else {
				nextNode.getPrevious().setNext(newNode);
				newNode.setNext(nextNode);
				newNode.setPrevious(nextNode.getPrevious());
				nextNode.setPrevious(newNode);
			}
			
			iterModCount++;
			modCount++;
			size++;
			nextIndex++;

		}
	}

}