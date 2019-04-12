package ond170030.SP11;


import java.util.Random;

/* Shuffle the elements of an array arr[from..to] randomly */
public class Shuffle {
    public static Random random = new Random();
    public static void shuffle(int[] arr) {
        shuffle(arr, 0, arr.length-1);
    }

    public static<T> void shuffle(T[] arr) {
        shuffle(arr, 0, arr.length-1);
    }

    public static void shuffle(int[] arr, int from, int to) {
        int n = to - from  + 1;
        for(int i=1; i<n; i++) {
            int j = random.nextInt(i);
            swap(arr, i+from, j+from);
        }
    }

    public static<T> void shuffle(T[] arr, int from, int to) {
        int n = to - from  + 1;
        Random random = new Random();
        for(int i=1; i<n; i++) {
            int j = random.nextInt(i);
            swap(arr, i+from, j+from);
        }
    }

    static void swap(int[] arr, int x, int y) {
        int tmp = arr[x];
        arr[x] = arr[y];
        arr[y] = tmp;
    }

    static<T> void swap(T[] arr, int x, int y) {
        T tmp = arr[x];
        arr[x] = arr[y];
        arr[y] = tmp;
    }

    public static<T> void printArray(T[] arr, String message) {
        printArray(arr, 0, arr.length-1, message);
    }

    public static<T> void printArray(T[] arr, int from, int to, String message) {
        System.out.print(message);
        System.out.println();
        for(int i=from; i<=to; i++) {
            System.out.print(" " + arr[i]);
        }
        System.out.println();
    }
}
