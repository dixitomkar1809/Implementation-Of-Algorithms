package ond170030.SP2;

import java.util.*;

public class SP2Optional {

    /**
     * Return elements common to l1 and l2, in sorted order
     * outList is an empty list created by the calling program
     * and passed as a parameter. Function should be efficient
     * whether the List is implemented using ArrayList or LinkedList.
     * Do not use HashSet/Map or TreeSet/Map or other complex data structures.
     * @param l1
     * @param l2
     * @param outList
     * @param <T>
     */
    public static<T extends Comparable<? super T>> void intersect(List<T> l1, List<T> l2, List<T> outList) {
        if(l1.isEmpty() || l2.isEmpty()){
            return;
        }
        int i = 0;
        int j = 0;
        while(i< l1.size() && j< l2.size()){
            if(l1.get(i)==l2.get(j)){
                outList.add(l1.get(i));
                i++;
                j++;
            }else if(l1.get(i).compareTo(l2.get(j)) < 0){
                i++;
            }else{
                j++;
            }
        }
        return;
    }

    /**
     * Return the union of l1 and l2, in sorted order.
     * Output is a set, so it should have no duplicates.
     * @param l1
     * @param l2
     * @param outList
     * @param <T>
     */
    public static<T extends Comparable<? super T>> void   union(List<T> l1, List<T> l2, List<T> outList) {
        if(l1.isEmpty() || l2.isEmpty()){
            return;
        }
        int i = 0;
        int j = 0;
        while(i< l1.size() && j< l2.size()){
            if(l1.get(i)==l2.get(j)){
                outList.add(l1.get(i));
                i++;
                j++;
            }else if(l1.get(i).compareTo(l2.get(j)) < 0){
                outList.add(l1.get(i));
                i++;
            }else{
                outList.add(l2.get(j));
                j++;
            }
        }
        while(i<l1.size()){
            outList.add(l1.get(i));
            i++;
        }
        while(j<l2.size()){
            outList.add(l2.get(j));
            j++;
        }
        return;

    }

    /**
     * Return l1 - l2 (i.e, items in l1 that are not in l2), in sorted order.
     * Output is a set, so it should have no duplicates.
     */
    public static<T extends Comparable<? super T>> void difference(List<T> l1, List<T> l2, List<T> outList) {
        if(l1.isEmpty() || l2.isEmpty()){
            return;
        }
        int i = 0;
        int j = 0;
        while(i< l1.size() && j< l2.size()){
            if(l1.get(i)==l2.get(j)){
                i++;
                j++;
            }else if(l1.get(i).compareTo(l2.get(j)) < 0){
                outList.add(l1.get(i));
                i++;
            }else{
                outList.add(l1.get(i));
                j++;
            }
        }
        return;
    }

    public static void main(String[] args){
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
//        for (int i = 0; i < 50 ; i++) {
//            l1.add(i);
//            l2.add(i+1);
//        }
        l1.add(1);
        l1.add(4);
        l1.add(5);
        l2.add(1);
        l2.add(3);
        l2.add(4);
//        System.out.println(l1.size()+" "+l2.size());
        System.out.println("1. Intersect \n2. Union \n3. Difference \n");
        Scanner reader = new Scanner(System.in);
        int choice = reader.nextInt();
        reader.close();
        switch(choice){
            case 1:
                intersect(l1, l2, result);
                break;
            case 2:
                union(l1, l2, result);
                break;
            case 3:
                difference(l1, l2, result);
                break;
        }
        System.out.println("\n Resulting Array: ");
        for(int i=0; i< result.size(); i++){
            System.out.println(result.toArray()[i]);
        }
    }
}
