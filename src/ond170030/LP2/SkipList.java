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
        for(int i =0; i<= PossibleLevels-1; i++){
            this.head.next[i] = this.tail;
            last[i] = this.head;
        }
        

    }

    private int chooseLevel(){
        // Slow Method
        int lev = 1;
        while(this.random.nextBoolean()){
            if(lev >= 33){
                break;
            }else{
                lev+=1;

            }
        }
        if(lev > this.maxLevel){
            maxLevel = lev;
            System.out.println("here");
        }
        return lev;
    }

    // Add x to list. If x already exists, reject it. Returns true if new node is added to list
    public boolean add(T x) { 
        if(contains(x)){
            return false;
        }else{
            int lev = this.chooseLevel();
            Entry<T> newNode = new Entry<T>(x, lev);
            for(int i = 0; i <= lev-1; i++){
                System.out.println(i);
                newNode.next[i] =last[i].next[i];
                last[i].next[i] = newNode;
            }
            newNode.next[0].prev = newNode;
            newNode.prev = last[0];
            this.size+=1;
            return true;
        }
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
        return null;
    }

    private void find(T x){
        Entry<T> cursor = this.head;
        for(int i = this.maxLevel - 1; i >= 0; i--){
            // System.out.println(i);
            while (cursor.next[i] != this.tail && x.compareTo(((T)cursor.next[i].getElement())) > 0) {
                cursor = cursor.next[i];
            }
            this.last[i] = cursor;
        }
    }

    // Does list contain x?
    public boolean contains(T x) {
        this.find(x);
        return this.last[0].next[0].getElement() == x;
    }

    // Return first element of list
    public T first() {
        if(isEmpty()){
            return null;
        }
        else{
            Entry<T> cursor = this.head;
            if(cursor.next[0] != this.tail){
                return (T) cursor.next[0].getElement();
            }
        }
        return (T) this.head.next[0].getElement();
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        // if(!this.isEmpty()){
        //     return null;
        // }else{
        //     Entry<T> cursor = this.contains(x);
        // }
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
            return (T) cursor.next[0].getElement();
        }
    }

    // Return last element of list
    public T last() {
        if(this.isEmpty()){
            return null;
        }else{
            Entry<T> cursor = this.head;
            for(int level = this.head.level; level >= 0; level--){
                while(cursor.next[level] != null && cursor.next[level].next[0] != null){
                    cursor = cursor.next[level];
                }
            }
            return (T) cursor.next[0].getElement();
        }
    }

    // Optional operation: Reorganize the elements of the list into a perfect skip list
    // Not a standard operation in skip lists. Eligible for EC.
    public void rebuild() {  }

    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
        if(!this.contains(x)){
            return null;
        }
        Entry ent = last[0].next[0];
        for(int i = 0; i <= ent.next.length-1; i++){
            last[i].next[i] = ent.next[i];
        }
        this.size-=1;
        return (T) ent.getElement();
    }

    // Return the number of elements in the list
    public int size() {
        return this.size;
    }

    private void printList(){
        System.out.println("Printing List at zeroth Level");
        Entry<T> cursor = this.head;
        while(cursor != this.tail){
            System.out.print(cursor.getElement() + " ");
            cursor = cursor.next[0];
        }
    }

    public static void main(String[] args) {
        SkipList sl = new SkipList<>();
        System.out.println(sl.add(1));
        System.out.println(sl.contains(1));
        sl.printList();
    }
}