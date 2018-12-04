import java.util.Comparator;

/**
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator. As
 * written uses Mergesort algorithm.
 *
 * @author CS221
 */
public class Sort {

	/**
	 * Returns a new list that implements the IndexedUnsortedList interface. As
	 * configured, uses WrappedDLL. Must be changed if using your own
	 * IUDoubleLinkedList class.
	 * 
	 * @return a new list that implements the IndexedUnsortedList interface
	 */
	private static <T> IndexedUnsortedList<T> newList() {
		return new IUDoubleLinkedList<T>(); 
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface using
	 * compareTo() method defined by class of objects in list. DO NOT MODIFY THIS
	 * METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface
	 * @see IndexedUnsortedList
	 */
	public static <T extends Comparable<T>> void sort(IndexedUnsortedList<T> list) {
		mergesort(list);
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface using given
	 * Comparator. DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface
	 * @param c
	 *            The Comparator used
	 * @see IndexedUnsortedList
	 */
	public static <T> void sort(IndexedUnsortedList<T> list, Comparator<T> c) {
		mergesort(list, c);
	}

	/**
	 * Mergesort algorithm to sort objects in a list that implements the
	 * IndexedUnsortedList interface, using compareTo() method defined by class of
	 * objects in list. DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface
	 */
	private static <T extends Comparable<T>> void mergesort(IndexedUnsortedList<T> list) {
		
		if (list.size() > 1) {
			IndexedUnsortedList<T> left = newList();
			IndexedUnsortedList<T> right = newList();
		
			for (int i = 0; i <= list.size()/2; i++) {
				left.add(list.removeFirst());
			}
			for (int i = 0; i <= list.size(); i++) {
				right.add(list.removeFirst());
			}

			mergesort(left);
			mergesort(right);
			
			while(left.size() > 0 && right.size() > 0 ) {
				if(left.first().compareTo(right.first()) == 1 ) {
					list.add(right.removeFirst());
				
				}else if(left.first().compareTo(right.first()) == -1 ) {
					list.add(left.removeFirst());
					
				}else{
					list.add(right.removeFirst());
				}
				
			}
			while(left.size() == 0 && right.size() > 0 ) {
				list.add(right.removeFirst());
			}
			while(right.size() == 0 && left.size() > 0 ) {
				list.add(left.removeFirst());
			}
			
		}
		

	}

	/**
	 * Mergesort algorithm to sort objects in a list that implements the
	 * IndexedUnsortedList interface, using the given Comparator. DO NOT MODIFY THIS
	 * METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface
	 * @param c
	 *            The Comparator used
	 */
	private static <T> void mergesort(IndexedUnsortedList<T> list, Comparator<T> c) {
		if (list.size() > 1) {
			IndexedUnsortedList<T> left = newList();
			IndexedUnsortedList<T> right = newList();
		
			for (int i = 0; i <= list.size()/2 ; i++) {
				left.add(list.removeFirst());
			}
			for (int i = 0; i <= list.size(); i++) {
				right.add(list.removeFirst());
			}

			mergesort(left,c);
			mergesort(right,c);
			
			while(left.size() > 0 && right.size() > 0 ) {
				if(c.compare(left.first(), right.first()) == 1) {
					list.add(right.removeFirst());
					
				}else if(c.compare(left.first(), right.first()) == -1 ) {
					list.add(left.removeFirst());
					
				}else {
					
					list.add(right.removeFirst());
				}
				
			}
			while(left.size() == 0 && right.size() > 0 ) {
				list.add(right.removeFirst());
			}
			while(right.size() == 0 && left.size() > 0 ) {
				list.add(left.removeFirst());
			}
			
		}

	}

}
