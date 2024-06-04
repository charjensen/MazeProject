
public interface Queue<T> {

	public void enqueue(T value);
	
	public T dequeue();
	
	public T first();
	
	public int size();
	
	public boolean isEmpty();
	
}
