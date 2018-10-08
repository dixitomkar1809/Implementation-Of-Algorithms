// Change this to netid of any member of team
package ond170030.LP2;
import java.util.*;
/* Starter code for LP2 */
// Skeleton for skip list implementation.

public class SkipList<T extends Comparable<? super T>> {
    static final int PossibleLevels = 33;
    Entry<T> head, tail;
    private int size, maxLevel;
    Entry<T>[] last;
    Random random;

    static class Entry<E> {
        E element;
        Entry[] next;
        Entry prev;
        int level;
        int[] span;

        public Entry(E x, int lev) {
            element = x;
            next = new Entry[lev];
            this.level = lev;
            this.span = new int[lev];
            // add more code if needed
        }

        public E getElement() {
            return element;
        }
    }

    // Constructor
    public SkipList() {
        this.head = new Entry<>(null, PossibleLevels);
        this.tail = new Entry<>(null, PossibleLevels);
        this.size = 0;
        this.maxLevel = 1;
        this.last = new Entry[33];
        this.random = new Random();
    }

    private void find(T x){
        Entry<T> cursor = this.head;
        for(int i = this.maxLevel - 1; i >= 0; i--){
            while(((T)cursor.next[i].element).compareTo((x)) > 0){
                cursor = cursor.next[i];
            }
            this.last[i] = cursor;
        }
    }

    // Add x to list. If x already exists, reject it. Returns true if new node is added to list
    public boolean add(T x) { return true; }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
        return null;
    }

    // Does list contain x?
    public boolean contains(T x) {
        this.find(x);
        return this.last[0].next[0].element == x;
    }

    // Return first element of list
    public T first() {
        if(isEmpty()){
            return null;
        }
        else{
            Entry<T> cursor = this.head;
            if(cursor.next[0] != this.tail){
                return (T) cursor.next[0].element;
            }
        }
        return (T) this.head.next[0].element;
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        return null;
    }

    // Return element at index n of list.  First element is at index 0.
    public T get(int n) {
        return null;
    }

    // O(n) algorithm for get(n)
    public T getLinear(int n) {
        return null;
    }

    // Optional operation: Eligible for EC.
    // O(log n) expected time for get(n). Requires maintenance of spans, as discussed in class.
    public T getLog(int n) {
        return null;
    }

    // Is the list empty?
    public boolean isEmpty() {
        if(this.size() == 0){
            return true;
        }else{
            return false;
        }
    }

    // Iterate through the elements of list in sorted order
    public Iterator<T> iterator() {
        return (Iterator<T>) new SkipListIterator();
    }

    protected class SkipListIterator implements SLIterator<T>{
        Entry<T> cursor;

        SkipListIterator(){
            this.cursor = head;
        }

        @Override
        public boolean hasNext() {
            return cursor.next[0] != null;
        }

        @Override
        public T next() {
            return (T) cursor.next[0].element;
        }
    }

    // Return last element of list
    public T last() {
        return null;
    }

    // Optional operation: Reorganize the elements of the list into a perfect skip list
    // Not a standard operation in skip lists. Eligible for EC.
    public void rebuild() {  }

    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
        if(!contains(x)){
            return null;
        }
        Entry ent = last[0].next[0];
        for(int i = 0; i <= ent.next.length-1; i++){
            last[i].next[i] = ent.next[i];
        }
        this.size-=1;
        return (T) ent.element;
    }

    // Return the number of elements in the list
    public int size() {
        return this.size;
    }
}