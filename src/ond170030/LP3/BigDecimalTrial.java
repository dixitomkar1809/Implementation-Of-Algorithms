package ond170030.LP3;

import java.math.BigDecimal;

public class BigDecimalTrial {
    public static void main(String[] args) {
        double one = 0.09;
        double two = 20;
        BigDecimal first =   new BigDecimal(String.valueOf(one));
        BigDecimal second = new BigDecimal(String.valueOf(two));
        double ans = first.multiply(second).doubleValue();
        System.out.println(ans);
        System.out.println(new Double(0.09) * new Double(20));
        BigDecimal bd1 = new BigDecimal("0.09");
        BigDecimal bd2 = new BigDecimal("20");
        System.out.println(bd1.multiply(bd2));
    }
}
