package ond170030.SP1;
import java.util.Iterator;
import java.util.Random;
import java.util.NoSuchElementException;

//Team Members : Arpita Agrawal and omkar Dixit
public class SinglyLinkedList<T> implements Iterable<T> {

    /** Class Entry holds a single node of the list */
    static class Entry<E> {
        E element;
        Entry<E> next;

        Entry(E x, Entry<E> nxt) {
            element = x;
            next = nxt;
        }	
    }

	/**
	 * Dummy header is used.  tail stores reference of tail element of list
	 */
    Entry<T> head, tail;
    int size;

	/**
	 * Constructor
	 */
	public SinglyLinkedList() {
        this.head = new Entry<>(null, null);
        this.tail = this.head;
        this.size = 0;
    }

    public Iterator<T> iterator() { return new SLLIterator(); }

    protected class SLLIterator implements Iterator<T> {
	Entry<T> cursor, prev;
	boolean ready;  // is item ready to be removed?

	SLLIterator() {
	    cursor = head;
	    prev = null;
	    ready = false;
	}

	public boolean hasNext() {
	    return cursor.next != null;
	}
	
	public T next() {
	    prev = cursor;
	    cursor = cursor.next;
	    ready = true;
	    return cursor.element;
	}

	// Removes the current element (retrieved by the most recent next())
	// Remove can be called only if next has been called and the element has not been removed
	public void remove() {
	    if(!ready) {
		    throw new NoSuchElementException();
	    }
	    prev.next = cursor.next;
	    // Handle case when tail of a list is deleted
	    if(cursor == tail) {
		    tail = prev;
	    }
	    cursor = prev;
	    ready = false;  // Calling remove again without calling next will result in exception thrown
	    size--;
	}
    }  // end of class SLLIterator

	/**
	 * Add new Elements at the end of the list
	 * @param x
	 */
	public void add(T x) {
		this.add(new Entry<>(x, null));
    }

	/**
	 * Helper Function for Add Method
	 * @param ent
	 */
	private void add(Entry<T> ent) {
		this.tail.next = ent;
		this.tail = this.tail.next;
		this.size++;
    }

	/**
	 * Add element at head
	 * @param x
	 */
	public void addFirst(T x){
		this.addFirst(new Entry<>(x, null));
	}

	/**
	 * Helper function for addFirst
	 * @param ent
	 */
	private void addFirst(Entry<T> ent){
		ent.next = this.head.next;
		this.head.next = ent;
		if(this.size==0){

        }
		this.size++;
	}

    /**
     * Remove First
     */
    public void removeFirst(){
        if(this.head.next == null){
            return;
        }else{
            this.head.next = this.head.next.next;
            this.size-=1;
            return;
        }
    }

    /**
     * Remove a particular node
     * @param x
     * @return
     */
    public T remove(T x){
        return null;
    }

    public void printList() {
	    System.out.print(this.size + ": ");
        for(T item: this) {
            System.out.print(item + " ");
        }
        System.out.println();
    }


    /**
     *  Rearrange the elements of the list by linking the elements at even index
     *  followed by the elements at odd index. Implemented by rearranging pointers
     *  of existing elements without allocating any new elements.
     */
    public void unzip() {
	if(size < 3) {  // Too few elements.  No change.
	    return;
	}

	Entry<T> tail0 = head.next;
	Entry<T> head1 = tail0.next;
	Entry<T> tail1 = head1;
	Entry<T> c = tail1.next;
	int state = 0;

	// Invariant: tail0 is the tail of the chain of elements with even index.
	// tail1 is the tail of odd index chain.
	// c is current element to be processed.
	// state indicates the state of the finite state machine
	// state = i indicates that the current element is added after taili (i=0,1).
	while(c != null) {
	    if(state == 0) {
		tail0.next = c;
		tail0 = c;
		c = c.next;
	    } else {
		tail1.next = c;
		tail1 = c;
		c = c.next;
	    }
	    state = 1 - state;
	}
	tail0.next = head1;
	tail1.next = null;
	// Update the tail of the list
	tail = tail1;
    }

    public static void main(String[] args) throws NoSuchElementException {
        int n = 10;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }
        Random random = new Random();
        SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
        lst.removeFirst();
        for(int i=1; i<=n; i++) {
//            lst.add(Integer.valueOf(i));
            if(random.nextBoolean()){
                lst.add(Integer.valueOf(i));
            }else{
                lst.addFirst(Integer.valueOf(i));
            }
        }
        lst.printList();
//        Iterator<Integer> it = lst.iterator();
//        Scanner in = new Scanner(System.in);
//        whileloop:
//        while(in.hasNext()) {
//            int com = in.nextInt();
//            switch(com) {
//            case 1:  // Move to next element and print it
//                if (it.hasNext()) {
//                    System.out.println(it.next());
//                } else {
//                    break whileloop;
//                }
//                break;
//            case 2:  // Remove element
//                it.remove();
//                lst.printList();
//                break;
//            default:  // Exit loop
//                break whileloop;
//            }
//        }
//        lst.printList();
//        lst.unzip();
//            lst.printList();
    }
}

/* Sample input:
   1 2 1 2 1 1 1 2 1 1 2 0
   Sample output:
10: 1 2 3 4 5 6 7 8 9 10 
1
9: 2 3 4 5 6 7 8 9 10 
2
8: 3 4 5 6 7 8 9 10 
3
4
5
7: 3 4 6 7 8 9 10 
6
7
6: 3 4 6 8 9 10 
6: 3 4 6 8 9 10 
6: 3 6 9 4 8 10
*/