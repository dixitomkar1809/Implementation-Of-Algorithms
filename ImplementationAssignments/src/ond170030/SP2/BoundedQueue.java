/*
 * Author : Omkar Dixit
 * Bounded Queue
 */

package ond170030.SP2;
/**
 * Bounded Queue
 * items is array of queue items
 * count keeps the number of items in the queue
 * tail is the last element
 * head is the first element
 * maxSize is the total number of elements in the queue
 * @param <T>
 */
public class BoundedQueue<T>{
	Object[] items;
	int count;
	int tail;
	int head;
	int maxSize;
	
	BoundedQueue(int size){
		this.items = new Object[size];
		this.count = 0;
		this.tail = -1;
		this.head = -1;
		this.maxSize = size;
	}

    /**
     * adds a new element x at the rear of the queue
     * returns false if the element was not added because the queue is full
     * */
	public boolean offer(T x) {
		if(this.count == this.maxSize) {
			System.out.println("Queue Full! Can't Add more!");
			return false;
		}
		this.items[(this.tail +1) % this.maxSize] = x;
		if(this.count==0) {
			this.head =(this.head +1) % this.maxSize;;
		}
		this.count++;
		this.tail = (this.tail +1) % this.maxSize;
		System.out.println("Enqueued "+ x);
		return true;
	}

    /**
     * remove and return the element at the front of the queue
     * return null if the queue is empty
     */
	public T poll() {
		if(this.isEmpty()) {
			return null;
		}
		T temp = (T) this.items[this.head];
		System.out.println("Dequeued "+ temp);
		this.head = (this.head + 1) % this.maxSize;
		this.count--;
		return temp;
	}

    /**
     * return front element, without removing it (null if queue is empty)
     */
	public T peek() {
		if(this.isEmpty()) {
			return null;
		}
		return (T) this.items[head];
	}

    /**
     * return the number of elements in the queue
     */
	public int size() {
		return this.count;
	}

    /**
     *  check if the queue is empty
     * @return
     */
	public boolean isEmpty() {
		return(this.count == 0);
	}

    /**
     * clear the queue
     */
	public void clear() {
		for(int i  = head; i <= tail; i++) {
			this.items[i] = Integer.MIN_VALUE;
		}
		this.head = this.tail = -1;
		this.count = 0;
		return;
	}

    /**
     * fill user supplied array with the elements of the queue, in queue order
     * @param a
     */
	public void toArray(T[] a) {
		if(a.length < this.count) {
			System.out.println("Array Provided is smaller than the No. of Queue Elemenets !");
			return;
		}
		int tempHead = this.head;
		for(int i =0; i <this.count; i++) {
			a[i] = (T) this.items[tempHead];
			tempHead = (tempHead + 1) % this.maxSize;
		}
		return ;
	}

    
	public static void main(String args[]) {
		
		BoundedQueue<Integer> bq = new BoundedQueue<>(6);
		
		Integer[] myArray = new Integer[3];
		for(int i=0; i < 10;i++) {
			bq.offer(i);
		}
		bq.poll();
		bq.poll();
		bq.toArray(myArray);
		System.out.println("Array Elements as follows :");
		for(int i =0; i < myArray.length; i++) {
			System.out.println(myArray[i]);
		}
		bq.offer(6);
		bq.offer(7);
		bq.offer(8);
		bq.toArray(myArray);
		System.out.println("Array Elements as follows :");
		for(int i =0; i < myArray.length; i++) {
			System.out.println(myArray[i]);
		}
	}
}