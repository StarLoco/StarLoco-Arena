/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class aLO
extends vi_2
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient int[] aiN;

    public aLO() {
    }

    public aLO(int n2) {
        super(n2);
    }

    public aLO(int n2, float f) {
        super(n2, f);
    }

    public aLO(ajd_1 ajd_12) {
        super(ajd_12);
    }

    public aLO(int n2, ajd_1 ajd_12) {
        super(n2, ajd_12);
    }

    public aLO(int n2, float f, ajd_1 ajd_12) {
        super(n2, f, ajd_12);
    }

    public Object clone() {
        aLO aLO2 = (aLO)super.clone();
        aLO2.aiN = (int[])this.aiN.clone();
        return aLO2;
    }

    public Ib aWE() {
        return new Ib(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.aiN = new int[n3];
        return n3;
    }

    public int m(long l2, int n2) {
        int n3 = 0;
        int n4 = this.aO(l2);
        boolean bl2 = true;
        if (n4 < 0) {
            n4 = -n4 - 1;
            n3 = this.aiN[n4];
            bl2 = false;
        }
        byte by = this.bCp[n4];
        this.aty[n4] = l2;
        this.bCp[n4] = 1;
        this.aiN[n4] = n2;
        if (bl2) {
            this.Z(by == 0);
        }
        return n3;
    }

    protected void rehash(int n2) {
        int n3 = this.aty.length;
        long[] lArray = this.aty;
        int[] nArray = this.aiN;
        byte[] byArray = this.bCp;
        this.aty = new long[n2];
        this.aiN = new int[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            long l2 = lArray[n4];
            int n5 = this.aO(l2);
            this.aty[n5] = l2;
            this.aiN[n5] = nArray[n4];
            this.bCp[n5] = 1;
        }
    }

    public int eL(long l2) {
        int n2 = this.az(l2);
        return n2 < 0 ? 0 : this.aiN[n2];
    }

    public void clear() {
        super.clear();
        long[] lArray = this.aty;
        int[] nArray = this.aiN;
        byte[] byArray = this.bCp;
        int n2 = lArray.length;
        while (n2-- > 0) {
            lArray[n2] = 0L;
            nArray[n2] = 0;
            byArray[n2] = 0;
        }
    }

    public int eM(long l2) {
        int n2 = 0;
        int n3 = this.az(l2);
        if (n3 >= 0) {
            n2 = this.aiN[n3];
            this.O(n3);
        }
        return n2;
    }

    public boolean equals(Object object) {
        if (!(object instanceof aLO)) {
            return false;
        }
        aLO aLO2 = (aLO)object;
        if (aLO2.size() != this.size()) {
            return false;
        }
        return this.a(new atm_0(aLO2));
    }

    public int hashCode() {
        aep_2 aep_22 = new aep_2(this, null);
        this.a(aep_22);
        return aep_22.dY();
    }

    protected void O(int n2) {
        this.aiN[n2] = 0;
        super.O(n2);
    }

    public int[] yb() {
        int[] nArray = new int[this.size()];
        int[] nArray2 = this.aiN;
        byte[] byArray = this.bCp;
        int n2 = nArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            nArray[n3++] = nArray2[n2];
        }
        return nArray;
    }

    public long[] eJ() {
        long[] lArray = new long[this.size()];
        long[] lArray2 = this.aty;
        byte[] byArray = this.bCp;
        int n2 = lArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            lArray[n3++] = lArray2[n2];
        }
        return lArray;
    }

    public long[] h(long[] lArray) {
        int n2 = this.size();
        if (lArray.length < n2) {
            lArray = (long[])Array.newInstance(lArray.getClass().getComponentType(), n2);
        }
        long[] lArray2 = this.aty;
        int n3 = lArray2.length;
        int n4 = 0;
        while (n3-- > 0) {
            if (lArray2[n3] == 0L || lArray2[n3] == 2L) continue;
            lArray[n4++] = lArray2[n3];
        }
        return lArray;
    }

    public boolean dy(int n2) {
        byte[] byArray = this.bCp;
        int[] nArray = this.aiN;
        int n3 = nArray.length;
        while (n3-- > 0) {
            if (byArray[n3] != 1 || n2 != nArray[n3]) continue;
            return true;
        }
        return false;
    }

    public boolean v(long l2) {
        return this.m(l2);
    }

    public boolean a(px_1 px_12) {
        return this.b(px_12);
    }

    public boolean f(aLR aLR2) {
        byte[] byArray = this.bCp;
        int[] nArray = this.aiN;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || aLR2.eG(nArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean a(uu_1 uu_12) {
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        int[] nArray = this.aiN;
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || uu_12.i(lArray[n2], nArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(uu_1 uu_12) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        int[] nArray = this.aiN;
        this.pf();
        try {
            int n2 = lArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || uu_12.i(lArray[n2], nArray[n2])) continue;
                this.O(n2);
                bl2 = true;
            }
        }
        finally {
            this.Y(true);
        }
        return bl2;
    }

    public void a(aMV aMV2) {
        byte[] byArray = this.bCp;
        int[] nArray = this.aiN;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            nArray[n2] = aMV2.pu(nArray[n2]);
        }
    }

    public boolean bW(long l2) {
        return this.n(l2, 1);
    }

    public boolean n(long l2, int n2) {
        int n3 = this.az(l2);
        if (n3 < 0) {
            return false;
        }
        int n4 = n3;
        this.aiN[n4] = this.aiN[n4] + n2;
        return true;
    }

    public int c(long l2, int n2, int n3) {
        boolean bl2;
        int n4;
        int n5 = this.aO(l2);
        if (n5 < 0) {
            int n6 = n5 = -n5 - 1;
            int n7 = this.aiN[n6] + n2;
            this.aiN[n6] = n7;
            n4 = n7;
            bl2 = false;
        } else {
            n4 = this.aiN[n5] = n3;
            bl2 = true;
        }
        byte by = this.bCp[n5];
        this.aty[n5] = l2;
        this.bCp[n5] = 1;
        if (bl2) {
            this.Z(by == 0);
        }
        return n4;
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this._size);
        atr atr2 = new atr(objectOutput);
        if (!this.a(atr2)) {
            throw atr2.cTR;
        }
    }

    public void readExternal(ObjectInput objectInput) {
        objectInput.readByte();
        int n2 = objectInput.readInt();
        this.N(n2);
        while (n2-- > 0) {
            long l2 = objectInput.readLong();
            int n3 = objectInput.readInt();
            this.m(l2, n3);
        }
    }
}

