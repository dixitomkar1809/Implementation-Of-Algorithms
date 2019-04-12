/*
* Author: Omkar Dixit
* */
package misc;


/*
* Implementation of SinglyLinkedList
* Takings just Integer Data
* It expects there will be no duplicate data because in that case the remove method would remove the first occuring node
* */
public class SinglyLinkedList {

    Node head;

    static class Node{
        int element;
        Node next;

        Node(int data){
            element = data;
            next = null;
        }
    }
//    Prints from head to end of the linkedlist
    public void print_list(){
        Node cursor = this.head;
        while(cursor != null){
            System.out.print(cursor.element + " ");
            cursor = cursor.next;
        }
        System.out.println();
    }

    public void addFirst(int data){
        Node new_node = new Node(data);
        new_node.next = this.head;
        this.head = new_node;

    }
    
//    Insert Node at the head of the list
    public void push(int data){
        Node new_node = new Node(data);
        new_node.next = this.head;
        this.head = new_node;
        System.out.println("New Node added !");
        this.print_list();
    }

//    Insert Node after a certain Node
    public void insert_after(int data, Node prev_node){
        Node cursor = this.head;
        Node new_node = new Node(data);
        while(cursor != null){
            if(cursor == prev_node){
                new_node.next = cursor.next;
                cursor.next = new_node;
                System.out.println("New Node added !");
                this.print_list();
                return;
            }
            cursor = cursor.next;
        }
        System.out.println("Node Not found !");
        this.print_list();
        return;
    }

//    Insert Node at the end of the list
    public void append(int data){
        Node cursor = head;
        Node new_node = new Node(data);
        while(cursor != null){
            if(cursor.next == null){
                cursor.next = new_node;
                System.out.println("New Node added !");
                this.print_list();
                return;
            }
            cursor = cursor.next;
        }
        System.out.println("Couldn't Add !");
        return;
    }

//    Removing a node from LinkedList
    public void remove(int data){
        Node cursor = this.head;
        while(cursor != null){
            if(cursor.next.element == data){
                cursor.next = cursor.next.next;
                System.out.println("New Node added !");
                this.print_list();
                return;
            }
            cursor = cursor.next;
        }
        System.out.println("Node Not found");
        return;
    }

    public static void main(String[] args){

        System.out.println("--Singly LinkedList--");
        SinglyLinkedList sll  = new SinglyLinkedList();

        sll.head = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);

        sll.head.next = second;
        second.next = third;

        sll.print_list();

        sll.push(0);
        sll.insert_after(4, second);
        sll.append(10);
        sll.remove(3);
    }

}