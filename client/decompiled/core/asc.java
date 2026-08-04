/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class asc
extends aMP
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient byte[] aFu;

    public asc() {
    }

    public asc(int n2) {
        super(n2);
    }

    public asc(int n2, float f) {
        super(n2, f);
    }

    public asc(ui_0 ui_02) {
        super(ui_02);
    }

    public asc(int n2, ui_0 ui_02) {
        super(n2, ui_02);
    }

    public asc(int n2, float f, ui_0 ui_02) {
        super(n2, f, ui_02);
    }

    public Object clone() {
        asc asc2 = (asc)super.clone();
        asc2.aFu = (byte[])this.aFu.clone();
        return asc2;
    }

    public asb_0 aFb() {
        return new asb_0(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.aFu = new byte[n3];
        return n3;
    }

    public byte d(int n2, byte by) {
        byte by2 = 0;
        int n3 = this.pr(n2);
        boolean bl2 = true;
        if (n3 < 0) {
            n3 = -n3 - 1;
            by2 = this.aFu[n3];
            bl2 = false;
        }
        byte by3 = this.bCp[n3];
        this.dYH[n3] = n2;
        this.bCp[n3] = 1;
        this.aFu[n3] = by;
        if (bl2) {
            this.Z(by3 == 0);
        }
        return by2;
    }

    protected void rehash(int n2) {
        int n3 = this.dYH.length;
        int[] nArray = this.dYH;
        byte[] byArray = this.aFu;
        byte[] byArray2 = this.bCp;
        this.dYH = new int[n2];
        this.aFu = new byte[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray2[n4] != 1) continue;
            int n5 = nArray[n4];
            int n6 = this.pr(n5);
            this.dYH[n6] = n5;
            this.aFu[n6] = byArray[n4];
            this.bCp[n6] = 1;
        }
    }

    public byte get(int n2) {
        int n3 = this.hJ(n2);
        return n3 < 0 ? (byte)0 : this.aFu[n3];
    }

    public void clear() {
        super.clear();
        int[] nArray = this.dYH;
        byte[] byArray = this.aFu;
        byte[] byArray2 = this.bCp;
        int n2 = nArray.length;
        while (n2-- > 0) {
            nArray[n2] = 0;
            byArray[n2] = 0;
            byArray2[n2] = 0;
        }
    }

    public byte lX(int n2) {
        byte by = 0;
        int n3 = this.hJ(n2);
        if (n3 >= 0) {
            by = this.aFu[n3];
            this.O(n3);
        }
        return by;
    }

    public boolean equals(Object object) {
        if (!(object instanceof asc)) {
            return false;
        }
        asc asc2 = (asc)object;
        if (asc2.size() != this.size()) {
            return false;
        }
        return this.a(new aOt(asc2));
    }

    public int hashCode() {
        HP hP = new HP(this, null);
        this.a(hP);
        return hP.dY();
    }

    protected void O(int n2) {
        this.aFu[n2] = 0;
        super.O(n2);
    }

    public byte[] GE() {
        byte[] byArray = new byte[this.size()];
        byte[] byArray2 = this.aFu;
        byte[] byArray3 = this.bCp;
        int n2 = byArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray3[n2] != 1) continue;
            byArray[n3++] = byArray2[n2];
        }
        return byArray;
    }

    public int[] pL() {
        int[] nArray = new int[this.size()];
        int[] nArray2 = this.dYH;
        byte[] byArray = this.bCp;
        int n2 = nArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            nArray[n3++] = nArray2[n2];
        }
        return nArray;
    }

    public int[] h(int[] nArray) {
        int n2 = this.size();
        if (nArray.length < n2) {
            nArray = (int[])Array.newInstance(nArray.getClass().getComponentType(), n2);
        }
        int[] nArray2 = this.dYH;
        int n3 = nArray2.length;
        int n4 = 0;
        while (n3-- > 0) {
            if (nArray2[n3] == 0 || nArray2[n3] == 2) continue;
            nArray[n4++] = nArray2[n3];
        }
        return nArray;
    }

    public boolean J(byte by) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.aFu;
        int n2 = byArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || by != byArray2[n2]) continue;
            return true;
        }
        return false;
    }

    public boolean bY(int n2) {
        return this.contains(n2);
    }

    public boolean e(aLR aLR2) {
        return this.a(aLR2);
    }

    public boolean c(amm_2 amm_22) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.aFu;
        int n2 = byArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || amm_22.aH(byArray2[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean a(di_2 di_22) {
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        byte[] byArray2 = this.aFu;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || di_22.c(nArray[n2], byArray2[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(di_2 di_22) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        byte[] byArray2 = this.aFu;
        this.pf();
        try {
            int n2 = nArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || di_22.c(nArray[n2], byArray2[n2])) continue;
                this.O(n2);
                bl2 = true;
            }
        }
        finally {
            this.Y(true);
        }
        return bl2;
    }

    public void a(aqI aqI2) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.aFu;
        int n2 = byArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            byArray2[n2] = aqI2.aQ(byArray2[n2]);
        }
    }

    public boolean cm(int n2) {
        return this.e(n2, (byte)1);
    }

    public boolean e(int n2, byte by) {
        int n3 = this.hJ(n2);
        if (n3 < 0) {
            return false;
        }
        int n4 = n3;
        this.aFu[n4] = (byte)(this.aFu[n4] + by);
        return true;
    }

    public byte a(int n2, byte by, byte by2) {
        boolean bl2;
        byte by3;
        int n3 = this.pr(n2);
        if (n3 < 0) {
            int n4 = n3 = -n3 - 1;
            byte by4 = (byte)(this.aFu[n4] + by);
            this.aFu[n4] = by4;
            by3 = by4;
            bl2 = false;
        } else {
            by3 = this.aFu[n3] = by2;
            bl2 = true;
        }
        byte by5 = this.bCp[n3];
        this.dYH[n3] = n2;
        this.bCp[n3] = 1;
        if (bl2) {
            this.Z(by5 == 0);
        }
        return by3;
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
            int n3 = objectInput.readInt();
            byte by = objectInput.readByte();
            this.d(n3, by);
        }
    }
}

