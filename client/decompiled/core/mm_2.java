/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from mm
 */
public class mm_2
extends aNk {
    private static final String[] yA = new String[]{"before", "after", "equal"};
    private static final ga_2 xa = ga_2.Qo();
    public static final mm_2 Jv = new mm_2("before");
    public static final mm_2 Jw = new mm_2("after");
    public static final mm_2 Jx = new mm_2("equal");

    public mm_2() {
    }

    public mm_2(String string) {
        this.setValue(string);
    }

    public String[] getValues() {
        return yA;
    }

    public boolean d(long l2, long l3) {
        return this.b(l2, l3, xa.Qp());
    }

    public boolean b(long l2, long l3, long l4) {
        int n2 = this.getIndex();
        if (n2 == -1) {
            throw new eq_2("TimeComparison value not set.");
        }
        if (n2 == 0) {
            return l2 - l4 < l3;
        }
        if (n2 == 1) {
            return l2 + l4 > l3;
        }
        return Math.abs(l2 - l3) <= l4;
    }

    public static int e(long l2, long l3) {
        return mm_2.c(l2, l3, xa.Qp());
    }

    public static int c(long l2, long l3, long l4) {
        long l5 = l2 - l3;
        long l6 = Math.abs(l5);
        return l6 > Math.abs(l4) ? (int)(l5 / l6) : 0;
    }
}

