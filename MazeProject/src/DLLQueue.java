import java.util.Iterator;
import java.util.LinkedList;

public class DLLQueue<T> implements Queue<T> {

	// make a new queue of type linked list
	LinkedList<T> queue;

	// instantiate new queue in the constructor
	public DLLQueue() {

		queue = new LinkedList<T>();

	}

	// add value to the end of the linked list when value is enqueued
	@Override
	public void enqueue(T value) {

		queue.addLast(value);

	}

	// remove the first value of the queue
	@Override
	public T dequeue() {

		return queue.removeFirst();

	}

	// return the first value in the queue
	@Override
	public T first() {

		return queue.peekFirst();

	}

	//return size
	@Override
	public int size() {

		return queue.size();

	}

	//return if there is nothing in the queue
	@Override
	public boolean isEmpty() {

		return queue.isEmpty();

	}

	//print out everything in the queue
	public String toString() {

		String result = "[ ";

		Iterator<T> iter = queue.iterator();

		while (iter.hasNext()) {

			result += iter.next() + ", ";

		}

		result += "]";

		return result;

	}

}
