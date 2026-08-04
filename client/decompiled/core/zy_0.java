/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/*
 * Renamed from zy
 */
public class zy_0
extends ws_2
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient byte[] aFu;

    public zy_0() {
    }

    public zy_0(int n2) {
        super(n2);
    }

    public zy_0(int n2, float f) {
        super(n2, f);
    }

    public zy_0(alo_0 alo_02) {
        super(alo_02);
    }

    public zy_0(int n2, alo_0 alo_02) {
        super(n2, alo_02);
    }

    public zy_0(int n2, float f, alo_0 alo_02) {
        super(n2, f, alo_02);
    }

    public Object clone() {
        zy_0 zy_02 = (zy_0)super.clone();
        zy_02.aFu = (byte[])this.aFu.clone();
        return zy_02;
    }

    public hn_1 GD() {
        return new hn_1(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.aFu = new byte[n3];
        return n3;
    }

    public byte e(byte by, byte by2) {
        byte by3 = 0;
        int n2 = this.E(by);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            by3 = this.aFu[n2];
            bl2 = false;
        }
        byte by4 = this.bCp[n2];
        this.auE[n2] = by;
        this.bCp[n2] = 1;
        this.aFu[n2] = by2;
        if (bl2) {
            this.Z(by4 == 0);
        }
        return by3;
    }

    protected void rehash(int n2) {
        int n3 = this.auE.length;
        byte[] byArray = this.auE;
        byte[] byArray2 = this.aFu;
        byte[] byArray3 = this.bCp;
        this.auE = new byte[n2];
        this.aFu = new byte[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray3[n4] != 1) continue;
            byte by = byArray[n4];
            int n5 = this.E(by);
            this.auE[n5] = by;
            this.aFu[n5] = byArray2[n4];
            this.bCp[n5] = 1;
        }
    }

    public byte H(byte by) {
        int n2 = this.D(by);
        return n2 < 0 ? (byte)0 : this.aFu[n2];
    }

    public void clear() {
        super.clear();
        byte[] byArray = this.auE;
        byte[] byArray2 = this.aFu;
        byte[] byArray3 = this.bCp;
        int n2 = byArray.length;
        while (n2-- > 0) {
            byArray[n2] = 0;
            byArray2[n2] = 0;
            byArray3[n2] = 0;
        }
    }

    public byte I(byte by) {
        byte by2 = 0;
        int n2 = this.D(by);
        if (n2 >= 0) {
            by2 = this.aFu[n2];
            this.O(n2);
        }
        return by2;
    }

    public boolean equals(Object object) {
        if (!(object instanceof zy_0)) {
            return false;
        }
        zy_0 zy_02 = (zy_0)object;
        if (zy_02.size() != this.size()) {
            return false;
        }
        return this.a(new zu_0(zy_02));
    }

    public int hashCode() {
        hx_0 hx_02 = new hx_0(this, null);
        this.a(hx_02);
        return hx_02.dY();
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

    public byte[] GF() {
        byte[] byArray = new byte[this.size()];
        byte[] byArray2 = this.auE;
        byte[] byArray3 = this.bCp;
        int n2 = byArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray3[n2] != 1) continue;
            byArray[n3++] = byArray2[n2];
        }
        return byArray;
    }

    public byte[] B(byte[] byArray) {
        int n2 = this.size();
        if (byArray.length < n2) {
            byArray = (byte[])Array.newInstance(byArray.getClass().getComponentType(), n2);
        }
        byte[] byArray2 = this.auE;
        int n3 = byArray2.length;
        int n4 = 0;
        while (n3-- > 0) {
            if (byArray2[n3] == 0 || byArray2[n3] == 2) continue;
            byArray[n4++] = byArray2[n3];
        }
        return byArray;
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

    public boolean K(byte by) {
        return this.contains(by);
    }

    public boolean b(amm_2 amm_22) {
        return this.a(amm_22);
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

    public boolean a(st_1 st_12) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.auE;
        byte[] byArray3 = this.aFu;
        int n2 = byArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || st_12.d(byArray2[n2], byArray3[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(st_1 st_12) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.auE;
        byte[] byArray3 = this.aFu;
        this.pf();
        try {
            int n2 = byArray2.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || st_12.d(byArray2[n2], byArray3[n2])) continue;
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

    public boolean L(byte by) {
        return this.f(by, (byte)1);
    }

    public boolean f(byte by, byte by2) {
        int n2 = this.D(by);
        if (n2 < 0) {
            return false;
        }
        int n3 = n2;
        this.aFu[n3] = (byte)(this.aFu[n3] + by2);
        return true;
    }

    public byte a(byte by, byte by2, byte by3) {
        boolean bl2;
        byte by4;
        int n2 = this.E(by);
        if (n2 < 0) {
            int n3 = n2 = -n2 - 1;
            byte by5 = (byte)(this.aFu[n3] + by2);
            this.aFu[n3] = by5;
            by4 = by5;
            bl2 = false;
        } else {
            by4 = this.aFu[n2] = by3;
            bl2 = true;
        }
        byte by6 = this.bCp[n2];
        this.auE[n2] = by;
        this.bCp[n2] = 1;
        if (bl2) {
            this.Z(by6 == 0);
        }
        return by4;
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
            byte by = objectInput.readByte();
            byte by2 = objectInput.readByte();
            this.e(by, by2);
        }
    }
}

