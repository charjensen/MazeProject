import java.util.Iterator;
import java.util.LinkedList;

public class DLLStack<T> implements Stack<T> {

	//make a new stack of linked lists
	LinkedList<T> stack;

	//instantiate stack when constructor called
	public DLLStack() {
		stack = new LinkedList<T>();
	}

	//return size
	@Override
	public int size() {
		return stack.size();
	}
	
	//return if stack is empty
	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	//push a value on to the stack
	@Override
	public void push(T v) {
		stack.addFirst(v);
	}

	//remove value from the stack
	@Override
	public T pop() {
		return stack.removeFirst();
	}

	//return the first value in the stack
	@Override
	public T top() {
		return stack.getFirst();
	}

	//print out the stack
	@Override
	public String toString() {

		String result = "[ ";

		Iterator<T> iter = stack.iterator();

		while (iter.hasNext()) {

			result += iter.next() + ", ";

		}

		result += "]";

		return result;

	}

}