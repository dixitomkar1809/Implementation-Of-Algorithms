package ond170030.SP1;
//Team Members: Arpita Agrawal and Omkar Dixit
public interface DoublyLinkedListIterator<T>
{
	void remove();
	boolean hasNext();
	boolean hasPrev(); 
    T next();
    T prev(); 
    void add(T x); 
}
