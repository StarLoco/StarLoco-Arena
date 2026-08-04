/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from vI
 */
public abstract class vi_2
extends OI
implements ajd_1 {
    protected transient long[] aty;
    protected ajd_1 atz;

    public vi_2() {
        this.atz = this;
    }

    public vi_2(int n2) {
        super(n2);
        this.atz = this;
    }

    public vi_2(int n2, float f) {
        super(n2, f);
        this.atz = this;
    }

    public vi_2(ajd_1 ajd_12) {
        this.atz = ajd_12;
    }

    public vi_2(int n2, ajd_1 ajd_12) {
        super(n2);
        this.atz = ajd_12;
    }

    public vi_2(int n2, float f, ajd_1 ajd_12) {
        super(n2, f);
        this.atz = ajd_12;
    }

    public Object clone() {
        vi_2 vi_22 = (vi_2)super.clone();
        vi_22.aty = (long[])this.aty.clone();
        return vi_22;
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.aty = new long[n3];
        return n3;
    }

    public boolean m(long l2) {
        return this.az(l2) >= 0;
    }

    public boolean b(px_1 px_12) {
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || px_12.aM(lArray[n2])) continue;
            return false;
        }
        return true;
    }

    protected void O(int n2) {
        this.aty[n2] = 0L;
        super.O(n2);
    }

    protected int az(long l2) {
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        int n2 = byArray.length;
        int n3 = this.atz.aP(l2) & Integer.MAX_VALUE;
        int n4 = n3 % n2;
        if (byArray[n4] != 0 && (byArray[n4] == 2 || lArray[n4] != l2)) {
            int n5 = 1 + n3 % (n2 - 2);
            do {
                if ((n4 -= n5) >= 0) continue;
                n4 += n2;
            } while (byArray[n4] != 0 && (byArray[n4] == 2 || lArray[n4] != l2));
        }
        return byArray[n4] == 0 ? -1 : n4;
    }

    protected int aO(long l2) {
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        int n2 = byArray.length;
        int n3 = this.atz.aP(l2) & Integer.MAX_VALUE;
        int n4 = n3 % n2;
        if (byArray[n4] == 0) {
            return n4;
        }
        if (byArray[n4] == 1 && lArray[n4] == l2) {
            return -n4 - 1;
        }
        int n5 = 1 + n3 % (n2 - 2);
        if (byArray[n4] != 2) {
            do {
                if ((n4 -= n5) >= 0) continue;
                n4 += n2;
            } while (byArray[n4] == 1 && lArray[n4] != l2);
        }
        if (byArray[n4] == 2) {
            int n6 = n4;
            while (byArray[n4] != 0 && (byArray[n4] == 2 || lArray[n4] != l2)) {
                if ((n4 -= n5) >= 0) continue;
                n4 += n2;
            }
            return byArray[n4] == 1 ? -n4 - 1 : n6;
        }
        return byArray[n4] == 1 ? -n4 - 1 : n4;
    }

    public final int aP(long l2) {
        return ha_0.S(l2);
    }
}

