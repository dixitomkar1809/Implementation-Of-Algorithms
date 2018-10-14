// Change this to netid of any member of team
package ond170030.LP2;
import java.security.KeyStore.Entry;
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
            this.head.span[i] = 0;
            this.tail.span[i] = 0;
            this.last[i] = this.head;
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
        }
        return lev;
    }

    // Add x to list. If x already exists, reject it. Returns true if new node is added to list
    public boolean add(T x) { 
        if(contains(x)){
            return false;
        }else{
            int lev = this.chooseLevel();
            // int spanCount = 0;
            Entry<T> newNode = new Entry<T>(x, lev);
            newNode.span[0] = 1;
            for(int i = 0; i <= lev-1; i++){
                newNode.next[i] = this.last[i].next[i];
                this.last[i].next[i] = newNode;
            }
            // System.out.println("Size of Span array of "+x+" during addition ->"+ newNode.span.length);
            // for(int i = lev-1; i >=0 ; i-- ){
            //     System.out.print(newNode.span[i]+" ");
            // }
            // System.out.println();
            newNode.next[0].prev = newNode;
            newNode.prev = this.last[0];
            this.size+=1;
            return true;
        }
    }

    private int fDistance(Entry<T> n1, Entry<T> n2){
        Entry<T> cursor = n1;
        int cnt = 0;
        while(cursor != n2){
            cnt+=1;
            cursor = cursor.next[0];
        }
        System.out.println("fDistance of "+n1.getElement()+" and "+n2.getElement()+" is "+ cnt);
        return cnt;
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
        if(this.isEmpty()){
            return null;
        }else{
            this.find(x);
            Entry<T> cursor = this.last[0];
            if(this.contains(x) || cursor != this.tail){
                return (T) cursor.next[0].getElement();    
            }else{
                return null;
            }
        }
    }

    private void find(T x){
        Entry<T> cursor = this.head;
        for(int i = this.maxLevel - 1; i >= 0; i--){
            while (cursor.next[i] != this.tail && x.compareTo(((T)cursor.next[i].getElement())) > 0) {
                cursor = cursor.next[i];
            }
            this.last[i] = cursor;
        }
    }

    // Does list contain x?
    public boolean contains(T x) {
        this.find(x);
        if(this.last[0].next[0] != this.tail && this.last[0].next[0].getElement().equals(x)){
            return true;
        }else{
            return false;
        }
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
        return null;
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        if(this.isEmpty()){
            return null;
        }else{
            if(this.contains(x)){
                return (T) this.last[0].next[0].getElement();
            }else{
                return this.last[0].getElement();
            }
        }
    }

    // Return element at index n of list.  First element is at index 0.
    public T get(int n){
        if(n < 0 || n > size - 1){
            System.out.println("get(): No Such Element");
            return null;
        }
        return this.getLinear(n);
    }

    // O(n) algorithm for get(n)
    public T getLinear(int n) {
        if(isEmpty()){
            System.out.println("getLinear(): SkipList is empty");
            return null;
        }else{
            Entry<T> cursor =this.head.next[0];
            for(int i=1; i<=n; i++){
                cursor = cursor.next[0];
            }
            return cursor.getElement();
        }
    }

    // Optional operation: Eligible for EC.
    // O(log n) expected time for get(n). Requires maintenance of spans, as discussed in class.
    public T getLog(int n) {
        if(this.isEmpty()){
            return null;
        }
        else{
            Entry<T> cursor = this.head;
            int position = 0;
            for(int i= this.maxLevel; i>=1 ; i--){
                while(position + cursor.span[i] <= n ){
                    position+=cursor.span[i];
                    cursor = cursor.next[i];
                }
            }
            return cursor.getElement();

        }
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
            Entry<T> cursor = this.head.next[0];
            while(cursor.next[0] != this.tail){
                cursor = cursor.next[0];
            }
            return (T) cursor.getElement();
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
        System.out.println("SkipList");
        for(int i=this.maxLevel-1; i>=0; i--){
            Entry<T> cursor = this.head;
            System.out.println("Level: "+i);
            while(cursor != this.tail){
                System.out.print(cursor.getElement() + " ");
                if(cursor.next[i] != this.tail){
                    cursor.span[i] = fDistance(cursor, cursor.next[i]);
                }
                cursor = cursor.next[i];
            }
            System.out.println();
        }
    }

    private void printSpanList(){
        Entry<T> cursor = this.head;
        while(cursor != this.tail){
            System.out.println("Size of Span array of "+cursor.getElement()+" ->"+ cursor.span.length);
            for(int i = cursor.span.length-1; i >=0 ; i-- ){
                System.out.print(cursor.span[i]+" ");
            }
            System.out.println();
            cursor = cursor.next[0];
        }
            
    }

    private void printLastArray(){
        System.out.println("Last Array");
        for(int i =0; i<= this.maxLevel-1; i++){
            System.out.println(this.last[i].getElement());
        }
        return;
    }

    public static void main(String[] args) {
        SkipList<Integer> sl = new SkipList<>();
        for(int i=1; i<=5; i++){
            sl.add(i);
        }
        System.out.println("Max Level of this SkipList -> "+sl.maxLevel);
        sl.printList();
        sl.printSpanList();
    }
}