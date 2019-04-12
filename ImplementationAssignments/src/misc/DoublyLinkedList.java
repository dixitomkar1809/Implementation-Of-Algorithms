package misc;

public class DoublyLinkedList {
    Node head;
    Node tail;

    static class Node{
        int element;
        Node prev;
        Node next;

        Node(int data){
            this.element = data;
            this.prev = this.next = null;
        }
    }

//    Insert a node at Front
    public void push(int data){
        Node new_node = new Node(data);
        new_node.next = head;
        if(head != null){
            head.prev = new_node;
        }
        this.head = new_node;
        System.out.println("Node Added !");
        this.print_list();
        return;
    }

//    Insert a node after a given node
    public void insert_after(int data, Node prev_node){
        if(prev_node == null){
            System.out.println("Given Node is null !");
            return;
        }
        Node cursor = this.head;
        Node new_node = new Node(data);
        new_node.next = prev_node.next;
        prev_node.next = new_node;
        new_node.prev = prev_node;
        if(new_node.next != null){
            new_node.next.prev = new_node;
        }
        System.out.println("Node Added !");
        this.print_list();
        return;
    }

//    Insert a node at the last
    public void insert_last(int data){
        Node new_node = new Node(data);
        Node cursor = this.head;
        if(head==null){
            new_node.prev = null;
            head = new_node;
            System.out.println("Node Added !");
            this.print_list();
            return;
        }
        while(cursor != null){
            if(cursor.next == null){
                cursor.next = new_node;
                new_node.prev = cursor;
                System.out.println("Node added !");
                this.print_list();
                return;
            }
            cursor = cursor.next;
        }
        return;
    }

//    Removing node from head
    public void remove_head(int data){

    }

//    Printing Entire List
    public void print_list(){
        Node cursor = this.head;
        while(cursor != null){
            System.out.print(cursor.element + " ");
            cursor = cursor.next;
        }
        System.out.println();
        return;
    }

    public static void main(String[] args){
        System.out.println("--DoublyLinkedList--");
        DoublyLinkedList dll = new DoublyLinkedList();
        dll.head = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);

        second.next = third;
        third.prev = second;
        dll.head.next = second;
        dll.print_list();
        dll.push(0);
        dll.insert_after(5, second);
        dll.insert_last(10);

    }


}
