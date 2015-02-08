package org.hope6537.note.thinking_in_java.seventeen;

import java.lang.ref.*;
import java.util.Arrays;
import java.util.LinkedList;

class VeryBig {
    private static final int SIZE = 10000;
    private long[] la = new long[SIZE];
    private String ident;

    public VeryBig(String id) {
        ident = id;
    }

    @Override
    public String toString() {
        return "VeryBig [la=" + Arrays.toString(la) + ", ident=" + ident + "]";
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalizing" + ident);
    }

}

public class References {

    private static ReferenceQueue<VeryBig> r = new ReferenceQueue<VeryBig>();

    public static void checkQueue() {
        Reference<? extends VeryBig> inq = r.poll();
        if (inq != null) {
            System.out.println("In Queue " + inq.get());
        }
    }

    public static void main(String[] args) {
        int size = 10;
        if (args.length > 0) {
            size = new Integer(args[0]);
        }
        LinkedList<SoftReference<VeryBig>> sa = new LinkedList<SoftReference<VeryBig>>();
        for (int i = 0; i < size; i++) {
            sa.add(new SoftReference<VeryBig>(new VeryBig("Soft" + i), r));
            System.out.println("Created " + sa.getLast());
            checkQueue();
        }
        LinkedList<WeakReference<VeryBig>> wa = new LinkedList<WeakReference<VeryBig>>();
        for (int i = 0; i < size; i++) {
            wa.add(new WeakReference<VeryBig>(new VeryBig("Weak" + i), r));
            System.out.println("Created " + wa.getLast());
            checkQueue();
        }
        SoftReference<VeryBig> s = new SoftReference<VeryBig>(new VeryBig(
                "SOFT"));
        WeakReference<VeryBig> w = new WeakReference<VeryBig>(new VeryBig(
                "WEAK"));
        System.gc();
        LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<PhantomReference<VeryBig>>();
        for (int i = 0; i < size; i++) {
            pa.add(new PhantomReference<VeryBig>(new VeryBig("Phantom" + i), r));
            System.out.println("Created " + pa.getLast());
            checkQueue();
        }

    }
}
