package ond170030.LP1;

// Starter code for lp1.
// Version 1.0 (8:00 PM, Wed, Sep 5).
import java.util.*;


public class Num implements Comparable<Num> {

    static long defaultBase = 10;  // Change as needed
    long base = defaultBase;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int len;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]

    public Num(String s) {

        isNegative = (s.charAt(0) == '-') ? true : false;
        double Log_10B = Math.log10(this.base);
        int stringLength = (isNegative) ? s.length() - 1 : s.length();
        int length = (int) Math.ceil((stringLength + 1 / Log_10B) + 1);
        this.len = stringLength;
        arr = new long[length];
        for (int i = 0; i < stringLength; i++) {
            arr[i] = Character.getNumericValue(s.charAt(s.length() - 1 - i));
        }


    }

    private int lengthInDiffBase( int len, int base)
    {
        double Log_10B = Math.log10(base);
        int length = (int) Math.ceil((len + 1 / Log_10B) + 1);
        return length;
    }


//    public Num(long x, int base) {
//        isNegative = (x<0)?true:false;
//        long num=x;
//        int digit_count=0;
//        while(num != 0)
//        {
//            num /= base;
//            ++digit_count;
//        }
//        len=digit_count;
//        if(x==0)
//        {
//            len = 1;
//        }
//
//        int length = (int) Math.ceil((len + 1 / Math.log10(this.base())) + 1);
//        arr = new long[length];
//        num=x;
//        int i=0;
//        while(num!=0){
//            arr[i] = num%10;
//            num /= 10;
//            i++;
//        }
//
//
//    }

    public Num(long x) {
        isNegative = (x<0)?true:false;
        long num=x;
        int digit_count=0;
        while(num != 0)
        {
            num /= 10;
            ++digit_count;
        }
        len=digit_count;
        if(x==0)
        {
            len = 1;
        }

        int length = (int) Math.ceil((len + 1 / Math.log10(this.base())) + 1);
        arr = new long[length];
        num=x;
        int i=0;
        while(num!=0){
            arr[i] = num%10;
            num /= 10;
            i++;
        }


    }

    public Num(int size) {
        arr = new long[size];
        len = 0;
        isNegative = false;


    }

    public Num(Num a) {

        this.len = a.len;
        this.arr = new long[a.arr.length];

        for (int i = 0; i < len; i++) {
            this.arr[i] = a.arr[i];
        }

    }

    public static Num add(Num a, Num b) {
        long carry=0;
        int req_len = Math.max(a.len,b.len);
        Num newNumber = new Num (req_len + 1);
        Num extra;

        if(a.isNegative && b.isNegative || !a.isNegative && !b.isNegative){
            int i;
            long addition=0;
            int min_len = Math.min(a.len,b.len);


            for(i=0;i<min_len;i++){
                addition = a.arr[i]+b.arr[i]+carry;
                newNumber.arr[i]=addition%a.base;
                newNumber.len++;
                carry=addition/a.base;
            }

            if(i == a.len)
            {
                while(i<b.len)
                {
                    addition=b.arr[i]+carry;
                    newNumber.arr[i]=(addition)%b.base;
                    carry=addition/b.base;
                    newNumber.len++;
                    i++;
                }

            }

            else if(i == b.len)
            {
                while(i<a.len)
                {
                    newNumber.len++;
                    addition=a.arr[i]+carry;
                    newNumber.arr[i]=(addition)%a.base;
                    carry=addition/a.base;
                    i++;
                }
            }

            if(carry !=0)
            {
                newNumber.arr[i]=carry;
                newNumber.len++;
            }


            newNumber.isNegative=(a.isNegative)?true:false;

        }
        else if(!a.isNegative && b.isNegative){
            extra=new Num(b);
            extra.isNegative=false;
            newNumber=subtract(a,extra);
        }
        else {
            extra=new Num(a);
            extra.isNegative=false;
            newNumber=subtract(b,extra);
        }
        //System.out.println(newNumber);
        return newNumber;
    }


    public static Num subtract(Num a, Num b) {


        Num result;

        Num a1 = new Num(a);
        a1.isNegative = false;
        Num b1 = new Num(b);
        b1.isNegative = false;


        if (b.isNegative && !a.isNegative) {
            result= add(a1, b1);
            result.isNegative = false;
            return result;
        }
        else if (a.isNegative && b.isNegative)
             subtract(b1, a1);
        else if (a.isNegative && !b.isNegative) {
            result = add(a1, b1);
            result.isNegative = true;
            return result;
        }


        if (a.len > b.len) {
            result = minus(a1, b1);
            result.isNegative = false;
        } else if (a.len < b.len) {
            result = minus(b1, a1);
            result.isNegative = true;
        } else {
            int index = a.len - 1;
            while (index >= 0 && a.arr[index] == b.arr[index])
                index--;
            if (a.arr[index] > b.arr[index]) {
                result = minus(a1, b1);
                result.isNegative = false;
            } else if (a.arr[index] < b.arr[index]) {
                result = minus(b1, a1);
                result.isNegative = true;
            } else {
                result = new Num((long)0);
            }
        }

        return result;
    }

    private static Num minus(Num a, Num b) {
        boolean borrow = false;
        Num result = new Num(a.arr.length);
        result.len = a.len;
        for (int i = 0; i < a.len; i++) {
            if (i < b.len) {
                if (borrow) {
                    a.arr[i]--;

                }

                long temp = a.arr[i] - b.arr[i];
                if (temp >= 0) {
                    result.arr[i] = temp;
                    borrow = false;
                } else {
                    result.arr[i] = temp + a.base;
                    borrow = true;
                }


            } else {
                if (borrow) {
                    a.arr[i]--;
                    if (a.arr[i] < 0) {
                        result.arr[i] = a.arr[i] + a.base;
                    } else {
                        result.arr[i] = a.arr[i];
                        borrow = false;
                    }
                } else {
                    result.arr[i] = a.arr[i];
                }


            }
        }


        while (result.len + 1 > 0 && result.arr[result.len - 1] == 0)
            result.len--;
        return result;
    }

    public static Num product(Num a, Num b) {
        Num first = new Num(a);
        Num second = new Num(b);
        if (first.isNegative) {
            first.isNegative = false;
        }
        if (second.isNegative) {
            second.isNegative = false;
        }
        Num temp;
        if (first.compareTo(second) == -1) {
            temp = first;
            first = second;
            second = temp;
        }




        Num sum = new Num(a.len+b.len+2);
        sum.len=1;
        Num zero = new Num((long)0);
        if(a.compareTo(zero)==0 || b.compareTo(zero)==0) {
             sum.len=1;
             return sum;
        }
            for (int j = 0; j < second.len; j++) {

                sum = add(sum, shift(multiply(first,second.arr[j]),j));

        }

        if (a.isNegative && !b.isNegative || !a.isNegative && b.isNegative) {
            sum.isNegative = true;
        }

        return sum;
    }


    private static Num multiply(Num a, long x)
    {
        Num result = new Num(a.len+1);
        long carry = 0;
        for(int i=0; i<a.len;i++)
        {
            long mult = a.arr[i]*x + carry;
            carry = mult / a.base;
            result.arr[i]= mult % a.base;
            result.len++;
        }
        if(carry!=0) {
            result.arr[a.len] = carry;
            result.len++;
        }
        return result;
    }

    private static Num  shift(Num a , int x)
    {
        Num result = new Num(a.len+x);
        int index = 0;

        while(index < x) {
            result.arr[index++] = 0;
            result.len++;
        }
        for(int i =0; i<a.len;i++)
        {
            result.arr[index++]= a.arr[i];
            result.len++;
        }
return result;
    }

    // Use divide and conquer
    public static Num power(Num a, long n) {

        Num pow = null;

        if(n == 0)
            return new Num((long)1);
        else if(n == 1)
            return new Num(a);
        else if(n == 2)
           return product(a,a);
        else
        {
            if(n%2 == 0)
                pow = product(power(a,n/2), power(a, n/2));
            else
                pow = product(power(a,(n-1)/2), power(a, (n+1)/2));
        }
       return pow;
    }


    private static Num div(Num a, Num b) {


        if (a.compareTo(b) < 0)
            return new Num((long)0);

        else if (b.compareTo(new Num((long)1)) == 0)
            return new Num(a);


        else {

            Num start = new Num((long)0);
            Num end = new Num(a);

            while (true) {

                Num mid = add(start, end).by2();

                int comp = a.compareTo(product(mid, b));
                if (comp == 0) {
                    return mid;
                } else if (comp < 0) {
                    end = mid;

                } else {
                    if (b.compareTo(subtract(a, product(mid, b))) > 0)
                        return mid;
                    else
                        start = mid;

                }
            }

        }
    }

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {

        Num result = null;
        if (b.compareTo(new Num("0")) == 0)
            return result;

        else {
            Num a1 = new Num(a);
            a1.isNegative = false;
            Num b1 = new Num(b);
            b1.isNegative = false;

            result = div(a1, b1);

            if (a.isNegative ^ b.isNegative)
                if(!(result.compareTo(new Num("0"))==0))
                result.isNegative = true;
        }

        return result;
    }


    private static Num modulus(Num a, Num b) {

        Num start = new Num("0");
        Num end = new Num(a);

        if (b.compareTo(start) == 0 || a.compareTo(start) == 0 || b.compareTo(new Num("1")) == 0)
            return start;
        else if(a.compareTo(new Num("1")) == 0 || a.compareTo(b)<0)
            return new Num(a);


        else {
            while (true) {
                Num temp = add(start, end);
                Num mid = temp.by2();

                int comp = a.compareTo(product(mid, b));
                if (comp == 0) {
                    return new Num("0");
                } else if (comp < 0) {
                    end = mid;

                } else {
                    if (b.compareTo(subtract(a, product(mid, b))) > 0)
                        return subtract(a, product(mid, b));
                    else
                        start = mid;
                }
            }

        }
    }
    // return a%b
    public static Num mod(Num a, Num b) {

        Num result;




            Num a1 = new Num(a);
            a1.isNegative = false;
            Num b1 = new Num(b);
            b1.isNegative = false;


            result = modulus(a1,b1);

            if(a.isNegative&& (a1.compareTo(b1)!=0))
                result = subtract(b1,result);


       return result;

    }

    // Use binary search
    public static Num squareRoot(Num a) {

        if (a.compareTo(new Num("0")) == 0 || a.compareTo(new Num("1")) == 0)
            return new Num(a);
        else if (a.isNegative)
            return null;

        else {
            Num start = new Num("0");
            Num end = new Num(a);

            while (true) {
                Num temp = add(start, end);
                Num mid = temp.by2();

                int comp = a.compareTo(product(mid, mid));
                if (comp == 0) {
                    return mid;
                } else if (comp > 0) {
                    Num midPlusOne = add(mid, new Num("1"));
                    if (a.compareTo(product(midPlusOne, midPlusOne)) < 0)
                        return mid;
                    else
                        start = mid;

                } else {
                    Num midPlusOne = add(mid, new Num("1"));
                    if (a.compareTo(product(midPlusOne, midPlusOne)) > 0)
                        return mid;
                    else
                        end = mid;

                }
            }
        }

    }


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        if (this.isNegative && !other.isNegative) {
            return -1;
        } else if (!this.isNegative && other.isNegative) {
            return 1;
        } else if (!this.isNegative && !other.isNegative) {
            if (this.len > other.len) {
                return 1;
            } else if (this.len < other.len) {
                return -1;
            } else {
                int i = this.len - 1;
                while (this.arr[i] == other.arr[i]) {
                    if (i == 0) {
                        return 0;
                    }
                    i--;
                }
                return (this.arr[i] > other.arr[i]) ? 1 : -1;
            }
        } else {
            this.isNegative = false;
            other.isNegative = false;
            int result = this.compareTo(other);
            this.isNegative = true;
            other.isNegative = true;
            if (result == 1) {
                return -1;
            } else if (result == -1) {
                return 1;
            } else {
                return 0;
            }
        }

    }


    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
    }

    // Return number to a string in base 10
    public String toString() {
        StringBuilder strbase10 = new StringBuilder();
        if (isNegative) {
            strbase10.append('-');
        }
        for (int i = len - 1; i >= 0; i--) {
            strbase10.append(arr[i]);
        }
        return strbase10.toString();
    }


    public long base() {
        return base;
    }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {

       Num result = new Num(0L);
       result.base = newBase;

        Num oldBaseInNewBase = new Num(this.base, newBase);
        for(int i = this.len-1; i>=1; i--)
        {
            Num temp = new Num(this.arr[i],newBase);
            temp.base = newBase;
            result = add(temp ,result);
            result = product(result,oldBaseInNewBase);
        }


        result = add(new Num(this.arr[0]),result);


        return result;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
        boolean borrow = false;
        Num result = new Num(this.arr.length);
        result.len = this.len;
        result.isNegative = this.isNegative;
        int j = this.len - 1;
        for (int i = this.len - 1; i >= 0; i--) {
            if (borrow) {
                result.arr[j] = (this.arr[i] + base) / 2;
                if ((this.arr[i] + base) % 2 == 1) {
                    borrow = true;
                } else {
                    borrow = false;
                }
                j--;
            } else {
                result.arr[j] = this.arr[i] / 2;
                if (this.arr[i] % 2 == 1) {
                    borrow = true;
                } else {
                    borrow = false;
                }
                j--;
            }

        }
        while (result.len > 1 && result.arr[result.len - 1] == 0)
            result.len--;
        return result;
    }

    private static boolean isOperand(String s1) {
        if(s1=="+" || s1=="-" || s1=="*" || s1=="/" || s1=="%" || s1=="^" ) {
            return true;
        }else {
            return false;
        }

    }

    private static Num eval(String operator, Num temp1, Num temp2) {
        switch(operator) {
            case "+":{
                System.out.println(temp2 +" + "+ temp1);
                return add(temp2, temp1);
            }
            case "-":{
                System.out.println(temp2 + " - " + temp1);
                return subtract(temp2, temp1);
            }
            case "*":{
                System.out.println(temp2 + " * " + temp1);
                return product(temp2, temp1);
            }
            case "/":{
                System.out.println(temp2 + " / " + temp1);
                return divide(temp2, temp1);
            }
            case "%":{
                System.out.println(temp2+ " % "+ temp1);
                return mod(temp2, temp1);
            }
            case "^":{
                System.out.println("Call Power");
            }
        }
        return null;
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {
        System.out.println("From evaluatePostfix");
        Stack<Num> stack = new Stack<>();
        Num temp1;
        Num temp2;
        for(int i  = 0; i < expr.length; i++) {
            String token = expr[i];
            if(!isOperand(token)) {
//                System.out.println(token);
                stack.push(new Num(token));
            }
            else {
                temp1 = stack.pop();
                temp2 = stack.pop();
                stack.push(eval(token, temp1, temp2));
                System.out.println("Result-> " + stack.peek());
            }
        }
        return stack.pop();
    }

    private static int getPrecedence(String s) {
        switch(s) {

            case "+":{
                return 1;
            }

            case "-":{
                return 1;
            }

            case "*":{
                return 2;
            }

            case "/":{
                return 2;
            }

            case "%":{
                return 2;
            }

            case "^":{
                return 3;
            }
        }
        return 0;
    }

    private static boolean getRightPrecedence(String s1) {
        if(s1=="^") {
            return true;
        }
        return false;
    }

    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluateInfix(String[] expr) {
        ArrayList<String> output = new ArrayList<>();
        Deque<String> stack = new LinkedList<>();
        for(int i = 0; i < expr.length; i++) {
            String token = expr[i];
            if(getPrecedence(token) > 0) {
                while(!stack.isEmpty() && (getPrecedence(stack.peek()) > getPrecedence(token) || (getPrecedence(token) == getPrecedence(stack.peek()) && !getRightPrecedence(token)) && !stack.peek().equals("("))) {
                    output.add(stack.pop());
                }
                stack.push(token);
            }else if(token == "(") {
                stack.push(token);
            }else if(token == ")") {
                while(!stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            }else {
                output.add(token);
            }
        }
        while(!stack.isEmpty()) {
            output.add(stack.pop());
        }
        System.out.println("From evaluateInfix");
        System.out.println(output);
        String[] temp = output.toArray(new String[0]);
        return evaluatePostfix(temp);
    }


    private Num(long x, long b) {
        long temp = x;
        Deque<Long> stack = new ArrayDeque<>();
        while(true) {
            stack.push(temp%b);
            temp = temp/b;
            if(temp==0) {
                break;
            }
        }

        arr = new long[stack.size()+2];
        len = 0;
        isNegative = false;
        int i =0;
        while(!stack.isEmpty()) {
            this.arr[i] = stack.removeLast();
            this.len++;
            i++;
        }

    }


    public static void main(String[] args) {


        Num base = new Num(129L,13L);

        Num x = new Num("129");

        Num y = x.convertBase(13);
//        Num y = new Num("9");
//        Num z = Num.subtract(x, y);
//        Num d = Num.add(x, y);
//        Num f = Num.add(z,d);
 //       Num newBase = Num.add(x,y);
//        Num divide = Num.divide(x, y);
//        Num sqrt = Num.squareRoot(x);
//        Num mod = Num.mod(x, y);
//        System.out.println(z.arr[0] + "---------" + z.arr[1]);
//        Num a = Num.power(x, 5);

    }
}