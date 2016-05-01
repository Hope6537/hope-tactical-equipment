package org.hope6537.string;

/**
 * Created by Hope6537 on 2015/3/5.
 */
public class KMPSearch {

    private final int radix;

    private String pat;

    private int[][] dfa;

    /**
     * 构造DFA
     */
    public KMPSearch(String pat) {

        this.radix = 256;
        this.pat = pat;
        int M = pat.length();
        dfa = new int[radix][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < radix; c++)
                dfa[c][j] = dfa[c][X];
            dfa[pat.charAt(j)][j] = j + 1;
            X = dfa[pat.charAt(j)][X];
        }
    }

    public int search(String target) {
        int M = pat.length();
        int N = target.length();
        int i, j;
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[target.charAt(i)][j];
        }
        if (j == M) return i - M;
        return N;
    }

}
