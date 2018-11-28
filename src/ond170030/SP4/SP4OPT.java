package ond170030.SP4;

import java.util.Map;
import java.util.TreeMap;

public class SP4OPT {

    public static int howMany(int[] arr, int x){

        Map<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i< arr.length; i++){
            Integer c = map.get(arr[i]);
            if(c==null){
                map.put(arr[i], 0);
            }else{
                map.put(arr[i], c+1);
            }
        }
        int sum = 0;
        for(int i =0; i< arr.length; i++){
            int c = map.get(x-arr[i]);
            sum+=c;
            if(arr[i]==x-arr[i]){
                sum-=1;
            }
        }
        return sum;
    }

    public static int[] exactlyOnce(int[] arr){
        Map<Integer, Integer> map = new TreeMap<>();
        int count = 0;
        for(int i = 0;i < arr.length; i++){
            Integer c = map.get(arr[i]);
            if( c == null){
                map.put(arr[i], 1);
                count+=1;
            }else{
                map.put(arr[i], c+1);
                if(c==1){
                    count--;
                }
            }
        }
        int[] result = new int[count];
        int index = 0;
        for(int i = 0; i < arr.length; i++){
            if(map.get(arr[i])==1){
                result[index++]= arr[i];
            }
            if(index==count){
                break;
            }
        }

        return result;
    }

    public static void main(String[] args){
        int[] arr = {3,3,4,5,3,5,4};
        int[] arr2 = {6,3,4,5,3,5};
        System.out.println(howMany(arr, 8));
        int[] result = exactlyOnce(arr2);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);
            System.out.print(" ");
        }
    }
}
