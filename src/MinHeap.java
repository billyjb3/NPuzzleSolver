
import java.util.ArrayList;
import java.util.Collection;

public class MinHeap<T extends Comparable<T>>
{
	private ArrayList<T> heap;
	private boolean isHeapified = true;
	
	public MinHeap()
	{
		heap = new ArrayList<T>(10);
	}
	public MinHeap(T[] array)
	{
		heap = new ArrayList<T>(array.length);
		for(int i = 0; i < array.length; i++)
			heap.add(array[i]);
		isHeapified = false;
	}
	public boolean contains(T t)
	{
		for(int i = 0; i < heap.size(); i++)
		{
			if(t == heap.get(i))
				return true;
		}
		return false;
	}
	public void add(T t)
	{
		heap.add(t);
		isHeapified = false;
	}
	public void add(T[] t)
	{
		for(int i = 0; i < t.length; i++)
			heap.add(t[i]);
	}
	public void add(ArrayList<T> list)
	{
		for(int i = 0; i < list.size(); i++)
			heap.add(list.get(i));
	}
	public boolean isEmpty()
	{
		if(heap.size() == 0)
			return true;
		return false;
	}
	public int size()
	{
		return heap.size();
	}
	public T min()
	{
		if(!isEmpty())
		{
			if(!isHeapified)
				heapify();
			return heap.get(0);
		}
		else
			System.out.println("Heap is empty!");
		return null;
	}
	public T removeMin()
	{
		if(!isEmpty())
		{
			if(!isHeapified)
				heapify();
			T min = heap.get(0);
			heap.remove(0);
			heapify();
			return min;
		}
		else
			System.out.println("Heap is empty!");
		return null;
	}
	public void heapify()
	{
		for(int i = heap.size()/2-1; i >= 0; i--)
			adjust(i);
		isHeapified = true;
	}
	public void adjust(int i)//sift down
	{
		int l = lefti(i);
		int r = righti(i);
		int min = i;
		
		if(l < heap.size())
		{
			if(heap.get(l).compareTo(heap.get(i)) < 0)
				min = l;
		}
		if(r < heap.size())
		{
			if(heap.get(r).compareTo(heap.get(min)) < 0)
				min = r;
		}
		if(min != i)
		{
			T temp = heap.get(i);
			heap.set(i, heap.get(min)); 
			heap.set(min, temp);
			adjust(min);
		}
	}
	public int lefti(int i)
	{
		return 2*i + 1;
	}
	public int righti(int i)
	{
		return 2*i + 2;
	}
	public int parenti(int i)
	{
		if(i == 0)
			System.out.println("The root has no parent");
		else if(i%2 == 0)
			return i/2 -1;
		else
			return i/2;
		return -1;
	}
}


