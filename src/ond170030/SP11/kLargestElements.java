package ond170030.SP11;
import java.util.PriorityQueue;

/**
 * Comparing o(n) select method for kth largest element with priority queue for stream of data
 */
public class kLargestElements {

    public static final int threshold = 2;

    /**
     * Select algorithm O(n)
     * @param arr
     * @param k
     */
    public static void select(int[] arr, int k)
    {
        select(arr, 0, arr.length,k);
    }

    /**
     * Select Helper returns kth largest element
     * @param arr
     * @param p
     * @param n
     * @param k
     * @return
     */
    public static int select(int[] arr, int p, int n, int k)
    {
        //insertion sort if less than threshold
        int q = randomPartition(arr,p,p+n-1);
        int left = q-p;
        int right = n-left-1;
        if(right>=k)
            return select(arr, q+1, right,k);
        else if(right+1==k)
            return q;
        else
            return select(arr,p,left,k-right-1);
    }

    /**
     * Random partition returns a random pivot
     * @param A
     * @param p
     * @param r
     * @return
     */
    static int randomPartition(int[] A, int p, int r)
    {
        int x=A[r];
        int i=p-1;
        int temp;
        for(int j=p;j<=r-1;j++)
        {
            if(A[j]<=x)
            {
                i=i+1;
                temp=A[i];
                A[i]=A[j];
                A[j]=temp;
            }
        }
        temp=A[i+1];
        A[i+1]=A[r];
        A[r]=temp;
        return i+1;
    }

    /**
     * returns kth largest element using priority queues
     * @param arr
     * @param k
     * @return
     */
    public static int selectPQ(int[] arr, int k)
    {
        PriorityQueue<Integer> q=new PriorityQueue<Integer>(k);
        for(int i=0;i<k;i++)
        {

            q.add(arr[i]);

        }
        for(int i=k;i<arr.length;i++)
        {
            if(arr[i]>q.peek())
            {
                q.remove();
                q.add(arr[i]);
            }
        }
//        for(Integer i: q)
//        {
//            System.out.println(i);
//        }
        Object[] temp = q.toArray();
        return (int)temp[q.size()-1];
//        return 0;
    }

    /**
     * Basic Driver code
     * @param args
     */
    public static void main(String[] args) {
        int n = 8000000;
        Timer timer1 = new Timer();
        Timer timer2 = new Timer();
        int[] arr = new int[n];
        int max = arr.length;
        int min = 1;
        int range = max - min + 1 ;
        for(int i=0; i<n; i++) {
            arr[i] = i;
        }
        Shuffle.shuffle(arr);
//        int k= (int) Math.random() * range + min;
        int k = n/2;
        timer1.start();
        selectPQ(arr,k);
        timer1.end();
        Shuffle.shuffle(arr);
        timer2.start();
        select(arr,k);
        timer2.end();
//        for(int i=0;i<k;i++)
//            System.out.println(arr[arr.length-1-i]);
        System.out.println("Value of n "+n);
        System.out.println("Value of k "+k);
        System.out.println("PQ " + timer1.toString());
        System.out.println("Select " + timer2.toString());

    }

}
