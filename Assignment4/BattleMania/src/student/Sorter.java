package student;

import java.util.ArrayList;
import java.util.List;

public class Sorter {

	/**
	 * Sorts the items in <i>lst</i> such that calling a.compareTo(b) will
	 * always be negative or 0 if a occurs in the list before b
	 * 
	 * @param <T>
	 *            A type of Comparable that whose compareTo method will be used
	 *            for sorting
	 * @param lst
	 *            The list that will be sorted by this method
	 */
	@SuppressWarnings("hiding")
	public static <Battle extends Comparable<Battle>> void sort(List<Battle> lst) {

	}

	@SuppressWarnings("hiding")
	public static <Battle extends Comparable<Battle>> void selectionSort(List<Battle> lst) {
		for (int i = 0; i < lst.size() - 1; i++) {
			// find min in x[i],...,x[n-1]
			int min = i;
			for (int j = i + 1; j < lst.size(); j++) {
				if (lst.get(j).compareTo(lst.get(j)) < 0)
					min = j;
			}
			// swap
			Battle tmp = lst.get(i);
			lst.set(i, lst.get(min));
			lst.set(min, tmp);
		}
	}

	@SuppressWarnings("hiding")
	public static <Battle extends Comparable<Battle>> void heapSort(List<Battle> lst) {
		int start = lst.size() / 2;
		Battle temp = null;
		List<Battle> sorted = new ArrayList<Battle>();
		//makes heap
		for (int j = start; j >= 0; j--) {
			for (int i = start; i >= 0; i--) {
				Battle parent = lst.get(i);
				Battle child1 = lst.get(2 * i + 1);
				Battle child2 = lst.get(2 * i + 2);
				if (parent.compareTo(child1) == 1
						&& parent.compareTo(child2) == 1) {
					if (child1.compareTo(child2) == 1) {
						temp = child2;
						child2 = parent;
						parent = temp;
					}
					if (child1.compareTo(child2) == -1) {
						temp = child1;
						child1 = parent;
						parent = temp;
					}
				}
				if (parent.compareTo(child1) == 1) {
					temp = child1;
					child1 = parent;
					parent = temp;
				}
				if (parent.compareTo(child2) == 1) {
					temp = child2;
					child2 = parent;
					parent = temp;
				}
			}
		}
		
		//sorts it (has issues, is not perfectly implemented)
		boolean stillNecessary = true;
		while (stillNecessary) {
			sorted.add(lst.get(0));
			temp = lst.get(lst.size()-1);
			lst.remove(lst.size() - 1);
			lst.set(0, null);
			int test = 0;
			while (test < lst.size()) {
				if (temp.compareTo(lst.get(2*test + 1)) == -1 && temp.compareTo(lst.get(2*test + 2)) == -1) {
					lst.set(test, temp);
					test = lst.size();
				}
				if (lst.get(test*2 + 1).compareTo(lst.get(test*2 + 2)) == -1) 
					lst.set(test, lst.get(test*2 + 1));
				if (lst.get(test*2 + 1).compareTo(lst.get(test*2 + 2)) == 1)
					lst.set(test, lst.get(test*2 + 2));
				test = 2*test + 1;
				stillNecessary = false;
			}	
		}
		
	}

}
