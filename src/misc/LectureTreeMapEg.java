package misc;
import java.util.*;

public class LectureTreeMapEg{
    public static void main(String[] args) {
        int[] arr = {3,3,4,5,3,5,4};
        int x = 10;
        Map<Integer, Integer> map = new TreeMap<>();
        for(int e: arr){
            int c;
            c = map.get(e);
            if(){
                map.put(e,1);
            }else{
                map.put(e, c+1);
            }
        }
        int sum = 0
        for(int e: arr){
            int c;
            c = map.get(x-e);
            sum +=c;
            if(e==x-e){
                sum-=1;
            }
        }
        System.out.println(x +" "+sum);

    }
}