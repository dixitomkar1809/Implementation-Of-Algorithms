package ond170030.LP2;
import ond170030.LP2.Timer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

//Driver program for skip list implementation.

public class SkipListDriver {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc;
        if (args.length > 0) {
            File file = new File(args[0]);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        String operation = "";
        long operand = 0;
        int modValue = 999983;
        long result = 0;
        Long returnValue = null;
        SkipList<Long> skipList = new SkipList<>();
        // Initialize the timer
        Timer timer = new Timer();
        long cnt = 1;
        while (!((operation = sc.next()).equals("End"))) {
            switch (operation) {
                case "Add": {
                    operand = sc.nextLong();
                    if(skipList.add(operand)) {
                        // System.out.println((cnt++) +" Add : 1");
                        result = (result + 1) % modValue;
                    }else{
                        // System.out.println((cnt++) +" Add : 0");
                    }
                    break;
                }
                case "Ceiling": {
                    operand = sc.nextLong();
                    returnValue = skipList.ceiling(operand);
                    // System.out.println((cnt++) +" Ceiling : "+returnValue);
                    if (returnValue != null) {
                        result = (result + returnValue) % modValue;
                    }
                    break;
                }
                case "First": {
                    returnValue = skipList.first();
                    // System.out.println((cnt++) +" First : "+returnValue);
                    if (returnValue != null) {
                        result = (result + returnValue) % modValue;
                    }
                    break;
                }
                case "Get": {
                    int intOperand = sc.nextInt();
                    returnValue = skipList.get(intOperand);
                    // System.out.println((cnt++) +" Get : "+returnValue);
                    if (returnValue != null) {
                        result = (result + returnValue) % modValue;
                    }
                    break;
                }
                case "Last": {
                    returnValue = skipList.last();
                    // System.out.println((cnt++) +" Last : "+returnValue);
                    if (returnValue != null) {
                        result = (result + returnValue) % modValue;
                    }
                    break;
                }
                case "Floor": {
                    operand = sc.nextLong();
                    returnValue = skipList.floor(operand);
                    // System.out.println((cnt++) +" Floor : "+returnValue);
                    if (returnValue != null) {
                        result = (result + returnValue) % modValue;
                    }
                    break;
                }
                case "Remove": {
                    operand = sc.nextLong();
                    if (skipList.remove(operand) != null) {
                        // System.out.println((cnt++) +" Remove : 1");
                        result = (result + 1) % modValue;
                    }else{
                        // System.out.println((cnt++) +" Remove : 0");
                    }
                    break;
                }
                case "Contains":{
                    operand = sc.nextLong();
                    if (skipList.contains(operand)) {
                        // System.out.println((cnt++) +" Contains : 1");
                        result = (result + 1) % modValue;
                    }else{
                        // System.out.println((cnt++) +" Contains : 0");
                    }
                    break;
                }

            }
        }

        // End Time
        timer.end();

        System.out.println(result);
        System.out.println(timer);
    }
}