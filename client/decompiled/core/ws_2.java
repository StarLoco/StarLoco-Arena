/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ws
 */
public abstract class ws_2
extends OI
implements alo_0 {
    protected transient byte[] auE;
    protected alo_0 auF;

    public ws_2() {
        this.auF = this;
    }

    public ws_2(int n2) {
        super(n2);
        this.auF = this;
    }

    public ws_2(int n2, float f) {
        super(n2, f);
        this.auF = this;
    }

    public ws_2(alo_0 alo_02) {
        this.auF = alo_02;
    }

    public ws_2(int n2, alo_0 alo_02) {
        super(n2);
        this.auF = alo_02;
    }

    public ws_2(int n2, float f, alo_0 alo_02) {
        super(n2, f);
        this.auF = alo_02;
    }

    public Object clone() {
        ws_2 ws_22 = (ws_2)super.clone();
        ws_22.auE = (byte[])this.auE.clone();
        return ws_22;
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.auE = new byte[n3];
        return n3;
    }

    public boolean contains(byte by) {
        return this.D(by) >= 0;
    }

    public boolean a(amm_2 amm_22) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.auE;
        int n2 = byArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || amm_22.aH(byArray2[n2])) continue;
            return false;
        }
        return true;
    }

    protected void O(int n2) {
        this.auE[n2] = 0;
        super.O(n2);
    }

    protected int D(byte by) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.auE;
        int n2 = byArray.length;
        int n3 = this.auF.F(by) & Integer.MAX_VALUE;
        int n4 = n3 % n2;
        if (byArray[n4] != 0 && (byArray[n4] == 2 || byArray2[n4] != by)) {
            int n5 = 1 + n3 % (n2 - 2);
            do {
                if ((n4 -= n5) >= 0) continue;
                n4 += n2;
            } while (byArray[n4] != 0 && (byArray[n4] == 2 || byArray2[n4] != by));
        }
        return byArray[n4] == 0 ? -1 : n4;
    }

    protected int E(byte by) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.auE;
        int n2 = byArray.length;
        int n3 = this.auF.F(by) & Integer.MAX_VALUE;
        int n4 = n3 % n2;
        if (byArray[n4] == 0) {
            return n4;
        }
        if (byArray[n4] == 1 && byArray2[n4] == by) {
            return -n4 - 1;
        }
        int n5 = 1 + n3 % (n2 - 2);
        if (byArray[n4] != 2) {
            do {
                if ((n4 -= n5) >= 0) continue;
                n4 += n2;
            } while (byArray[n4] == 1 && byArray2[n4] != by);
        }
        if (byArray[n4] == 2) {
            int n6 = n4;
            while (byArray[n4] != 0 && (byArray[n4] == 2 || byArray2[n4] != by)) {
                if ((n4 -= n5) >= 0) continue;
                n4 += n2;
            }
            return byArray[n4] == 1 ? -n4 - 1 : n6;
        }
        return byArray[n4] == 1 ? -n4 - 1 : n4;
    }

    public final int F(byte by) {
        return ha_0.aQ(by);
    }
}

