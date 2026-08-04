/*
 * Decompiled with CFR 0.152.
 */
public abstract class aMP
extends OI
implements ui_0 {
    protected transient int[] dYH;
    protected ui_0 dYI;

    public aMP() {
        this.dYI = this;
    }

    public aMP(int n2) {
        super(n2);
        this.dYI = this;
    }

    public aMP(int n2, float f) {
        super(n2, f);
        this.dYI = this;
    }

    public aMP(ui_0 ui_02) {
        this.dYI = ui_02;
    }

    public aMP(int n2, ui_0 ui_02) {
        super(n2);
        this.dYI = ui_02;
    }

    public aMP(int n2, float f, ui_0 ui_02) {
        super(n2, f);
        this.dYI = ui_02;
    }

    public Object clone() {
        aMP aMP2 = (aMP)super.clone();
        aMP2.dYH = (int[])this.dYH.clone();
        return aMP2;
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.dYH = new int[n3];
        return n3;
    }

    public boolean contains(int n2) {
        return this.hJ(n2) >= 0;
    }

    public boolean a(aLR aLR2) {
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || aLR2.eG(nArray[n2])) continue;
            return false;
        }
        return true;
    }

    protected void O(int n2) {
        this.dYH[n2] = 0;
        super.O(n2);
    }

    protected int hJ(int n2) {
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        int n3 = byArray.length;
        int n4 = this.dYI.ie(n2) & Integer.MAX_VALUE;
        int n5 = n4 % n3;
        if (byArray[n5] != 0 && (byArray[n5] == 2 || nArray[n5] != n2)) {
            int n6 = 1 + n4 % (n3 - 2);
            do {
                if ((n5 -= n6) >= 0) continue;
                n5 += n3;
            } while (byArray[n5] != 0 && (byArray[n5] == 2 || nArray[n5] != n2));
        }
        return byArray[n5] == 0 ? -1 : n5;
    }

    protected int pr(int n2) {
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        int n3 = byArray.length;
        int n4 = this.dYI.ie(n2) & Integer.MAX_VALUE;
        int n5 = n4 % n3;
        if (byArray[n5] == 0) {
            return n5;
        }
        if (byArray[n5] == 1 && nArray[n5] == n2) {
            return -n5 - 1;
        }
        int n6 = 1 + n4 % (n3 - 2);
        if (byArray[n5] != 2) {
            do {
                if ((n5 -= n6) >= 0) continue;
                n5 += n3;
            } while (byArray[n5] == 1 && nArray[n5] != n2);
        }
        if (byArray[n5] == 2) {
            int n7 = n5;
            while (byArray[n5] != 0 && (byArray[n5] == 2 || nArray[n5] != n2)) {
                if ((n5 -= n6) >= 0) continue;
                n5 += n3;
            }
            return byArray[n5] == 1 ? -n5 - 1 : n7;
        }
        return byArray[n5] == 1 ? -n5 - 1 : n5;
    }

    public final int ie(int n2) {
        return ha_0.aQ(n2);
    }
}

