

import java.util.ArrayList;

public class PriorityQueue {
	private ArrayList<Coordinate> queue;
	
	public PriorityQueue(int initialCapacity) {
		queue = new ArrayList<Coordinate>(initialCapacity);
	}
	
	public void add(Coordinate coord) {
		queue.add(binarySearch(coord.key), coord);
	}
	
	private int binarySearch(int key) {
		int left = 0;
		int right = queue.size() - 1;
		
		while (right >= left) {
			int middle = (left + right + 1) / 2;
			if (queue.get(middle).key >= key)
				right = middle - 1;
			else if (queue.get(middle).key < key)
				left = middle + 1;
			else {
				return middle;
			}
		}
		
		return left;
	}
	
	public boolean contains(Coordinate coord) {
		//first, find an index with the right distance
		int index = binarySearch(coord.key);
		
		//starting from this index, go left
		for (int i=index; i>=0 && i<queue.size()
				&& queue.get(i).key == coord.key; i--) {
			if (queue.get(i).equals(coord))
				return true;
		}
		
		//starting from this index, go right
		for (int i=index+1; i<queue.size()
				&& queue.get(i).key == coord.key; i++) {
			if (queue.get(i).equals(coord))
				return true;
		}
		
		return false;
	}
	
	public Coordinate popMin() {
		Coordinate result = queue.get(0);
		queue.remove(0);
		return result;
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
