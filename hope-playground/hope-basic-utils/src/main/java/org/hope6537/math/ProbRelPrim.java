package org.hope6537.math;

public class ProbRelPrim {

    private ProbRelPrim() {

    }

    public static double probRelPrim(int n) {
        int rel = 0;
        int tot = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                tot++;
                if (Gcd.gcd(i, j) == 1) {
                    rel++;
                }
            }
        }
        return (double) rel / tot;
    }

    public static void main(String[] args) {
        System.out.println(probRelPrim(100000));
    }

}
