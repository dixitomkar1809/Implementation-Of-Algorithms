
/** Starter code for AVL Tree
 */
package ond170030.SP5;

import java.util.Comparator;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        int height;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 0;
        }
    }

    AVLTree() {
	super();
    }

    @Override
    public boolean add(T x) {
	return super.add(x);
    }
}
