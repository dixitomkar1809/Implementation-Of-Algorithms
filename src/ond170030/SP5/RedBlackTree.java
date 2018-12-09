
/** Starter code for Red-Black Tree
 */
package ond170030.SP5;

import java.util.Comparator;

public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    static class Entry<T> extends BinarySearchTree.Entry<T> {
        boolean color;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            color = RED;
        }

        boolean isRed() {
	    return color == RED;
        }

        boolean isBlack() {
	    return color == BLACK;
        }
    }

    RedBlackTree() {
	super();
    }
}
