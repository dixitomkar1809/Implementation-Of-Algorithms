package ond170030.LP1;


import java.util.*;


public class Num implements Comparable<Num> {

    static long defaultBase = 1000000000;  // Change as needed
    long base = defaultBase;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int len;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]


    public Num(String s)
    {
        int count = 9;
        isNegative = (s.charAt(0) == '-') ? true : false;
        int stringLength = (isNegative) ? s.length() - 1 : s.length();
        int size = lengthInDiffBase(stringLength,base);
        this.arr = new long[size];
        int index = 0;

        for (int i=0; i < stringLength; i=i+count) {
            if(stringLength - i <= count) {
                this.arr[index++] = (isNegative)?Long.parseLong(s.substring(1,s.length() - i)):Long.parseLong(s.substring(0,s.length() - i));
                this.len++;
            }
            else {
                this.arr[index++] = Long.parseLong(s.substring(s.length() - i - count, s.length()  - i));
                this.len++;

            }
        }

    }

    private int lengthInDiffBase( int len, long base)
    {
        double Log_10B = Math.log10(base);
        int length = (int) Math.ceil(((len + 1) / Log_10B) + 1);
        return length;
    }




    public Num(long x) {
        isNegative = (x<0)?true:false;
        long num=Math.abs(x);
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
        num=Math.abs(x);
        int i=0;
        while(num!=0){
            arr[i] = num%10;
            num /= 10;
            i++;
        }


    }

    private Num(int size) {
        arr = new long[size];
        len = 0;
        isNegative = false;


    }

    private Num(Num a) {

        this.len = a.len;
        this.arr = new long[a.arr.length];
        this.base=a.base;
        for (int i = 0; i < len; i++) {
            this.arr[i] = a.arr[i];
        }
        this.isNegative = a.isNegative;

    }

    public static Num add(Num a, Num b) {
        long carry=0;
        int req_len = Math.max(a.len,b.len);
        Num newNumber = new Num (req_len + 1);
        newNumber.base=a.base;
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
        sum.base=a.base;
        sum.len=1;
        Num zero = new Num((long)0);
        zero.base=a.base;
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
        result.base=a.base;
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
        result.base=a.base;
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
        return power(a, new Num(n, a.base()));
    }

    private static Num power(Num a, Num n) {
        Num pow;
        if(n.compareTo(new Num(0, a.base())) == 0){
            return new Num(1L);
        }
        else{
            pow = power(product(a,a), n.by2());
            if(mod(n, new Num(2, n.base)).compareTo(new Num(0, n.base)) == 0){
                return pow;
            }
            else{
                return product(pow, a);
            }
        }
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
        if (b.compareTo(new Num(0L)) == 0)
            return result;

        else {
            Num a1 = new Num(a);
            a1.isNegative = false;
            Num b1 = new Num(b);
            b1.isNegative = false;

            result = div(a1, b1);

            if (a.isNegative ^ b.isNegative)
                if(!(result.compareTo(new Num(0L))==0))
                    result.isNegative = true;
        }

        return result;
    }


    private static Num modulus(Num a, Num b) {

        Num start = new Num(0L);
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
                    return new Num(0L);
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




        return result;

    }

    // Use binary search
    public static Num squareRoot(Num a) {

        if (a.compareTo(new Num(0L)) == 0 || a.compareTo(new Num("1")) == 0)
            return new Num(a);
        else if (a.isNegative)
            return null;

        else {
            Num start = new Num(0L);
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
    // Return number to a string in base 10
    public String toString() {
        int count = 9;
        int length=0;
        int diff=0;
        StringBuilder strbase10 = new StringBuilder();
        if (isNegative) {
            strbase10.append('-');
        }
        strbase10.append(arr[len-1]);
        for (int i = len -2 ;i >= 0; i--) {
            length=Long.toString(arr[i]).length();
            diff=count-length;
            if(diff > 0) {
                strbase10.append(String.format("%0" + count + "d", arr[i]));
            }
            else{
                strbase10.append(arr[i]);
            }
        }
        return strbase10.toString();
    }


    public long base() {
        return base;
    }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(long newBase) {

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

        Num temp=new Num(this.arr[0],newBase);
        temp.base=newBase;
        result = add(temp,result);


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
                //System.out.println(temp2 +" + "+ temp1);
                return add(temp2, temp1);
            }
            case "-":{
                //System.out.println(temp2 + " - " + temp1);
                return subtract(temp2, temp1);
            }
            case "*":{
                //System.out.println(temp2 + " * " + temp1);
                return product(temp2, temp1);
            }
            case "/":{
                //System.out.println(temp2 + " / " + temp1);
                return divide(temp2, temp1);
            }
            case "%":{
                //System.out.println(temp2+ " % "+ temp1);
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
                //System.out.println("Result-> " + stack.peek());
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
        this.base=b;
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

    public void printList() {
        System.out.print(this.base + ": ");
        for(int i=this.len - 1; i>=0; i--){
            System.out.print(this.arr[i] + " ");
        }
    }


    public static void main(String[] args) {


        //Num x=new Num("123897324987398749382798273298479374979723897489273874");
        //Num y=new Num("987342687234687648273648732468327468374683648392638274632864871");
//        Num z=fibonacci(100);
//        System.out.println(z);

        // System.out.println(y);


    }
}