package ond170030.SP11;
import java.util.PriorityQueue;

public class kLargestElements {

    public static final int threshold = 2;
    public static void select(int[] arr, int k)
    {
        select(arr, 0, arr.length,k);
    }

    public static int select(int[] arr, int p, int n, int k)
    {
        //insertion sort
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
    public static void main(String[] args) {
        int n = 32000000;
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
        int k= (int) Math.random() * range + min;
        timer1.start();
        selectPQ(arr,k);
        timer1.end();
//        System.out.println("*************************");
        Shuffle.shuffle(arr);
        timer2.start();
        select(arr,k);
        timer2.end();
        for(int i=0;i<k;i++)
//            System.out.println(arr[arr.length-1-i]);
        System.out.println("PQ " + timer1.toString());
        System.out.println("Select " + timer2.toString());

    }

}
