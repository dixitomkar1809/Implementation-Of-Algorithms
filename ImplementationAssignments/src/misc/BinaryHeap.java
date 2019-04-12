package misc;

import java.util.Comparator;

public class BinaryHeap<T extends Comparable<? super T>> {
    T[] pq;
    Comparator<T> comp;
    int size;

    public BinaryHeap(T[] q){
        this(q, (T a, T b)->a.compareTo(b));
    }

    public BinaryHeap(T[] q, Comparator<T> o) {
        this.pq = q;
        this.comp = o;
    }

    private BinaryHeap(T[] q, Comparator<T> c, int n) {
        pq = q;
        comp = c;
        // You need to add more code here to build queue
    }

    public void add(T x){

    }

}
