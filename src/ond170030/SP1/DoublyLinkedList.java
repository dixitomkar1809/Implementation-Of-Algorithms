package ond170030.SP1;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import ond170030.SP1.*;

//Team Members : Arpita Agrawal and Omkar Dixit

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {
    static class Entry<E> extends SinglyLinkedList.Entry<E> {
	Entry<E> prev;
	Entry(E x, Entry<E> next, Entry<E> prev) {
	    super(x, next);
	    this.prev = prev;
	}
    }
  
   
    public DoublyLinkedList() {
        head = new Entry<>(null, null,null);
        tail = head;
       size = 0;
    }
    
    public DoublyLinkedListIterator<T> dllIterator() { return new DLLIterator(); }
    public Iterator<T> iterator() { return new DLLIterator(); }
   
    protected class DLLIterator extends SLLIterator implements DoublyLinkedListIterator<T>{
    	DLLIterator() {
    		
    	    super();
    	    cursor = head;
    	}
    	
    	public boolean hasPrev () {
    		return ((Entry<T>) cursor).prev != null;
    	}
    	
    	public T prev() {
    		
    		if((((Entry<T>) cursor).prev)!= null){
    		cursor = prev;
    		prev = ((Entry<T>) cursor).prev;
    		ready = true;
    		return cursor.element;
    		}
    		else
    			return null;
    	}
    	public void add(T x) {  
    		add(new Entry<>(x, null,null));
    	    }

    	    public void add(Entry<T> ent) {  	    	
    	    	ent.prev = (Entry<T>) cursor;
                ent.next = cursor.next;
                if(cursor != tail)
                    ((Entry<T>) cursor.next).prev = ent;
                cursor.next = ent;
                
                prev = cursor;
                cursor = ent;
                tail=cursor;
                ready=true;
                size++;
    	    	
    	    }
    	    public void remove(){
    	    	super.remove();
    	    	if(cursor != tail){
    	    		((Entry<T>) cursor.next).prev= (Entry<T>) prev;
    	    	}//if
    	    }
         }
           
    public static void main(String[] args) throws NoSuchElementException {
        int n = 10;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        DoublyLinkedList<Integer> lst = new DoublyLinkedList<>();
       
        
    DoublyLinkedListIterator<Integer> iter =  lst.dllIterator();
	
    lst.printList();
	Scanner in = new Scanner(System.in);
	System.out.println("Enter to perform following operations:");
	System.out.println("1.Move to Next Element\n2.Remove Element\n3.Add New Element\n4.Previoue Element");
	whileloop:
	while(in.hasNext()) {
	    int com = in.nextInt();
	    switch(com) {
	    
	    
	    case 1:  // Move to next element and print it
		if (iter.hasNext()) {
		    System.out.println(iter.next());
		} else {
			System.out.println("No Element");
		    break whileloop;
		}
		break;
	    case 2:  // Remove element
	    iter.remove();
		lst.printList();
		break;
	    case 3:
	     iter.add(in.nextInt());
	     lst.printList();
	     break;
	    	
	    case 4:
	    	if(iter.hasPrev()){
	    		System.out.println(iter.prev());
	    	}else{
	    		System.out.println("No previous element");
	    		break whileloop;
	    	}
	    	    break;
	    default:  // Exit loop
		 break whileloop;
	    }
	}
//	lst.printList();
//	lst.unzip();
//    lst.printList();
    }
    	
}

    	
    	
    	
    
