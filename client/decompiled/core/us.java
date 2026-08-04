/*
 * Decompiled with CFR 0.152.
 */
public abstract class us
extends OI
implements Nh {
    protected transient short[] aqv;
    protected Nh aqw;

    public us() {
        this.aqw = this;
    }

    public us(int n2) {
        super(n2);
        this.aqw = this;
    }

    public us(int n2, float f) {
        super(n2, f);
        this.aqw = this;
    }

    public us(Nh nh) {
        this.aqw = nh;
    }

    public us(int n2, Nh nh) {
        super(n2);
        this.aqw = nh;
    }

    public us(int n2, float f, Nh nh) {
        super(n2, f);
        this.aqw = nh;
    }

    public Object clone() {
        us us2 = (us)super.clone();
        us2.aqv = (short[])this.aqv.clone();
        return us2;
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.aqv = new short[n3];
        return n3;
    }

    public boolean contains(short s) {
        return this.ab(s) >= 0;
    }

    public boolean a(cj_1 cj_12) {
        byte[] byArray = this.bCp;
        short[] sArray = this.aqv;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || cj_12.aq(sArray[n2])) continue;
            return false;
        }
        return true;
    }

    protected void O(int n2) {
        this.aqv[n2] = 0;
        super.O(n2);
    }

    protected int ab(short s) {
        byte[] byArray = this.bCp;
        short[] sArray = this.aqv;
        int n2 = byArray.length;
        int n3 = this.aqw.ad(s) & Integer.MAX_VALUE;
        int n4 = n3 % n2;
        if (byArray[n4] != 0 && (byArray[n4] == 2 || sArray[n4] != s)) {
            int n5 = 1 + n3 % (n2 - 2);
            do {
                if ((n4 -= n5) >= 0) continue;
                n4 += n2;
            } while (byArray[n4] != 0 && (byArray[n4] == 2 || sArray[n4] != s));
        }
        return byArray[n4] == 0 ? -1 : n4;
    }

    protected int ac(short s) {
        byte[] byArray = this.bCp;
        short[] sArray = this.aqv;
        int n2 = byArray.length;
        int n3 = this.aqw.ad(s) & Integer.MAX_VALUE;
        int n4 = n3 % n2;
        if (byArray[n4] == 0) {
            return n4;
        }
        if (byArray[n4] == 1 && sArray[n4] == s) {
            return -n4 - 1;
        }
        int n5 = 1 + n3 % (n2 - 2);
        if (byArray[n4] != 2) {
            do {
                if ((n4 -= n5) >= 0) continue;
                n4 += n2;
            } while (byArray[n4] == 1 && sArray[n4] != s);
        }
        if (byArray[n4] == 2) {
            int n6 = n4;
            while (byArray[n4] != 0 && (byArray[n4] == 2 || sArray[n4] != s)) {
                if ((n4 -= n5) >= 0) continue;
                n4 += n2;
            }
            return byArray[n4] == 1 ? -n4 - 1 : n6;
        }
        return byArray[n4] == 1 ? -n4 - 1 : n4;
    }

    public final int ad(short s) {
        return ha_0.aQ(s);
    }
}

