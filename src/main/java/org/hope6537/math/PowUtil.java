package org.hope6537.math;

import java.math.BigDecimal;
import java.util.Scanner;

public class PowUtil {

    public static long pow(long x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }
        if (n >= 2 && n % 2 == 0) {
            return pow(x * x, n / 2);
        } else {
            return pow(x * x, n / 2) * x;
        }
    }

    public static BigDecimal pow(double x, int n) {
        if (n == 0) {
            return BigDecimal.ONE;
        }
        if (n == 1) {
            return BigDecimal.valueOf(x);
        }
        if (n >= 2 && n % 2 == 0) {
            return pow(x * x, n / 2);
        } else {
            return pow(x * x, n / 2).multiply(BigDecimal.valueOf(x));
        }
    }


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (s.hasNext()) {
            double d = s.nextDouble();
            int n = s.nextInt();
            if (d > 0 && d < 99.999 && n > 0 && n <= 25) {
                System.out.println(pow(d, n));
            }

        }
    }

}
