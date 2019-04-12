/** @author 
 *  Binary search tree (starter code)
 **/

package ond170030.SP5;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
	    this.left = left;
	    this.right = right;
        }
    }
    
    Entry<T> root;
    int size;
    Stack<Entry<T>> stack = new Stack();

    public BinarySearchTree() {
	root = null;
	size = 0;
    }
    
    Entry<T> find(T x){
    	this.stack.clear();
    	stack.push(null);
		return find(this.root, x);
    }

    Entry<T> find(Entry<T> t, T x){
    	if (t == null || t.element == x) {
    		return t;
    	}
    	while(true) {
    		if(x.compareTo(t.element) < 0) {
    			if(t.left == null) {
    				break;
    			}else {
    				stack.push(t);
    				t = t.left;
    			}
    		}else if(x == t.element) {
    			break;
    		}else if(t.right ==null) {
    			break;
    		}else {
    			stack.push(t);
    			t = t.right;
    		}
    	}
		return t;
    }
    
    /** TO DO: Is x contained in tree?
     */
    public boolean contains(T x) {
    	Entry<T> t = find(x);
    	if (t==null || t.element != x) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }

    /** TO DO: Is there an element that is equal to x in the tree?
     *  Element in tree that is equal to x is returned, null otherwise.
     */
    public T get(T x) {
	return null;
    }

    /** TO DO: Add x to tree. 
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
		Entry<T> toInsert = new BinarySearchTree.Entry<T>(x, null, null);
    	if(this.size ==0) {
//    		System.out.println("No Root Setting to null");
    		this.root = toInsert;
    		this.size++;
    		return true;
    	}else {
    		Entry<T> cursor = find(x);
    		if(cursor.element == x) {
    			cursor.element = x;
    			return false;
    		}else if (x.compareTo(cursor.element) < 0) {
    			cursor.left = toInsert;
    		}else {
    			cursor.right = toInsert;
    		}
    		this.size++;
    		return true;
    	}
    }

    /** TO DO: Remove x from tree. 
     *  Return x if found, otherwise return null
     */
    public T remove(T x) {
    	if(this.root == null) {
    		return null;
    	}
    	Entry<T> cursor = find(x);
    	if(!cursor.element.equals(x)) {
    		return null;
    	}
    	T result = cursor.element;
    	if(cursor.left == null || cursor.right == null ) {
    		bypass(cursor);
    	}else {
    		this.stack.push(cursor);
    		Entry<T> minRight = find(cursor.right, x);
    		cursor.element = minRight.element;
    		bypass(minRight);
    	}
    	this.size--;
		return result;
    }
    
    void bypass(Entry<T> t){
    	Entry<T> parent = this.stack.peek();
    	Entry<T> child = t.left == null ? t.right : t.left;
    	if(parent == null) {
    		this.root = child;
    	}else if(parent.left == t) {
    		parent.left = child;
    	}else {
    		parent.right = child;
    	}
    }

    public T min() {
    	if(this.size == 0) {
    		return null;
    	}
    	Entry<T> cursor = this.root;
    	while(cursor.left != null) {
    		cursor = cursor.left;
    	}
    	return cursor.element;
    }

    public T max() {
    	if(this.size == 0) {
    		return null;
    	}
    	Entry<T> cursor = this.root;
    	while(cursor.right != null) {
    		cursor = cursor.right;
    	}
    	return cursor.element;
    }

    // TODO: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
	Comparable[] arr = new Comparable[size];
	toArray(this.root,  0, arr);
	/* write code to place elements in array here */
	return arr;
    }

    int toArray(Entry<T> t, int i, Comparable[] arr) {
    	if(t == null) {
    		return i;
    	}
    	i = toArray(t.left, i, arr);
    	arr[i] = t.element;
    	i++;
    	i = toArray(t.right, i, arr);
    	return i;
    }

// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
	Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
	return null;
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

// End of Optional problem 2

    public static void main(String[] args) {
	BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }           
        }
    }


    public void printTree() {
	System.out.print("[" + size + "]");
	printTree(root);
	System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
	if(node != null) {
	    printTree(node.left);
	    System.out.print(" " + node.element);
	    printTree(node.right);
	}
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/