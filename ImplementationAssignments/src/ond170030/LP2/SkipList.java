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
        int[] position;

    static class rebuildElement<E>{
        E element;
        public rebuildElement(E element){
            this.element = element;
        }
        public E getElement(){ return element;}
    }

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
        this.tail.prev = this.head;
        this.size = 0;
        this.maxLevel = 1;
        this.last = new Entry[33];
        this.position = new int[33];
        this.random = new Random();
        for(int i =0; i<= PossibleLevels-1; i++){
            this.head.next[i] = this.tail;
            this.head.span[i] = 0;
            this.tail.span[i] = 0;
            this.last[i] = this.head;
            this.position[i] = 0;
        }
    }

    /**
     * returns the level for the new skiplist node 
     */
    private int chooseLevel(){
        int lev = 1;
        while(this.random.nextBoolean()){
            if(lev >= 33){
                break;
            }else{
                lev+=1;
            }
        }
        if(lev > this.maxLevel){
            this.maxLevel = lev;
            this.head.next[this.maxLevel-1] = this.tail;
            this.head.span[this.maxLevel-1] = this.size;
        }
        return lev;
    }

    /**
     * Add x to list. If x already exists, reject it. Returns true if new node is added to list
     */
    public boolean add(T x) { 
        if(contains(x)){
            return false;
        }else if( x == null){
            throw new NullPointerException();
        }
        else{
            return add(x, this.chooseLevel());
        }
    }

    /**
     * Used for getLog and also in add, remove and rebuild methods to find the spans at each level for   each node
     * Fills the span array, the span of the last node will be zero as it would be pointing to the tail of the Skiplist,the span of a node at a certain height which points to the tail will be just the count of nodes it would jump you not including the tail
     */
    private void spanFiller(Entry<T>[] node){
        for(int i=this.maxLevel-1; i>=0; i-- ){
            this.spanFinder(node[i], node[i].next[i], i);
        }
    }

    /**
     * SpanFinder finds the number of nodes you can jump
    here we use count to store the number of nodes we jump, which is initialized to zero incase of all elements except tail, where as tail is initialized to -1;
     * @param n1, nodes to calculate the span between them at a Level
     * @param n2
     * @param level
     */
    private void spanFinder(Entry<T> n1, Entry<T> n2, int level){
        Entry<T> cursor = n1;
        int count = 0;
        if(n2.equals(this.tail)){
            count-=1;
        }
        while(cursor != n2){
            count+=1;
            n1.span[level] = count;
            cursor = cursor.next[0];
        };
    }

    /**
     * Finds smallest element that is greater or equal to x
     * @param x, where x being element of a node to find ceiling
     * @return ceiling of x 
     */
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

    /**
     * helper method that fills the last array of the node with all the nodes which are visited to find T x
     * @param x, where x being the element we are finding
     */
    private void find(T x){
        Entry<T> cursor = this.head;
        for(int i = this.maxLevel - 1; i >= 0; i--){
            this.position[i] = 0;
            while (cursor.next[i] != this.tail && x.compareTo(((T)cursor.next[i].getElement())) > 0) {
                this.position[i]+=cursor.span[i];
                cursor = cursor.next[i];
            }
            this.last[i] = cursor;
        }
    }

    /**
     * Method to check if the list contains a certain element
     * @param x, x being element of a node
     * @return
     */
    public boolean contains(T x) {
        this.find(x);
        if(this.last[0].next[0] != this.tail && this.last[0].next[0].getElement().equals(x)){
            return true;
        }else{
            return false;
        }
    }

    
    /**
     * Method to return first element of the skiplist
     * @return first element of skiplist
     */
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

    
    /**
     * Find largest element that is less than or equal to x 
     * @param x
     * @return floor of element x 
     */
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

    /**
     * Return element at index n of list.  First element is at index 0.
     * @param n
     * @return element at nth index
     */
    public T get(int n){
        if(n < 0 || n > this.size() - 1){
            return null;
        }
        return this.getLog(n);
    }

    /**
     * O(n) algorithm for get(n)
     * @param n
     * @return element at nth index
     */
    public T getLinear(int n) {
        if(isEmpty()){
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
    // 
    /**
     * O(log n) algorithm for get(n), uses the spanFiller method, uncomment the spanfiller call in add and remove
     * @param n
     * @return
     */
    public T getLog(int n) {
        if(this.isEmpty()){
            // System.out.println("Null from get log");
            return null;
        }
        else{
            Entry<T> cursor = this.head;
            int position = -1;
            for(int i= this.maxLevel-1; i>=0 ; i--){
                while((position + cursor.span[i]) <= n && cursor.next[i] != this.tail ){
                    position+=cursor.span[i];
                    cursor = cursor.next[i];
                }
            }
            return cursor.getElement();
        }
    }

    /**
     * returns true if list is empty
     * @return
     */
    public boolean isEmpty() {
        if(this.size() == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Iterate through the elements of list in sorted order
     * @return
     */
    public Iterator<T> iterator() {
        return new SkipListIterator(this);
    }

    protected class SkipListIterator implements Iterator<T>{
        SkipList<T> skipList;
        Entry<T> cursor, tail;
        // ready flag is used to make sure the element is ready to be remvoved
        boolean ready;

        SkipListIterator(SkipList<T> skipList){
            this.cursor = skipList.head;
            this.tail = skipList.tail;
            this.ready = false;
        }

        @Override
        public boolean hasNext() {
            return cursor.next[0] != this.tail;
        }

        @Override
        public T next() {
            this.cursor = this.cursor.next[0];
            this.ready = true;
            return this.cursor.getElement();
        }

        // Removes the element (cursor) most recent next()
        // Remove can be used only after next has been called
		@Override
		public void remove() {
			if(!this.ready){
                throw new NoSuchElementException();
            }
            this.cursor = this.cursor.prev;
            skipList.remove((T) this.cursor.next[0].getElement());
            this.ready = false;
            
		}
    }

    /**
     *  Returns last element of list
     */
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

    /**
     * rebuilds the skiplist into a perfect skiplist
     */
    public void rebuild() {
        rebuildElement[] elements = new rebuildElement[size()];
        int[] perfectLevels = new int[this.size()];
        int index = 0;
        int n = this.size();
        int count = n;
        for(int i = 0; i<n; i++){
            T slElement = this.remove(getLog(0));
            elements[i] = new rebuildElement(slElement);
        }
        this.maxLevel = (int)(Math.ceil(Math.log10(n)/Math.log10(2)));
        
        // At this point we have all the elements of the skiplist in an array(elements) and the original one is empty, so now we have to set everything to default
        for(int i = 0; i< this.maxLevel; i++) {
            this.head.next[i] = this.tail;
            this.last[i] = this.head;
            this.head.span[i] = 0;
        }
        calculatePerfectLevels(0, n-1, perfectLevels);
        for(int i = n-1; i >= 0; i--){
            this.add((T) elements[i].getElement(), perfectLevels[i]);
        }
    }

    /**
     * Add method to be used in the rebuild function,
     * @param element, element to be added in the list
     * @param i, the height of the next array
     * @return true if added, false if already exists
     */
    private boolean add(T element, int level) {
        Entry<T> newNode = new Entry<T>(element, level);
        /**
         * When the list is empty and there are no nodes apart from just the head and tail, the span of head is one and the span of new element that would be inserted between head and tail will be zero since that node wont be going over any nodes, since the next is the tail.
         */
        if(this.isEmpty()){
            for(int i=0; i < this.maxLevel; i++){
                head.span[i] = 1;
            }
            for(int i=0; i< level; i++){
                newNode.span[i] = 0;
            }
        }else{
            int start = this.getTravelledDistance();
            int previous = -1;

            // Computing Spans
            for(int i=this.maxLevel-1; i >= 0; i--){
                previous+=position[i];
                if(i<level){
                    newNode.span[i] = this.last[i].span[i] + 1 - (start - previous);
                    this.last[i].span[i] = start - previous;
                }else{
                    this.last[i].span[i]+=1;
                }
            }
        }
        for(int j = 0; j <= level-1; j++){
            newNode.next[j] = this.last[j].next[j];
            this.last[j].next[j] = newNode;

        }
        newNode.next[0].prev = newNode;
        newNode.prev = this.last[0];
        this.size+=1;
        return true;
    }

    /**
     * Helper method for add 
     * @return returns the number of nodes spanned by the find() or contains()
     */
    public int getTravelledDistance(){
        int start = 0;
        for(int i = this.maxLevel -1; i >= 0; i--){
            start+=this.position[i];
        }
        return start;
    }

     
    /**
     *  calculate levels that should be there in a perfect skiplist and then assign each node to their     perfect level. 
     * we use the divide and conquer method to set levels 
     * calculatePerfectLevels for first to last which then divides into two first to mid and mid+1 to end
     * @param first and last positions in the array, and the array of levels at each index
     */
    private void calculatePerfectLevels(int first, int last, int[] perfectLevels){
        if(last<first){
            return;
        }
        else if(first==last){
            perfectLevels[first] = 1;
            return;
        }else{
            int mid = (last-first+1)/2;
            int temp = last - first + 1;
            perfectLevels[first+mid] = (int) Math.ceil(Math.log10(temp) / Math.log10(2));
            calculatePerfectLevels(first, first+mid-1, perfectLevels);
            calculatePerfectLevels(first+mid+1, last, perfectLevels);
        }
    }

    /**
     * Remove x from list.  Removed element is returned. Return null if x not in list
     * @param x
     * @return
     */
    public T remove(T x) {
        if(!this.contains(x)){
            return null;
        }
        Entry<T> cursor = this.last[0].next[0];
        for(int i = 0; i <= cursor.next.length-1; i++){
            this.last[i].next[i] = cursor.next[i];
            this.last[i].span[i] += cursor.span[i]-1;
        }
        for(int i=cursor.next.length; i < this.maxLevel; i++){
            this.last[i].span[i]-=1;
        }
        cursor.next[0].prev = cursor.prev;
        this.size-=1;
        return (T) cursor.getElement();
    }

    /**
     * Return the number of elements in the list
     * @return 
     */
    public int size() {
        return this.size;
    }

    // 
    /** Helper Method
     * Prints the skiplist starting from top most level of the skiplist
     */
    private void printList(){
        // System.out.println("SkipList Nodes-> ");
        for(int i=this.maxLevel-1; i>=0; i--){
            Entry<T> cursor = this.head;
            // System.out.println("Level: "+i);
            while(cursor != this.tail){
                // System.out.print(cursor.getElement() + " ");
                cursor = cursor.next[i];
            }
            System.out.println();
        }
    }

    
    /**
     * Helper method to print the span array of the nodes of the skiplist
     */
    private void printSpanList(){
        Entry<T> cursor = this.head;
        while(cursor != this.tail){
            // System.out.println("Size of Span array of "+cursor.getElement()+" ->"+ cursor.span.length);
            for(int i = cursor.span.length-1; i >=0 ; i-- ){
                System.out.print(cursor.span[i]+" ");
            }
            System.out.println();
            cursor = cursor.next[0];
        }
    }

    /**
     * Helper method to just check if the last array is filled with proper nodes after the find(T x) method 
     */
    private void printLastArray(){
        // System.out.println("--Last--");
        for(int i =0; i<= this.maxLevel-1; i++){
            System.out.println(this.last[i].getElement());
        }
        // System.out.println("--Last Ends--");
        return;
    }
}